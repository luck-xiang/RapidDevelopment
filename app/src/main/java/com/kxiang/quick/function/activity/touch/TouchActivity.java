package com.kxiang.quick.function.activity.touch;

import android.os.Bundle;
import android.view.MotionEvent;

import com.kexiang.function.utils.LogUtils;
import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseActivity;

public class TouchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch);
        initStatusBarColor();
        initTitle();
    }

    @Override
    protected void initView() {

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtils.toE("TouchActivity","dispatchTouchEvent:"+ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.toE("TouchActivity","onTouchEvent:"+event.getAction());
        return super.onTouchEvent(event);
    }
}
