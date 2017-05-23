package com.kxiang.quick.function.activity;

import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.kexiang.function.view.OnDialogBackListener;
import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseActivity;
import com.kxiang.quick.function.view.DialogCheckUpdate;

public class SmallToolsActivity extends BaseActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quick_activity_small_tools);
        initStatusBarColor();
        initTitle();
        tv_title_name.setText("小工具集合");

        initView();
    }

    @Override
    protected void initView() {
        findViewById(R.id.btn_2).setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                if (isWifi(thisActivity)) {
                    showToastShort("现在连接的网络是wifi");
                }
                else {
                    showToastShort("现在连接的网络是2G/3G/4G");
                }
                break;
            case R.id.btn_2:
                final DialogCheckUpdate checkUpdate = new DialogCheckUpdate();
                checkUpdate.setCancelable(false);
//                checkUpdate.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.DialogTheme);

                checkUpdate.setOnDialogBackListener(new OnDialogBackListener<String>() {
                    @Override
                    public void onDialogBack(String back) {
                        DownloadManagerTest();
                        checkUpdate.dismiss();
                        ProgressDialog progressDialog = new ProgressDialog(thisActivity);
                        progressDialog.setMessage("开始更新");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
//                        android.os.Process.killProcess(android.os.Process.myPid());
                    }
                });
                checkUpdate.show(getSupportFragmentManager(), "");
                break;
            case R.id.btn_3:
                DownloadManagerTest();
                break;

        }
    }


    private void DownloadManagerTest() {
        final DownloadManager dManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
        Uri uri = Uri.parse("http://192.168.2.57:8080/OrderServer/img/test.apk");
        DownloadManager.Request request = new DownloadManager.Request(uri);

        //设置允许使用的网络类型，这里是移动网络和wifi都可以
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE |
                DownloadManager.Request.NETWORK_WIFI);

//        //不显示下载界面
//        request.setVisibleInDownloadsUi(false);
        //禁止发出通知，既后台下载，如果要使用这一句必须声明一个权限：android.permission.DOWNLOAD_WITHOUT_NOTIFICATION
        //request.setShowRunningNotification(false);
        // 设置下载路径和文件名
        request.setDestinationInExternalPublicDir("download", "小工具.apk");
        request.setDescription("软件新版本下载");
        request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
        request.setMimeType("application/vnd.android.package-archive");
        // 设置为可被媒体扫描器找到
        request.allowScanningByMediaScanner();
        // 设置为可见和可管理
        request.setVisibleInDownloadsUi(true);
        // 获取此次下载的ID
        final long refernece = dManager.enqueue(request);
        // 注册广播接收器，当下载完成时自动安装
        IntentFilter filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        BroadcastReceiver receiver = new BroadcastReceiver() {

            public void onReceive(Context context, Intent intent) {
                long myDwonloadID = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                if (refernece == myDwonloadID) {
                    Intent install = new Intent(Intent.ACTION_VIEW);
                    Uri downloadFileUri = dManager.getUriForDownloadedFile(refernece);
                    install.setDataAndType(downloadFileUri, "application/vnd.android.package-archive");
                    install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(install);
                }
            }
        };
        registerReceiver(receiver, filter);
    }

    /**
     * make true current connect service is wifi
     *
     * @param mContext
     * @return
     */
    private static boolean isWifi(Context mContext) {
        ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        if (activeNetInfo != null
                && activeNetInfo.getType() == ConnectivityManager.TYPE_WIFI) {
            return true;
        }
        return false;
    }



}
