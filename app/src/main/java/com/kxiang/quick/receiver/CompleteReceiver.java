package com.kxiang.quick.receiver;

import android.app.DownloadManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.widget.Toast;

public class CompleteReceiver extends BroadcastReceiver {

    private DownloadManager downloadManager;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "广播返回....", Toast.LENGTH_LONG).show();
        String action = intent.getAction();
        if (action.equals(DownloadManager.ACTION_DOWNLOAD_COMPLETE)) {
            Toast.makeText(context, "下载完成了....", Toast.LENGTH_LONG).show();

            long id = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, 0);
            //TODO 判断这个id与之前的id是否相等，如果相等说明是之前的那个要下载的文件
            DownloadManager.Query query = new DownloadManager.Query();
            query.setFilterById(id);
            downloadManager = (DownloadManager) context.getSystemService(Context.DOWNLOAD_SERVICE);
            Cursor cursor = downloadManager.query(query);

            int columnCount = cursor.getColumnCount();
            String path = null;
            //TODO 这里把所有的列都打印一下，有什么需求，就怎么处理,文件的本地路径就是path
            while (cursor.moveToNext()) {
                for (int j = 0; j < columnCount; j++) {
                    String columnName = cursor.getColumnName(j);
                    String string = cursor.getString(j);
                    if (columnName.equals("local_uri")) {
                        path = string;
                    }
                    if (string != null) {
                        System.out.println(columnName + ": " + string);
                    }
                    else {
                        System.out.println(columnName + ": null");
                    }
                }
            }
            cursor.close();
            //如果sdcard不可用时下载下来的文件，那么这里将是一个内容提供者的路径，这里打印出来，有什么需求就怎么样处理
            if (path.startsWith("content:")) {
                cursor = context.getContentResolver().query(Uri.parse(path), null, null, null, null);
                columnCount = cursor.getColumnCount();
                while (cursor.moveToNext()) {
                    for (int j = 0; j < columnCount; j++) {
                        String columnName = cursor.getColumnName(j);
                        String string = cursor.getString(j);
                        if (string != null) {
                            System.out.println(columnName + ": " + string);
                        }
                        else {
                            System.out.println(columnName + ": null");
                        }
                    }
                }
                cursor.close();
            }

        }
        else if (action.equals(DownloadManager.ACTION_NOTIFICATION_CLICKED)) {
            Toast.makeText(context, "", Toast.LENGTH_LONG).show();
        }
    }
}
