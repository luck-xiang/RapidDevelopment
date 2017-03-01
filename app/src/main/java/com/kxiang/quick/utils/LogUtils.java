package com.kxiang.quick.utils;

import android.util.Log;

import com.google.gson.Gson;

/**
 * 项目名称:PraSupPlatform
 * 创建人:kexiang
 * 创建时间:2017/2/17 10:29
 */

public class LogUtils {

    private static boolean logGoing = false;

    public static void initDebug(boolean start) {
        logGoing = start;
    }

    public static void toNetError(Throwable e) {

        if (logGoing) {
            Log.e("NetError", "error:" + "\n" +
                    e.getMessage() + "\n" +
                    e.getLocalizedMessage() + "\n" +
                    e.toString()
            );
        }
    }

    public static void toJsonString(Object jsonBean) {
        if (logGoing) {

            Log.e("JsonString", new Gson().toJson(jsonBean));
        }
    }

    public static void toJsonString(String tag, Object jsonBean) {
        if (logGoing) {

            Log.e(tag, new Gson().toJson(jsonBean));
        }
    }

    public static void toE(String tag, Object print) {
        if (logGoing) {
            Log.e(tag, print + "");
        }
    }

    public static void toE(Object print) {
        if (logGoing) {
            Log.e("BaseLog", print + "");
        }
    }

    public static void toI(String tag, Object print) {
        if (logGoing) {
            Log.i(tag, print + "");
        }
    }


    public static void toI(Object print) {
        if (logGoing) {
            Log.i("BaseLog", print + "");
        }
    }


}
