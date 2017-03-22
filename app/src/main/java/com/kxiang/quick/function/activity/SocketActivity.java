package com.kxiang.quick.function.activity;

import android.os.Bundle;

import com.kexiang.function.utils.LogUtils;
import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseActivity;
import com.kxiang.quick.utils.socket.Connection;
import com.kxiang.quick.utils.socket.ConnectionManager;

import java.io.IOException;
import java.net.ConnectException;

public class SocketActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_socket);
        initStatusBarColor();
        initTitle();
        initView();

    }

    private Connection con;
    //socket长连接
    public ConnectionManager connectionManager;

    @Override
    protected void initView() {
        connectionManager = new ConnectionManager(
                "192.168.2.140",
                41489,
                "1120",
                "192.168.2.139",
                connectionCallBack
        );
        initSocket(); //启动socket线程
    }


    private Connection.ConnectionCallBack connectionCallBack = new Connection.ConnectionCallBack() {
        @Override
        public void doCmd(String ret) {
            LogUtils.toE(ret);
        }

        @Override
        public void doNewConnect() {
            connectionManager = new ConnectionManager(
                    "192.168.2.140",
                    41489,
                    "1120",
                    "192.168.2.139",
                    connectionCallBack
            );
            initSocket(); //启动socket线程
        }

        @Override
        public void uploadStream() {
            LogUtils.toE("upload", "心跳检查是否数据库中有流水");
        }
    };

    //外面刷新ConnectionManager,不会内存溢出
    private void initSocket() {
        new Thread(new Runnable() {
            public void run() {
                try {
                    con = connectionManager.createConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                    con = null;
                    while (con == null) {
                        try {
                            Thread.sleep(3000); //各3秒连接下
                            con = connectionManager.createConnection();
                        } catch (ConnectException e2) {
                            e2.printStackTrace();
                        } catch (IOException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        } catch (InterruptedException e1) {
                            // TODO Auto-generated catch block
                            e1.printStackTrace();
                        }
                    }
                }
            }
        }).start();
    }
}
