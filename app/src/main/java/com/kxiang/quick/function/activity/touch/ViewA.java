package com.kxiang.quick.function.activity.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

import com.kexiang.function.utils.LogUtils;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/4/25 9:30
 */

public class ViewA extends RelativeLayout {
    public ViewA(Context context) {
        super(context);
    }

    public ViewA(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewA(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * true:不执行后面的事件
     * 事件的拦截
     *
     * @param ev
     * @return
     */

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtils.toE("ViewA", "dispatchTouchEvent："+ev.getAction());
        return super.dispatchTouchEvent(ev);
//        return true;
    }


    /**
     * 事件的分发
     *
     * @param ev
     * @return
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev){
        LogUtils.toE("ViewA", "onInterceptTouchEvent："+ev.getAction());
       return super.onInterceptTouchEvent(ev);
//        return true;
    }

    /**
     * 处理
     * 事件
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.toE("ViewA", "onTouchEvent："+event.getAction());
       return super.onTouchEvent(event);
//        return true;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public void computeScroll() {


        super.computeScroll();
    }
}
