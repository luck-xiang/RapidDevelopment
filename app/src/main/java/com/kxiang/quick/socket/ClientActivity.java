package com.kxiang.quick.socket;

import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.kexiang.function.utils.LogUtils;
import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseActivity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

public class ClientActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client);
        initStatusBarColor();
        initView();
    }

    @Override
    protected void initView() {
        initClient();
        initRead();
    }


    private void initRead() {
        String fileName = Environment.getExternalStorageDirectory() + "/newTemp.txt";
        LogUtils.toE(fileName);
        ReadFromFile.readFileByBytes(fileName);
        ReadFromFile.readFileByChars(fileName);
        ReadFromFile.readFileByLines(fileName);
        ReadFromFile.readFileByRandomAccess(fileName);

    }

    private TextView tv;
    private EditText et;
    private EditText IPet;
    private Handler myhandler;
    boolean readRunning = false;
    private Button btnSend;
    private Button btnStart;
    private Button btnStop;


    private void initClient() {
        tv = (TextView) findViewById(R.id.TV);
        et = (EditText) findViewById(R.id.et);
        IPet = (EditText) findViewById(R.id.IPet);
        btnSend = (Button) findViewById(R.id.btnSend);
        btnStart = (Button) findViewById(R.id.btnStart);
        btnStop = (Button) findViewById(R.id.btnStop);
        setButtonOnStartState(true);//设置按键状态为可开始连接
        btnSend.setOnClickListener(this);
        btnStart.setOnClickListener(this);
        btnStop.setOnClickListener(this);
        myhandler = new MyHandler();//实例化Handler，用于进程间的通信

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStart:
                //按下开始连接按键即开始StartThread线程
                new StartThread().start();
                //设置按键状态为不可开始连接
                setButtonOnStartState(false);
                break;
            case R.id.btnSend:
                // 发送请求数据
                OutputStream os;
                try {
                    os = socket.getOutputStream();//得到socket的输出流
                    //输出EditText里面的数据，数据最后加上换行符才可以让服务器端的readline()停止阻塞
                    os.write((et.getText().toString() + "\n").getBytes("utf-8"));
                    //发送后输入框清0
                    et.setText("");
                } catch (IOException e) {
                    registSocket();
                    e.printStackTrace();
                }

                break;
            case R.id.btnStop:
                readRunning = false;
                setButtonOnStartState(true);//设置按键状态为不可开始连接
                try {
                    socket.close();
                } catch (NullPointerException e) {
                    e.printStackTrace();
                    showToastShort("未连接成功");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                break;
        }

    }

    private void registSocket() {
//        检测是否失效
//        socket.sendUrgentData(0xff);
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    Thread.sleep(3000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                new StartThread().start();
//            }
//        }).start();


    }


    private Socket socket;

    private class StartThread extends Thread {
        @Override
        public void run() {
            try {
                //连接服务端的IP
                socket = new Socket(IPet.getText().toString(), 40012);
                //设置读取超时
                socket.setSoTimeout(3000);

                readRunning = true;
                LogUtils.toE(socket.isConnected());
                //成功连接获取socket对象则发送成功消息
                if (socket.isConnected()) {
                    Message msg0 = myhandler.obtainMessage();
                    msg0.what = 0;
                    myhandler.sendMessage(msg0);

                    //启动接收数据的线程
                    new ReceiveThread(socket).start();
                }
            } catch (IOException e) {
                e.printStackTrace();
                registSocket();
            }
        }
    }

    private class ReceiveThread extends Thread {
        private InputStream is;

        //建立构造函数来获取socket对象的输入流
        public ReceiveThread(Socket socket) throws IOException {
            is = socket.getInputStream();
        }

        @Override
        public void run() {

//            InputStreamReader isr = new InputStreamReader(is);


            while (readRunning) {
                BufferedReader br=null;
                try {

                    br = new BufferedReader(new InputStreamReader(is));
                    //读服务器端发来的数据，阻塞直到收到结束符\n或\r
//                    String str = br.readLine();
                    StringBuffer buffer = null;
                    char[] chars = new char[SocketHeaderUtils.HeaderLength];
                    if (br.read(chars) != -1) {
                        String header = new String(chars);
                        LogUtils.toE("strheader:" + header);
                        if (header.length() == SocketHeaderUtils.HeaderLength) {
                            buffer = new StringBuffer();
                            buffer.append(header);
                            long readStringLength = Long.parseLong(header);
                            long readingLength = 0;
                            int tempInt;
                            while ((tempInt = br.read()) != -1) {
                                readingLength++;
                                buffer.append((char) tempInt);
                                LogUtils.toE("strchar:" + (char) tempInt);
                                if (readingLength >= readStringLength) {
                                    break;
                                }
                            }
                        }
                    }


                    if (buffer != null) {
                        String str = buffer.toString().substring(
                                SocketHeaderUtils.HeaderLength,
                                buffer.length()
                        );
                        LogUtils.toE("readLine:" + str);
                        //用Handler把读取到的信息发到主线程
                        Message msg = myhandler.obtainMessage();
                        msg.what = 1;
                        msg.obj = str;
                        myhandler.sendMessage(msg);
                    }
                    else {
                        readRunning = false;//防止服务器端关闭导致客户端读到空指针而导致程序崩溃
                        Message msg2 = myhandler.obtainMessage();
                        msg2.what = 2;
                        myhandler.sendMessage(msg2);//发送信息通知用户客户端已关闭
                        registSocket();
                    }


                } catch (IOException e) {
                    //连接超时后会抛异常
                    LogUtils.toNetError(e);
                    e.printStackTrace();
                }finally {
//                    if (br!=null){
//                        try {
//                            br.close();
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                        }
//                    }
                }
                //执行下一次循环
                LogUtils.toE("执行下一次循环:" + "下一次循环开始");
            }
        }
    }


    private void setButtonOnStartState(boolean flag) {//设置按钮的状态
        btnSend.setEnabled(!flag);
        btnStop.setEnabled(!flag);
        btnStart.setEnabled(flag);
        IPet.setEnabled(flag);
    }


    class MyHandler extends Handler {//在主线程处理Handler传回来的message

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    showToastShort("连接成功");
                    break;
                case 1:
                    String str = (String) msg.obj;
                    System.out.println(msg.obj);
                    tv.setText(str);//把读到的内容更新到UI
                    break;
                case 2:
                    showToastShort("服务器端已断开,重新连接中");
                    tv.setText(null);
                    setButtonOnStartState(true);//设置按键状态为可开始
                    break;

            }

        }
    }
}
