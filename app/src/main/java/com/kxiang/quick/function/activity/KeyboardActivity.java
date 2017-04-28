package com.kxiang.quick.function.activity;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.kexiang.function.view.KeyboardUtils;
import com.kexiang.function.view.banner.BannersView;
import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseActivity;

public class KeyboardActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyboard);
        initStatusBarColor(R.color.color_no);
        initTitle();
        initKeyboard();
        initTitleBar();
        initBanner();
    }

    @Override
    protected void initView() {

    }

    private EditText et_number;

    private void initKeyboard() {
        et_number = (EditText) findViewById(R.id.et_number);
        //禁止光标出现
//        et_money.setInputType(InputType.TYPE_NULL);
        new KeyboardUtils(thisActivity, et_number).hideSoftInputMethod(et_number, thisActivity);
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

                    }
                });
                fruitImageView.setBackgroundResource(imageID[position % imageID.length]);
                //Glide.with(context).load(imageUrlList.get(position)).into(fruitImageView);
                return fruitImageView;
            }
        });
    }

}
