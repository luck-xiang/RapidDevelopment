package com.kexiang.function.view.refresh;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/5/10 9:55
 */

public class RefreshLoadingLayout extends FlingScrollerLayout {
    public RefreshLoadingLayout(Context context) {
        super(context);
        initView();
    }

    public RefreshLoadingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public RefreshLoadingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        addViewSize = 3;
    }


    @Override
    protected void setViewTranslationY(View view, int moveAllSize) {

//        LogUtils.toE("setViewChildTranslationY", "moveAllSize:" + moveAllSize);

        if (getPullType() == PULL_DOWN && headerView != null) {
            ViewCompat.setTranslationY(headerView, moveAllSize);
            baseHeaderView.onRefresScroll(moveAllSize, refreshingTpye);
//            LogUtils.toE("setViewChildTranslationY", "getPullType() :" + getPullType());

        }
        else if (getPullType() == PULL_UP && footerView != null) {
            ViewCompat.setTranslationY(footerView, moveAllSize);
            baseFooterView.onScroll(moveAllSize);
        }

    }


    private View headerView;
    private RefreshHeaderable baseHeaderView;
    private View footerView;
    private LoadFooterable baseFooterView;

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {

        if (child instanceof RefreshHeaderable) {
            headerView = child;
            baseHeaderView = (RefreshHeaderable) child;
            baseHeaderView.setPullRefreshLayout(this);
        }
        else if (child instanceof LoadFooterable) {
            footerView = child;
            baseFooterView = (LoadFooterable) child;
        }

        super.addView(child, index, params);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        int height = getHeight();
        if (headerView != null) {
            headerView.layout(
                    headerView.getLeft(),
                    -headerView.getMeasuredHeight(),
                    headerView.getRight(), 0);
            headerHeigh = headerView.getMeasuredHeight();
        }

        if (footerView != null) {
            footerView.layout(
                    footerView.getLeft(), height,
                    footerView.getRight(), height + footerView.getMeasuredHeight());
            footerHeigh = footerView.getMeasuredHeight();
        }
    }
}
