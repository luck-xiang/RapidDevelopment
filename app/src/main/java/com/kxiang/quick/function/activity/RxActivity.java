package com.kxiang.quick.function.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.kexiang.function.utils.JsonLog;
import com.kexiang.function.utils.LogUtils;
import com.kexiang.function.utils.OkhttpBackUtils;
import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseActivity;
import com.kxiang.quick.news.bean.NewsInfo;
import com.kxiang.quick.utils.ApplicationUtils;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

import static com.kxiang.quick.utils.ApplicationUtils.getApplicition;

public class RxActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_content;
    /**
     * 1
     */
    private Button btn_1;
    /**
     * 1
     */
    private Button btn_2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rx);
        initStatusBarColor();
        initTitle();
        tv_content = (TextView) findViewById(R.id.tv_content);

        initView();
    }

    @Override
    protected void initView() {


        btn_1 = (Button) findViewById(R.id.btn_1);
        btn_1.setOnClickListener(this);
        btn_2 = (Button) findViewById(R.id.btn_2);
        btn_2.setOnClickListener(this);
    }

    public void netGetTableData() {


        getApplicition(this)
                .getApiNetService()
                .getQueueList("headline", "T1348647909107", 0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Response<NewsInfo>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Response<NewsInfo> value) {
                        NewsInfo bean = value.body();
                        LogUtils.toJsonString("NewsInfo", bean);

                        tv_content.setText(JsonLog.printJson(new Gson().toJson(value)));
                        if (OkhttpBackUtils.isBackDataNoEmpty(value)) {

                        }
                        else {

                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.toNetError(e);

                    }

                    @Override
                    public void onComplete() {
                        LogUtils.toE("Observer", "onComplete");

                    }
                });
    }


    public void netGetTableDataCall() {

        Observable
                .create(new ObservableOnSubscribe<NewsInfo>() {
                    @Override
                    public void subscribe(ObservableEmitter<NewsInfo> e) throws Exception {

                        try {
                            Thread.sleep(5000);
                        } catch (Exception e1) {
                            e1.printStackTrace();
                        }

                        Response<NewsInfo> response = ApplicationUtils
                                .getApplicition(thisActivity)
                                .getApiNetService()
                                .getQueueListCall("headline", "T1348647909107", 0)
                                .execute();
                        e.onNext(response.body());
                        e.onComplete();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Observer<NewsInfo>() {

                            Disposable d;

                            @Override
                            public void onSubscribe(Disposable d) {
                                disposable.add(d);
                                this.d = d;
                                LogUtils.toE("Disposable", "onSubscribe:" + d.isDisposed());
                            }

                            @Override
                            public void onNext(NewsInfo value) {
                                LogUtils.toE("Disposable", "onNext:" + d.isDisposed());
                                LogUtils.toJsonString("NewsInfo", value);
                                tv_content.setText(JsonLog.printJson(new Gson().toJson(value)));

                            }

                            @Override
                            public void onError(Throwable e) {
                                LogUtils.toE("Disposable", "onError:" + d.isDisposed());
                                disposable.remove(d);
                            }

                            @Override
                            public void onComplete() {
                                disposable.remove(d);
                                LogUtils.toE("Disposable", "onComplete:" + d.isDisposed());
                            }
                        }
                );


//        new Observer<NewsInfo>() {
//
//            Disposable d;
//
//            @Override
//            public void onSubscribe(Disposable d) {
//                disposable.add(d);
//                this.d = d;
//                LogUtils.toE("Disposable", "onSubscribe:" + d.isDisposed());
//            }
//
//            @Override
//            public void onNext(NewsInfo value) {
//                LogUtils.toE("Disposable", "onNext:" + d.isDisposed());
//                LogUtils.toJsonString("NewsInfo", value);
//
//                tv_content.setText(JsonLog.printJson(new Gson().toJson(value)));
//
//            }
//
//            @Override
//            public void onError(Throwable e) {
//                LogUtils.toE("Disposable", "onError:" + d.isDisposed());
//                disposable.remove(d);
//            }
//
//            @Override
//            public void onComplete() {
//                disposable.remove(d);
//                LogUtils.toE("Disposable", "onComplete:" + d.isDisposed());
//            }
//        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_1:
                netGetTableData();
                break;
            case R.id.btn_2:
                netGetTableDataCall();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        disposable.clear();
        LogUtils.toE("Disposable", "onDestroy:");
    }
}
