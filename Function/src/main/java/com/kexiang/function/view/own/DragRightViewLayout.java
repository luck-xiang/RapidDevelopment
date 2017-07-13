package com.kexiang.function.view.own;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.widget.Scroller;

import com.kexiang.function.utils.LogUtils;
import com.kxiang.function.R;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/4/28 14:20
 */

public class DragRightViewLayout extends ViewGroup
//        implements View.OnTouchListener
{

    public DragRightViewLayout(Context context) {
        super(context);
        initView(context);



    }

    public DragRightViewLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initType(context, attrs);
    }

    public DragRightViewLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initType(context, attrs);
    }

    private void initType(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.DragRightViewLayout);

        //单个滑动，不受外部影响
        if (a.getBoolean(R.styleable.DragRightViewLayout_singleShow, false)) {
            setSingleShow();
        }
        a.recycle();
    }

    private Scroller scroller;

    //滑动时间
    private float scrollerTime = 200;
    private int mTouchSlop;//滑动判断最小距离
    private VelocityTracker mVelocityTracker;//滑动速度变量
    private int mMinimumVelocity;//滑动最小速率
    private int mMaximumVelocity;//滑动最多速率
    private DragRightViewUtils dragRightViewUtils;

    private void initView(Context context) {
        scroller = new Scroller(context, new LinearInterpolator());
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
        mMinimumVelocity = configuration.getScaledMinimumFlingVelocity() * 10;
        mMaximumVelocity = configuration.getScaledMaximumFlingVelocity();
        dragRightViewUtils = DragRightViewUtils.getDragRightViewUtils();
    }

    public int getScrollerTime(int copies) {
        return (int) (scrollerTime / rightMax * Math.abs(copies));
    }

    public void setSingleShow() {
        dragRightViewUtils = new DragRightViewUtils();
    }

    public void setSingleShow(DragRightViewUtils dragRightViewUtils) {
        this.dragRightViewUtils = dragRightViewUtils;
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() != 2) {
            throw new RuntimeException("必须为两个子控件");
        }
