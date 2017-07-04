package com.kexiang.function.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 各种小工具集合
 */
public class SmallTools {
    /**
     * 弹出软键盘功能
     *
     * @param activity
     */
    public static void popupSoftKeyboard(Context activity) {

        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);

    }

    /**
     * 强制隐藏输入法键盘
     */
    protected void hideSoftKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (activity.getWindow().getAttributes().softInputMode != WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN) {
            if (activity.getCurrentFocus() != null)
                imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(),
                        InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 强制隐藏输入法键盘
     */
    public static void hideSoftKeyboard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

        boolean isOpen = imm.isActive();// isOpen若返回true，则表示输入法打开
        if (isOpen) {

            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

    public static String inputToString(InputStream inputStream) throws IOException {
        String s = null;
        // 从URL上取得字节流
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int ch = -1;
        byte[] buffer = new byte[1024 * 4];
        while ((ch = inputStream.read(buffer)) != -1) {
            baos.write(buffer, 0, ch);
        }
        baos.flush();
        // 依据需求可以选择要要的字符编码格式

        return baos.toString("UTF-8");
    }
}
