package com.kxiang.quick.function.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.kexiang.function.utils.FileUtils;
import com.kexiang.function.utils.LogUtils;
import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseActivity;
import com.kxiang.quick.net.ApiNetworkAddressService;
import com.kxiang.quick.net.UrlFields;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;
import okio.Source;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DownLoadActivity extends BaseActivity implements View.OnClickListener {

    /**
     * 测试1
     */
    private Button btn_1;
    /**
     * 测试1
     */
    private Button btn_2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_down_load);
        initTitle();
        initStatusBarColor();
        initView();

    }

    @Override
    protected void initView() {

        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_1.setOnClickListener(this);
        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_2.setOnClickListener(this);
        btn_2.postDelayed(new Runnable() {
            @Override
            public void run() {
                applyPermission();
            }
        }, 666);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                EnvironmentJudge();
                break;
            case R.id.btn_2:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    int check = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
                    LogUtils.toE(check);
                    if (check != PackageManager.PERMISSION_GRANTED) {
                        showMissingPermissionDialog();
                        return;
                    }
                }

                if (judgeSD()) {

                    if (DownloadConfig()) {
                        downLoading();
                    }

                }
                break;
        }
    }


    private void downLoading() {
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {
//                        Response<ResponseBody> body = ApplicationUtils
//                                .getApplicition(getApplication())
//                                .getApiNetService()
//                                .downloadFileWithDynamicUrlSync("http://192.168.2.57:8080/OrderServer/img/OrderServer.zip")
//                                .execute();
//
//                        writeResponseBodyToDisk(body.body());
//                        LoadingProgress();
//                        new Progress()
//                        .run("http://192.168.2.57:8080/OrderServer/img/OrderServer.zip");
                        down();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {

                    }

                    @Override
                    public void onError(Throwable e) {

                        LogUtils.toNetError(e);
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    private void down() {

        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://192.168.56.1");
        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException {
                        okhttp3.Response orginalResponse = chain.proceed(chain.request());

                        return orginalResponse.newBuilder()
                                .body(new ProgressResponseBody(orginalResponse.body(),
                                        new ProgressListener() {
                                            @Override
                                            public void onProgress(long progress, long total,
                                                                   boolean done) {
                                                Log.e("onProgress", Looper.myLooper() + "");
                                                Log.e("onProgress", "onProgress: " + "total ---->" + total
                                                        + done+" ---->" + progress);
                                            }
                                        }))
                                .build();
                    }
                })
                .build();
        ApiNetworkAddressService api = builder.client(client)
                .build().create(ApiNetworkAddressService.class);

        Call<ResponseBody> call = api.downloadFileWithDynamicUrlSync("http://192.168.2.57:8080/OrderServer/img/OrderServer.zip");
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    InputStream is = response.body().byteStream();
                    File file = new File(Environment.getExternalStorageDirectory(), "text_img.png");
                    FileOutputStream fos = new FileOutputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = bis.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                        fos.flush();
                    }
                    fos.close();
                    bis.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
//               Log.e(TAG,"success");
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }


    private boolean writeResponseBodyToDisk(ResponseBody body) {
        try {
            // todo change the file location/name according to your needs
            File futureStudioIconFile = new File(downloadDir + "OrderServer.zip");

            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {

                byte[] fileReader = new byte[4096];
                long fileSize = body.contentLength();
                long fileSizeDownloaded = 0;

                inputStream = body.byteStream();

                outputStream = new FileOutputStream(futureStudioIconFile);

                while (true) {
                    int read = inputStream.read(fileReader);

                    if (read == -1) {
                        break;
                    }

                    outputStream.write(fileReader, 0, read);

                    fileSizeDownloaded += read;

                    LogUtils.toE("", "file download: " + fileSizeDownloaded + " of " + fileSize);
                }

                outputStream.flush();

                return true;
            } catch (IOException e) {
                return false;
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }

                if (outputStream != null) {
                    outputStream.close();
                }
            }
        } catch (IOException e) {
            return false;
        }
    }

    // 下载目录
    private String downloadDir;

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

        return true;
    }


    private void LoadingProgress() {
        RetrofitCallback<ResponseBody> callback = new RetrofitCallback<ResponseBody>() {
            @Override
            public void onSuccess(Call<ResponseBody> call, Response<ResponseBody> response) {

                LogUtils.toE("onSuccess", "onSuccess：");
                try {
                    InputStream is = response.body().byteStream();
                    File file = new File(downloadDir, "OrderServer.zip");
                    FileOutputStream fos = new FileOutputStream(file);
                    BufferedInputStream bis = new BufferedInputStream(is);
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = bis.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                    }
                    fos.flush();
                    fos.close();
                    bis.close();
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                // runOnUIThread(activity, t.getMessage());
            }
        };

        Call<ResponseBody> call = getRetrofitService(callback).downloadFileWithDynamicUrlSync(
                "http://192.168.2.57:8080/OrderServer/img/OrderServer.zip");
        call.enqueue(callback);
    }

    private <T> ApiNetworkAddressService getRetrofitService(final RetrofitCallback<T> callback) {
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Response response = chain.proceed(chain.request());
//将ResponseBody转换成我们需要的FileResponseBody
                return response.newBuilder().body(
                        new FileResponseBody<T>(response.body(), callback)
                ).build();
            }
        });
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(UrlFields.URL_BASE)
                .client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        ApiNetworkAddressService service = retrofit.create(ApiNetworkAddressService.class);
        return service;
    }
