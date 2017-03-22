package com.kexiang.function.view.banner;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kxiang.function.R;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by Administrator on 2016/4/27.
 */
public class BannersView extends LinearLayout {
    private ViewPager vp_Banners_id;
    private BannersCircle roll_circle_id;
    private BannersAdapter bannersAdapter;
    private ViewPagerScroller viewPagerScroller;
    private Timer swipeTimer;
    private SwipeTask swipeTask;
    private int currentIndex = 0;
    private Context context;
    private int slideInterval = 3000;
    private int count = 0;
    private boolean stateBoolean=true;

    private class SwipeTask extends TimerTask {
        public void run() {
            //LogUtils.toE("SwipeTask","SwipeTask");
            if (vp_Banners_id != null) {
                vp_Banners_id.post(new Runnable() {
                    public void run() {

                        currentIndex++;
                        vp_Banners_id.setCurrentItem(currentIndex);
                    }
                });
            }

        }
    }
    //设置轮播时间间隔
    public void setSlideInterval(int slideInterval) {
        this.slideInterval = slideInterval;

        if (null != vp_Banners_id) {
            playCarousel();
        }
    }

    /**
     * 开启定时器，轮播开始
     */
    public void playCarousel() {

        resetScrollTimer();

        swipeTimer.schedule(swipeTask, slideInterval, slideInterval);
    }

    /**
     * 听着轮播
     */
    public void stopScrollTimer() {

        if (null != swipeTimer) {
            swipeTimer.cancel();
        }

        if (null != swipeTask) {
            swipeTask.cancel();
        }
    }


    private void resetScrollTimer() {

        stopScrollTimer();

        swipeTask = new SwipeTask();
        swipeTimer = new Timer();

    }


    public int getCount() {
        return count;
    }

    public void setCount(final int count) {
        this.count = count;
        currentIndex= count*200;

    }

    public interface ScollViewListener {

        View setViewForPosition(int position);
    }

    private ScollViewListener scollViewListener;

    public void setScollViewListener(ScollViewListener scollViewListener) {
        this.scollViewListener = scollViewListener;
    }


    public void start() {

        bannersAdapter = new BannersAdapter();
        roll_circle_id.setCount(count);
        viewPagerScroller.initViewPagerScroll(vp_Banners_id);
        vp_Banners_id.setAdapter(bannersAdapter);
        vp_Banners_id.setOffscreenPageLimit(3);
        vp_Banners_id.setCurrentItem(currentIndex);
        vp_Banners_id.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (stateBoolean){
                    // 停止图片滚动
                    stopScrollTimer();
                    stateBoolean=false;
                }
                if (MotionEvent.ACTION_UP==event.getAction()){
                    // 开始图片滚动
                    playCarousel();
                    stateBoolean=true;
                }


                return false;
            }
        });
        vp_Banners_id.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {


            }

            @Override
            public void onPageSelected(int position) {
                roll_circle_id.choose(position % count);
                currentIndex=position;

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                if (arg0== ViewPager.SCROLL_STATE_DRAGGING){
                    if (arg0 == ViewPager.SCROLL_STATE_IDLE) {

                    } else {

                    }
                }

            }
        });
    }


    public BannersView(Context context) {
        super(context);
        this.context = context;
        initView(context, null, 0);
    }

    public BannersView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs, 0);
        this.context = context;
    }

    public BannersView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs, defStyleAttr);
        this.context = context;

    }

    private void initView(final Context context, AttributeSet attrs, int defStyleAttr) {
        if (isInEditMode()) {
            return;
        } else {
            viewPagerScroller = new ViewPagerScroller(context);
            viewPagerScroller.setScrollDuration(1000);
            View view = LayoutInflater.from(context).inflate(R.layout.fun_banners_view_custom, this, true);
            vp_Banners_id = (ViewPager) view.findViewById(R.id.vp_Banners_id);
            roll_circle_id = (BannersCircle) view.findViewById(R.id.roll_circle_id);
        }

    }


    class BannersAdapter extends PagerAdapter {


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            if (scollViewListener != null) {
                View view = scollViewListener.setViewForPosition(position % count);
                container.addView(view);
                return view;
            }

            return null;


        }

        @Override
        public int getCount() {
            return count * 500;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);
        }
    }

}
