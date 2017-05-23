package com.kexiang.function.view.refresh;

import android.content.Context;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Scroller;

import com.kexiang.function.utils.LogUtils;
import com.ybao.pullrefreshview.support.impl.Pullable;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/5/8 14:21
 */

public class FlingScrollerLayout extends FrameLayout {


    public FlingScrollerLayout(Context context) {
        super(context);
        initView();
    }

    public FlingScrollerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public FlingScrollerLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    /**
     * 用于完成滚动操作的实例
     */
    private Scroller mScroller;

    /**
     * 判定为拖动的最小移动像素数
     */
    private int mTouchSlop;

    protected int headerHeigh = 0;
    protected int footerHeigh = 0;

    /**
     * 上拉
     */
    public static final int PULL_UP = 0;
    /**
     * 下拉
     */
    public static final int PULL_DOWN = 1;

    protected int pullType = PULL_DOWN;
    protected int motionEvent;

    public int getMotionEvent() {
        return motionEvent;
    }

    ViewConfiguration viewConfiguration;

    private void initView() {
        // 第一步，创建Scroller的实例
        mScroller = new Scroller(getContext());
        viewConfiguration = ViewConfiguration.get(getContext());
        mTouchSlop = viewConfiguration.getScaledTouchSlop();

    }

    private float actionMoveX = 0;
    private float actionMoveY = 0;
    protected int refreshingTpye = RefreshHeaderable.NORMAL;
    private int mPointerId;
    private int moveAllSize = 0;

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        LogUtils.toE("MotionEvent", "MotionEvent:" + ev.getAction());

        if (stop) {
            return super.dispatchTouchEvent(ev);
        }

        if (!mScroller.isFinished()) {
            mScroller.abortAnimation();
        }
        //获取触控点的数量，比如2则可能是两个手指同时按压屏幕
        int pointerCount = ev.getPointerCount();
        int pointerIndex = ev.getActionIndex();
//        LogUtils.toE("dispatchTouchEvent", "pointerCount:" + pointerCount);

        //多点触碰调用getActionMasked

