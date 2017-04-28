package com.kexiang.function.utils;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/3/30 16:01
 */

public class JsonLog {
    public static final int JSON_INDENT = 4;
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static String printJson(String msg) {
        if (TextUtils.isEmpty(msg)) {


            return "null";
        }

        String message;

        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(JSON_INDENT);
            }
            else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(JSON_INDENT);
            }
            else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }
        String[] lines = message.split(LINE_SEPARATOR);
        StringBuilder builder = new StringBuilder();
        for (String line : lines) {
            builder.append(line);
            builder.append("\n");
        }
        return builder.toString();
    }

    public static void printJson(String tag, String msg) {
        if (TextUtils.isEmpty(msg)) {

            Log.e(tag, msg + "");
            return;
        }

        String message;

        try {
            if (msg.startsWith("{")) {
                JSONObject jsonObject = new JSONObject(msg);
                message = jsonObject.toString(JSON_INDENT);
            }
            else if (msg.startsWith("[")) {
                JSONArray jsonArray = new JSONArray(msg);
                message = jsonArray.toString(JSON_INDENT);
            }
            else {
                message = msg;
            }
        } catch (JSONException e) {
            message = msg;
        }


        String[] lines = message.split(LINE_SEPARATOR);
//        for (String line : lines) {
//            Log.e(tag, "║ " + line);
//        }
        LogUtils.toE(tag, message);
        StringBuilder builder = new StringBuilder();
        for (String line : lines) {
            builder.append(line);
            builder.append("\n");
            if (builder.length() > 1000) {
                Log.e(tag, builder.toString());
                builder.delete(0, builder.length());
            }

        }
        Log.e(tag, builder.toString());
    }
}
