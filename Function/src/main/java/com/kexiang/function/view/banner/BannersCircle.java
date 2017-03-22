package com.kexiang.function.view.banner;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.view.View;

import com.kxiang.function.R;


/**
 * 自定义圆球显示
 */
public class BannersCircle extends View {

    private int radius = 5;
    private int count = 0;

    private int choosePosition = 0;

    private int gap = 40;
    private Bitmap select;
    private Bitmap normal;


    public BannersCircle(Context context) {
        super(context);
        select = BitmapFactory.decodeResource(getResources(), R.drawable.banner_select);
        normal = BitmapFactory.decodeResource(getResources(), R.drawable.banner_normal);

    }

    public BannersCircle(Context context, AttributeSet attrs) {
        super(context, attrs);
        select = BitmapFactory.decodeResource(getResources(), R.drawable.banner_select);
        normal = BitmapFactory.decodeResource(getResources(), R.drawable.banner_normal);

    }

    public BannersCircle(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        select = BitmapFactory.decodeResource(getResources(), R.drawable.banner_select);
        normal = BitmapFactory.decodeResource(getResources(), R.drawable.banner_normal);
    }

    public BannersCircle(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr);
        select = BitmapFactory.decodeResource(getResources(), R.drawable.banner_select);
        normal = BitmapFactory.decodeResource(getResources(), R.drawable.banner_normal);
    }

    public void setCount(int cnt) {
        count = cnt;
    }

    public void choose(int pos) {
        choosePosition = pos;
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int w = this.getWidth();
        int h = this.getHeight();

        int start_x = (w - (count - 1) * gap) / 2;

        for (int i = 0; i < count; i++) {
            if (choosePosition == i) {

                canvas.drawBitmap(select, start_x + i * gap, h - 50, null);
            } else {

                canvas.drawBitmap(normal, start_x + i * gap, h - 50, null);

            }
        }
    }


}
