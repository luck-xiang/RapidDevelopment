package com.kxiang.quick.function.activity.own;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/5/2 14:09
 */

public class BaseGraphicsView extends View {
    public BaseGraphicsView(Context context) {
        super(context);
        initView();
    }

    public BaseGraphicsView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BaseGraphicsView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    Paint mPaint;

    private void initView() {

        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(getResources().getColor(com.kxiang.function.R.color.plum));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(20);
        //设置阴影
//        mPaint.setShadowLayer(10, 15, 15, Color.GREEN);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint=new Paint();
        paint.setColor(Color.RED);  //设置画笔颜色
        paint.setStyle(Paint.Style.STROKE);//填充样式改为描边
        paint.setStrokeWidth(5);//设置画笔宽度

        Path path = new Path();
        

        path.moveTo(10, 10); //设定起始点
        path.lineTo(10, 100);//第一条直线的终点，也是第二条直线的起点
        path.lineTo(300, 100);//画第二条直线
//        path.moveTo(400, 100);
        path.lineTo(500, 100);//第三条直线
        path.close();//闭环


        canvas.drawPath(path, paint);

//        Paint paint=new Paint();
//        paint.setColor(Color.RED);  //设置画笔颜色
//        paint.setStyle(Paint.Style.FILL);//设置填充样式
//        paint.setStrokeWidth(15);//设置画笔宽度
//        RectF rect = new RectF(0, 0, 500, 200);
//        canvas.drawRoundRect(rect, 200, 80, paint);
    }
}
