package com.kxiang.quick.function.activity.own;

import android.os.Bundle;

import com.kexiang.function.view.own.BellCircleView;
import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseActivity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class OwnViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_own_view);
        initStatusBarColor();
        title_bar.setTitleName("自定义view");
        initView();

    }


    @Override
    protected void initView() {
        initBellCircleView();
    }

    private BellCircleView bell_circle_view;
    private boolean bellCircleBoolean = true;

    private void initBellCircleView() {
        bell_circle_view = (BellCircleView) findViewById(R.id.bell_circle_view);

        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("hh:mm:ss", Locale.CANADA);

        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {

                        while (bellCircleBoolean) {
                            e.onNext(simpleDateFormat.format(new Date()));
                            try {
                                Thread.sleep(1000);
                            } catch (Exception e1) {
                                e1.printStackTrace();
                            }
                        }

                        e.onComplete();
                    }
                })
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {

                        String[] time = value.split(":");
//                        LogUtils.toE(value);
                        bell_circle_view.initData(
                                Integer.parseInt(time[0]),
                                Integer.parseInt(time[1]),
                                Integer.parseInt(time[2])
                        );
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bellCircleBoolean = false;
    }
}
