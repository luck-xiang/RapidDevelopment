package com.kxiang.quick.news;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseActivity;
import com.kxiang.quick.news.bean.NewsContentBean;
import com.kxiang.quick.news.fragment.NewsMainFragment;
import com.nineoldandroids.view.ViewHelper;

public class NewsMainActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_main);
        initStatusBarColor(R.color.color_81ccff);
        initView();
    }


    public void addShoppingCart(NewsContentBean.ItemBean itemBean) {

        int position = newsMainDataUitls.addShoppingCart(itemBean);
        if (position == 0) {
            tv_show.setText("");

        }
        else {
            tv_show.setText("数量：" + position);
        }
    }

    public void subShoppingCart(NewsContentBean.ItemBean itemBean) {


        int position = newsMainDataUitls.subShoppingCart(itemBean);

        if (position == 0) {
            tv_show.setText("");

        }
        else {
            tv_show.setText("数量：" + position);
        }

    }


    NewsMainDataUitls newsMainDataUitls;

    public NewsMainDataUitls getNewsMainDataUitls() {
        return newsMainDataUitls;
    }

    private TextView tv_show;

    @Override
    protected void initView() {
        newsMainDataUitls = new NewsMainDataUitls();
        tv_show = (TextView) findViewById(R.id.tv_show);
        initDrawerLayout();
        initFragment();
    }

    private void initFragment() {

        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_main, new NewsMainFragment())
                .commit();
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
                mDrawerLayout.closeDrawer(Gravity.LEFT);
                return false;
            }
        }
//        if (keyCode == KeyEvent.KEYCODE_BACK) {
//            moveTaskToBack(false);
//            return true;
//        }
        return super.onKeyDown(keyCode, event);
    }

    private DrawerLayout mDrawerLayout;

    public DrawerLayout getmDrawerLayout() {
        return mDrawerLayout;
    }

    private void initDrawerLayout() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.dl_main);
        mDrawerLayout.setScrimColor(Color.TRANSPARENT);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED,
                Gravity.RIGHT);

        mDrawerLayout.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerStateChanged(int newState) {
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                View mContent = mDrawerLayout.getChildAt(0);
                View mMenu = drawerView;
                float scale = 1 - slideOffset;
                float rightScale = 0.8f + scale * 0.2f;
                if (drawerView.getTag().equals("LEFT")) {
                    float leftScale = 1 - 0.3f * scale;
                    Log.e("onDrawerSlide", "leftScale:" + leftScale);
                    ViewHelper.setScaleX(mMenu, leftScale);
                    ViewHelper.setScaleY(mMenu, leftScale);
                    ViewHelper.setAlpha(mMenu, 0.6f + 0.4f * (1 - scale));
                    ViewHelper.setTranslationX(mContent, mMenu.getMeasuredWidth() * (1 - scale));
                    ViewHelper.setPivotX(mContent, 0);
                    ViewHelper.setPivotY(mContent, mContent.getMeasuredHeight() / 2);
                    mContent.invalidate();
                    ViewHelper.setScaleX(mContent, rightScale);
                    ViewHelper.setScaleY(mContent, rightScale);
                }
            }

            @Override
            public void onDrawerOpened(View drawerView) {
            }

            @Override
            public void onDrawerClosed(View drawerView) {

            }
        });
    }
}
