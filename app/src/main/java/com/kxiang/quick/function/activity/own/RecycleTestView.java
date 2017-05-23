package com.kxiang.quick.function.activity.own;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;

import com.kexiang.function.utils.LogUtils;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/5/9 10:11
 */

public class RecycleTestView extends RecyclerView {
    public RecycleTestView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public RecycleTestView(Context context) {
        super(context);
    }

    public RecycleTestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {
        LogUtils.toE("RecycleTestView","onTouchEvent:"+e.getAction());
        return super.onTouchEvent(e);
    }
}
