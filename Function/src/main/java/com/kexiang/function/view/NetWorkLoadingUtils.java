package com.kexiang.function.view;

import android.app.Activity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kxiang.function.R;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/4/13 14:11
 */

public class NetWorkLoadingUtils {

    public LinearLayout ll_network_loading;
    public ProgressBar pb_circle;
    public TextView tv_show_net;
    private boolean netWorkShow = true;

    public NetWorkLoadingUtils(Activity activity, View.OnClickListener onClickListener) {
        ll_network_loading = (LinearLayout) activity.findViewById(R.id.ll_network_loading);
        pb_circle = (ProgressBar) activity.findViewById(R.id.pb_circle);
        tv_show_net = (TextView) activity.findViewById(R.id.tv_show_net);
        ll_network_loading.setOnClickListener(onClickListener);
        ll_network_loading.setEnabled(false);
    }

    /**
     * 正在加载中
     */
    public void loading() {
        if (netWorkShow) {
            ll_network_loading.setEnabled(false);
            ll_network_loading.setVisibility(View.VISIBLE);
            pb_circle.setVisibility(View.VISIBLE);
            tv_show_net.setVisibility(View.GONE);
        }


    }

    /**
     * 点击加载
     */
    public void clickLoading() {
        if (netWorkShow) {
            ll_network_loading.setEnabled(true);
            ll_network_loading.setVisibility(View.VISIBLE);
            pb_circle.setVisibility(View.GONE);
            tv_show_net.setVisibility(View.VISIBLE);
        }

    }

    public void clickLoading(String clickName) {
        if (netWorkShow) {
            clickLoading();
            tv_show_net.setText(clickName);
        }

    }

    public void noMessage(String clickName) {
        netWorkShow = false;
        ll_network_loading.setEnabled(false);
        clickLoading();
        tv_show_net.setText("没有消息！");
    }

    public void loadingSuccess() {
        netWorkShow = false;
        ll_network_loading.setVisibility(View.GONE);
    }

}
