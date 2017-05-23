package com.kxiang.quick.utils;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.View;

import com.kxiang.quick.base.BaseApplication;


/**
 * 项目名称:IQ
 * 创建人:kexiang
 * 创建时间:2016/12/23 9:24
 */

public class ApplicationUtils {
    public static BaseApplication getApplicition(View view) {

        if (view != null) {
            return (BaseApplication) ((Activity) (view.getContext()))
                    .getApplication();
        }
        else {
            return BaseApplication.getContext();
        }


    }

    public static BaseApplication getApplicition(Activity activity) {
        if (activity != null) {
            return (BaseApplication) activity.getApplication();
        }
        else {
            return BaseApplication.getContext();
        }


    }

    public static BaseApplication getApplicition(Application allApplication) {
        if (allApplication != null) {
            return (BaseApplication) allApplication;
        }
        else {
            return BaseApplication.getContext();
        }


    }

    public static BaseApplication getApplicition(Context context) {
        if (context != null) {
            return (BaseApplication) ((Activity) context).getApplication();
        }
        else {
            return BaseApplication.getContext();
        }


    }

    public static FragmentManager getSupportFragmentManager(View view) {
        return ((FragmentActivity) (view.getContext())).getSupportFragmentManager();

    }
}
