package com.kexiang.function.view.refresh;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/5/10 10:38
 */

public class FooterView extends RelativeLayout implements LoadFooterable {
    public FooterView(Context context) {
        super(context);
    }

    public FooterView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FooterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onScroll(float y) {
        return false;
    }

    @Override
    public void startLoad() {

    }

    @Override
    public void stopLoad() {

    }
}
