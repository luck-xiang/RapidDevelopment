package com.kxiang.quick.utils.socket;

import android.net.ParseException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;


public class Connection {
	private Socket socket;
	private OutputStream out;
	public InputStream in ;
	private long lastActTime = 0;
	private ConnectionCallBack mCallback;
	public  interface ConnectionCallBack{
		public void doCmd(String ret);
		public void doNewConnect();
		public  void uploadStream();//上传流水
	}
	Connection(String host, int port, ConnectionCallBack callback) throws IOException {
		socket = new Socket();
		socket.connect(new InetSocketAddress(host,port),3000); //3秒连接超时
		in =socket.getInputStream();
		out = socket.getOutputStream();
		mCallback = callback;
	}
	
	
	
	private void sendBytes(byte[] data) throws IOException {
		lastActTime = System.currentTimeMillis();
		out.write(data);
		out.flush();
	}
	
	public synchronized void send(byte[] data) throws IOException, ParseException {
		sendBytes(data);
	}
	
	public synchronized void close() throws IOException {
		lastActTime = System.currentTimeMillis();
		if(socket!=null)socket.close();
		if(in!=null)in.close();
		if(out!=null)out.close();
	}
	
	public synchronized long getLastActTime(){
		return lastActTime;
	}
}