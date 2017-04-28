package com.kxiang.quick.socket.chat;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.kexiang.function.utils.LogUtils;
import com.kexiang.function.view.OnServiceBackListener;
import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseActivity;
import com.kxiang.quick.socket.SocketHeaderUtils;
import com.kxiang.quick.socket.chat.bean.LoginBean;
import com.kxiang.quick.utils.ApplicationUtils;

public class LoginActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initStatusBarColor();
        initView();
        initService();
    }

    private EditText et_name;
    private EditText et_port;
    Gson gson = new Gson();


    @Override
    protected void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        et_port = (EditText) findViewById(R.id.et_port);
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mBoundService != null)
                    mBoundService.initClientService(et_port.getText().toString(),
                            SocketHeaderUtils.PORT, et_name.getText().toString()).start();
            }
        });
        findViewById(R.id.btn_start).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent startIntent = new Intent(thisActivity, ChartUtilsService.class);
                startService(startIntent);
            }
        });
    }

    private void initService() {
        Intent startIntent = new Intent(this, ClientUtilsService.class);
        startService(startIntent);
        Intent bindIntent = new Intent(this, ClientUtilsService.class);
        bindService(bindIntent, mConnection, BIND_AUTO_CREATE);
    }

    protected ClientUtilsService mBoundService;

    //service和Activity交互的桥梁
    private ServiceConnection mConnection = new ServiceConnection() {
        public void onServiceConnected(ComponentName className, IBinder service) {
            mBoundService = ((ClientUtilsService.BindBinder) service).getService();
            LogUtils.toE("OnServiceBack");
            mBoundService.setOnServiceBackListener(new OnServiceBackListener<String>() {
                @Override
                public void OnServiceBack(String back) {

                    String titleType = back.substring(0, SocketHeaderUtils.HeaderLengthStart);
                    String content = back.substring(SocketHeaderUtils.HeaderLength, back.length());
                    if (titleType.equals(SocketHeaderUtils.LOGIN)) {
                        LoginBean loginBean = ClientChatMessageUtils.getLogin(content, gson);
                        if (loginBean.isLoginStatus()) {
                            ApplicationUtils.getApplicition(getApplication()).setUserName(
                                    et_name.getText().toString()
                            );
                            startActivity(new Intent(thisActivity, ChatMainActivity.class));
                            finish();
                        }
                    }

                    LogUtils.toE("OnServiceBack", back);
                }
            });

        }

        public void onServiceDisconnected(ComponentName className) {
            mBoundService = null;
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBoundService != null) {
            mBoundService.stopSelf();
        }
    }
}
