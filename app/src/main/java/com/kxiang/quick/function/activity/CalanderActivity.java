package com.kxiang.quick.function.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.kexiang.function.view.calendar.DataMonthBean;
import com.kexiang.function.view.calendar.DialogCalender;
import com.kexiang.function.view.loop.calendar.CalendarWheelDialog;
import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseActivity;
import com.kxiang.quick.net.ApiNetworkAddressService;
import com.kxiang.quick.net.UrlFields;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.kxiang.quick.net.BaseRetrofit.gson;


public class CalanderActivity extends BaseActivity implements View.OnClickListener {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calander);
        initStatusBarColor();
        initView();


//        interceptor();
    }

    private TextView tv_loop;
    private TextView tv_calendar;
    private CheckBox cb_1;
    private CheckBox cb_2;
    private CheckBox cb_3;
    private CheckBox cb_4;
    private CheckBox cb_5;

    public void initView() {
        initTitle();
        title_name.setText("日历控件");
        tv_loop = (TextView) findViewById(R.id.tv_loop);
        tv_calendar = (TextView) findViewById(R.id.tv_calendar);
        cb_1 = (CheckBox) findViewById(R.id.cb_1);
        cb_2 = (CheckBox) findViewById(R.id.cb_2);
        cb_3 = (CheckBox) findViewById(R.id.cb_3);
        cb_4 = (CheckBox) findViewById(R.id.cb_4);
        cb_5 = (CheckBox) findViewById(R.id.cb_5);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.btn_1:
                CalendarWheelDialog dialogStart = new CalendarWheelDialog(this,
                        2016,
                        11,
                        12,
                        25,
                        30);
                dialogStart.show();

                if (!cb_1.isChecked()) {
                    dialogStart.hideYears();
                }

                if (!cb_2.isChecked()) {
                    dialogStart.hideMonth();
                }

                if (!cb_3.isChecked()) {
                    dialogStart.hideDay();
                }

                if (!cb_4.isChecked()) {
                    dialogStart.hideHH();
                }

                if (!cb_5.isChecked()) {
                    dialogStart.hideMM();
                }


                dialogStart.setOnDateSelectListener(new CalendarWheelDialog.OnDateSelectListener() {
                    @Override
                    public void onDateSelect(String year, String month,
                                             String day, String hh, String mm) {
                        tv_loop.setText(year + "-" + month + "-" + day + "  " + hh + ":" + mm);

                    }
                });
                break;
            case R.id.btn_2:
                final DialogCalender dialogCalender = DialogCalender.newInstance(2016, 12, 22);
                dialogCalender.show(getSupportFragmentManager(), "calender");
                dialogCalender.setOnItemClickListener(new DialogCalender.OnItemClickListener() {
                    @Override
                    public void OnItemClick(int year, int month, DataMonthBean.DayBean dayBean) {

                        tv_calendar.setText(year + "-" + month + "-" + dayBean.getSolarCalendar()
                                + "\n农历:" + dayBean.getLunarYear() + " " + dayBean.getLunarMonth() +
                                " " + dayBean.getLunarCalendar());
                        dialogCalender.dismiss();
                    }
                });
                break;
        }
    }


    private void interceptor() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                OkHttpClient okHttpClient =
                        new OkHttpClient.Builder()
                                .retryOnConnectionFailure(true)
                                .addInterceptor(new Interceptor() {
                                    @Override
                                    public Response intercept(Chain chain) throws IOException {
                                        Request request = chain.request();
                                        Log.e("zzz", "request====111111111111111111111111111111");
                                        Log.e("zzz", "request====" + request.headers().toString());
                                        okhttp3.Response proceed = chain.proceed(request);
                                        Log.e("zzz", "proceed====" + proceed.headers().toString());
                                        Log.e("zzz", "request.body()====" + request.body());
                                        Log.e("zzz", "proceed.body()====" + proceed.body().string());

                                        return proceed;
                                    }
                                })
                                .connectTimeout(10, TimeUnit.SECONDS)
                                .build();

                Retrofit retrofit =
                        new Retrofit.Builder()
                                .client(okHttpClient)
                                .baseUrl(UrlFields.URL_BASE)
                                .addConverterFactory(GsonConverterFactory.create(gson))
                                .build();

                try {
                    retrofit.create(ApiNetworkAddressService.class).newWork("http://www.baidu.com").execute();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }
}
