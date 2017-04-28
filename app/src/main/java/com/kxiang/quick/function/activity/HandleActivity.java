package com.kxiang.quick.function.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kexiang.function.utils.LogUtils;
import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseActivity;

public class HandleActivity extends BaseActivity implements View.OnClickListener {

    private Button btn_1;

    private Thread1 thread1;
    private Thread thread2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handle);
        title_bar.setRightName("你试一试");
        title_bar.setTitleName("标题哦");
        title_bar.right.setVisibility(View.INVISIBLE);
        initStatusBarColor();
        initView();

        LogUtils.toE("handle", Looper.myLooper().getClass().getName());
        LogUtils.toE("handleThread", Thread.currentThread().getName());

        thread1 = new Thread1(new Runnable() {
            @Override
            public void run() {

//                for (;;){
//
//                }
            }
        });
        thread1.start();
    }

    public class Thread1 extends Thread {

        public Thread1(Runnable target) {
            super(target);
        }

        public void log() {
            LogUtils.toE("thread1", "thread1");
        }
    }


    Handler handler = new Handler(Looper.myLooper()) {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            LogUtils.toE("handleMessage", Thread.currentThread().getName());

        }
    };

    @Override
    protected void initView() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                Message message = handler.obtainMessage();
                message.arg1 = 1;
                handler.sendMessage(message);
//                LogUtils.toE("handle", Looper.myLooper().getClass().getName());
                LogUtils.toE("handleRun", Thread.currentThread().getName());
            }
        }).start();
        iv_title_left = (ImageView) findViewById(R.id.iv_title_left);
        tv_title_name = (TextView) findViewById(R.id.tv_title_name);
        tv_title_right = (TextView) findViewById(R.id.tv_title_right);
        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                thread2 = new Thread(new Runnable() {
                    @Override
                    public void run() {


                        thread1.log();

                    }


                });
                thread2.start();
                break;
        }
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }
}
