package com.kxiang.quick.utils.thread;

import android.util.Log;

import com.kxiang.quick.utils.socket.ConnectionManager;
import com.kxiang.quick.utils.socket.SocketUtil;


public class SendClass extends Thread {
	private String order;
	private String orderContent;
	private String deviceId;
	private ConnectionManager connectionManager;
	public SendClass(String order, String orderContent, String deviceId, ConnectionManager conn)
	{
		this.order = order;
		this.orderContent = orderContent;
		this.deviceId = deviceId;
		connectionManager = conn;
	}
	@Override
	public void run() {
		super.run();
		String cmd = SocketUtil.getCmd(order, orderContent, deviceId);
		Log.i("cmd", cmd);
		connectionManager.send(cmd); //终端状态查询
	}	
}
