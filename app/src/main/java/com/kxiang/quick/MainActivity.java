package com.kxiang.quick;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.kxiang.quick.base.BaseRecycleActivity;
import com.kxiang.quick.bean.ClassBean;
import com.kxiang.quick.function.activity.CalanderActivity;
import com.kxiang.quick.function.activity.keboard.KeyboardActivity;
import com.kxiang.quick.function.activity.MemoryActivity;
import com.kxiang.quick.function.activity.OnlyRefreshActivity;
import com.kxiang.quick.function.activity.RadioCheckActivity;
import com.kxiang.quick.function.activity.RefreshLoadingActivity;
import com.kxiang.quick.function.activity.SQLiteActivity;
import com.kxiang.quick.function.activity.SmallToolsActivity;
import com.kxiang.quick.function.adapter.MainAdapter;
import com.kxiang.quick.function.view.rlv.OnRecycleItemSelectListener;
import com.kxiang.quick.function.view.rlv.decoration.RecycleItemDivider;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseRecycleActivity {


    @Override
    protected int getContentView() {
        return R.layout.quick_activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerInternet();
    }


    private List<ClassBean> classBeanList;
    private MainAdapter allAdapter;

    @Override
    protected void initRecycle() {
        classBeanList = new ArrayList<>();
        addData();
        allAdapter = new MainAdapter(this, classBeanList);
        setRecycleBase(allAdapter, new LinearLayoutManager(this),
                new RecycleItemDivider(this,
                        LinearLayoutManager.VERTICAL,
                        getResources().getColor(R.color.refresh_dddddd)));
        allAdapter.setOnRecycleItemSelectListener(new OnRecycleItemSelectListener() {
            @Override
            public void OnRecycleItemSelect(View view, int position) {
                startActivity(new Intent(thisActivity, classBeanList.get(position).getTheClass()));
            }
        });
    }

    private void addData() {
        classBeanList.add(getClassBean("自定义软键盘", KeyboardActivity.class));
        classBeanList.add(getClassBean("数据库操作", SQLiteActivity.class));
        classBeanList.add(getClassBean("Rxjava测试", MemoryActivity.class));
        classBeanList.add(getClassBean("小工具集合", SmallToolsActivity.class));
        classBeanList.add(getClassBean("单选复选定向选择", RadioCheckActivity.class));
        classBeanList.add(getClassBean("日历控件", CalanderActivity.class));
        classBeanList.add(getClassBean("只带下拉刷新功能页面", OnlyRefreshActivity.class));
        classBeanList.add(getClassBean("下拉刷新上拉加载更多功能页面", RefreshLoadingActivity.class));
    }

    private ClassBean getClassBean(String name, Class<?> theClass) {
        ClassBean bean = new ClassBean();
        bean.setName(name);
        bean.setTheClass(theClass);
        return bean;
    }

    private InternetBroadcast internetBroadcast;

    /**
     * 动态注广播监听网络变化
     */

    public void registerInternet() {
        IntentFilter intentFilter = new IntentFilter();
        internetBroadcast = new InternetBroadcast();
        intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        Log.e("tag", "Done1");
        registerReceiver(internetBroadcast, intentFilter);

    }


    public class InternetBroadcast extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            if (!changInternew()) {
                showToastShort("你已开启没网状态");
            }
            else {
                if (isWifi(thisActivity)) {
                    showToastShort("现在连接的网络是wifi");
                }
                else {
                    showToastShort("现在连接的网络是2G/3G/4G");
                }
            }


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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(internetBroadcast);
    }
}
