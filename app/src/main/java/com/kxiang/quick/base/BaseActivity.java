package com.kxiang.quick.base;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kexiang.function.utils.LogUtils;
import com.kexiang.function.view.SystemBarTintSet;
import com.kexiang.function.view.TitleBarView;
import com.kxiang.quick.MainActivity;
import com.kxiang.quick.R;

import io.reactivex.disposables.CompositeDisposable;


/**
 * 项目名称:世旭智慧平台
 * 创建人:kexiang
 * 创建时间:2016/8/5 9:11
 */
public abstract class BaseActivity extends AppCompatActivity implements TitleBarView.OnTitleBarClickListener {


    /**
     * 通用Activity的this
     */
    protected BaseActivity thisActivity;
    protected CompositeDisposable disposable;
    protected TitleBarView title_bar;

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
        //打印当前继承BaseActivity的界面activity
        LogUtils.toE("BaseActivity", getClass().getSimpleName() + "");
        // bandService();
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        title_bar = (TitleBarView) findViewById(R.id.title_bar);
        if (title_bar != null)
            title_bar.setOnTitleBarClickListener(this);
        initView();
    }

    /**
     * 设置状态栏固定颜色颜色
     */
    protected void initStatusBarColor() {
        SystemBarTintSet.setStatusBarKITKATabove(this, R.color.color_f03e1b);
    }

    /**
     * 设置状态栏特定颜色
     */
    protected void initStatusBarColor(int color) {
        SystemBarTintSet.setStatusBarKITKATabove(this, color);
    }

    @Override
    public void onRightClickListener(View v) {

    }

    @Override
    public void onLeftClickListener(View v) {
        finish();
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

    protected AlertDialog.Builder exceptionBuilder;

    /**
     * 初始化控件或者数据，该方法是setContentView(int layoutResID)后执行的
     */
    protected abstract void initView();

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
    public Resources getResources() {
        Resources res = super.getResources();
        Configuration config = new Configuration();
        config.setToDefaults();
        res.updateConfiguration(config, res.getDisplayMetrics());
        return res;
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
