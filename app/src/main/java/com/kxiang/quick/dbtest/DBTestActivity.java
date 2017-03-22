package com.kxiang.quick.dbtest;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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

public class DBTestActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbtest);
        initStatusBarColor();
        initTitle();
        initView();
    }


    private DaoBlacklist daoBlacklist;
    private EditText et_number;
    private EditText et_number1;

    @Override
    protected void initView() {
        daoBlacklist = (DaoBlacklist) DaoFactory.getTable(DaoFactory.BlackeList, thisActivity);
        findViewById(R.id.btn_1).setOnClickListener(this);
        findViewById(R.id.btn_2).setOnClickListener(this);
        findViewById(R.id.btn_3).setOnClickListener(this);
        findViewById(R.id.btn_4).setOnClickListener(this);
        et_number = (EditText) findViewById(R.id.et_number);
        et_number1 = (EditText) findViewById(R.id.et_number1);


    }

    int save = 10;

    @Override
    public void onClick(View v) {
        save++;
        switch (v.getId()) {
            case R.id.btn_1:
//                btn1();
                daoBlacklist.insert(save + "", System.currentTimeMillis() + "");
                break;
            case R.id.btn_2:
//                btn1();
                daoBlacklist.deleteAndroid(et_number.getText().toString());
                break;

            case R.id.btn_3:
//                btn1();
                daoBlacklist.delete();
                break;
            case R.id.btn_4:
                daoBlacklist.select(et_number1.getText().toString());
                break;


        }
    }


    private void btn1() {
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {

                        daoBlacklist.insert(12346 + "", System.currentTimeMillis() + "");


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
}
