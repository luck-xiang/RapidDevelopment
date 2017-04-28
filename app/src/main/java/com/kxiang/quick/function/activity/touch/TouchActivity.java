package com.kxiang.quick.function.activity.touch;

import android.os.Bundle;

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
}
