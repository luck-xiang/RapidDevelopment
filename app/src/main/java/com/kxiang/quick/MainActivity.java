package com.kxiang.quick;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kexiang.function.utils.AppVersionUtils;
import com.kexiang.function.utils.LogUtils;
import com.kexiang.function.view.banner.BannersView;
import com.kexiang.function.view.recycleview.OnRecycleItemClickListener;
import com.kexiang.function.view.recycleview.RecycleDividerItemLinears;
import com.kxiang.quick.base.BaseActivity;
import com.kxiang.quick.bean.ClassBean;
import com.kxiang.quick.dbtest.DBTestActivity;
import com.kxiang.quick.function.activity.BeautifulCalendarActivity;
import com.kxiang.quick.function.activity.CalanderActivity;
import com.kxiang.quick.function.activity.ComeBackMenuViewActivity;
import com.kxiang.quick.function.activity.CycleViewActivity;
import com.kxiang.quick.function.activity.DownLoadActivity;
import com.kxiang.quick.function.activity.HandleActivity;
import com.kxiang.quick.function.activity.KeyboardActivity;
import com.kxiang.quick.function.activity.KeyboardCustomActivity;
import com.kxiang.quick.function.activity.LeftDrawerLayoutActivity;
import com.kxiang.quick.function.activity.MaterialMainActivity;
import com.kxiang.quick.function.activity.MemoryActivity;
import com.kxiang.quick.function.activity.RadioCheckActivity;
import com.kxiang.quick.function.activity.RefreshLoadingActivity;
import com.kxiang.quick.function.activity.RxActivity;
import com.kxiang.quick.function.activity.SQLiteActivity;
import com.kxiang.quick.function.activity.ScreenActivity;
import com.kxiang.quick.function.activity.SmallToolsActivity;
import com.kxiang.quick.function.activity.SnowViewActivity;
import com.kxiang.quick.function.activity.SocketActivity;
import com.kxiang.quick.function.activity.StarPraiseActivity;
import com.kxiang.quick.function.activity.ViewDragHelperActivity;
import com.kxiang.quick.function.activity.XmlCreateScreanActivity;
import com.kxiang.quick.function.activity.erwm.QrCodeActivity;
import com.kxiang.quick.function.activity.own.BaseViewActivity;
import com.kxiang.quick.function.activity.own.OwnViewActivity;
import com.kxiang.quick.function.activity.own.ScrollTestActivity;
import com.kxiang.quick.function.activity.own.View1Activity;
import com.kxiang.quick.function.activity.start.AActivity;
import com.kxiang.quick.function.activity.touch.TouchActivity;
import com.kxiang.quick.function.activity.wipemenu.MainCstActivity;
import com.kxiang.quick.function.adapter.MainAdapter;
import com.kxiang.quick.news.NewsMainActivity;
import com.kxiang.quick.socket.ClientActivity;
import com.kxiang.quick.socket.ServerActivity;
import com.kxiang.quick.socket.chat.ChatMainActivity;
import com.kxiang.quick.socket.chat.LoginActivity;
import com.tencent.bugly.beta.Beta;
import com.tencent.bugly.beta.UpgradeInfo;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    String s;


    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quick_activity_main);
        initStatusBarColor(R.color.color_no);
        initTitleBar();
        registerInternet();
        initRecycle();
        initBanner();
        TestTools();
        loadUpgradeInfo();


        LogUtils.toE("SnowViewActivity", "SnowViewActivity:" + Thread.currentThread().getName());
        LogUtils.toE("Pixels", "heightPixels:" + getResources().getDisplayMetrics().heightPixels);
        LogUtils.toE("Pixels", "widthPixels:" + getResources().getDisplayMetrics().widthPixels);
    }

    @Override
    protected void initView() {
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
                fruitImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        showToastShort("点击了" + position);
                    }
                });
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
        allAdapter = new MainAdapter(this, classBeanList, rlv_all);

        rlv_all.setLayoutManager(new LinearLayoutManager(this));
//        rlv_all.addItemDecoration(
//                new RecycleDividerItemLinear(this,
//                        LinearLayoutManager.VERTICAL,
//                        getResources().getColor(R.color.refresh_dddddd)
//                )
//        );
        rlv_all.addItemDecoration(
                new RecycleDividerItemLinears()
        );

        allAdapter.setOnRecycleItemSelectListener(new OnRecycleItemClickListener() {
            @Override
            public void OnRecycleItemSelect(View view, int position) {
                startActivity(new Intent(thisActivity, classBeanList.get(position).getTheClass()));
            }
        });
        rlv_all.setAdapter(allAdapter);


