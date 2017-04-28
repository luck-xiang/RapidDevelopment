package com.kxiang.quick.function.activity;

import android.os.Bundle;
import android.widget.EditText;

import com.kexiang.function.view.KeyboardCustomUtils;
import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseActivity;

public class KeyboardCustomActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard_custom);
        initView();
    }

    private EditText et_number;
    @Override
    protected void initView() {

        et_number= (EditText) findViewById(R.id.et_number);
        KeyboardCustomUtils keyboardView=new KeyboardCustomUtils(thisActivity,et_number);
    }
}
