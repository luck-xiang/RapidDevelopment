package com.kexiang.function.view.own.star;

import android.animation.ObjectAnimator;
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
import android.view.animation.AccelerateInterpolator;

import com.kexiang.function.utils.LogUtils;
import com.kxiang.function.R;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/5/24 14:16
 */

public class StarLikesView extends View {
    public StarLikesView(Context context) {
        super(context);
        initView(context);
    }

    public StarLikesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initType(context, attrs);
        initView(context);
    }

    public StarLikesView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initType(context, attrs);
        initView(context);
    }


    private void initType(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.StarLikesView);
        starNumbers = a.getInt(R.styleable.StarLikesView_starNumbers, starNumbers);
        starPadding = a.getDimensionPixelSize(R.styleable.StarLikesView_starPadding, starPadding);
        starSingleSize = a.getDimensionPixelSize(R.styleable.StarLikesView_starSingleSize,
                starSingleSize);
        a.recycle();
    }


    Bitmap starFilled;
    Bitmap starEmpty;
    private Paint mPaint;

    private void initView(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        starFilled = BitmapFactory.decodeResource(getResources(), R.drawable.star_filled);
        starEmpty = BitmapFactory.decodeResource(getResources(), R.drawable.star_empty);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < select; i++) {
            canvas.save();
            canvas.translate(starPadding + (starSingleSize + starPadding) * i, toTop);
            if (i == select - 1) {

                if (stytle == SCALE) {
                    float scale = 2 * toTop / starSingleSize * starFilledPosition / scaleSize + 1;
//                float scale = 2*toTop / starSingleSize  + 1;
                    if (scale > 2f) {
                        scale = 2f;
                    }
                    canvas.scale(
                            scale, scale,
                            0.5f * starSingleSize, 0.5f * starSingleSize
                    );
                }
                else if (stytle == ROTATE) {

                    float rotate = 720 / scaleSize * starFilledPosition;
                    canvas.rotate(rotate, 0.5f * starSingleSize, 0.5f * starSingleSize);
                }


            }
            canvas.drawBitmap(starFilled, null, normalRect, null);
            canvas.restore();
        }
        for (int i = select; i < starNumbers; i++) {

            canvas.save();
            canvas.translate(starPadding + (starSingleSize + starPadding) * i, toTop);
            canvas.drawBitmap(starEmpty, null, normalRect, null);
            canvas.restore();

        }

    }

    public static final int NORMAL = 0;
    public static final int SCALE = 1;
    public static final int ROTATE = 2;
    private int stytle = NORMAL;

    public void setStytle(int stytle) {
        if (stytle != NORMAL && stytle != SCALE && stytle != ROTATE)
            return;
        this.stytle = stytle;
    }

    private int starFilledPosition;

    public void setStarFilledPosition(int starFilledPosition) {
        LogUtils.toE("setStarFilledPosition", "starFilledPosition:" + starFilledPosition);
        this.starFilledPosition = starFilledPosition;
        invalidate();
    }


    public void addStar() {
        animator.cancel();
        setStarFilledPosition(0);
        select++;
        if (select > starNumbers) {
            select = starNumbers;
        }
        if (onSelectListener != null) {
            onSelectListener.onSelect(select);
        }
        animator.start();
    }

    public void subStar() {
        animator.cancel();
        setStarFilledPosition(0);
        select--;
        if (select < 0) {
            select = 0;
        }
        if (onSelectListener != null) {
            onSelectListener.onSelect(select);
        }
        animator.start();
    }


    /**
     * 选中星星的位置
     *
     * @return
     */
    public int getSelect() {
        return select;
    }

    Rect normalRect;
    private ObjectAnimator animator;
    private int starNumbers = 6;
    private int scaleSize = 100;
    private float toTop = 10;
    private int select = 0;
    private int starSingleSize = 80;
    private int selectSingleSize;
    private int starPadding = 10;

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
//        starSingleSize = (int) (h * 0.8);
//        starPadding = (int) (0.1 * h);
        toTop = 0.5f * (getHeight() - starSingleSize);
        animator = ObjectAnimator.ofInt(this, "starFilledPosition", 0, scaleSize, 0);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(500);
        selectSingleSize = (w - starPadding) / starNumbers;
        normalRect = new Rect(0, 0, starSingleSize, starSingleSize);
        LogUtils.toE("onSizeChanged", "onSizeChanged:");
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

//        LogUtils.toE("onTouchEvent", "getY:" + event.getX());
        int tempSelect;
//        float temp = (event.getX() - 0.1f * selectSingleSize) / selectSingleSize;
////        float temp = (event.getX() - 0.1f * selectSingleSize) / selectSingleSize;
//        if (temp >= starNumbers) {
//            tempSelect = starNumbers;
//        }
//        else if (temp < 0) {
//            tempSelect = 0;
//        }
//        else {
//            tempSelect = (int) temp + 1;
//        }
        tempSelect = getSelectPosition((int) event.getX());
        LogUtils.toE("onTouchEvent", "tempSelect:" + tempSelect);
        LogUtils.toE("onTouchEvent", "selectSingleSize:" + selectSingleSize);

        if (tempSelect == 0) {
            return true;
        }
        else if (tempSelect == -1) {
            tempSelect = 0;
        }


        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                animator.cancel();
                setStarFilledPosition(0);
                select = tempSelect;
                animator.start();
                break;
            case MotionEvent.ACTION_MOVE:
                if (select != tempSelect) {
                    animator.cancel();
                    animator.start();
                }
                select = tempSelect;
//                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                if (select != tempSelect) {
                    select = tempSelect;
                    invalidate();
                }
                if (onSelectListener != null) {
                    onSelectListener.onSelect(select);
                }
                break;

        }
        return true;
    }


    private int getSelectPosition(int x) {

        int select = 0;

        if (x < starPadding) {
            select = -1;
        }
        else if (x > ((starSingleSize + starPadding) * starNumbers)) {
            select = starNumbers;
        }
        else {
            for (int i = 1; i <= starNumbers; i++) {

                if (x - (starSingleSize + starPadding) * i < 0 &&
                        x - (starPadding + (starSingleSize + starPadding) * (i - 1)) > 0
                        ) {
                    select = i;
                    break;
                }

//                    if (starPadding + (starSingleSize + starPadding) * i < x &&
//                            x < starPadding + starSingleSize * (i + 1)) {
//                        select = i;
//                        break;
//                    }
            }
        }
        return select;
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
//        生成测量工具
//        int heightMeasureSpecTest = MeasureSpec.makeMeasureSpec(heightSize, heightMode);

        if (widthMode == MeasureSpec.AT_MOST) {
            //相当于我们设置为wrap_content
            width = (starSingleSize + starPadding) * starNumbers + starPadding;
        }
//        else if (widthMode == MeasureSpec.EXACTLY) {
//            //相当于我们设置为match_parent或者为一个具体的值
//            width = widthSize;
//        }
        if (heightMode == MeasureSpec.AT_MOST) {
            //相当于我们设置为wrap_content
            height = (int) (starSingleSize + toTop * 2);
        }
//        else if (heightMode == MeasureSpec.EXACTLY) {
//            //相当于我们设置为match_parent或者为一个具体的值
//            height = widthSize;
//        }
        setMeasuredDimension(width, height);
    }


    public interface OnSelectListener {
        void onSelect(int select);


    }

    private OnSelectListener onSelectListener;

    public void setOnSelectListener(OnSelectListener onSelectListener) {
        this.onSelectListener = onSelectListener;
    }
}
