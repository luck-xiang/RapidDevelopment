package com.kexiang.function.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kxiang.function.R;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/4/27 9:20
 */

public class TitleBar extends RelativeLayout implements View.OnClickListener {

    private int heightPixels;


    public TitleBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public TitleBar(Context context) {
        super(context);

    }

    public TitleBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(context, attrs);
    }


    public TextView left;
    public TextView right;
    private TextView titile;

    private void initView(Context context, AttributeSet attrs) {
//        TypedArray a = context.obtainStyledAttributes(attrs,
//                R.styleable.TitleBar);
        int heightTemp = getResources().getDisplayMetrics().heightPixels;
        if (heightTemp == 0) {
            heightTemp = 1920;
        }
//        LogUtils.toE("TitileBar", "heightTemp:" + heightTemp);
        heightPixels = (int) (heightTemp * 0.07);
//        LogUtils.toE("TitileBar", "heightPixels:" + heightPixels);
        int leftPadding = (int) (heightPixels * 0.2);
//        LogUtils.toE("TitileBar", "leftPadding:" + leftPadding);
        float titleTextSize = heightPixels * 0.22f;
        float rightTextSize = heightPixels * 0.18f;
//        LogUtils.toE("TitileBar", "titleTextSize:" + titleTextSize);
//        LogUtils.toE("TitileBar", "rightTextSize:" + rightTextSize);

        left = new TextView(context);
        left.setOnClickListener(this);
        left.setId(R.id.titlebar_left);
        Drawable drawable = getResources().getDrawable(R.drawable.back_default);
//      必须设置图片大小，否则不显示
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        left.setCompoundDrawables(drawable, null, null, null);
        left.setPadding(leftPadding, 0, 2 * leftPadding, 0);
        RelativeLayout.LayoutParams leftLayout = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        addView(left, leftLayout);


        right = new TextView(context);
        right.setOnClickListener(this);
        right.setId(R.id.titlebar_right);
//        right.setText("你好呀");
        right.setTextColor(getResources().getColor(R.color.color_ffffff));
        right.setTextSize(rightTextSize);
        right.setGravity(Gravity.CENTER);
        right.setPadding(leftPadding, 0, leftPadding, 0);
        RelativeLayout.LayoutParams rightLayout = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        rightLayout.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        rightLayout.addRule(RelativeLayout.ALIGN_PARENT_END);
        addView(right, rightLayout);


        titile = new TextView(context);
//        titile.setText("标题");
        titile.setTextSize(titleTextSize);
        titile.setTextColor(getResources().getColor(R.color.color_ffffff));
        titile.setPadding(leftPadding, 0, 2 * leftPadding, 0);
        titile.setGravity(Gravity.CENTER);
        RelativeLayout.LayoutParams titileLayout = new LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.MATCH_PARENT
        );
        titileLayout.addRule(RelativeLayout.CENTER_IN_PARENT);
        addView(titile, titileLayout);

//        a.recycle();
    }


    public void setRightName(String rightName) {
        right.setText(rightName);
    }

    public void setRightImage(int resId) {
        Drawable drawable = getResources().getDrawable(resId);
//      必须设置图片大小，否则不显示
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        right.setCompoundDrawables(drawable, null, null, null);
    }

    public void setLeftName(String leftName) {
        left.setText(leftName);
    }

    public void setLeftImage(int resId) {
        Drawable drawable = getResources().getDrawable(resId);
//      必须设置图片大小，否则不显示
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
        left.setCompoundDrawables(drawable, null, null, null);
    }


    public void setTitleName(String titleName) {
        titile.setText(titleName);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(
                widthMeasureSpec,
                MeasureSpec.makeMeasureSpec(heightPixels, MeasureSpec.AT_MOST)
        );

    }

    @Override
    public void onClick(View v) {


        if (v.getId() == R.id.titlebar_left) {
            if (onTitleBarClickListener != null) {
                onTitleBarClickListener.onLeftClickListener(v);
            }

        }
        else if (v.getId() == R.id.titlebar_right) {
            if (onTitleBarClickListener != null) {
                onTitleBarClickListener.onRightClickListener(v);
            }
        }

    }

    public interface OnTitleBarClickListener {

        void onLeftClickListener(View v);

        void onRightClickListener(View v);
    }

    private OnTitleBarClickListener onTitleBarClickListener;

    public void setOnTitleBarClickListener(OnTitleBarClickListener onTitleBarClickListener) {
        this.onTitleBarClickListener = onTitleBarClickListener;
    }
}
