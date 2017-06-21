package com.kexiang.function.view.own.snow;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.kexiang.function.utils.LogUtils;

import java.util.Random;


/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/6/19 16:04
 */

public class SnowFallingView extends View {


    public SnowFallingView(Context context) {
        super(context);
        initView(context);
    }

    public SnowFallingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context);
        initAttrs(context, attrs);
    }

    public SnowFallingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
        initAttrs(context, attrs);
    }

    Random random;
    Paint paint;

    private void initView(Context context) {
        random = new Random();
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
    }

    private void initAttrs(Context context, AttributeSet attrs) {

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < snowSingle.length; i++) {
            snowSingle[i].move(canvas, paint);
        }

        if (start) {
            postInvalidateDelayed(5);
        }

    }

    SnowSingle[] snowSingle;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        LogUtils.toE("onSizeChanged", "onSizeChanged:" + getPaddingTop());
        snowSingle = new SnowSingle[100];
        for (int i = 0; i < snowSingle.length; i++) {
            snowSingle[i] = new SnowSingle(random, w, h, 15, 5,
                    getPaddingLeft(), getPaddingRight(), getPaddingTop(), getPaddingBottom()
            );
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    public void layout(int l, int t, int r, int b) {
        super.layout(l, t, r, b);

    }

    //activity退出或者view被移除的时候调用
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        stop();
        LogUtils.toE("生命周期", "onDetachedFromWindow");
    }

    //activity创建或者view创建的时候调用
    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        start();
        LogUtils.toE("生命周期", "onAttachedToWindow");
    }


    private boolean start = true;

    public void stop() {
        start = false;
    }

    public void start() {
        start = true;
        invalidate();
    }
}
