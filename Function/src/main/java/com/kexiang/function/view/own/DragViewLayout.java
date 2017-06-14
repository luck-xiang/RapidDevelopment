package com.kexiang.function.view.own;

import android.content.Context;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.kexiang.function.utils.LogUtils;

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
        LogUtils.toE("DragViewLayout", "initView");
        mViewDragHelper = ViewDragHelper.create(this, callback);
        mViewDragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);
    }

    ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return mMenuView == child;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {

            LogUtils.toE("Horizontal", "left:" + left);

            int temp = (-getWidth() + 600);
            if (left > temp)
                left = temp;

            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            return 0;
        }

        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            LogUtils.toE("onViewReleased", "onViewReleased:" + mMenuView.getRight());

            if (mMenuView.getRight() > 300) {
                mViewDragHelper.smoothSlideViewTo(mMenuView, -getWidth() + 600, 0);
            }
            else {
                mViewDragHelper.smoothSlideViewTo(mMenuView, -getWidth(), 0);
            }
            ViewCompat.postInvalidateOnAnimation(DragViewLayout.this);
        }

        //在边界拖动时回调
        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            LogUtils.toE("edgeFlags", "dy:" + edgeFlags);
            mViewDragHelper.captureChildView(mMenuView, pointerId);
//            dragHelper.captureChildView(mAutoBackView, pointerId);
        }
    };

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() != 2) {
            throw new RuntimeException("必须为两个子控件");
        }
        else {
            mMainView = getChildAt(0);
            mMenuView = getChildAt(1);
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
    public boolean dispatchTouchEvent(MotionEvent ev) {

        return super.dispatchTouchEvent(ev);
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
//        return super.onTouchEvent(event);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);


        for (int i = 0; i < getChildCount(); i++) {
            View child = this.getChildAt(i);
            measureChild(child, widthMeasureSpec, heightMeasureSpec);
//            int cw = child.getMeasuredWidth();
            // int ch = child.getMeasuredHeight();
        }
//        getChildAt(0).measure(widthMeasureSpec, heightMeasureSpec);
//        getChildAt(1).measure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(widthSize, heightSize);
        if (widthMode == MeasureSpec.AT_MOST) {
            //相当于我们设置为wrap_content
        }
        else if (widthMode == MeasureSpec.EXACTLY) {
            //相当于我们设置为match_parent或者为一个具体的值
        }
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        LogUtils.toE("onLayout",
                "left:" + left + "\n" +
                        "top:" + top + "\n" +
                        "right:" + right + "\n" +
                        "bottom:" + bottom + "\n"
        );
        getChildAt(0).layout(left, 0, right, getHeight());
        getChildAt(1).layout(left - right, 0, 0, getHeight());

//        int childCount = this.getChildCount();
//        for (int i = 0; i < childCount; i++) {
//            View child = this.getChildAt(i);
//            LayoutParams lParams =  child.getLayoutParams();
//            child.layout(lParams.left, lParams.top, lParams.left + childWidth,
//                    lParams.top + childHeight);
//        }

    }
}
