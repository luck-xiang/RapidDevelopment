package com.kxiang.quick.socket.chat;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.google.gson.Gson;
import com.kexiang.function.utils.LogUtils;
import com.kexiang.function.view.OnServiceBackListener;
import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseActivity;
import com.kxiang.quick.socket.SocketHeaderUtils;
import com.kxiang.quick.socket.chat.bean.ChatBean;
import com.kxiang.quick.socket.chat.bean.ChatBeanUtils;
import com.kxiang.quick.utils.ApplicationUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatListActivity extends BaseActivity {


    public final static String ReciviedName = "recivied_name";
    public static String recivied;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_list);
        initStatusBarColor();
        recivied = getIntent().getStringExtra(ReciviedName);
        initView();
    }

    private EditText et_name;

    Gson gson = new Gson();

    @Override
    protected void initView() {
        et_name = (EditText) findViewById(R.id.et_name);
        findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!TextUtils.isEmpty(et_name.getText().toString())) {
                    ChatBean chatBean = new ChatBean();
                    chatBean.setChatSingle(true);
                    chatBean.setContent(et_name.getText().toString());
                    chatBean.setReceiveName(recivied);
                    chatBean.setSendName(ApplicationUtils
                            .getApplicition(getApplication())
                            .getUserName()
                    );
                    chatBean.setShowTyep(ChatBeanUtils.RightWord);
                    try {
                        mBoundService.sendMsgToPrivate(
                                SocketHeaderUtils.getHeader(
                                        SocketHeaderUtils.CHART,
                                        gson.toJson(chatBean)
                                )
                        );
                        dataList.add(chatBean);
                        chatListAdapter.notifyDataSetChanged();
                        et_name.setText("");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        initRecycle();
        initService();
    }

    private RecyclerView rlv_all;
    private ChatListAdapter chatListAdapter;
    private List<ChatBean> dataList;

    private void initRecycle() {
        dataList = new ArrayList<>();
        chatListAdapter = new ChatListAdapter(thisActivity, dataList);
        rlv_all = (RecyclerView) findViewById(R.id.rlv_all);
//        rlv_all.addItemDecoration(
//                new RecycleDividerItemLinear(
//                        getContext()
//                        , LinearLayoutManager.VERTICAL
//                        , getResources().getColor(R.color.color_dddddd)
//                ));
        rlv_all.setLayoutManager(new LinearLayoutManager(thisActivity));
        rlv_all.setAdapter(chatListAdapter);
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

            mBoundService.setOnServiceBackListener(new OnServiceBackListener<String>() {
                @Override
                public void OnServiceBack(String back) {

                    String titleType = back.substring(0, SocketHeaderUtils.HeaderLengthStart);


                    if (titleType.equals(SocketHeaderUtils.CHART)) {
                        String content = back.substring(SocketHeaderUtils.HeaderLength,
                                back.length());
                        dataList.add(gson.fromJson(content, ChatBean.class));
                        rlv_all.post(new Runnable() {
                            @Override
                            public void run() {
                                chatListAdapter.notifyDataSetChanged();
                                LinearLayoutManager linearLayoutManager = ((LinearLayoutManager) rlv_all
                                        .getLayoutManager());
                                linearLayoutManager.scrollToPositionWithOffset(dataList.size(),10);
                            }
                        });

                        LogUtils.toE("OnServiceBack", content);
                    }


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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }
}
