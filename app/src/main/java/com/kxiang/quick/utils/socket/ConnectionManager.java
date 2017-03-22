package com.kxiang.quick.utils.socket;

import android.net.ParseException;
import android.util.Log;

import com.kexiang.function.utils.LogUtils;
import com.kxiang.quick.utils.HEX;

import java.io.IOException;




/**
 * socket心跳包,故障重连
 * <p>
 * 创建时间：2016-8-20
 * @author 
 * HeShuai
 */
public class ConnectionManager {
	/**
	 * 心跳周期（单位：毫秒）
	 */
	private volatile static long activeCycle = 3000; //3秒发送一次心跳
	private static Connection.ConnectionCallBack mCallback;
	private Connection mCon;	
	private String mhost;
	private int mport;
	private boolean running = true;
	private String mDeviceId;
	
	
	private String[] ips;
	private String[] clientMask;
	private String[] clientGetway;
	private String[] serverips;
	private String[] clientMac;
	private ReadThread readThread;
	private SendHearBeat heart;
	private HeartCountThread heartCountThread;
	private boolean isReConnect = false;
	private int heartSendCount = 0;
	
	public  ConnectionManager(String host, int port, String deviceId, String clientIp, Connection.ConnectionCallBack Callback){
		mhost = host;
		mport = port;
		mDeviceId = deviceId;
		mCallback = Callback;
		ips = clientIp.split("\\.");
	}

	
	public  synchronized Connection createConnection() throws IOException {
		Connection conn = new Connection(mhost,mport,mCallback);
		if(conn!=null)
		{
			LogUtils.toE("createConnection");
			mCon = conn;
			openReadThread(); //开启读线程
			initSend();	 //开启发送线程
			SendHearFuc(); //开启心跳包
			SendHearCountFuc();//开启心跳计数线程
			mCallback.uploadStream(); // 检查是否有流水
		}
		return mCon;
	}
	
	//开启心跳线程
    public void SendHearFuc()
    {	
    	if(heart == null)
		{
    		heart = new SendHearBeat();
    		heart.start();
		}
		else if(!heart.isAlive())
		{
			heart.start();
		}
    }




	//开启心跳计数线程
	public void SendHearCountFuc()
	{
		if(heartCountThread == null)
		{
			heartCountThread = new HeartCountThread();
			heartCountThread.start();
		}
		else if(!heartCountThread.isAlive())
		{
			heartCountThread.start();
		}
	}

