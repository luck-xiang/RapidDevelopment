package com.kxiang.quick.base;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kxiang.quick.R;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/1/17 11:21
 * 没有Recycleview界面的网络显示
 */

public abstract class BaseInternetActivity extends BaseActivity {

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        initNetWorkPrompt();
    }

    protected LinearLayout ll_network_prompt;
    protected ProgressBar pb_circle;
    protected TextView tv_show_net;

    /**
     * 在没有下拉刷新界面的网络显示
     * 网络提示初始化，在有网，没网，网络连接错误或者获取数据失败后提示点击刷新
     */
    protected void initNetWorkPrompt() {
        ll_network_prompt = (LinearLayout) findViewById(R.id.ll_network_prompt);
        ll_network_prompt.setVisibility(View.VISIBLE);
        pb_circle = (ProgressBar) findViewById(R.id.pb_circle);
        tv_show_net = (TextView) findViewById(R.id.tv_show_net);
    }

}
