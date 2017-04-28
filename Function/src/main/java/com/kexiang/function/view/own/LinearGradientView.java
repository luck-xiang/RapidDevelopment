package com.kexiang.function.view.own;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import com.kxiang.function.R;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/4/27 15:16
 */

public class LinearGradientView extends View {

    private LinearGradient linearGradient = null;
    private Paint paint = null;

    public LinearGradientView(Context context) {
        super(context);
    }

    public LinearGradientView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView( context);
    }

    public LinearGradientView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView( context);
    }


    public void initView(Context context) {
        linearGradient = new LinearGradient(0, 0, 0, 100, new int[]{
                Color.YELLOW, Color.GREEN,getResources().getColor(R.color.color_0ecd9e) , Color.WHITE}, null,
                Shader.TileMode.REPEAT);
        paint = new Paint();
    }



    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
        super.onDraw(canvas);
        //设置渲染器
        paint.setShader(linearGradient);
        //绘制圆环
        canvas.drawRect(0, 0, 150,150, paint);

//        Paint paint = new Paint();
//        paint.setColor(Color.GREEN);
//        LinearGradient linearGradient = new LinearGradient(0, 0, getMeasuredWidth(), getMeasuredHeight(),new int[]{Color.RED, Color.WHITE, Color.BLUE}, null, LinearGradient.TileMode.CLAMP);
//        paint.setShader(linearGradient);
//        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),paint);

    }

}
