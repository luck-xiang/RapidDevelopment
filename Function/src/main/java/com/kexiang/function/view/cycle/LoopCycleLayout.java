package com.kexiang.function.view.cycle;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.kexiang.function.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/6/27 13:49
 */

public class LoopCycleLayout extends LinearLayout {
    public LoopCycleLayout(Context context) {
        super(context);
        initView(context);
    }

    public LoopCycleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public LoopCycleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    private View view;

    private ValueAnimator animator;
    private Scroller scroller;

    private int moveSize = 0;
    private int moveTempX = 0;
    private boolean down = true;
    private boolean finger = false;

    private LoopCycleLayout.Adapter adapter;

    private List<LoopCycleLayout.ViewHolder> viewHolderList;
    private int cyclePosition = 0;

    protected void initView(Context context) {
        animator = new ValueAnimator();
        viewHolderList = new ArrayList<>();

        setGravity(VERTICAL);
        animator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

                int pre = (cyclePosition) % viewHolderList.size();
                int item = (cyclePosition + 2) % adapter.getItemCount();
                adapter.onBindViewHolder(viewHolderList.get(pre), item);
                view = getChildAt(0);
                removeView(view);
                addView(view);
                cyclePosition++;
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                moveSize = (int) animation.getAnimatedValue() / 10;
                scrollTo(0, moveSize);
            }
        });
        animator.setDuration(3000);
        animator.setRepeatCount(ValueAnimator.INFINITE);

        scroller = new Scroller(context);

    }

    public void setAdapter(LoopCycleLayout.Adapter adapter) {
        this.adapter = adapter;
        addItemViewId(adapter);
    }

    private void addItemViewId(LoopCycleLayout.Adapter adapter) {
        for (int i = 0; i < adapter.getItemViewCount(); i++) {
            ViewHolder viewHolder = adapter.onCreateViewHolder(this, 0);
            viewHolderList.add(viewHolder);
            addView(viewHolder.itemView);
            if (i > 0)
                adapter.onBindViewHolder(viewHolder, (i - 1) % adapter.getItemCount());

        }

    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                finger = true;
//                animator.cancel();
//                scroller.abortAnimation();
//                moveTempX = (int) ev.getY();
//                break;
//            case MotionEvent.ACTION_MOVE:
//
//                int tempY = moveTempX - (int) ev.getY();
//                moveSize = moveSize + tempY;
//                LogUtils.toE("ACTION_MOVE", "" + moveSize);
//                moveTempX = (int) ev.getY();
//                if (moveSize < 0) {
//                    if (down) {
//                        view = this.getChildAt(getChildCount() - 1);
//                        this.removeView(view);
//                        this.addView(view, 0);
//                        down = false;
//                    }
//
//                    if (moveSize < -getHeight()) {
//                        moveSize = -getHeight();
//                    }
//
//                    scrollTo(0, moveSize);
//                    if (moveSize == -getHeight()) {
//                        moveSize = 0;
//                        down = true;
//                    }
//                }
//                else {
//                    if (moveSize > getHeight()) {
//                        moveSize = getHeight();
//                    }
//                    this.scrollTo(0, moveSize);
//                    if (moveSize == getHeight()) {
//                        view = this.getChildAt(0);
//                        this.removeView(view);
//                        this.addView(view);
//                        moveSize = 0;
//                        this.scrollTo(0, moveSize);
//                    }
//
//                }
//
//
//                break;
//            case MotionEvent.ACTION_UP:
//                finger = false;
//                startSize = moveSize;
//                if (moveSize != 0) {
//                    scroller.startScroll(0, 0, 0, moveSize,
//                            (int) (3000f * Math.abs(moveSize) / getHeight()));
//                }
//                LogUtils.toE("computeScroll", "startScroll:" + moveSize);
//                LogUtils.toE("computeScroll", "time:" + (int) (3000f * Math.abs(moveSize) / getHeight()));
//                invalidate();
////                animator.start();
//                break;
//
//        }
//        return super.dispatchTouchEvent(ev);
//    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        LogUtils.toE("onSizeChanged", "onSizeChanged:");
        super.onSizeChanged(w, h, oldw, oldh);
        animator.setIntValues(0, 0, 0, getHeight() * 10);
        if (getChildCount() >= 2)
            animator.start();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {

        LogUtils.toE("onLayout", "left:" + left);
        LogUtils.toE("onLayout", "top:" + top);
        LogUtils.toE("onLayout", "right:" + right);
        LogUtils.toE("onLayout", "bottom:" + bottom);

        int width = right - left;
        int height = bottom - top;

//        if (getChildCount() != 3) {
//            throw new IllegalStateException("控件只能为三个");
//        }
        super.onLayout(changed, left, top, right, bottom);

        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
//            view.layout(left,-2*height+i*height,right,-height+i*height);
            view.layout(left, -height + i * height, right, i * height);
        }


    }

    private int startSize;

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (!finger) {
            if (scroller.computeScrollOffset()) {
                moveSize = startSize - scroller.getCurrY();
                this.scrollTo(0, moveSize);
                LogUtils.toE("computeScroll", "moveSize:" + moveSize);
                invalidate();
            }
            else {
                if (!animator.isRunning() && getChildCount() >= 2) {
                    animator.start();
                    LogUtils.toE("computeScroll", "computeScroll:" + animator.isRunning());
                }
            }
        }

    }

    /**
     * 该方法在当前View被附到一个window上时被调用
     */
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        try {
            if (animator != null)
                if (!animator.isRunning()) {
                    animator.start();
                }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 该方法在当前View从一个window上分离时被调用
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (animator != null)
            if (animator.isRunning()) {
                animator.cancel();
            }
    }


    public abstract static class Adapter {

        public abstract int getItemCount();

        public int getItemViewCount() {
            return 3;
        }

        public abstract LoopCycleLayout.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType);


        public abstract void onBindViewHolder(LoopCycleLayout.ViewHolder holder, int position);

    }

    public abstract static class ViewHolder {

        public View itemView;

        public ViewHolder(View itemView) {
            if (itemView == null) {
                throw new IllegalArgumentException("itemView may not be null");
            }
            this.itemView = itemView;
        }
    }


}
