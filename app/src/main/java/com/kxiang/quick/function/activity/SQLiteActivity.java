package com.kxiang.quick.function.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.kexiang.function.utils.LogUtils;
import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseActivity;
import com.kxiang.quick.db.DaoFactory;
import com.kxiang.quick.db.DaoTest;
import com.kxiang.quick.db.TestBean;
import com.kxiang.quick.temp.GetTicket;
import com.kxiang.quick.temp.Sum;
import com.kxiang.quick.utils.ApplicationUtils;

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

    DaoTest testTable;

    List<TestBean> testBeen;
    List<TestBean> testBeen1;
    private TextView tv_show;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_sqlite);
        testTable = (DaoTest) DaoFactory
                .getTable(DaoFactory.TestTable, getApplicationContext());
        testBeen = new ArrayList<>();
        testBeen1 = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            TestBean bean = new TestBean();
            TestBean bean1 = new TestBean();

            bean.setName("数据testBeen：" + i);
            bean1.setName("数据testBeen：" + i);
            bean1.setId_DB(i);
            if (i == 6000) {
                bean.setId_DB(100000);
            }
            else {
                bean.setId_DB(100000);
            }
            bean.setSex("男" + i);
            bean1.setSex("男bean1" + i);
            testBeen.add(bean);
            testBeen1.add(bean1);
        }
        tv_show = (TextView) findViewById(R.id.tv_show);


    }

    Sum sum = new Sum();

    @Override
    protected void initView() {

    }


    int position = 0;

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_1:

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        testTable.getDatabase().beginTransaction();
                        testTable.insertAndroid( new String[]{
                                "fdsa",
                                "sdf",
                                "0001",
                        });

                        try {
                            Thread.sleep(60000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        testTable.getDatabase().setTransactionSuccessful();
                        testTable.getDatabase().endTransaction();

                        position++;
                        LogUtils.toE("sql", "insert:testBeen" + position);
                    }
                }).start();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        testTable.insertAndroid(testBeen1, "线程2");
                        position++;
                        LogUtils.toE("sql", "insert:testBeen" + position);
                    }
                }).start();
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        testTable.insertAndroid(testBeen1, "线程3");
                        position++;
                        LogUtils.toE("sql", "insert:testBeen" + position);
                    }
                }).start();

                 new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        testTable.upDate("线程4", "100","线程4");
                        position++;
                        LogUtils.toE("sql", "insert:testBeen" + position);
                    }
                }).start();


//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        DBHelper helper = new DBHelper(thisActivity, null, 1);
//                        helper.getWritableDatabase().beginTransaction();
//                        helper.getWritableDatabase().beginTransaction();
//                        try {
//                            SQLiteStatement stmt = helper.getWritableDatabase().compileStatement(
//                                    DaoTest.Sql.insertALL
//                            );
//                            for (int i = 0; i < testBeen1.size(); i++) {
//                                LogUtils.toE("sql", "insert:" + type);
//                                stmt.bindString(1, testBeen1.get(i).getName());
//                                stmt.bindString(2, testBeen1.get(i).getSex());
//                                stmt.bindString(3, testBeen1.get(i).getId_DB() + "");
//                                stmt.execute();
//                                stmt.clearBindings();
//                            }
//                            //事务完成
//                            helper.getWritableDatabase().setTransactionSuccessful();
//
//                        } catch (Exception e) {
//
//                        } finally {
//                            //提交事务
//                            helper.getWritableDatabase().endTransaction();
//                        }
//
//                        testTable.insertAndroid(testBeen1, 3);
//                        position++;
//                        LogUtils.toE("sql", "insert:testBeen" + position);
//                    }
//                }).start();


//                dataBase(1);
//                dataBase(1);

                break;
            case R.id.btn_2:


                dataBase(2);
                break;
            case R.id.btn_4:
                dataBase(3);
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
            case R.id.btn_5:
                new Thread(new Runnable() {
                    @Override
                    public void run() {


                        while (sum.getPositionSize() > 0) {

                            Sum sum = getSum();
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            sum .getsum("线程1");
                        }


                    }
                }).start();
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                        while (sum.getPositionSize() > 0) {
                            getSum().getsum1("线程2");
//                            try {
//                                Thread.sleep(200);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
                        }


                    }
                }).start();
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        while (sum.getPositionSize() > 0) {
//                            getSum().getsum2("线程3");
//                            try {
//                                Thread.sleep(300);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//
//                    }
//                }).start();
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//
//                        while (sum.getPositionSize() > 0) {
//                            getSum().getsum3("线程4");
//                            try {
//                                Thread.sleep(200);
//                            } catch (InterruptedException e) {
//                                e.printStackTrace();
//                            }
//                        }
//
//
//                    }
//                }).start();


                break;

            case R.id.btn_6:
                GetTicket getTicket = new GetTicket();
                new Thread(getTicket, "线程1").start();
                new Thread(getTicket, "线程2").start();
                new Thread(getTicket, "线程3").start();
                new Thread(getTicket, "线程4").start();
                break;


        }
    }

    public Sum getSum() {
        synchronized (this) {
            return sum;
        }
    }

    int position1 = 0;


    public void dataBase(final int type) {

        final ProgressDialog pd = new ProgressDialog(thisActivity);
        pd.setCancelable(false);
        pd.setMessage("保存中……");
        pd.show();
        final long starTime = System.currentTimeMillis();
        Observable
                .create(new ObservableOnSubscribe<String>() {
                    @Override
                    public void subscribe(ObservableEmitter<String> e) throws Exception {

                        if (type == 1) {
                            testTable.insertAndroid(testBeen, "线程4");
                            e.onNext("第一种方法：");
                        }
                        else if (type == 2) {
                            for (int i = 0; i < testBeen.size(); i++) {
                                String[] insert = new String[]{
                                        testBeen.get(i).getName(),
                                        testBeen.get(i).getSex(),
                                        testBeen.get(i).getId_DB() + ""
                                };
                                testTable.insertAndroid(insert);
                            }
                            e.onNext("第二种方法：");
                        }
                        else if (type == 3) {
                            testTable.seletct(6000);
                            e.onNext("查询：");
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
                        tv_show.setText(value + "耗时：" + (System.currentTimeMillis() - starTime));
                        pd.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtils.toNetError(e);
                        tv_show.setText(e.getMessage());
                        pd.dismiss();
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


}
