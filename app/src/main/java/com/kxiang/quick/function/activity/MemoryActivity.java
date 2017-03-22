package com.kxiang.quick.function.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.kexiang.function.utils.LogUtils;
import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseActivity;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MemoryActivity extends BaseActivity {

    private TextView tv_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_memory);
//        MyThread myThread=  new MyThread();
//        myThread.start();
        tv_show = (TextView) findViewById(R.id.tv_show);
        netWork();

    }

    @Override
    protected void initView() {

    }

    Disposable able;

    private void netWork() {
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {

                        for (int i = 0; i < 10; i++) {
                            e.onNext(i + "");
                            LogUtils.toE("onNext", i + "");
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ex) {
//                                Thread.currentThread().interrupt();
                            }

                            if (i == 5) {
                                able.dispose();
                            }
                        }
                        LogUtils.toE(able.isDisposed());
                        e.onComplete();
                    }
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        disposable.add(d);
                        able = d;
                    }

                    @Override
                    public void onNext(String value) {
                        tv_show.setText(value);
                        LogUtils.toE(value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.toNetError(e);
                    }

                    @Override
                    public void onComplete() {

                        LogUtils.toE("onComplete");
                        LogUtils.toE(able.isDisposed());

                    }
                });
    }

    private static class MyThread extends Thread {
        @Override
        public void run() {
            int i = 100;
            for (int i1 = 0; i1 < i; i1++) {

                LogUtils.toE("MemoryActivity", "onNext:" + i1);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException ex) {

                }

            }
        }
    }





}
