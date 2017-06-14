package com.kexiang.function.view.own;

import android.content.Context;
import android.graphics.Point;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.kexiang.function.utils.LogUtils;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/6/2 14:22
 */

public class DrawerAppLayout extends LinearLayout {
    public DrawerAppLayout(Context context) {
        super(context);
        initView(context);
    }

    public DrawerAppLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public DrawerAppLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private ViewDragHelper dragHelper;

    private void initView(Context context) {

        dragHelper = ViewDragHelper.create(this, 1.0f, helperCallBack);
        dragHelper.setEdgeTrackingEnabled(ViewDragHelper.EDGE_LEFT);

    }

    ViewDragHelper.Callback helperCallBack = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(View child, int pointerId) {

            LogUtils.toE("ViewDragHelper", "tryCaptureView:" + pointerId);
            LogUtils.toE("ViewDragHelper", "tryCaptureView:" + child.getId());
            //mEdgeTrackerView禁止直接移动
            return child == mDragView || child == mAutoBackView;
//            return true;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            LogUtils.toE("ViewDragHelper", "clampViewPositionHorizontalleft:" + left);
            LogUtils.toE("ViewDragHelper", "clampViewPositionHorizontaldx:" + dx);
            return left;
        }

        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            LogUtils.toE("ViewDragHelper", "clampViewPositionVerticaltop:" + top);
            LogUtils.toE("ViewDragHelper", "clampViewPositionVerticaldy:" + dy);
            return child.getTop();
        }

        //手指释放的时候回调
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            //mAutoBackView手指释放时可以自动回去
            if (releasedChild == mAutoBackView) {
                dragHelper.settleCapturedViewAt(mAutoBackOriginPos.x, mAutoBackOriginPos.y);
                invalidate();
            }
        }

        //在边界拖动时回调
        @Override
        public void onEdgeDragStarted(int edgeFlags, int pointerId) {
            LogUtils.toE("edgeFlags", "dy:" + edgeFlags);
            dragHelper.captureChildView(mEdgeTrackerView, pointerId);
//            dragHelper.captureChildView(mAutoBackView, pointerId);
        }
    };

    @Override
    public void computeScroll() {
        if (dragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);

        mAutoBackOriginPos.x = mAutoBackView.getLeft();
        mAutoBackOriginPos.y = mAutoBackView.getTop();
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        return dragHelper.shouldInterceptTouchEvent(event);
    }

    private Point mAutoBackOriginPos = new Point();

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        dragHelper.processTouchEvent(event);
        return true;
    }

    private View mDragView;
    private View mAutoBackView;
    private View mEdgeTrackerView;

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mDragView = getChildAt(0);
        mAutoBackView = getChildAt(1);
        mEdgeTrackerView = getChildAt(2);
    }

}