//        else {
//            mainView = getChildAt(0);
//            menuView = getChildAt(1);
//        }
    }


    private int moveAllX = 0;
    private int moveToX = 0;
    private int rightMax = 300;

    /**
     * 滑动开始保存信息
     */
    private boolean startMove = false;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {



        // 判断是否需要拦截事件，不传递给子控件，
        // 子控件在MotionEvent.ACTION_UP事件的时候响应点击事件，
        // 所以在该事件的时候判断触摸位置是不是在显示内容上，
        // 如果是在显示位置上屏蔽该事件，放置出现松开手的时候出现响应点击事件出现对应操作
        boolean temp = false;
        acquireVelocityTracker(ev);
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //当有一个手指按下的时候，点击其它位置的时候屏蔽掉所有事件
                if (dragRightViewUtils.isSelect()) {
                    return false;
                }
                //点击快关，当一个手指头按下的时候开启开关
                dragRightViewUtils.setSelect(true);
                //当moveAllX有移动拒绝为0时候，表示不存在滑动,重置滑动开关
                if (moveAllX == 0) {
                    startMove = false;
                }
                rightItemShowOther();
                moveToX = (int) ev.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                int tempX = (int) ev.getX() - moveToX;
                moveAllX = moveAllX + tempX;
                //当滑动值大于最小滑动值时候开启滑动，同时将moveAllX置0
                if (Math.abs(moveAllX) > mTouchSlop && !startMove) {
                    startMove = true;
                    moveAllX = 0;

                }

                if (startMove) {
                    //点击位置开始滑动，屏蔽父类的滑动事件
                    getParent().requestDisallowInterceptTouchEvent(true);
                    //滑动开始
                    scrollView();
                }
                else if (dragRightViewUtils.getShowItem() != null) {
                    if (dragRightViewUtils.getShowItem() != this &&
                            dragRightViewUtils.getShowItem().moveAllX < 0) {
                        //点击位置开始滑动，屏蔽父类的滑动事件
                        getParent().requestDisallowInterceptTouchEvent(true);
                    }
                }

                moveToX = (int) ev.getX();
                //滚动实现的几种方法
//                this.scrollTo(-moveAllX, 0);
//                this.scrollBy(-tempX, 0);
//                menuView.offsetLeftAndRight(tempX);
//                mainView.offsetLeftAndRight(tempX);
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                if (dragRightViewUtils.getShowItem() == null) {
                    dragRightViewUtils.setShowItem(this);
                }
                dragRightViewUtils.setSelect(false);
                //判断是否存在滑动
                //所有item不存在滑动事件的时候直接将事件返回
                if (moveAllX == 0 && !startMove &&
                        dragRightViewUtils.getShowItem().moveAllX == 0) {
                    return super.dispatchTouchEvent(ev);
                }


                //根据滑动速率或者滑动距离判断是否收缩
                final VelocityTracker velocityTracker = mVelocityTracker;
                velocityTracker.computeCurrentVelocity(1000, mMaximumVelocity);
                int velocityX = (int) velocityTracker.getXVelocity();
                //释放
                releaseVelocityTracker();
                int moveX = 0;
                LogUtils.toE("dragRightViewUtils","moveAllX1:"+moveAllX);
                //小于最小滑动距离且没有开始滑动，将moveAllX置0
                if (Math.abs(moveAllX) < mTouchSlop && !startMove) {
                    moveAllX = 0;
                }
                //抛fling滑动，手势判断是否滑出,当滑动速率大于阀门值时候促发滑动，
                else if ((Math.abs(velocityX) > mMinimumVelocity)) {

                    //展开
                    if (velocityX < -mMinimumVelocity) {
                        moveX = -rightMax;
                    }
                    //关闭
                    else {
                        moveX = 0;
                    }

                }
                //正常滑动，判断位置是否收缩
                else {
                    //显示状态，滑动的距离大于
                    if (dragRightViewUtils.isRightShow()) {
                        if (moveAllX > -rightMax / 5 * 4) {
                            moveX = 0;
                        }
                        else {
                            moveX = -rightMax;
                        }

                    }
                    else {
                        if (moveAllX < -rightMax / 5) {
                            moveX = -rightMax;
                        }
                        else {
                            moveX = 0;
                        }
                    }
                }

                //开始滑动距离
                scrollStart = moveAllX;

                //点击是同一个item的时候
                if (dragRightViewUtils.getShowItem() == this) {
                    //判断该item是否展开
                    if (dragRightViewUtils.isRightShow()) {
                        if (moveX == 0) {
                            temp = true;
                        }
                        //当点击位不是侧滑菜单，屏蔽所有点击事件，同时缩回菜单
                        else if ((getWidth() + moveAllX) > ev.getX()) {
                            startBack();
                            return true;
                        }

                    }
                    else {
                        if (startMove) {
                            temp = true;
                        }
                    }
                }
                //当点击不是显示item的时候，判断展开的item是否展开，当前类是否存在滑动
                else if (dragRightViewUtils.getShowItem().moveAllX < 0 || startMove||moveAllX<0) {
                    temp = true;
                }

                //存在滑动距离，开始滑动
                if ((moveAllX - moveX) != 0) {
                    scroller.startScroll(0, 0, moveAllX - moveX, 0, getScrollerTime(moveAllX - moveX));
                    invalidate();
                }
                //根据回退位置判断是否是展开，修改状态
                if (moveX == -rightMax) {
                    dragRightViewUtils.setShowItem(this);
                    dragRightViewUtils.setRightShow(true);
                }
                else if (moveX == 0) {
                    dragRightViewUtils.setRightShow(false);
                }

                break;
        }

        if (!temp) {
            temp = super.dispatchTouchEvent(ev);
        }
        return temp;
    }


    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        LogUtils.toE("onInterceptTouchEvent", "getAction:" + ev.getAction());
        return super.onInterceptTouchEvent(ev);
    }

    public void startBack() {
        dragRightViewUtils.setRightShow(false);
        scrollStart = moveAllX;
        scroller.startScroll(0, 0, scrollStart, 0, getScrollerTime(scrollStart));
        invalidate();
    }

    public void delete() {
        dragRightViewUtils.getShowItem().moveAllX = 0;
        dragRightViewUtils.getShowItem().scrollStart = 0;
        dragRightViewUtils.getShowItem().startBack();


    }


    private void backItem() {
        dragRightViewUtils.setRightShow(false);
        scrollStart = moveAllX;
        if (moveAllX != 0) {
            scroller.startScroll(0, 0, moveAllX, 0, getScrollerTime(scrollStart));
            invalidate();
        }

    }

    /**
     * @param event 向VelocityTracker添加MotionEvent
     * @see VelocityTracker#obtain()
     * @see VelocityTracker#addMovement(MotionEvent)
     */
    private void acquireVelocityTracker(final MotionEvent event) {
        if (null == mVelocityTracker) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    /**
     * * 释放VelocityTracker
     *
     * @see VelocityTracker#clear()
     * @see VelocityTracker#recycle()
     */
    private void releaseVelocityTracker() {
        if (null != mVelocityTracker) {
            mVelocityTracker.clear();
            mVelocityTracker.recycle();
            mVelocityTracker = null;
        }
    }

    //展开时，禁止长按
    @Override
    public boolean performLongClick() {
        if (getScrollX() > 0) {
            return false;
        }
        return super.performLongClick();
    }


    /**
     * 点击的位置不是滑动的item，并且该item处于展开状态，将该item收回
     */
    private void rightItemShowOther() {
        if (dragRightViewUtils.getShowItem() != null && dragRightViewUtils.getShowItem() != (this)) {
            if (dragRightViewUtils.isRightShow()) {
                dragRightViewUtils.getShowItem().backItem();
            }

        }
    }

    /**
     * 点击的位置是已经滑动出来的item
     */
    private boolean rightItemShow() {
        boolean temp;
        if (dragRightViewUtils.getShowItem() != null &&
                dragRightViewUtils.getShowItem() != (this)
                ) {

            if (dragRightViewUtils.isRightShow()) {
                dragRightViewUtils.getShowItem().backItem();
                temp = true;
            }
            else {
                temp = false;
            }

        }
        else {
            temp = false;
        }
        return temp;
    }

    private void scrollView() {
        if (moveAllX < -rightMax) {
            moveAllX = -rightMax;
        }
        else if (moveAllX > 0) {
            moveAllX = 0;
        }
        this.scrollTo(-moveAllX, 0);
    }


    private int scrollStart;

    @Override
    public void computeScroll() {

        if (scroller.computeScrollOffset()) {
            moveAllX = scrollStart - scroller.getCurrX();
            scrollView();
            if (dragRightViewUtils.getShowItem() != null)
                LogUtils.toE("computeScroll",
                        "moveAllX:" + dragRightViewUtils.getShowItem().moveAllX);
            LogUtils.toE("computeScroll",
                    "moveAllX:" + moveAllX);

//            LogUtils.toE("computeScroll", "getCurrX" + scroller.getCurrX());
            invalidate();
        }


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.toE("onTouchEvent", "getY:" + event.getAction());
        return true;
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

            ViewGroup.LayoutParams layoutParams=child.getLayoutParams();

            measureChild(child, widthMeasureSpec, heightMeasureSpec);

        }
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

        rightMax = getChildAt(1).getMeasuredWidth();
//        LogUtils.toE("onLayout", "rightMax:" + rightMax);
        getChildAt(0).layout(left, 0, right, getHeight());
        getChildAt(1).layout(right, 0, right + rightMax, getHeight());

    }

    //activity退出或者view被移除的时候调用
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
    //activity创建或者view创建的时候调用
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }
    /**
     * 实现滑动的七种方法
     * layout();
     * 偏移位置
     * offsetLeftAndRight();
     * offsetTopAndBottom();
     * LayoutParams
     * 具体位置
     * scrollTo();
     * 偏移量
     * scrollBy();
     */
}
