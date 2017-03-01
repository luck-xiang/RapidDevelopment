package com.kxiang.quick.function.activity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;

import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseActivity;

public class SmallToolsActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected int getContentView() {
        return R.layout.quick_activity_small_tools;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitle();
        tv_title_name.setText("小工具集合");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                if (isWifi(thisActivity)) {
                    showToastShort("现在连接的网络是wifi");
                }
                else {
                    showToastShort("现在连接的网络是2G/3G/4G");
                }
                break;
        }
    }

    /**
     * make true current connect service is wifi
     *
     * @param mContext
     * @return
     */
    private static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }
}
