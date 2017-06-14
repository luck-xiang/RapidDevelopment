package com.kexiang.function.view.own;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.kexiang.function.utils.LogUtils;
import com.kxiang.function.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/5/26 15:27
 */

public class ComeBackMenuView extends View
        implements ValueAnimator.AnimatorUpdateListener {
    public ComeBackMenuView(Context context) {
        super(context);
        initView(context);
    }

    public ComeBackMenuView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initType(context, attrs);
        initView(context);
    }

    public ComeBackMenuView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initType(context, attrs);
        initView(context);
    }


    private void initType(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ComeBackMenuView);
        alphaStart = a.getInt(R.styleable.ComeBackMenuView_alphaStart, alphaStart);
        alphaEnd = a.getInt(R.styleable.ComeBackMenuView_alphaStart, alphaEnd);
        moveMax = a.getDimension(R.styleable.ComeBackMenuView_moveMax, moveMax);
        itemSize = (int) a.getDimension(R.styleable.ComeBackMenuView_itemSize, itemSize);
        starBoolean = !a.getBoolean(R.styleable.ComeBackMenuView_starBoolean, starBoolean);
        itemCenterSize = (int) a.getDimension(R.styleable.ComeBackMenuView_itemCenterSize,
                itemCenterSize);
        radianStart = a.getInt(R.styleable.ComeBackMenuView_radianStart, radianStart);
        radianEnd = a.getInt(R.styleable.ComeBackMenuView_radianEnd, radianEnd);

        a.recycle();

        if (starBoolean) {
            move = 0;
            alphaSize = alphaStart;
            scaleSize = alphaStart;

        }
        else {
            move = (int) moveMax;
            alphaSize = alphaEnd;
            scaleSize = SIZE_MUL;
        }
        radianSize = radianEnd - radianStart;
    }


    private List<Bitmap> itemDraw;
    private Paint itemPaint;
    private ValueAnimator itemAnimatorStart;
    private ValueAnimator itemAnimatorEnd;

    private int alphaStart = 10;
    private int alphaEnd = 255;
    private int alphaSize = alphaStart;

    private int radianStart = 0;
    private int radianEnd = 180;
    private int radianSize = 180;

    private float moveMax = 200;

    private static final float SIZE_MUL = 100;
    private int aniTimeSize = 300;
    private int move = 0;
    private float scaleSize;

    private void initView(Context context) {
        itemPaint = new Paint();
        // 设置alpha不透明度，范围为0~255
        itemPaint.setAlpha(alphaSize);
        // 是否抗锯齿
        itemPaint.setAntiAlias(true);
        itemDraw = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            itemDraw.add(BitmapFactory
                    .decodeResource(context.getResources(), R.drawable.star_filled)
            );
        }

        itemAnimatorStart = ValueAnimator.ofFloat(0, SIZE_MUL);
        itemAnimatorStart.setDuration(aniTimeSize);
        itemAnimatorEnd = ValueAnimator.ofFloat(SIZE_MUL, 0);
        itemAnimatorEnd.setDuration(aniTimeSize);
        itemAnimatorEnd.addUpdateListener(this);
        itemAnimatorStart.addUpdateListener(this);


        itemAnimatorEnd.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                starBoolean = true;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                starBoolean = true;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        itemAnimatorStart.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                starBoolean = false;
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                starBoolean = false;
            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        drawMenuByTrans(canvas);
        drawMenuByLocation(canvas);
    }


    private void drawMenuByTrans(Canvas canvas) {
        int translateX = (getWidth() - itemSize) / 2 - move;
        int translateY = getHeight() - itemSize;

        for (int i = itemDraw.size() - 1; i >= 0; i--) {
            canvas.save();
            itemPaint.setAlpha(alphaSize);
            canvas.translate(translateX, translateY);
            canvas.rotate(180 / (itemDraw.size() - 1) * i, move + itemSize / 2, itemSize / 2);
            canvas.scale(move * 1f / SIZE_MUL, move * 1f / SIZE_MUL,
                    itemSize / 2, itemSize / 2);
            canvas.drawBitmap(itemDraw.get(0), null, rectItem, itemPaint);
            canvas.restore();

        }

        canvas.save();
        canvas.translate(leftSize, getHeight() - itemSize);
        canvas.drawBitmap(itemDraw.get(0), null, rectItem, null);
        canvas.restore();
    }


    boolean starBoolean = true;

    public void starAlpha() {

        if (starBoolean) {
            if (!itemAnimatorStart.isRunning()) {
                LogUtils.toE("starAlpha", "Start");
                itemAnimatorStart.start();
            }
        }
        else {
            if (!itemAnimatorEnd.isRunning()) {
                LogUtils.toE("starAlpha", "End");
                itemAnimatorEnd.start();

            }

        }


    }

    private void drawMenuByLocation(Canvas canvas) {
        itemPaint.setAlpha(alphaSize);
        LogUtils.toE("drawMenuByLocation", "alphaSize:" + alphaSize);
        LogUtils.toE("drawMenuByLocation", "move:" + move);
        canvas.save();
        canvas.scale(scaleSize * 1f / SIZE_MUL, scaleSize * 1f / SIZE_MUL,
                leftSize+itemCenterSize/2, getHeight() - itemSize / 2
        );

        for (int i = 0; i < itemDraw.size(); i++) {

            double radians = (Math.PI / 180) * (radianSize / (itemDraw.size() - 1) * i + radianStart);
            int moveX = (int) (Math.cos(radians) * move);
            int moveY = (int) (Math.sin(radians) * move);

//            canvas.drawBitmap(itemDraw.get(0), null,
//                    new Rect((getWidth() - itemSize) / 2 - moveX,
//                            getHeight() - itemSize - moveY,
//                            (getWidth() + itemSize) / 2 - moveX,
//                            getHeight() - moveY), itemPaint);
            canvas.drawBitmap(itemDraw.get(0), null,
                    new Rect(leftSize - moveX,
                            getHeight() - itemSize - moveY,
                            leftSize + itemSize - moveX,
                            getHeight() - moveY), itemPaint);

        }
        canvas.restore();
//        canvas.drawBitmap(itemDraw.get(0), null,
//                new Rect(
//                        (getWidth() - itemCenterSize) / 2, getHeight() - itemCenterSize,
//                        (getWidth() + itemCenterSize) / 2, getHeight()
//                ), null);

        canvas.drawBitmap(itemDraw.get(0), null,
                new Rect(
                        leftSize, getHeight() - itemCenterSize,
                        leftSize + itemCenterSize, getHeight()
                ), null);


    }

    private Rect rectItem;
    private Rect rectItemCenter;
    private int itemSize = 80;
    private int itemCenterSize = 80;
    private int leftSize;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        rectItem = new Rect(0, 0, itemSize, itemSize);
        rectItemCenter = new Rect(0, 0, itemCenterSize, itemCenterSize);
        leftSize = Math.abs((int) (Math.cos((Math.PI / 180d) * radianStart) * moveMax));
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        final int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        final int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        final int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int width = widthSize;
        int height = heightSize;
        if (widthMode == MeasureSpec.AT_MOST) {
            int left = Math.abs((int) (Math.cos((Math.PI / 180d) * radianStart) * moveMax));
            int right = Math.abs((int) (Math.cos((Math.PI / 180d) * radianEnd) * moveMax));

            if (left < itemCenterSize / 2 || right < itemCenterSize / 2) {
                width = left + right + itemCenterSize / 2 + itemSize / 2;
            }
            else if (left < itemCenterSize / 2 && right < itemCenterSize / 2) {
                width = itemCenterSize;
            }
            else if (radianEnd < 90) {
                width = left + itemCenterSize / 2 + itemSize / 2;
            }
            else {
                width = left + right + itemSize;
            }
        }
        if (heightMode == MeasureSpec.AT_MOST) {
            height = (int) (moveMax + itemCenterSize / 2 + itemSize / 2);
        }

        setMeasuredDimension(width, height);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                int select;
                if (select(
                        event.getX(),
                        event.getY(),
                        leftSize + itemCenterSize / 2,
                        getHeight() - itemCenterSize / 2,
                        itemSize / 2)) {
                    starAlpha();
                }
                else if (
                        !starBoolean &&
                                (select = selectItem(event.getX(), event.getY())) != -1) {

                    if (onSelectItemListener != null) {
                        onSelectItemListener.OnSelectItem(select);
                    }
                    LogUtils.toE("selectItem", "selectItem:" + select);
                }

                break;


        }

        return true;
    }


    private int selectItem(float x, float y) {


        int item = -1;
        for (int i = 0; i < itemDraw.size(); i++) {
            double radians = (Math.PI / 180) * (radianSize / (itemDraw.size() - 1) * i + radianStart);
            int moveX = (int) (Math.cos(radians) * move);
            int moveY = (int) (Math.sin(radians) * move);
            int radiusX = leftSize - moveX + itemSize / 2;
            int radiusY = getHeight() - itemSize - moveY + itemSize / 2;
            if (select(x, y, radiusX, radiusY, itemSize)) {
                item = i;
                break;
            }
        }

        return item;
    }


    private boolean select(float x, float y, int radiusX, int radiusY, int radius) {

        double temp = Math.sqrt(
                (Math.pow(Math.abs(x - radiusX), 2) + Math.pow(Math.abs(y - radiusY), 2))
        );
        LogUtils.toE("onTouchEvent", "temp:" + temp);
        if (radius > temp) {
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        float size = (float) animation.getAnimatedValue();
        alphaSize = (int) ((alphaEnd - alphaStart) / SIZE_MUL * size + alphaStart);
        move = (int) (size / SIZE_MUL * moveMax);
        scaleSize = size;
        invalidate();
    }

    public void setItemDraw(List<Bitmap> itemDraw) {
        this.itemDraw = itemDraw;
        invalidate();
    }


    public interface OnSelectItemListener {
        void OnSelectItem(int position);
    }

    OnSelectItemListener onSelectItemListener;

    public void setOnSelectItemListener(OnSelectItemListener onSelectItemListener) {
        this.onSelectItemListener = onSelectItemListener;
    }
}
