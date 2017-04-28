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

import java.io.IOException;

public class ChatMainActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_main);
        initStatusBarColor();
        initView();
        initChartFragmen();
        initService();
    }

    private EditText et_name;
    Gson gson = new Gson();


    @Override
    protected void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        findViewById(R.id.btn_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mBoundService.sendMsgToPrivate(
                            SocketHeaderUtils.getHeader(SocketHeaderUtils.FRENDS
                                    ,et_name.getText().toString())
                    );
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private ChatFriendListFragment chatFriendListFragment;

    private void initChartFragmen() {
        chatFriendListFragment = ChatFriendListFragment.newInstance();
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_main, chatFriendListFragment)
                .commit();
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
            try {
                mBoundService.sendMsgToPrivate(SocketHeaderUtils.getHeader(SocketHeaderUtils.FRENDS,"1"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            mBoundService.setOnServiceBackListener(new OnServiceBackListener<String>() {
                @Override
                public void OnServiceBack(String back) {

                    String titleType = back.substring(0, SocketHeaderUtils.HeaderLengthStart);
                    String content = back.substring(SocketHeaderUtils.HeaderLength, back.length());
                    if (titleType.equals(SocketHeaderUtils.FRENDS)) {

                        chatFriendListFragment.setChatFriend(
                                ClientChatMessageUtils.getFriends(content)
                        );
                    }

                    LogUtils.toE("OnServiceBack", content);
                }
            });

        }

        public void onServiceDisconnected(ComponentName className) {
            mBoundService = null;
        }
    };

    public ClientUtilsService getmBoundService() {
        return mBoundService;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mBoundService != null) {
            mBoundService.stopSelf();
        }
    }

}