        switch (ev.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                motionEvent = MotionEvent.ACTION_DOWN;
                mPointerId = ev.getPointerId(pointerIndex);
                actionMoveY = ev.getY(pointerIndex);
                if (moveAllSize != 0) {
                    return true;
                }
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                mPointerId = ev.getPointerId(pointerIndex);
                actionMoveY = ev.getY(pointerIndex);
                break;
            case MotionEvent.ACTION_POINTER_UP:
                // 获取离开屏幕的手指的索引
                int pointerIdLeave = ev.getPointerId(pointerIndex);
                if (mPointerId == pointerIdLeave) {
                    // 离开屏幕的正是目前的有效手指，
                    // 此处需要重新调整，并且需要重置VelocityTracker
                    int reIndex = pointerIndex == 0 ? 1 : 0;
                    mPointerId = ev.getPointerId(reIndex);
                    // 调整触摸位置，防止出现跳动
                    actionMoveY = ev.getY(reIndex);
                }
                break;

            case MotionEvent.ACTION_MOVE:
                motionEvent = MotionEvent.ACTION_MOVE;
                pointerIndex = ev.findPointerIndex(mPointerId);
                float my;
                if (pointerCount > pointerIndex && pointerIndex >= 0) {
                    my = ev.getY(pointerIndex);
                }
                else {
                    my = ev.getY();
                }


                float moveMul = Math.abs(moveAllSize) * 9 / maxMoveY + 1;
                float moveY = (my - actionMoveY) / moveMul;
                if (moveAllSize == 0) {
                    if (pullable.isGetTop() && moveY > 0 ||
                            pullable.isGetBottom() && moveY < 0) {
                        setY((int) moveY);
                        setViewTranslationY(mPullView);
//                        actionMoveY = my;
                        return true;
                    }
                }
                else {
                    LogUtils.toE("setAction", "setAction:" + moveAllSize);
                    //当不在0,0处
                    ev.setAction(MotionEvent.ACTION_CANCEL);//屏蔽原事件
                    if ((moveAllSize < 0 && moveY + moveAllSize >= 0) ||
                            (moveAllSize > 0 && moveY + moveAllSize <= 0)) {
                        ev.setAction(MotionEvent.ACTION_DOWN);
                        setY(-moveAllSize);
                        setViewTranslationY(mPullView);
                    }
                    else {
                        setY((int) moveY);
                        setViewTranslationY(mPullView);
                    }
//                    actionMoveY = my;
                }
                actionMoveY = my;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                motionEvent = MotionEvent.ACTION_UP;
                if (moveAllSize > 0) {
                    if (moveAllSize >= headerHeigh) {
                        mScroller.startScroll(0, moveAllSize,
                                0, -moveAllSize + headerHeigh, 500);
                    }
                    else {
                        mScroller.startScroll(0, moveAllSize, 0,
                                -moveAllSize, 500);
                    }

                }
                else if (moveAllSize < 0) {
                    if (moveAllSize < -footerHeigh) {
                        mScroller.startScroll(0, moveAllSize, 0,
                                -moveAllSize + footerHeigh, 500);
                    }
                    else {
                        mScroller.startScroll(0, moveAllSize, 0,
                                -moveAllSize, 500);
                    }
                }
                invalidate();
                break;

        }
        return super.dispatchTouchEvent(ev);
    }

    public void startScroll(int y) {
        mScroller.startScroll(0, 0, 0, y, 500);
        invalidate();
    }

    boolean stop = false;

    public void stop() {
        stop = true;
        mScroller.startScroll(0, moveAllSize, 0, -moveAllSize, 500);
        invalidate();
    }


    public int getPullType() {
        return pullType;
    }

    private void setY(int value) {
        moveAllSize = moveAllSize + value;
    }

    protected void setViewTranslationY(View view, int moveAllSize) {
    }

    private void setViewTranslationY(View view) {
        if (view == null) {
            return;
        }
        if (moveAllSize > 0) {
            pullType = PULL_DOWN;
        }
        else if (moveAllSize < 0) {
            pullType = PULL_UP;
        }
        LogUtils.toE("moveAllSize", "moveAllSize:" + moveAllSize);
        ViewCompat.setTranslationY(view, moveAllSize);
//        view.offsetTopAndBottom(value);
//        ViewCompat.setTranslationY(view, moveAllSize);
        setViewTranslationY(view, moveAllSize);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return true;
    }

    protected View mPullView;
    protected Pullable pullable;
    protected int addViewSize = 1;

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {

        if (getChildCount() > addViewSize - 1) {
            throw new IllegalStateException("FlingScrollerLayout can host only "
                    + addViewSize + " direct child");
        }

        if (!(child instanceof RefreshHeaderable) && !(child instanceof LoadFooterable)) {
            mPullView = child;
            pullable = CanPullUtil.getPullAble(child);
        }

        super.addView(child, index, params);
    }

    private int maxMoveY;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        maxMoveY = (int) (0.9 * h);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }


    @Override
    public void computeScroll() {
        if (!mScroller.isFinished()) {
            // 第三步，重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
            if (mScroller.computeScrollOffset()) {
                LogUtils.toE("computeScroll", "getCurrY:" + mScroller.getCurrY());
                moveAllSize = mScroller.getCurrY();
                setViewTranslationY(mPullView);
                invalidate();
            }
            else {
                if (stop) {
                    moveAllSize = 0;
                }
                stop = false;
            }
        }
        else {
            if (stop) {
                moveAllSize = 0;
            }
            stop = false;
        }
    }

    public boolean isStop() {
        return stop;
    }

    /**
     * 实现滑动的七种方法
     * layout();
     * offsetLeftAndRight();
     * offsetTopAndBottom();
     * LayoutParams
     * scrollTo();
     * scrollBy();
     */
}