//        getMainLooper().quit();
    }

    private void addData() {

//        ScrollView


        classBeanList.add(getClassBean("二维码扫描功能", QrCodeActivity.class));
        classBeanList.add(getClassBean("漂亮的日历控件", BeautifulCalendarActivity.class));
        classBeanList.add(getClassBean("竖直滚动测试", CycleViewActivity.class));
        classBeanList.add(getClassBean("飘雪", SnowViewActivity.class));
        classBeanList.add(getClassBean("开源侧滑删除", MainCstActivity.class));
        classBeanList.add(getClassBean("ViewDragHelper和GestureDetector测试",
                LeftDrawerLayoutActivity.class));
        classBeanList.add(getClassBean("自定义侧滑删除测试", ViewDragHelperActivity.class));
        classBeanList.add(getClassBean("收缩菜单", ComeBackMenuViewActivity.class));
        classBeanList.add(getClassBean("星星点赞和VelocityTracker测试", StarPraiseActivity.class));
        classBeanList.add(getClassBean("xml生成测试", XmlCreateScreanActivity.class));
        classBeanList.add(getClassBean("scroller测试", View1Activity.class));
        classBeanList.add(getClassBean("滑动冲突测试", ScrollTestActivity.class));
        classBeanList.add(getClassBean("基本图形", BaseViewActivity.class));
        classBeanList.add(getClassBean("启动动画", AActivity.class));
        classBeanList.add(getClassBean("自定义View", OwnViewActivity.class));
        classBeanList.add(getClassBean("触摸事件", TouchActivity.class));
        classBeanList.add(getClassBean("Handle测试", HandleActivity.class));
        classBeanList.add(getClassBean("rx和Retrofit结合使用", RxActivity.class));
        classBeanList.add(getClassBean("小工具集合", SmallToolsActivity.class));
        classBeanList.add(getClassBean("聊天客", LoginActivity.class));
        classBeanList.add(getClassBean("聊天客服端", ChatMainActivity.class));
        classBeanList.add(getClassBean("Client客户端", ClientActivity.class));
        classBeanList.add(getClassBean("Server服务端", ServerActivity.class));
        classBeanList.add(getClassBean("适配app", ScreenActivity.class));
        classBeanList.add(getClassBean("新闻app", NewsMainActivity.class));
        classBeanList.add(getClassBean("Material", MaterialMainActivity.class));
        classBeanList.add(getClassBean("数据下载测试", DownLoadActivity.class));
        classBeanList.add(getClassBean("数据库测试", DBTestActivity.class));
        classBeanList.add(getClassBean("Sokcet测试", SocketActivity.class));
        classBeanList.add(getClassBean("自定义软键盘", KeyboardCustomActivity.class));
        classBeanList.add(getClassBean("自定义软键盘\n带删除输入框\n轮播广告条",
                KeyboardActivity.class));
        classBeanList.add(getClassBean("数据库操作", SQLiteActivity.class));
        classBeanList.add(getClassBean("Rxjava测试", MemoryActivity.class));
        classBeanList.add(getClassBean("单选复选定向选择", RadioCheckActivity.class));
        classBeanList.add(getClassBean("日历控件", CalanderActivity.class));
        classBeanList.add(getClassBean("下拉刷新上拉加载更多功能页面", RefreshLoadingActivity.class));
    }

    private void TestTools() {
        AppVersionUtils.getAppVersionName(thisActivity);


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

    private void loadUpgradeInfo() {


        /***** 获取升级信息 *****/
        UpgradeInfo upgradeInfo = Beta.getUpgradeInfo();

        if (upgradeInfo == null) {
            LogUtils.toE("升级", "无升级信息");
            return;
        }

        StringBuilder info = new StringBuilder();
        info.append("id: ").append(upgradeInfo.id).append("\n");
        info.append("标题: ").append(upgradeInfo.title).append("\n");
        info.append("升级说明: ").append(upgradeInfo.newFeature).append("\n");
        info.append("versionCode: ").append(upgradeInfo.versionCode).append("\n");
        info.append("versionName: ").append(upgradeInfo.versionName).append("\n");
        info.append("发布时间: ").append(upgradeInfo.publishTime).append("\n");
        info.append("安装包Md5: ").append(upgradeInfo.apkMd5).append("\n");
        info.append("安装包下载地址: ").append(upgradeInfo.apkUrl).append("\n");
        info.append("安装包大小: ").append(upgradeInfo.fileSize).append("\n");
        info.append("弹窗间隔（ms）: ").append(upgradeInfo.popInterval).append("\n");
        info.append("弹窗次数: ").append(upgradeInfo.popTimes).append("\n");
        info.append("发布类型（0:测试 1:正式）: ").append(upgradeInfo.publishType).append("\n");
        info.append("弹窗类型（1:建议 2:强制 3:手工）: ").append(upgradeInfo.upgradeType).append("\n");
        info.append("图片Url：").append(upgradeInfo.imageUrl);

        LogUtils.toE("升级", info.toString());
    }

}
