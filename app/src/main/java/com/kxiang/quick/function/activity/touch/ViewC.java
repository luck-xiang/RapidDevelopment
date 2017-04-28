package com.kxiang.quick.function.activity.touch;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.kexiang.function.utils.LogUtils;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/4/25 9:30
 */

public class ViewC extends View {
    public ViewC(Context context) {
        super(context);
    }

    public ViewC(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ViewC(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        LogUtils.toE("ViewC","dispatchTouchEvent："+ev.getAction());


        return super.dispatchTouchEvent(ev);
       // return true;
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        LogUtils.toE("ViewC","onTouchEvent："+event.getAction());
        return super.onTouchEvent(event);
//        return true;
    }
}
