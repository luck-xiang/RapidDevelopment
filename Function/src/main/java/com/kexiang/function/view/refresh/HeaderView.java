package com.kexiang.function.view.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kxiang.function.R;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/5/10 10:39
 */

public class HeaderView extends FrameLayout implements RefreshHeaderable {
    public HeaderView(Context context) {
        super(context);
        initView(context);
    }

    public HeaderView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public HeaderView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    /**
     * 下拉箭头的转180°动画
     */
    private RotateAnimation outAnima;
    private RotateAnimation pullAnima;

    TextView tv_content;
    ImageView iv_finish_show;
    ProgressBar pb_circle;

    private void initView(Context context) {
        LayoutInflater.from(getContext()).inflate(R.layout.header_view, this, true);
        tv_content = (TextView) findViewById(R.id.tv_content);
        iv_finish_show = (ImageView) findViewById(R.id.iv_finish_show);
        pb_circle = (ProgressBar) findViewById(R.id.pb_circle);

        outAnima = (RotateAnimation) AnimationUtils.loadAnimation(
                context, R.anim.anim_out_header);
        pullAnima = (RotateAnimation) AnimationUtils.loadAnimation(
                context, R.anim.anim_pull_header);

        // 添加匀速转动动画
        LinearInterpolator lir = new LinearInterpolator();
        outAnima.setInterpolator(lir);
        pullAnima.setInterpolator(lir);

    }

    private boolean refreshing;
    private boolean outBoolean = true;
    private boolean pullBoolean = true;

    @Override
    public boolean onRefresScroll(float y, int type) {

        if (!refreshing && !refreshLayout.isStop()) {
            if (refreshLayout.getMotionEvent() == MotionEvent.ACTION_UP
                    && y >= getMeasuredHeight()) {
                tv_content.setText("正在刷新");
                iv_finish_show.clearAnimation();
                iv_finish_show.setVisibility(View.GONE);
                pb_circle.setVisibility(View.VISIBLE);
                refreshing = true;
                if (onRefreshListener != null) {
                    onRefreshListener.onRefresh();
                }
            }
            else {

                if (y >= getMeasuredHeight()) {
                    if (outBoolean) {
                        iv_finish_show.setVisibility(View.VISIBLE);
                        pb_circle.setVisibility(View.GONE);
                        tv_content.setText("松开刷新");
//                        iv_finish_show.clearAnimation();
                        iv_finish_show.startAnimation(outAnima);

                        outBoolean = false;
                        pullBoolean = true;
                    }
                }
                else {
                    if (pullBoolean) {
                        iv_finish_show.setVisibility(View.VISIBLE);
                        pb_circle.setVisibility(View.GONE);
                        tv_content.setText("下拉刷新");
                        if (!outBoolean) {
//                            iv_finish_show.clearAnimation();
                            iv_finish_show.startAnimation(pullAnima);
                        }
                        outBoolean = true;
                        pullBoolean = false;
                    }

                }
            }
        }

        return false;
    }


    protected RefreshLoadingLayout refreshLayout;

    @Override
    public void setPullRefreshLayout(RefreshLoadingLayout refreshLayout) {
        this.refreshLayout = refreshLayout;
    }

    @Override
    public void startRefresh() {
        tv_content.setText("正在刷新");
        refreshing = true;
        if (onRefreshListener != null) {
            onRefreshListener.onRefresh();
        }
        refreshLayout.startScroll(getMeasuredHeight());
    }


    public void stopRefreshFail() {
        tv_content.setText("刷新失败");
        postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshLayout.stop();
                refreshing = false;
            }
        }, 1000);


    }

    public void stopRefreshSuccess() {
        tv_content.setText("刷新成功");
        iv_finish_show.setVisibility(View.GONE);
        pb_circle.setVisibility(View.GONE);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                refreshing = false;
                refreshLayout.stop();
            }
        }, 1000);
    }


    @Override
    public void stopRefresh() {
        refreshing = false;
        refreshLayout.stop();
    }

    public OnRefreshListener onRefreshListener;

    public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
        this.onRefreshListener = onRefreshListener;
    }
}
