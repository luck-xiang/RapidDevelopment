package com.kxiang.quick;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kexiang.function.view.banner.BannersView;
import com.kexiang.function.view.recycleview.OnRecycleItemClickListener;
import com.kexiang.function.view.recycleview.RecycleDividerItemLinear;
import com.kxiang.quick.base.BaseActivity;
import com.kxiang.quick.bean.ClassBean;
import com.kxiang.quick.dbtest.DBTestActivity;
import com.kxiang.quick.function.activity.CalanderActivity;
import com.kxiang.quick.function.activity.KeyboardActivity;
import com.kxiang.quick.function.activity.MemoryActivity;
import com.kxiang.quick.function.activity.RadioCheckActivity;
import com.kxiang.quick.function.activity.RefreshLoadingActivity;
import com.kxiang.quick.function.activity.SQLiteActivity;
import com.kxiang.quick.function.activity.SmallToolsActivity;
import com.kxiang.quick.function.activity.SocketActivity;
import com.kxiang.quick.function.adapter.MainAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quick_activity_main);
        initView();
    }
    @Override
    protected void initView() {
        initStatusBarColor(R.color.color_no);
        registerInternet();
        initRecycle();
        initTitleBar();
        initBanner();
    }



    RelativeLayout rl_title_bar;

    private void initTitleBar() {
        rl_title_bar = (RelativeLayout) findViewById(R.id.rl_title_bar);
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams)
                rl_title_bar.getLayoutParams();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) { //4.4 全透明

            /**
             * 获取状态栏高度——方法1
             * */
            int statusBarHeight1 = -1;
//获取status_bar_height资源的ID
            int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
            if (resourceId > 0) {
                //根据资源ID获取响应的尺寸值
                statusBarHeight1 = getResources().getDimensionPixelSize(resourceId);
            }
            Log.e("WangJ", "状态栏-方法1:" + statusBarHeight1);
            layoutParams.topMargin = statusBarHeight1;
            rl_title_bar.setLayoutParams(layoutParams);


        }
    }
    private BannersView banners_view;
    private int[] imageID;

    private void initBanner() {
        imageID = new int[]{
                R.drawable.banner01,
                R.drawable.banner02,
                R.drawable.banner03,

        };
        banners_view = (BannersView) findViewById(R.id.banners_view);
        banners_view.setCount(imageID.length);
        banners_view.playCarousel();
        banners_view.start();
        banners_view.setScollViewListener(new BannersView.ScollViewListener() {
            @Override
            public View setViewForPosition(final int position) {

                ImageView fruitImageView = new ImageView(getApplicationContext());
                fruitImageView.setScaleType(ImageView.ScaleType.CENTER);
                FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                fruitImageView.setLayoutParams(params);
//                fruitImageView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                    }
//                });
                fruitImageView.setBackgroundResource(imageID[position % imageID.length]);
                //Glide.with(context).load(imageUrlList.get(position)).into(fruitImageView);
                return fruitImageView;
            }
        });
    }
    private List<ClassBean> classBeanList;
    private MainAdapter allAdapter;
    private RecyclerView rlv_all;


    protected void initRecycle() {

        rlv_all = (RecyclerView) findViewById(R.id.rlv_all);
        classBeanList = new ArrayList<>();
        addData();
        allAdapter = new MainAdapter(this, classBeanList);

        rlv_all.setLayoutManager(new LinearLayoutManager(this));
        rlv_all.addItemDecoration(
                new RecycleDividerItemLinear(this,
                        LinearLayoutManager.VERTICAL,
                        getResources().getColor(R.color.refresh_dddddd)
                )
        );
        allAdapter.setOnRecycleItemSelectListener(new OnRecycleItemClickListener() {
            @Override
            public void OnRecycleItemSelect(View view, int position) {
                startActivity(new Intent(thisActivity, classBeanList.get(position).getTheClass()));
            }
        });
        rlv_all.setAdapter(allAdapter);
    }

    private void addData() {
        classBeanList.add(getClassBean("数据库测试", DBTestActivity.class));
        classBeanList.add(getClassBean("Sokcet测试", SocketActivity.class));
        classBeanList.add(getClassBean("自定义软键盘\n带删除输入框\n轮播广告条", KeyboardActivity.class));
        classBeanList.add(getClassBean("数据库操作", SQLiteActivity.class));
        classBeanList.add(getClassBean("Rxjava测试", MemoryActivity.class));
        classBeanList.add(getClassBean("小工具集合", SmallToolsActivity.class));
        classBeanList.add(getClassBean("单选复选定向选择", RadioCheckActivity.class));
        classBeanList.add(getClassBean("日历控件", CalanderActivity.class));
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
//            if (!changInternew()) {
//                showToastShort("你已开启没网状态");
//            }
//            else {
//                if (isWifi(thisActivity)) {
//                    showToastShort("现在连接的网络是wifi");
//                }
//                else {
//                    showToastShort("现在连接的网络是2G/3G/4G");
//                }
//            }


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