	//心跳计数线程
	public class HeartCountThread extends Thread {
		@Override
		public void run() {
			while(running){
				try {
					sleep(3000);
				} catch (InterruptedException e) {
					e.printStackTrace();
					doExp();
				}
				Log.i("心跳计数线程","当前计数"+heartSendCount);
				//5次心跳没返回数据,就重新开始(用于防止socket read 阻塞)
				if(heartSendCount > 5){
					running = false; //停止线程
					doExp();
				}
			}
		}
	}
	//心跳线程,只在这里面做异常重启
	public class SendHearBeat extends Thread
	{
		@Override
		public void run() {
			while(running){
				long time = System.currentTimeMillis();
				try { 
					   if(mCon != null)
		    	       {
							if(mCon.getLastActTime()+activeCycle<time) //3秒发送一次心跳包
							{
						    	if(mCon == null)
						    	{
						    		 continue;
						    	}
								String cmd = SocketUtil.getCmd("1D", "10101010", mDeviceId);
								Log.i("send心跳", cmd);
								mCon.send(HEX.hexToBytes(cmd));
								heartSendCount++;//发送一次,增加一次
							}
		    	       }
					} catch (IOException e) {
							running = false; //停止线程
							doExp();
					} catch (ParseException e) {
							running = false; //停止线程
							doExp();
					} catch (Exception e) {
							running = false; //停止线程
							doExp();
					}
			   }
		  }
	}
    //开启读线程
	public void openReadThread()
	{
		if(readThread == null)
		{
			readThread = new ReadThread();
			readThread.start();
		}
		else if(!readThread.isAlive())
		{
			readThread.start();
		}
	}
	public class ReadThread extends Thread {
		public void run(){
			while(running){
				try {
					byte [] header = new byte[10];
					if(mCon!=null && mCon.in.read(header)!=10) //没读到头就循环
					{
						try {
							sleep(3000);
						} catch (InterruptedException e) {
							e.printStackTrace();
							doExp();
						}
						continue;
					}
					//收到数据,就赋值为0重新开始计数
					heartSendCount = 0;
					String headerStr = HEX.bytesToHex(header);
					String content_len = headerStr.substring(2,6);
					content_len =  content_len.replaceFirst("^0*", "");
					int  len = Integer.parseInt(content_len,16);
					byte [] cache = new byte [len+10];
					System.arraycopy(header, 0, cache, 0, header.length);
					if(mCon.in.read(cache, header.length, len)!=len)
						throw new IOException("未能读取完整的包体部分");
					String all = HEX.bytesToHex(cache);
					//回调回去
					mCallback.doCmd(all);
					Log.i("recv", all);
				} catch (IOException e) { //IO异常重启
					e.printStackTrace();
					doExp();
				}  catch (Exception e) { //防止空指针
					e.printStackTrace();
					doExp();
				}
			}
		}
	}
	public  void send(String data)
	{
		 try {
			 if(mCon == null)
			 {
			 }
			 else
			 {
				 mCon.send(HEX.hexToBytes(data)); 
			 }
		} catch (IOException e) {
			e.printStackTrace();
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	//异常处理,重新开启连接
	public synchronized void doExp() //同步方法
	{
		 if(isReConnect == false) //是否重新连接过
		 {
			 running = false; //关闭所以子线程
			 isReConnect = true;
			 heartSendCount = 0; //赋值为0
			 mCallback.doNewConnect(); //新建连接
		 }
	}
	//发送初始化
	public void initSend()
	{
		Log.i("initSend", "initSend");
		clientMask = new String[]{"192","168","2","139"};
		clientGetway = new String[]{"255","255","255","0"};
		clientMac = new String[]{"AA","AA","AA","AA","AA","AA"};
		serverips = mhost.split("\\.");
		String order = "221122"
				+clientMac[0]
				+clientMac[1]
				+clientMac[2]
				+clientMac[3]
				+clientMac[4]
				+clientMac[5]
				+ Integer.toHexString(mport)
				+HEX.toHexString(Integer.toHexString(Integer.parseInt(ips[0])))
				+HEX.toHexString(Integer.toHexString(Integer.parseInt(ips[1])))
				+HEX.toHexString(Integer.toHexString(Integer.parseInt(ips[2])))
				+HEX.toHexString(Integer.toHexString(Integer.parseInt(ips[3])))
				+HEX.toHexString(Integer.toHexString(Integer.parseInt(clientMask[0])))
				+HEX.toHexString(Integer.toHexString(Integer.parseInt(clientMask[1])))
				+HEX.toHexString(Integer.toHexString(Integer.parseInt(clientMask[2])))
				+HEX.toHexString(Integer.toHexString(Integer.parseInt(clientMask[3])))
				+HEX.toHexString(Integer.toHexString(Integer.parseInt(clientGetway[0])))
				+HEX.toHexString(Integer.toHexString(Integer.parseInt(clientGetway[1])))
				+HEX.toHexString(Integer.toHexString(Integer.parseInt(clientGetway[2])))
				+HEX.toHexString(Integer.toHexString(Integer.parseInt(clientGetway[3])))
				+HEX.toHexString(Integer.toHexString(Integer.parseInt(serverips[0])))
				+HEX.toHexString(Integer.toHexString(Integer.parseInt(serverips[1])))
				+HEX.toHexString(Integer.toHexString(Integer.parseInt(serverips[2])))
				+HEX.toHexString(Integer.toHexString(Integer.parseInt(serverips[3])))
				+"00000000";//4位随机数
		String cmd = SocketUtil.getCmd("0B", order, mDeviceId);
		send(cmd);
	}
}