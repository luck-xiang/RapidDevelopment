package com.kxiang.quick.function.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseActivity;
import com.kxiang.quick.db.DaoFactory;
import com.kxiang.quick.db.DaoTest;
import com.kxiang.quick.db.TestBean;
import com.kxiang.quick.utils.ApplicationUtils;
import com.kxiang.quick.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Response;

public class SQLiteActivity extends BaseActivity implements View.OnClickListener {
    @Override
    protected int getContentView() {
        return R.layout.activity_sqlite;
    }

    DaoTest testTable;

    List<TestBean> testBeen;
    private TextView tv_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        DaoTest testTable = (DaoTest) DaoFactory
                .getTable(DaoFactory.TestTable, getApplicationContext());
        testBeen = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            TestBean bean = new TestBean();
            bean.setId_DB(i);
            bean.setName("数据：" + i);
            bean.setSex("男" + i);
            testBeen.add(bean);
        }
        tv_show = (TextView) findViewById(R.id.tv_show);

    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_1:
                dataBase(1);

                break;
            case R.id.btn_2:
                dataBase(2);
                break;
            case R.id.btn_3:

                Observable
                        .create(new ObservableOnSubscribe<String>() {
                            @Override
                            public void subscribe(ObservableEmitter<String> e) throws Exception {

                                RequestBody body = RequestBody.create(okhttp3.MediaType.
                                        parse("application/json; charset=utf-8"), "{\"id\":2103}");

                                Response<ResponseBody> backBody = ApplicationUtils
                                        .getApplicition(getApplication())
                                        .getApiNetService()
                                        .setData("2013")
                                        .execute();
                                LogUtils.toE(backBody.code());
                                LogUtils.toE(backBody.body().string());
                                LogUtils.toE(backBody.message());
                                LogUtils.toE(backBody.errorBody().string());
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

                break;


        }
    }

    public void dataBase(final int type) {

//        final ProgressDialog pd = new ProgressDialog(thisActivity);
//        pd.setCancelable(false);
//        pd.setMessage("保存中……");
//        pd.show();
        final long starTime = System.currentTimeMillis();
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {

                        if (type == 1) {
                            testTable.insert(testBeen);
                            e.onNext("成功");
                        }
                        else if (type == 2) {
                            for (int i = 0; i < testBeen.size(); i++) {
                                String[] insert = new String[]{
                                        testBeen.get(i).getName(),
                                        testBeen.get(i).getSex(),
                                        testBeen.get(i).getId_DB() + ""
                                };
                                testTable.insert(insert);
                            }
                            e.onNext("成功");
                        }

                        e.onComplete();
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
                        tv_show.setText("耗时：" + (System.currentTimeMillis() - starTime));
//                        pd.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        tv_show.setText(e.getMessage());
//                        pd.dismiss();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


}
