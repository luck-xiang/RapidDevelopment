package com.kexiang.function.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

/**
 * 项目名称:PraSupPlatform
 * 创建人:kexiang
 * 创建时间:2017/2/17 10:29
 * <BR/> 打印类
 * <BR/> 打印尽量用这个类
 * <BR/> 在BaseAppliction调用initDebug()，即可打印，在发布正式版本的时候注销这个方法
 */

public class LogUtils {

    private static boolean logGoing = false;

    public static void initDebug() {
        logGoing = true;
    }

    /**
     * 打印为：NetError
     *
     * @param e
     */
    public static void toNetError(Throwable e) {

        if (logGoing) {
            Log.e("NetError", "error:" + "\n" +
                    e.getMessage() + "\n" +
                    e.getLocalizedMessage() + "\n" +
                    e.toString()
            );
        }
    }

    /**
     * 打印为：JsonString
     *
     * @param jsonBean
     */
    public static void toJsonString(Object jsonBean) {
        if (logGoing) {

            JsonLog.printJson("JsonBean", new Gson().toJson(jsonBean));
        }
    }

    public static void toJsonString(String tag, Object jsonBean) {
        if (logGoing) {

            JsonLog.printJson(tag, new Gson().toJson(jsonBean));
        }
    }

    /**
     * 打印为显示为：JsonString
     *
     * @param json
     */
    public static void toJsonFormat(String json) {
        if (logGoing) {
            JsonLog.printJson("JsonFormat", json+"");

        }
    }

    public static void toJsonFormat(String tag, Object json) {
        toJsonFormat(tag, new Gson().toJson(json));
    }

    public static void toJsonFormat(Object json) {
        toJsonFormat(new Gson().toJson(json));
    }

    public static void toJsonFormat(String tag, String json) {
        if (logGoing) {
            JsonLog.printJson(tag, json+"");

        }
    }


    public static void toE(String tag, Object print) {
        if (logGoing) {
            Log.e(tag, print + "");
        }
    }

    /**
     * 打印为BaseLog
     *
     * @param print
     */
    public static void toE(Object print) {
        if (logGoing) {
            Log.e("BaseLog", print + "");
        }
    }

    /**
     * 打印为PRETTYLOGGER
     * 使用Logger方式打印
     *
     * @param print
     */
    public static void toELogger(Object print) {
        if (logGoing) {
            Log.e("PRETTYLOGGER", "BaseLog");
            Logger.e(print + "");
        }
    }

    /**
     * 打印为PRETTYLOGGER
     * 使用Logger方式打印
     *
     * @param print
     */
    public static void toELogger(String tag, Object print) {
        if (logGoing) {
            Log.e("PRETTYLOGGER", tag);
            Logger.e(print + "");
        }
    }

    /**
     * 打印为显示为：JsonString
     *
     * @param json
     */
    public static void toJsonFormatLogger(String json) {
        if (logGoing) {
            Log.e("PRETTYLOGGER", "toJsonFormatLogger");
            Logger.json(json);
        }
    }

    public static void toJsonFormatLogger(String tag, String json) {
        if (logGoing) {
            Log.e("PRETTYLOGGER", tag);
            Logger.json(json+"");

        }
    }

    public static void toI(String tag, Object print) {
        if (logGoing) {
            Log.i(tag, print + "");
        }
    }

    /**
     * 打印为BaseLog
     *
     * @param print
     */
    public static void toI(Object print) {
        if (logGoing) {
            Log.i("BaseLog", print + "");
        }
    }


}
