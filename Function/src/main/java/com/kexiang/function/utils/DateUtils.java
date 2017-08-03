package com.kexiang.function.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by kexiang on 2016/10/14 0014.
 */
public class DateUtils {

    /**
     * 时间戳转换成日期格式字符串
     *
     * @param time   时间戳
     * @param format
     * @return
     */
    public static String timeStamp2Date(long time, String format) {
        if (time == 0) {
            return "";
        }
        if (format == null || format.isEmpty()) {
            format = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CANADA);
        return sdf.format(new Date(time));
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param date   字符串日期
     * @param format 如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static long date2TimeStamp(String date, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.CANADA);
            return sdf.parse(date).getTime();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    public void getToday() {
        Date date = new Date();//取时间
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);//把日期往后增加一天.整数往后推,负数往前移动
        date = calendar.getTime(); //这个时间就是日期往后推一天的结果
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
        String dateString = formatter.format(date);
    }

    /**
     * 将日期转换为字符串数组
     *
     * @return
     */
    public String[] getDataForOne() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CANADA);
        String[] time = format.format(new Date()).split(" ");
        String[] timeYYMMDD = time[0].split("-");
        String[] timeHHMMSS = time[1].split(":");
        return new String[]{
                timeYYMMDD[0],
                timeYYMMDD[1],
                timeYYMMDD[2],
                timeHHMMSS[0],
                timeHHMMSS[1],
                timeHHMMSS[2],
        };
    }

}
