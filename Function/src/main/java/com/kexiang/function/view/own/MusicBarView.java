package com.kexiang.function.view.own;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Scroller;

import com.kexiang.function.utils.LogUtils;
import com.kxiang.function.R;

import java.util.Random;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/4/27 14:10
 */

public class MusicBarView extends View {
    public MusicBarView(Context context) {
        super(context);
        initView();
    }

    public MusicBarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public MusicBarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

    }

    Paint paint;
    Random random;

    /**
     * 用于完成滚动操作的实例
     */
    private Scroller mScroller;

    private void initView() {
        mScroller = new Scroller(getContext());
        mScroller.startScroll(0, 0, 0, 400, 2000);
        paint = new Paint();

        random = new Random();
//        paint.setColor(getResources().getColor(R.color.color_ffa200));
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (true) {
                    try {
                        Thread.sleep(100);
                        post(new Runnable() {
                            @Override
                            public void run() {
//                                bar++;
//                                if (bar > barNumber)
//                                    bar = 1;
//                                invalidate();
                                invalidate();
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }


            }
        }).start();
    }


    @Override
    public void computeScroll() {

        // 第三步，重写computeScroll()方法，并在其内部完成平滑滚动的逻辑
        LogUtils.toE("computeScroll", "computeScroll:" + mScroller.computeScrollOffset());
        if (mScroller.computeScrollOffset()) {
            ViewCompat.postInvalidateOnAnimation(this);
        }

    }

    private int bar = 10;
    private final int barNumber = 10;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int barSingleSize = widthSize / barNumber / 2;
        int barSingleHight = heightSize / barNumber;

        for (int i = 0; i < bar; i++) {
//            canvas.drawRect(40*i*2, 800,40*i*2+40, 800-40*i, paint);
            canvas.drawRect(
                    barSingleSize * i * 2,
//                    heightSize - barSingleHight * i,
                    heightSize * random.nextInt(barNumber) / barNumber,
                    barSingleSize * i * 2 + barSingleSize,
                    heightSize,
                    paint);
//            canvas.drawRect(0, 760,40, 800, paint);
        }

    }

    LinearGradient linearGradient;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        widthSize = w;
        heightSize = h;
        linearGradient = new LinearGradient(
                0, 0, w, h,
                getResources().getColor(R.color.color_FE749C),
                getResources().getColor(R.color.color_fa4672),
                Shader.TileMode.CLAMP
        );
        paint.setShader(linearGradient);
    }

    private int widthSize = 0;
    private int heightSize = 0;

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        widthSize = MeasureSpec.getSize(widthMeasureSpec);
        heightSize = MeasureSpec.getSize(heightMeasureSpec);
    }
}