//通过上面的设置后，我们需要在回调RetrofitCallback中实现onLoading方法来进行进度的更新操作，与上传文件的方法相同

    /**
     * 扩展OkHttp的请求体，实现上传时的进度提示
     *
     * @param <T>
     */
    public final class FileResponseBody<T> extends ResponseBody {
        /**
         * 实际请求体
         */
        private ResponseBody mResponseBody;
        /**
         * 下载回调接口
         */
        private RetrofitCallback<T> mCallback;
        /**
         * BufferedSource
         */
        private BufferedSource mBufferedSource;

        public FileResponseBody(ResponseBody responseBody, RetrofitCallback<T> callback) {
            super();
            this.mResponseBody = responseBody;
            this.mCallback = callback;
        }

        @Override
        public BufferedSource source() {
            if (mBufferedSource == null) {
                mBufferedSource = Okio.buffer(source(mResponseBody.source()));
            }
            return mBufferedSource;
        }

        @Override
        public long contentLength() {
            return mResponseBody.contentLength();
        }

        @Override
        public MediaType contentType() {
            return mResponseBody.contentType();
        }

        /**
         * 回调进度接口
         *
         * @param source
         * @return Source
         */
        private Source source(Source source) {
            return new ForwardingSource(source) {
                long totalBytesRead = 0L;

                @Override
                public long read(Buffer sink, long byteCount) throws IOException {
                    long bytesRead = super.read(sink, byteCount);
                    totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                    mCallback.onLoading(mResponseBody.contentLength(), totalBytesRead);
                    return bytesRead;
                }
            };
        }
    }


    public abstract class RetrofitCallback<T> implements Callback<T> {
        @Override
        public void onResponse(Call<T> call, Response<T> response) {
            if (response.isSuccessful()) {
                onSuccess(call, response);
            }
            else {
                onFailure(call, new Throwable(response.message()));
            }
        }

        public abstract void onSuccess(Call<T> call, Response<T> response);

        public void onLoading(long total, long progress) {
        }
    }


    private void EnvironmentJudge() {

        LogUtils.toE("Environment", "getDataDirectory:" +
                Environment.getDataDirectory());
        LogUtils.toE("Environment", "getDownloadCacheDirectory:" +
                Environment.getDownloadCacheDirectory());
        LogUtils.toE("Environment", "getExternalStorageDirectory:" +
                Environment.getExternalStorageDirectory());
        LogUtils.toE("Environment", "getExternalStorageState:" +
                Environment.getExternalStorageState());
        LogUtils.toE("Environment", "getRootDirectory:" +
                Environment.getRootDirectory());


    }


    public interface ProgressListener {
        /**
         * @param progress 已经下载或上传字节数
         * @param total    总字节数
         * @param done     是否完成
         */
        void onProgress(long progress, long total, boolean done);
    }

    public class ProgressResponseBody extends ResponseBody {

        private final ResponseBody responseBody;
        private final ProgressListener listener;
        private BufferedSource bufferedSource;

        public ProgressResponseBody(ResponseBody responseBody, ProgressListener listener) {
            this.responseBody = responseBody;
            this.listener = listener;
        }

        @Override
        public MediaType contentType() {
            return responseBody.contentType();
        }

        @Override
        public long contentLength() {
            return responseBody.contentLength();
        }

        @Override
        public BufferedSource source() {
            if (null == bufferedSource) {
                bufferedSource = Okio.buffer(source(responseBody.source()));
            }
            return bufferedSource;
        }

        private Source source(Source source) {
            return new ForwardingSource(source) {
                long totalBytesRead = 0L;

                @Override
                public long read(Buffer sink, long byteCount) throws IOException {
                    long bytesRead = super.read(sink, byteCount);
                    totalBytesRead += bytesRead != -1 ? bytesRead : 0;
                    listener.onProgress(totalBytesRead, responseBody.contentLength(), bytesRead == -1);
                    return bytesRead;
                }
            };
        }
    }

    public boolean judgeSD() {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return true;
        }
        showToastShort("该手机没有内存卡");
        return false;
    }

    public void applyPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            int check = checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE);
            LogUtils.toE(check);
            if (check != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 0);
            }
        }
    }

    private AlertDialog dialog;

    private void showMissingPermissionDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.notifyTitle);
        builder.setMessage(R.string.notifyMsgRead);

        // 拒绝, 退出应用
        builder.setNegativeButton(R.string.cancel,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builder.setPositiveButton(R.string.setting,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        getAppDetailSettingIntent(thisActivity);
                    }
                });

        builder.setCancelable(false);

        dialog = builder.show();
        dialog.show();
    }


    public void getAppDetailSettingIntent(Context context) {
        Intent localIntent = new Intent();
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= 9) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        }
        else if (Build.VERSION.SDK_INT <= 8) {
            localIntent.setAction(Intent.ACTION_VIEW);
            localIntent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
            localIntent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
        }
        context.startActivity(localIntent);
    }
}
