package com.kxiang.quick.socket.chat;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.google.gson.Gson;
import com.kexiang.function.utils.LogUtils;
import com.kexiang.function.view.OnServiceBackListener;
import com.kxiang.quick.socket.SocketHeaderUtils;
import com.kxiang.quick.socket.chat.bean.LoginBean;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/4/5 9:15
 */

public class ClientUtilsService extends Service {

    public class BindBinder extends Binder {
        public ClientUtilsService getService() {
            return ClientUtilsService.this;
        }
    }

    private final IBinder mBinder = new BindBinder();

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private OnServiceBackListener<String> onServiceBackListener;


    public void setOnServiceBackListener(OnServiceBackListener<String> onServiceBackListener) {
        this.onServiceBackListener = onServiceBackListener;
    }

    Gson gson = new Gson();
    private String ip;
    private int port;
    private String name;
    public ClientUtilsService initClientService(String ip, int port, String name) {
        this.ip = ip;
        this.port = port;
        this.name = name;
        return this;
    }
    public void start() {
        new StartThread().start();
    }

    private Socket socket;

    public class StartThread extends Thread {
        @Override
        public void run() {
            try {
                //连接服务端的IP
                socket = new Socket(ip, port);
                //设置读取超时
                socket.setSoTimeout(3000);
                LogUtils.toE("isConnected",socket.isConnected());
                //成功连接获取socket对象则发送成功消息
                if (socket.isConnected()) {

                    // 发送请求数据
                    //得到socket的输出流
                    OutputStream os = socket.getOutputStream();
                    //输出EditText里面的数据，数据最后加上换行符才可以让服务器端的readline()停止阻塞

                    LoginBean loginBean = new LoginBean();
                    loginBean.setName(name);
                    String input = SocketHeaderUtils
                            .getHeader(
                                    SocketHeaderUtils.LOGIN,
                                    gson.toJson(loginBean)

                            );

                    os.write(input.getBytes("utf-8"));
                    //发送后输入框清0

                    //启动接收数据的线程
                    new ReceiveThread(socket).start();
                }
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }

    /*
   * 将给定的消息转发给私聊的客户端
   */
    public synchronized void sendMsgToPrivate(String message) throws IOException {
        //将对应客户端的聊天信息取出作为私聊内容发送出去
        OutputStream out = socket.getOutputStream();
        if (out != null) {
            LogUtils.toE("ClientUtilsServicesendMsgToPrivate",message);
            out.write(message.getBytes("utf-8"));

        }
    }

    private class ReceiveThread extends Thread {
        private InputStream is;
        private boolean readRunning = false;
        //建立构造函数来获取socket对象的输入流
        public ReceiveThread(Socket socket) throws IOException {
            is = socket.getInputStream();
        }

        @Override
        public void run() {
            readRunning = true;
//            InputStreamReader isr = new InputStreamReader(is);


            while (readRunning) {

                try {

                    BufferedReader br = new BufferedReader(new InputStreamReader(is));
                    //读服务器端发来的数据，阻塞直到收到结束符\n或\r
                    StringBuffer buffer = null;
                    char[] chars = new char[SocketHeaderUtils.HeaderLength];
                    if (br.read(chars) != -1) {
                        String header = new String(chars);
                        LogUtils.toE("ClientUtilsServicestrheader:" + header);
                        if (header.length() == SocketHeaderUtils.HeaderLength) {
                            buffer = new StringBuffer();
                            buffer.append(header);
                            long readStringLength = Long.parseLong(header.substring(
                                    SocketHeaderUtils.HeaderLengthStart,
                                    SocketHeaderUtils.HeaderLength));
                            long readingLength = 0;
                            int tempInt;
                            while ((tempInt = br.read()) != -1) {
                                readingLength++;
                                buffer.append((char) tempInt);
                                LogUtils.toE("ClientUtilsServicestrchar:" + (char) tempInt);
                                if (readingLength >= readStringLength) {
                                    break;
                                }
                            }
                        }
                    }


                    if (buffer != null) {
                        String str = buffer.toString();
                        LogUtils.toE("ClientUtilsServicereadLine:" + str);
                        onServiceBackListener.OnServiceBack(str);

                        //用Handler把读取到的信息发到主线程

                    }
                    else {
                        readRunning = false;//防止服务器端关闭导致客户端读到空指针而导致程序崩溃
                    }


                } catch (IOException e) {
                    //连接超时后会抛异常
                    LogUtils.toNetError(e);
                    e.printStackTrace();
                } finally {
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

}
