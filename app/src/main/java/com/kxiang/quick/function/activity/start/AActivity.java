package com.kxiang.quick.function.activity.start;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseActivity;

public class AActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 跳转到B
     */
    private Button btn_1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a);
        initView();
    }

    @Override
    protected void initView() {

        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_1.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.btn_1:
                if (android.os.Build.VERSION.SDK_INT >
                        Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(new Intent(thisActivity, BActivity.class),
                            ActivityOptions.makeSceneTransitionAnimation(this)
                                    .toBundle()
                    );
                }

                break;
        }
    }
}
