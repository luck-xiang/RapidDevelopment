package com.kexiang.function.view.cycle;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Scroller;

import com.kexiang.function.utils.LogUtils;
import com.kxiang.function.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/6/27 13:49
 */

public class LoopCycleLayout extends LinearLayout {

    public static final int LOOP_UP = 0;
    public static final int LOOP_DOWN = 1;
    public static final int LOOP_LEFT = 2;
    public static final int LOOP_RINGH = 3;
    public int scroolLoop = LOOP_UP;


    public LoopCycleLayout(Context context) {
        super(context);
        initView(context);
    }

    public LoopCycleLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs, 0);
    }

    public LoopCycleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(context, attrs, defStyleAttr);
    }

    private void initAttrs(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.LoopCycleLayout,
                defStyleAttr, 0);
        scroolLoop = a.getInt(R.styleable.LoopCycleLayout_scrool_loop, LOOP_UP);
        a.recycle();
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
    /**
     * 最小滑动值
     */
    private int touchSlop;
    /**
     * 滑动开始，屏蔽所有点击事件
     */
    private boolean startScroll = true;

    protected void initView(Context context) {
        animator = new ValueAnimator();
        viewHolderList = new ArrayList<>();
        ViewConfiguration configuration = ViewConfiguration.get(context);
        touchSlop = configuration.getScaledTouchSlop();
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

                LogUtils.toE("addUpdateListener", "onAnimationRepeat:" + moveSize);
                //手指按下的时候禁止动画滑动
                if (!finger) {
                    if (getOrientation() == VERTICAL) {
                        switch (scroolLoop) {
                            case LOOP_UP:
                                rollUp();
                                break;
                            case LOOP_DOWN:
                                rollDown();
                                break;
                        }
                    }
                    else {
                        switch (scroolLoop) {
                            case LOOP_LEFT:
                                rollUp();
                                break;
                            case LOOP_RINGH:
                                rollDown();
                                break;
                        }
                    }


                }
            }
        });
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                //手指按下的时候禁止动画滑动
                if (!finger) {
                    if (getOrientation() == VERTICAL) {
                        moveSize = (int) animation.getAnimatedValue() / 10;
                        scrollTo(0, moveSize);
                    }
                    else {
                        moveSize = (int) animation.getAnimatedValue() / 10;
                        scrollTo(moveSize, 0);
                    }
                    LogUtils.toE("addUpdateListener", "moveSize:" + moveSize);
//                    if (moveSize == 0) {
//                        startScroll = true;
//                    }
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
        int topTemp = topItem - 1;
        for (int i = 0; i < adapter.getItemViewCount(); i++) {
            ViewHolder viewHolder = adapter.onCreateViewHolder(this, 0);

            //设置第一个的值到topItem-1的位置
            if (i < topTemp) {
                adapter.onBindViewHolder(viewHolder, adapter.getItemCount() - topTemp + i);
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

    /**
     * 向上滚动,向左滚动
     */
    public static final int LEFT_TOP = 0;
    /**
     * 向下滚动,向右滚动
     */
    public static final int RIGHT_BOTTOM = 1;
    public static final int NOSELECT = 2;
    private int scollPosition = NOSELECT;


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {


        boolean temp;
        if (getOrientation() == VERTICAL) {
            temp = moveTouchVertical(ev);
        }
        else {
            temp = moveTouchHorizontal(ev);
        }

        return temp || super.dispatchTouchEvent(ev);

    }

    private boolean moveTouchHorizontal(MotionEvent ev) {
//        if (!scroller.isFinished()){
//            return true;
//        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                finger = true;
                animator.cancel();
                scroller.abortAnimation();
                moveTempX = (int) ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:

                int tempY = moveTempX - (int) ev.getX();
                moveSize = moveSize + tempY;
                moveTempX = (int) ev.getX();
                if (Math.abs(moveSize) >= touchSlop && startScroll) {
                    moveSize = 0;
                    startScroll = false;
                }
                if (startScroll) {

                    return false;
                }

                int tempAt = this.getChildAt(getChildCount() - 1).getWidth();
                //向上滚动,向左滚动
                if (moveSize > tempAt - MAX_MOVE) {
//                    this.scrollTo(moveSize, 0);
                    LogUtils.toE("moveSize", "moveSize:" + moveSize);
                    moveSize = moveSize - tempAt;
                    LogUtils.toE("moveSize", "moveSize:" + moveSize);
                    LogUtils.toE("moveSize", "moveSize:" +
                            this.getChildAt(getChildCount() - 1).getWidth());
                    rollUp();
                }

                //向下滚动,向右滚动
                if (moveSize < -tempAt + MAX_MOVE) {
                    moveSize = moveSize + tempAt;
                    rollDown();
                }
                this.scrollTo(moveSize, 0);


                break;
            case MotionEvent.ACTION_UP:
                finger = false;

                startSize = moveSize;
                if (startScroll) {
                    int tempItemSize = getChildAt(0).getWidth();
                    int temp = (getWidth() - tempItemSize) / 2;
                    LogUtils.toE("ev.getX()", "ev.getX():" + ev.getX());
                    LogUtils.toE("ev.getX()", "ev.getX():" + ev.getX());
                    LogUtils.toE("ev.getX()", "temp:" + temp);
                    LogUtils.toE("ev.getX()", "temp+:" + temp + tempItemSize);
                    if (ev.getX() < temp) {

                        scollPosition = RIGHT_BOTTOM;
                        startSize = moveSize = 0;
                        removeItem = true;
                        scroller.startScroll(0, 0, tempItemSize, 0,
                                (int) (1500f * Math.abs(tempItemSize) / getWidth()));
                        invalidate();

                        return true;
                    }
                    else if (ev.getX() > temp + tempItemSize) {
                        scollPosition = LEFT_TOP;
                        startSize = moveSize = 0;
                        removeItem = true;
                        scroller.startScroll(0, 0, -tempItemSize, 0,
                                (int) (1500f * Math.abs(tempItemSize) / getWidth()));

                        invalidate();
                        return true;
                    }
                    else {
                        scollPosition = NOSELECT;
                    }

                }

                if (moveSize != 0) {
                    removeItem = true;
                    scroller.startScroll(0, 0, moveSize, 0,
                            (int) (1500f * Math.abs(moveSize) / getWidth()));
                }

                invalidate();
                break;

        }
        return !startScroll;

    }

    private boolean moveTouchVertical(MotionEvent ev) {

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
        return false;
    }

    /**
     * 向下滚动,向右滚动
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
     * 向上滚动,向左滚动
     */
    private void rollUp() {
        view = getChildAt(0);
        removeView(view);
        addView(view);
        //当前排列中最后一个item在保存中的位置
        int pre = (cyclePositionSelect) % adapter.getItemViewCount();
        //滑动中显示在界面最上面的位置
        int item = (cyclePositionDown + topItem) % adapter.getItemCount();
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
        if (getOrientation() == VERTICAL) {
            scollSize = getChildAt(1).getMeasuredHeight();
        }
        else {
            scollSize = getChildAt(1).getMeasuredWidth();
        }

        LogUtils.toE("onSizeChanged", "onSizeChanged:" + scroolLoop);
        switch (scroolLoop) {
            case LOOP_UP:
                animator.setIntValues(0, 0, 0, scollSize * 10);
                break;
            case LOOP_DOWN:
                animator.setIntValues(0, 0, 0, -scollSize * 10);
                break;
            case LOOP_LEFT:
                animator.setIntValues(0, 0, 0, scollSize * 10);
                break;
            case LOOP_RINGH:
                animator.setIntValues(0, 0, 0, -scollSize * 10);
                break;
        }

//        if (getChildCount() >= 2)
//            animator.start();
    }


    /**
     * 顶部排列个数
     */
    private int topItem = 3;

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);

        LogUtils.toE("onLayout", "onLayout");
        if (getOrientation() == VERTICAL) {
            layoutVertical(left, top, right, bottom);
        }
        else {
            layoutHorizontal(left, top, right, bottom);
        }


    }

    private void layoutVertical(int left, int top, int right, int bottom) {

        int height = bottom - top;
        if (getChildCount() >= topItem) {
            View viewFirt = getChildAt(0);
            View viewSecnd = getChildAt(1);
            View viewCenter = getChildAt(2);
            //子view布局
            int centerLeft = (height - viewCenter.getHeight()) / 2;
            int centerRight = centerLeft + viewCenter.getHeight();
            int secndLeft = centerLeft - viewSecnd.getHeight();
            int firstLeft = secndLeft - viewFirt.getHeight();

            //布局第一
            viewFirt.layout(left, firstLeft, right, secndLeft);
            //布局第二个
            viewSecnd.layout(left, secndLeft, right, centerLeft);
            //中间显示
            viewCenter.layout(left, centerLeft, right, centerRight);

            //布局其它的
            for (int i = topItem; i < getChildCount(); i++) {
                View view = getChildAt(i);
                view.layout(left, centerRight + view.getHeight() * (i - topItem),
                        right, centerRight + view.getHeight() * (i - topItem + 1));
            }


        }
        else {
            for (int i = 0; i < getChildCount(); i++) {
                View view = getChildAt(i);
                view.layout(left, -height + i * height, right, i * height);
            }
        }
    }

    private void layoutHorizontal(int left, int top, int right, int bottom) {


        int height = bottom - top;
        int width = right - left;
        if (getChildCount() >= topItem) {
            View viewFirt = getChildAt(0);
            View viewSecnd = getChildAt(1);
            View viewCenter = getChildAt(2);
            //子view布局
            int centerLeft = (width - viewCenter.getWidth()) / 2;
            int centerRight = centerLeft + viewCenter.getWidth();
            int secndLeft = centerLeft - viewSecnd.getWidth();
            int firstLeft = secndLeft - viewFirt.getWidth();

            //布局第一
            viewFirt.layout(firstLeft, 0, secndLeft, height);
            //布局第二个
            viewSecnd.layout(secndLeft, 0, centerLeft, height);
            //中间显示
            viewCenter.layout(centerLeft, 0, centerRight, height);

            //布局其它的
            for (int i = topItem; i < getChildCount(); i++) {
                View view = getChildAt(i);


                view.layout(centerRight + view.getWidth() * (i - topItem), 0,
                        centerRight + view.getWidth() * (i - topItem + 1), height);

            }


        }
        else {
            for (int i = 0; i < getChildCount(); i++) {
                View view = getChildAt(i);
                view.layout(-width + i * width, 0, i * width, height);
            }
        }
    }


    private int startSize;

    private boolean removeItem;

    @Override
    public void computeScroll() {
        super.computeScroll();
        //手指按下时候，不执行自动滑动
        if (!finger) {

            //scroller类停止滑动的时候开始动画，
            if (scroller.computeScrollOffset()) {

                if (getOrientation() == VERTICAL) {
                    moveSize = startSize - scroller.getCurrY();
                    this.scrollTo(0, moveSize);
                }
                else {

                    moveSize = startSize - scroller.getCurrX();


                    if (removeItem && Math.abs(moveSize) > scollSize - 50) {
                        LogUtils.toE("computeScroll", "computeScroll:LEFT_TOP" + moveSize);

                        startSize = -moveSize;

                        if (scollPosition == LEFT_TOP) {

                            rollUp();
                            scollPosition = NOSELECT;
                        }
                        else if (scollPosition == RIGHT_BOTTOM) {

                            rollDown();
                            scollPosition = NOSELECT;
                        }

                        removeItem = false;
                    }
                    this.scrollTo(moveSize, 0);


                }


                LogUtils.toE("computeScroll", "computeScroll:" + moveSize);

            }
            else {

                LogUtils.toE("computeScroll", "computeScroll:LEFT_TOP" + moveSize);
//                if (scollPosition == LEFT_TOP) {
//
////                    moveSize=0;
//                    rollUp();
////                    this.scrollTo(0, 0);
//
//                    scollPosition = NOSELECT;
//                }
//                else if (scollPosition == RIGHT_BOTTOM) {
//
////                    moveSize=0;
//                    this.scrollTo(0, 0);
//                    rollDown();
////                    this.scrollTo(0, 0);
//
//
//
//                    scollPosition = NOSELECT;
//                }
                startScroll = true;

                //如果动画没有运行或则子view大于2的时候开始动画
                if (!animator.isRunning() && getChildCount() >= 2) {
                    startScroll = true;
                    animator.start();

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
//                    animator.start();
                    LogUtils.toE("animator.start", "onAttachedToWindow");
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
                animator.setRepeatCount(1);
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
