package com.kxiang.quick.function.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseActivity;
import com.kxiang.quick.function.view.calendar.DataMonthBean;
import com.kxiang.quick.function.view.calendar.DialogCalender;
import com.kxiang.quick.function.view.loop.calendar.CalendarWheelDialog;

public class CalanderActivity extends BaseActivity implements View.OnClickListener {

    @Override
    protected int getContentView() {
        return R.layout.activity_calander;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initTitle();
        tv_title_name.setText("日历控件");
        initView();
    }

    private TextView tv_loop;
    private TextView tv_calendar;
    private CheckBox cb_1;
    private CheckBox cb_2;
    private CheckBox cb_3;
    private CheckBox cb_4;
    private CheckBox cb_5;

    private void initView() {
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
                    public void onDateSelect(String year, String month, String day, String hh, String mm) {
                        tv_loop.setText(year + "-" + month + "-" + day + "  " + hh + ":" + mm);

                    }
                });
                break;
            case R.id.btn_2:
                final DialogCalender dialogCalender=DialogCalender.newInstance(2016,12,22);
                dialogCalender.show(getSupportFragmentManager(),"calender");
                dialogCalender.setOnItemClickListener(new DialogCalender.OnItemClickListener() {
                    @Override
                    public void OnItemClick(int year, int month, DataMonthBean.DayBean dayBean) {

                        tv_calendar.setText(year + "-" + month + "-" + dayBean.getSolarCalendar()
                                + "\n农历:" + dayBean.getLunarYear() + " " + dayBean.getLunarMonth()+
                        " "+dayBean.getLunarCalendar());
                        dialogCalender.dismiss();
                    }
                });
                break;
        }
    }
}
