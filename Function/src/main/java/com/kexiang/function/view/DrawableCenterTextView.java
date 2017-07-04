package com.kexiang.function.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.TextView;

/**
 * 项目名称:世旭智慧平台
 * 创建人:kexiang
 * 创建时间:2016/8/8 13:05
 */
public class DrawableCenterTextView extends TextView {

    public DrawableCenterTextView(Context context, AttributeSet attrs,
                                  int defStyle) {
        super(context, attrs, defStyle);
    }

    public DrawableCenterTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public DrawableCenterTextView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {


        //通过平移画布实现文字和图片居中，但是同时只能又一个图片居中，
        // 实际思路是将文字和图片在一起，然后通过平移画布实现居中
        Drawable[] drawables = getCompoundDrawables();
        boolean drawableBoolean = false;
        for (int i = 0; i < drawables.length; i++) {
            Drawable drawableItem = drawables[i];

            if (drawableItem != null) {

                if (i == 0 || i == 2) {
                    //获取字体长度
                    float textWidth = getPaint().measureText(getText().toString());
                    //获取drawablePadding值
                    int drawablePadding = getCompoundDrawablePadding();
                    if (i == 0) {
                        setGravity(Gravity.CENTER_VERTICAL);
                        int drawableWidth = drawableItem.getIntrinsicWidth();
                        float bodyWidth = textWidth + drawableWidth + drawablePadding;
                        canvas.translate((getWidth() - bodyWidth) / 2, 0);
                    }
                    else {
                        setGravity(Gravity.CENTER_VERTICAL | Gravity.END);
                        int drawableWidth = drawableItem.getIntrinsicWidth();
                        float bodyWidth = textWidth + drawableWidth + drawablePadding;
                        canvas.translate(-(getWidth() - bodyWidth) / 2, 0);
                    }
                }

                if (i == 1 || i == 3) {
                    // 否则如果上边的Drawable不为空时，获取文本的高度
                    Rect rect = new Rect();
                    getPaint().getTextBounds(getText().toString(), 0,
                            getText().toString().length(), rect);
                    float textHeight = rect.height();
                    int drawablePadding = getCompoundDrawablePadding();

                    if (i == 1) {
                        setGravity(Gravity.CENTER_HORIZONTAL);
                        int drawableHeight = drawableItem.getIntrinsicHeight();
                        // 计算总高度（文本高度 + drawablePadding + drawableHeight）
                        float bodyHeight = textHeight + drawablePadding + drawableHeight;
                        // 移动画布开始绘制的Y轴
                        canvas.translate(0, (getHeight() - bodyHeight) / 2);
                    }
                    else {
                        setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
                        int drawableHeight = drawableItem.getIntrinsicHeight();
                        // 计算总高度（文本高度 + drawablePadding + drawableHeight）
                        float bodyHeight = textHeight + drawablePadding + drawableHeight;
                        // 移动画布开始绘制的Y轴
                        canvas.translate(0, -(getHeight() - bodyHeight) / 2);
                    }

                }
                drawableBoolean = true;
            }
            if (drawableBoolean) {
                break;
            }

        }
        super.onDraw(canvas);
    }

}
