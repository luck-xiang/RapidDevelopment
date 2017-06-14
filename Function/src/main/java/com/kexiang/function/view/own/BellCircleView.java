package com.kexiang.function.view.own;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.kxiang.function.R;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/4/28 9:48
 */

public class BellCircleView extends View {
    public BellCircleView(Context context) {
        super(context);
        initView();
    }

    public BellCircleView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BellCircleView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private Paint mPaint;
    /**
     * 圆框大小
     */
    private float strokeWidth;
    /**
     * 针脚大小
     */
    private float strokeStitchSize;
    /**
     * 针脚长度
     */
    private float stitchBig;

    private void initView() {
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(strokeWidth);
        mPaint.setColor(getResources().getColor(R.color.dimgray));
    }

    private int hour = 0, minute = 0, second = 0;

    /**
     * 十二小时制度
     *
     * @param hour：时
     * @param minute：分
     * @param second：秒
     */

    public void initData(int hour, int minute, int second) {
        if (hour - 6 < 0) {
            this.hour = hour + 6;
        }
        else {
            this.hour = hour - 6;
        }

        if (minute - 30 < 0) {
            this.minute = minute + 30;
        }
        else {
            this.minute = minute - 30;
        }

        if (second - 30 < 0) {
            this.second = second + 30;
        }
        else {
            this.second = second - 30;
        }

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(strokeWidth);
        canvas.drawCircle(radius, radius, radius - strokeWidth - 1, mPaint);
        canvas.save();
        for (int i = 0; i < 60; i++) {
            if (i % 5 == 0) {
                mPaint.setStrokeWidth(strokeWidth);
                canvas.drawLine(radius, strokeWidth + 1, radius, strokeWidth + 1 + stitchBig * 2, mPaint);
            }
            else {
                mPaint.setStrokeWidth(strokeWidth / 2);
                canvas.drawLine(radius, strokeWidth + 1, radius, strokeWidth + 1 + stitchBig, mPaint);
            }
            canvas.rotate(360 / 60, radius, radius);
        }
        canvas.restore();


        //时针
        canvas.save();
        canvas.translate(radius, radius);
        int minuteTemp;
        if (minute < 30) {
            minuteTemp = minute + 30;
        }
        else {
            minuteTemp = minute - 30;
        }
        canvas.rotate(360 / 12 * hour + minuteTemp / 60f * 30);
        mPaint.setStrokeWidth(strokeWidth * 1.2f);
        canvas.drawLine(0, 0, 0, radius * 0.6f, mPaint);
        canvas.restore();

        //分针
        canvas.save();
        canvas.translate(radius, radius);
        int secondTemp;
        if (second < 30) {
            secondTemp = second + 30;
        }
        else {
            secondTemp = second - 30;
        }
        canvas.rotate(360 / 60 * minute + secondTemp / 60f * 5);
//        canvas.rotate(360 / 60 * minute);
        mPaint.setStrokeWidth(strokeWidth * 0.8f);
        canvas.drawLine(0, 0, 0, radius * 0.75f, mPaint);
        canvas.restore();

        //秒针
        canvas.save();
        canvas.translate(radius * 0.8f, radius * 0.8f);
        canvas.rotate(360 / 60 * second, radius * 0.2f, radius * 0.2f);
        mPaint.setStrokeWidth(strokeWidth * 0.5f);
        canvas.drawLine(radius * 0.2f, 0, radius * 0.2f, radius, mPaint);
        canvas.restore();


        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(radius, radius, radius / 30, mPaint);

    }

    private int radius = 0;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//        生成测量工具
//        int heightMeasureSpecTest = MeasureSpec.makeMeasureSpec(heightSize, heightMode);

        if (widthMode == MeasureSpec.AT_MOST) {
            //相当于我们设置为wrap_content
        }
        else if (widthMode == MeasureSpec.EXACTLY) {
            //相当于我们设置为match_parent或者为一个具体的值
        }
        if (widthSize == 0 || heightSize == 0) {
            setMeasuredDimension(100, 100);
        }
        else if (widthSize > heightSize) {
            setMeasuredDimension(heightSize, heightSize);
        }
        else if (widthSize < heightSize) {
            setMeasuredDimension(widthSize, widthSize);
        }
        else {
            setMeasuredDimension(widthSize, heightSize);
        }
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (w == 0 || h == 0) {
            radius = 100 / 2;
        }
        else if (w > h) {
            radius = h / 2;
        }
        else {
            radius = w / 2;
        }
        stitchBig = radius / 13;
        strokeWidth = radius / 30;
        strokeStitchSize = strokeWidth / 2;
        if (stitchBig < 1) {
            stitchBig = 1;
        }
        if (strokeStitchSize < 2) {
            strokeStitchSize = 2;
        }
        if (strokeWidth < 2) {
            strokeWidth = 2;
        }

    }

}
