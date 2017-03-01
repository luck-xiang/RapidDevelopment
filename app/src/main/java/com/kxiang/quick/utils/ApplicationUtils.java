package com.kxiang.quick.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.view.View;

import com.kxiang.quick.base.BaseActivity;
import com.kxiang.quick.base.BaseApplication;


/**
 * 项目名称:IQ
 * 创建人:kexiang
 * 创建时间:2016/12/23 9:24
 */

public class ApplicationUtils {

    public static BaseApplication getApplicition(View view) {
        return (BaseApplication) ((BaseActivity) (view.getContext()))
                .getApplication();

    }

    public static BaseApplication getApplicition(Activity activity) {
        return (BaseApplication) activity.getApplication();
    }

    public static BaseApplication getApplicition(Application allApplication) {
        return (BaseApplication) allApplication;

    }

    public static BaseApplication getApplicition(Context context) {

        return (BaseApplication) ((BaseActivity) context).getApplication();

    }


}
