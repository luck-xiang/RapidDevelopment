package com.kexiang.function.base;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.kexiang.function.utils.ScreenUtils;


/**
 * 项目名称:frontdesk
 * 创建人:kexiang
 * 创建时间:2016/10/20 14:58
 */

public abstract class BaseDialogFragment extends DialogFragment {
    /**
     * 返回对应Activity的布局文件的id
     *
     * @return
     */
    protected abstract int getContentView();

    protected abstract void onActivityCreatedBase(Bundle savedInstanceState);

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return inflater.inflate(getContentView(), container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        onActivityCreatedBase(savedInstanceState);
    }
    protected void layoutParams(Context context, View view, float w, float h) {
        int width = (int) (ScreenUtils.getScreenWidth(context) * w);
        int height = (int) (ScreenUtils.getScreenHeight(context) * h);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(width, height);
        view.setLayoutParams(params);
    }
}
