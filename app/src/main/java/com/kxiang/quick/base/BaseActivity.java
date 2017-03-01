package com.kxiang.quick.base;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kxiang.quick.MainActivity;
import com.kxiang.quick.R;
import com.kxiang.quick.function.view.utils.SystemBarTintSet;
import com.kxiang.quick.utils.LogUtils;

import io.reactivex.disposables.CompositeDisposable;


/**
 * 项目名称:世旭智慧平台
 * 创建人:kexiang
 * 创建时间:2016/8/5 9:11
 */
public abstract class BaseActivity extends FragmentActivity {


    /**
     * 通用Activity的this
     */
    protected BaseActivity thisActivity;
    protected CompositeDisposable disposable;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!isTaskRoot()) {
            Intent intent = getIntent();
            String action = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && action.equals(Intent.ACTION_MAIN)) {
                finish();
                return;
            }
        }
        disposable = new CompositeDisposable();
        thisActivity = this;
        initStatusBar();
        setContentView(getContentView());
        //打印当前继承BaseActivity的界面activity
        LogUtils.toE("BaseActivity", getClass().getSimpleName() + "");
       // bandService();
    }


    /**
     * 如果不需要设置头部颜色或者需要改变头部颜色修改该函数
     * 填上SystemBarTintSet.setStatusBarKITKATabove(this, R.color.color_f03e1b);
     */
    protected void initStatusBar() {
        SystemBarTintSet.setStatusBarKITKATabove(this, R.color.color_f03e1b);
    }


    protected ImageView iv_title_left;
    protected TextView tv_title_name;
    protected TextView tv_title_right;

    //初始化title显示
    protected void initTitle() {
        iv_title_left = (ImageView) findViewById(R.id.iv_title_left);
        tv_title_name = (TextView) findViewById(R.id.tv_title_name);
        tv_title_right = (TextView) findViewById(R.id.tv_title_right);
        if (iv_title_left != null)
            iv_title_left.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    leftFinish();
                }
            });

    }

    /**
     * 如需操作左返回键执行该方法
     */
    protected void leftFinish() {
        finish();
    }

    /**
     * 返回对应Activity的布局文件的id
     *
     * @return
     */
    protected abstract int getContentView();

    protected AlertDialog.Builder exceptionBuilder;


    //强制退出登录显示框
    public void showExceptionDialog(String exceptionType) {

        String st = getResources().getString(R.string.Logoff_notification);

        // clear up global variables
        if (exceptionBuilder == null)
            exceptionBuilder = new AlertDialog.Builder(BaseActivity.this);
        exceptionBuilder.setTitle(st);
        exceptionBuilder.setMessage(exceptionType);
        exceptionBuilder.setPositiveButton(R.string.ok,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        exceptionBuilder = null;
                        finish();
                        Intent intent = new Intent(BaseActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        exceptionBuilder.setCancelable(false);
        exceptionBuilder.create().show();
    }


    @Override
    public void finish() {
        super.finish();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        boolean isOpen = imm.isActive();// isOpen若返回true，则表示输入法打开
        if (isOpen) {
            imm.hideSoftInputFromWindow(getWindow().getDecorView().getWindowToken(), 0);
        }
    }


    /**
     * 网络是否开启图示UI
     * 有网时候返回true
     * 没有网络返回false
     */
    protected boolean changInternew() {

        ConnectivityManager connectivityManager = (ConnectivityManager) BaseActivity.this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isAvailable()) {
            return true;
        }
        return false;
    }


    //强制退出登录界面

    public void showChangePermissionDialog(String exceptionType) {

        String st = getResources().getString(R.string.Logoff_notification);

        // clear up global variables
        if (exceptionBuilder == null)
            exceptionBuilder = new AlertDialog.Builder(BaseActivity.this);
        exceptionBuilder.setTitle(st);
        exceptionBuilder.setMessage(exceptionType);
        exceptionBuilder.setPositiveButton(R.string.ok,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        exceptionBuilder = null;
                        finish();
                        Intent intent = new Intent(BaseActivity.this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });
        exceptionBuilder.setCancelable(false);
        exceptionBuilder.create().show();
    }


    /**
     * toast用方法
     *
     * @param toast
     */
    protected void showToastShort(String toast) {
        if (!TextUtils.isEmpty(toast)) {
            Toast.makeText(getApplicationContext(), toast, Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //解绑服务
//        unbindService(mConnection);
        if (exceptionBuilder != null) {
            exceptionBuilder.create().dismiss();
            exceptionBuilder = null;
        }
        disposable.dispose();
    }

//    /**
//     * 开启后服务
//     */
//    protected void bandService() {
//        Intent startIntent = new Intent(this, CheckPermissionService.class);
//        startService(startIntent);
//        Intent bindIntent = new Intent(this, CheckPermissionService.class);
//        bindService(bindIntent, mConnection, BIND_AUTO_CREATE);
//    }
//    绑定后台服务
//    protected CheckPermissionService mBoundService;
//
//    //service和Activity交互的桥梁
//    private ServiceConnection mConnection = new ServiceConnection() {
//        public void onServiceConnected(ComponentName className, IBinder service) {
//            mBoundService = ((CheckPermissionService.BindBinder) service).getService();
//            LogUtils.toE("ServiceConnection", "1");
//            mBoundService.setChangePermissionListener(checkPermissionService);
//        }
//
//        public void onServiceDisconnected(ComponentName className) {
//            mBoundService = null;
//        }
//    };
//
//    CheckPermissionService.ChangePermissionListener checkPermissionService = new CheckPermissionService.ChangePermissionListener() {
//        @Override
//        public void changePermission(int type) {
//            showChangePermissionDialog("你的操作权限已经改变，将重新启动app");
//        }
//    };
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        if (mBoundService != null) {
//            mBoundService.setChangePermissionListener(checkPermissionService);
//        }
//    }

}
