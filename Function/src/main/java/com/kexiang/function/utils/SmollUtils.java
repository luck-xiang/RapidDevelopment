package com.kexiang.function.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/4/1 15:05
 */

public class SmollUtils {

    /**
     * 保留num的位数
     * 0 代表前面补充0
     * num 代表长度为4
     * d 代表参数为正数型
     *
     * @param code
     * @param num
     * @return
     */
    public static String autoGenericCode(String code, int num) {


        if (code.length() != num) {
            StringBuilder builder = new StringBuilder();
            for (int i = code.length(); i < num; i++) {
                builder.append(0);
            }
            builder.append(code);
            return builder.toString();
        }
        else {
            return code;
        }


    }


    public static String getVersionName(Context context) {
        String version = "";
        try {
            PackageManager manager = context.getPackageManager();
            PackageInfo info = null;
            info = manager.getPackageInfo(context.getPackageName(),
                    0);
            version = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return version;
    }


    /**
     * 获取内存大小
     *
     * @param context
     * @return
     */
    public static int getMemorySize(Context context) {
        ActivityManager activityManager = (ActivityManager)
                context.getSystemService(
                        Context.ACTIVITY_SERVICE
                );

        return activityManager.getLauncherLargeIconSize();
    }

}
