package com.kexiang.function.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 项目名称:IQ
 * 创建人:kexiang
 * 创建时间:2016/12/28 11:25
 * <BR>错误打印，错误打印类，将错误打印在磁盘中
 */

public class AppException extends Exception implements Thread.UncaughtExceptionHandler {


    /**
     * 系统默认的UncaughtException处理类
     */
    private Thread.UncaughtExceptionHandler mDefaultHandler;

    /**
     * 无参的构造函数
     */
    private AppException() {
        this.mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
    }


    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        saveErrorLog((Exception) ex);
        //如果异常没有被处理
        if (mDefaultHandler != null) {
            //进行异常捕获
            mDefaultHandler.uncaughtException(thread, ex);
        }
    }


    /**
     * 保存异常日志
     * <p>
     * excp(异常日志)
     */
    public void saveErrorLog(Exception excp) {
        //定义错误日志文件存储信息
        String errorlog = "errorlog.txt";
        String savePath = "";
        String logFilePath = "";
        //文件输出
        FileWriter fw = null;
        PrintWriter pw = null;
        try {
            // 判断是否挂载了SD卡
            String storageState = Environment.getExternalStorageState();
            if (storageState.equals(Environment.MEDIA_MOUNTED)) {
                //设置存储路径
//                savePath = Environment.getExternalStorageDirectory()
//                        .getAbsolutePath() + "/OSChina/Log/";

                savePath = Environment.getExternalStorageDirectory().getAbsolutePath() +
                        "/iq/";


                //实例化File
                File file = new File(savePath);

                if (!file.exists()) {
                    //创建此抽象路径名指定的目录，包括所有必需但不存在的父目录。
                    file.mkdirs();
                }

                //日志路径
                logFilePath = savePath + errorlog;
            }
            // 没有挂载SD卡，无法写文件
            if (logFilePath == "") {
                return;
            }
            //实例化logFile
            File logFile = new File(logFilePath);
            //判断是否已经存在
            if (!logFile.exists()) {
                logFile.createNewFile();
            }
//            else {
//                logFile.delete();
//                logFile.createNewFile();
//            }
            //实例化文件输出
            fw = new FileWriter(logFile, true);
            pw = new PrintWriter(fw);
            //在命令行打印异常信息在程序中出错的位置及原因
            excp.printStackTrace(pw);
            //释放资源
            pw.close();
            fw.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (pw != null) {
                pw.close();
            }
            if (fw != null) {
                try {
                    fw.close();
                } catch (IOException e) {
                }
            }

        }
    }

    private static AppException appException;

    /**
     * @return AppException
     * 获取APP异常崩溃处理对象
     */
    public static AppException getAppExceptionHandler() {

        if (appException == null) {
            synchronized (AppException.class) {
                if (appException == null) {
                    appException = new AppException();
                }
            }
        }

        return appException;
    }

    /**
     * 获取APP崩溃异常报告
     * </BR>
     * context(上下文), Throwable ex(异常信息)
     */

    private String getCrashReport(Context context, Throwable ex) {

        StringBuffer exceptionStr = new StringBuffer();
        // android版本以及系统型号
        exceptionStr.append("Android: " + android.os.Build.VERSION.RELEASE
                + "(" + android.os.Build.MODEL + ")\n");
        // 异常信息
        exceptionStr.append("Exception: " + ex.getMessage() + "\n");
        //？
        StackTraceElement[] elements = ex.getStackTrace();
        for (int i = 0; i < elements.length; i++) {
            exceptionStr.append(elements[i].toString() + "\n");
        }
        // 返回组成的StringBuffer
        return exceptionStr.toString();
    }
}
