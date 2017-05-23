package com.kexiang.function.view.own;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/4/28 14:20
 */

public class DragViewLayout extends ViewGroup {

    public DragViewLayout(Context context) {
        super(context);
        initView();
    }

    public DragViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public DragViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private ViewDragHelper mViewDragHelper;
    private View mMenuView, mMainView;
    private int mWidth;

    private void initView() {
        String s = Build.FINGERPRINT;
        mViewDragHelper = ViewDragHelper.create(this, callback);
    }

    ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return mMainView == child;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return 0;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            if (mMainView.getLeft() < 500) {
                mViewDragHelper.smoothSlideViewTo(mMainView, 0, 0);
            }
            else {
                mViewDragHelper.smoothSlideViewTo(mMainView, 300, 0);
            }
            ViewCompat.postInvalidateOnAnimation(DragViewLayout.this);
        }
    };

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() != 2) {
            throw new RuntimeException("必须为两个子控件");
        }
        else {
            mMenuView = getChildAt(0);
            mMainView = getChildAt(1);
        }
    }

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(DragViewLayout.this);
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = mMenuView.getWidth();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
//        super.onInterceptTouchEvent(ev);
        return mViewDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);


        setMeasuredDimension(widthSize * 2, heightSize);
        if (widthMode == MeasureSpec.AT_MOST) {
            //相当于我们设置为wrap_content
        }
        else if (widthMode == MeasureSpec.EXACTLY) {
            //相当于我们设置为match_parent或者为一个具体的值
        }
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {


        getChildAt(0).layout(left, top, right, bottom);
        getChildAt(1).layout(100, top, 0, bottom);

    }
}
