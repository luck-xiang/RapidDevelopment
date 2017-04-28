package com.kexiang.function.download;

import android.os.Environment;

import com.kexiang.function.utils.FileUtils;

import java.io.File;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/3/27 10:36
 */

public class DownloadConfig {



    public void stopAllLoading() {

    }

    public void stopLoading(int position) {

    }

    public void startLoading(String url) {

    }

    private static DownloadConfig downloadConfig = new DownloadConfig();

    public static DownloadConfig getDownloadConfig() {
        synchronized (DownloadConfig.class) {
            if (downloadConfig == null) {
                downloadConfig = new DownloadConfig();
            }
        }

        return downloadConfig;
    }


    private DownloadConfig() {
        DownloadConfig();
    }

    // 下载目录
    private String downloadDir;
    private int loadingMaxNumber;

    //创建失败返回false
    private boolean DownloadConfig() {
        downloadDir = Environment.getExternalStorageDirectory().getAbsolutePath() +
                "/MvpApp/video/";
        File dir = new File(downloadDir);
        //判断是否是存在
        if (!FileUtils.isFileExists(dir)) {
            if (dir.mkdirs()) {
                return false;
            }

        }
        //判断是否是文件夹，不是则删除
        else if (!dir.isDirectory()) {
            if (dir.delete())
                return false;
            if (dir.mkdirs())
                return false;
        }
        loadingMaxNumber = 10;
        return true;
    }
}
