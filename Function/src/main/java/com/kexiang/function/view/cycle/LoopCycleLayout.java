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

    /**
     * 手指按下时候停止动画滚动
     */
    private boolean finger = false;

    private LoopCycleLayout.Adapter adapter;

    /**
     * 几个viewHolder
     */
    private List<LoopCycleLayout.ViewHolder> viewHolderList;
    /**
     * 像上滑动位置滑动位置
     */
    private int cyclePositionDown = 0;
    /**
     * 像上滑动位置滑动位置
     */
    private int cyclePositionSelect;

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
                //手指按下的时候禁止动画滑动
                if (!finger) {
                    rollUp();
                }
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //手指按下的时候禁止动画滑动
                if (!finger) {
                    moveSize = (int) animation.getAnimatedValue() / 10;
                    scrollTo(0, moveSize);
                }

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
        int topTemp=topItem - 1;
        for (int i = 0; i < adapter.getItemViewCount(); i++) {
            ViewHolder viewHolder = adapter.onCreateViewHolder(this, 0);

            //设置第一个的值到topItem-1的位置
            if (i < topTemp) {
                adapter.onBindViewHolder(viewHolder, adapter.getItemCount() - topTemp+i);
            }

            //设置第topItem-1个到第adapter.getItemViewCount()值
            if (i >= topTemp)
                adapter.onBindViewHolder(viewHolder, (i - topTemp) % adapter.getItemCount());

            //添加holder
            viewHolderList.add(viewHolder);
            addView(viewHolder.itemView);
        }
        cyclePositionSelect = adapter.getItemViewCount();


    }

    public final static int MAX_MOVE = 10;
    /**
     * 滚动的距离，跟item有关
     */
    private int scollSize = 0;


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                finger = true;
                animator.cancel();
                scroller.abortAnimation();
                moveTempX = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:

                int tempY = moveTempX - (int) ev.getY();
                moveSize = moveSize + tempY;
                moveTempX = (int) ev.getY();
                //向下上滚动
                if (moveSize > this.getChildAt(getChildCount() - 1).getHeight() - MAX_MOVE) {
                    this.scrollTo(0, moveSize);
                    moveSize = MAX_MOVE;
                    rollUp();
                }

                //向下滚动
                if (moveSize < -this.getChildAt(getChildCount() - 1).getHeight() + MAX_MOVE) {
                    this.scrollTo(0, moveSize);
                    moveSize = -MAX_MOVE;
                    rollDown();
                }
                this.scrollTo(0, moveSize);

                LogUtils.toE("moveSize", "moveSize:" + moveSize);
                break;
            case MotionEvent.ACTION_UP:
                finger = false;
                startSize = moveSize;
                if (moveSize != 0) {
                    scroller.startScroll(0, 0, 0, moveSize,
                            (int) (3000f * Math.abs(moveSize) / getHeight()));
                }
                invalidate();
                break;

        }
        return super.dispatchTouchEvent(ev);
    }


    /**
     * 向下滚动
     */
    private void rollDown() {
        view = getChildAt(getChildCount() - 1);
        removeView(view);
        addView(view, 0);
        int pre = (cyclePositionSelect + adapter.getItemViewCount() - 1) % adapter.getItemViewCount();
        int item = (cyclePositionDown + adapter.getItemCount() - topItem) % adapter.getItemCount();
        LogUtils.toE("cyclerollDown", "pre:" + pre);
        LogUtils.toE("cyclerollDown", "item:" + item);
        LogUtils.toE("cyclerollDown", "cyclePositionDown:" + cyclePositionDown);
        adapter.onBindViewHolder(viewHolderList.get(pre), item);

        cyclePositionDown--;
        if (cyclePositionDown < 0) {
            cyclePositionDown = adapter.getItemCount() - 1;
        }
        cyclePositionSelect--;
        if (cyclePositionSelect < 0) {
            cyclePositionSelect = adapter.getItemViewCount() - 1;
        }
    }

    /**
     * 向上滚动
     */
    private void rollUp() {
        view = getChildAt(0);
        removeView(view);
        addView(view);
        //当前排列中最后一个item在保存中的位置
        int pre = (cyclePositionSelect) % adapter.getItemViewCount();
        //滑动中显示在界面最上面的位置
        int item = (cyclePositionDown  + topItem) % adapter.getItemCount();
        LogUtils.toE("cyclerollUp", "pre:" + pre);
        LogUtils.toE("cyclerollUp", "item:" + item);
        LogUtils.toE("cyclerollUp", "cyclePositionDown:" + cyclePositionDown);
        adapter.onBindViewHolder(viewHolderList.get(pre), item);
        cyclePositionDown++;
        cyclePositionSelect++;
        if (cyclePositionSelect > adapter.getItemViewCount()) {
            cyclePositionSelect = 1;
        }
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        return true;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {

        super.onSizeChanged(w, h, oldw, oldh);
        scollSize = getChildAt(1).getMeasuredHeight();
        LogUtils.toE("onSizeChanged", "onSizeChanged:" + scollSize);
        animator.setIntValues(0, 0, 0, scollSize * 10);
        if (getChildCount() >= 2)
            animator.start();
    }


    /**
     * 顶部排列个数
     */
    private int topItem = 3;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        LogUtils.toE("onLayout", "left:" + left);
        LogUtils.toE("onLayout", "top:" + top);
        LogUtils.toE("onLayout", "right:" + right);
        LogUtils.toE("onLayout", "bottom:" + bottom);
        int width = right - left;
        int height = bottom - top;


        if (getChildCount() >= topItem) {
            View viewFirt = getChildAt(0);
            View viewSecnd = getChildAt(1);
            View viewCenter = getChildAt(2);
            //子view布局
            int centerTop = (height - viewCenter.getHeight()) / 2;
            int centerBottom = centerTop + viewCenter.getHeight();
            int secndTop = centerTop - viewSecnd.getHeight();
            int firstTop = secndTop - viewFirt.getHeight();


            LogUtils.toE("onLayout", "firstTop:" + firstTop);
            LogUtils.toE("onLayout", "firstTop:" + secndTop);
            LogUtils.toE("onLayout", "centerTop:" + centerTop);
            LogUtils.toE("onLayout", "centerBottom:" + centerBottom);

            //布局第一
            viewFirt.layout(left, firstTop, right, secndTop);
            //布局第二个
            viewSecnd.layout(left, secndTop, right, centerTop);
            //中间显示
            viewCenter.layout(left, centerTop, right, centerBottom);

            //布局其它的
            for (int i = topItem; i < getChildCount(); i++) {
                View view = getChildAt(i);
                view.layout(left, centerBottom + view.getHeight() * (i - topItem),
                        right, centerBottom + view.getHeight() * (i - topItem + 1));
            }


        }
        else {
            for (int i = 0; i < getChildCount(); i++) {
                View view = getChildAt(i);
                view.layout(left, -height + i * height, right, i * height);
            }
        }


    }

    private int startSize;

    @Override
    public void computeScroll() {
        super.computeScroll();
        //手指按下时候，不执行自动滑动
        if (!finger) {
            //scroller类停止滑动的时候开始动画，
            if (scroller.computeScrollOffset()) {
                moveSize = startSize - scroller.getCurrY();
                this.scrollTo(0, moveSize);
                LogUtils.toE("computeScroll", "moveSize:" + moveSize);
                invalidate();
            }
            else {
                //如果动画没有运行或则子view大于2的时候开始动画
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

        /**
         * 隐藏和显示的view的总个数
         *
         * @return
         */
        public int getItemViewCount() {
            return 5;
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
