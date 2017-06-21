package com.kxiang.quick.function.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.kexiang.function.view.own.snow.SnowFallingView;
import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseActivity;

public class SnowViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snow);
    }
    SnowFallingView snowFallingView;
    RelativeLayout relative;
    @Override
    protected void initView() {
        snowFallingView= (SnowFallingView) findViewById(R.id.snow);
        relative= (RelativeLayout) findViewById(R.id.relative);
        findViewById(R.id.btn_1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relative.removeView(snowFallingView);
            }
        });
        findViewById(R.id.btn_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                relative.addView(snowFallingView);
            }
        });

    }
}
