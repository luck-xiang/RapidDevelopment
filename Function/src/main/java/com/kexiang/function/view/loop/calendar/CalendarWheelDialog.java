package com.kexiang.function.view.loop.calendar;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;

import com.kexiang.function.utils.LogUtils;
import com.kexiang.function.view.loop.LoopView;
import com.kexiang.function.view.loop.OnItemSelectedListener;
import com.kxiang.function.R;

import java.util.ArrayList;
import java.util.List;


/**
 * 项目名称:growing
 * 创建人:kexiang
 * 创建时间:2016/8/17 13:22
 */
public class CalendarWheelDialog extends Dialog implements View.OnClickListener {

    private LoopView lv_years;
    private LoopView lv_month;
    private LoopView lv_day;
    private LoopView lv_hh;
    private LoopView lv_mm;
//    private TextView tv_years;
//    private TextView tv_month;
//    private TextView tv_day;
//    private TextView tv_hh;
//    private TextView tv_mm;

//    private LinearLayout ll_title;


//    private Button btn_canal;
//    private Button btn_sure;

    protected CalendarWheelDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    private int year;
    private int month;
    private int day;
    private int hh;
    private int mm;

    public CalendarWheelDialog(Context context,
                               int year,
                               int month,
                               int day,
                               int hh,
                               int mm) {
        super(context);
        this.year = year;
        this.month = month;
        this.day = day;
        this.hh = hh;
        this.mm = mm;
    }

    public CalendarWheelDialog(Context context, int themeResId) {
        super(context, themeResId);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.fun_dialog_calendar_wheel);
//        btn_sure = (Button) findViewById(btn_sure);
//        btn_canal = (Button) findViewById(btn_canal);
//        btn_sure.setOnClickListener(this);
//        btn_canal.setOnClickListener(this);

        findViewById(R.id.tv_dialog_cancel).setOnClickListener(this);
        findViewById(R.id.tv_dialog_sure).setOnClickListener(this);
//        tv_years = (TextView) findViewById(R.id.tv_years);
//        tv_month = (TextView) findViewById(R.id.tv_month);
//        tv_day = (TextView) findViewById(R.id.tv_day);
//        tv_hh = (TextView) findViewById(R.id.tv_hh);
//        tv_mm = (TextView) findViewById(R.id.tv_mm);
//        ll_title = (LinearLayout) findViewById(R.id.ll_title);


        createLoopView();

    }

    public void hideYears() {
//        tv_years.setVisibility(View.GONE);
        lv_years.setVisibility(View.GONE);
    }

    public void hideMonth() {
//        tv_month.setVisibility(View.GONE);
        lv_month.setVisibility(View.GONE);
    }

    public void hideDay() {
//        tv_day.setVisibility(View.GONE);
        lv_day.setVisibility(View.GONE);
    }

    public void hideHH() {
//        tv_hh.setVisibility(View.GONE);
        lv_hh.setVisibility(View.GONE);
    }


    public void hideMM() {
//        tv_mm.setVisibility(View.GONE);
        lv_mm.setVisibility(View.GONE);
    }

    public void hideTitle() {
//        ll_title.setVisibility(View.GONE);
    }


    List<String> yearsList;
    List<String> monthList;
    List<String> dayList;
    List<String> hhList;
    List<String> mmList;


    private void createLoopView() {

        yearsList = new ArrayList<>();
        monthList = new ArrayList<>();
        hhList = new ArrayList<>();
        mmList = new ArrayList<>();
        dayList = new ArrayList<>();


        for (int i = 1970; i <= 2050; i++) {
            yearsList.add(i + "");
        }
        for (int i = 1; i <= 12; i++) {
            monthList.add(i + "");
        }

        int tempDays = getDaysOfMonth(
                isLeapYear(year),
                month
        );

        for (int i = 1; i <= tempDays; i++) {
            dayList.add(i + "");

        }
        LogUtils.toE("tempDays", "" + tempDays);
        LogUtils.toE("tempDays", "" + month);


        for (int i = 0; i <= 23; i++) {
            hhList.add(i + "");

        }
        for (int i = 0; i < 60; i++) {
            mmList.add(i + "");

        }
        lv_years = (LoopView) findViewById(R.id.lv_years);
        lv_month = (LoopView) findViewById(R.id.lv_month);
        lv_day = (LoopView) findViewById(R.id.lv_day);
        lv_hh = (LoopView) findViewById(R.id.lv_hh);
        lv_mm = (LoopView) findViewById(R.id.lv_mm);
        //设置原始数据
        lv_years.setItems(yearsList);
        //设置初始位置
        lv_years.setInitPosition(year - 1970);
        //设置字体大小
        lv_years.setTextSize(20);
        //设置是否循环播放
        //loopView.setNotLoop();


        //设置原始数据
        lv_month.setItems(monthList);
        //设置初始位置
        lv_month.setInitPosition(month - 1);
        //设置字体大小
        lv_month.setTextSize(20);


        //设置原始数据
        lv_day.setItems(dayList);
        //设置初始位置
        lv_day.setInitPosition(day - 1);
        //设置字体大小
        lv_day.setTextSize(20);
        //设置是否循环播放
        //loopView.setNotLoop();


        //设置原始数据
        lv_hh.setItems(hhList);
        //设置初始位置
        lv_hh.setInitPosition(hh);
        //设置字体大小
        lv_hh.setTextSize(20);
        //设置是否循环播放
        //loopView.setNotLoop();


        //设置原始数据
        lv_mm.setItems(mmList);
        //设置初始位置
        lv_mm.setInitPosition(mm);
        //设置字体大小
        lv_mm.setTextSize(20);
        //设置是否循环播放
        //loopView.setNotLoop();


        lv_years.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {

                if (getInt(monthList.get(lv_month.getSelectedItem())) == 2) {

                    setMonthDay(getInt(yearsList.get(index)),
                            getInt(monthList.get(lv_month.getSelectedItem())));

                    lv_day.invalidate();
                }
            }
        });

        lv_month.setListener(new OnItemSelectedListener() {
            @Override
            public void onItemSelected(int index) {
                setMonthDay(getInt(yearsList.get(lv_years.getSelectedItem())),
                        getInt(monthList.get(index)));
                lv_day.invalidate();
            }
        });


    }

    private int getInt(String str) {
        //  return Integer.parseInt(str.substring(0, str.length() - 1));
        return Integer.parseInt(str);
    }


    private void setMonthDay(int year, int month) {

        dayList.clear();

        int tempDays = getDaysOfMonth(
                isLeapYear(year),
                month
        );

        for (int i = 1; i <= tempDays; i++) {
            dayList.add(i + "");

        }
    }


    // 得到某月有多少天数
    private int getDaysOfMonth(boolean isLeapyear, int month) {
        int daysOfMonth = 30;

        switch (month) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                daysOfMonth = 31;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                daysOfMonth = 30;
                break;
            case 2:
                if (isLeapyear) {
                    daysOfMonth = 29;
                }
                else {
                    daysOfMonth = 28;
                }

        }
        return daysOfMonth;
    }


    // 判断是否为闰年
    private boolean isLeapYear(int year) {
        if (year % 100 == 0 && year % 400 == 0) {
            return true;
        }
        else if (year % 100 != 0 && year % 4 == 0) {
            return true;
        }
        return false;
    }


    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.tv_dialog_cancel) {
            dismiss();

        }
        else if (i == R.id.tv_dialog_sure) {
            if (!lv_years.isLoopScrool() &&
                    !lv_month.isLoopScrool() &&
                    !lv_day.isLoopScrool() &&
                    !lv_hh.isLoopScrool() &&
                    !lv_mm.isLoopScrool()) {

                if (onDateSelectListener != null) {
                    String month;
                    String day;
                    String hh;
                    String mm;

                    if (monthList.get(lv_month.getSelectedItem()).length() == 1) {
                        month = "0" + monthList.get(lv_month.getSelectedItem());
                    }
                    else {
                        month = monthList.get(lv_month.getSelectedItem());
                    }

                    if (dayList.get(lv_day.getSelectedItem()).length() == 1) {
                        day = "0" + dayList.get(lv_day.getSelectedItem());
                    }
                    else {
                        day = dayList.get(lv_day.getSelectedItem());
                    }

                    if (hhList.get(lv_hh.getSelectedItem()).length() == 1) {
                        hh = "0" + hhList.get(lv_hh.getSelectedItem());
                    }
                    else {
                        hh = hhList.get(lv_hh.getSelectedItem());
                    }

                    if (mmList.get(lv_mm.getSelectedItem()).length() == 1) {
                        mm = "0" + mmList.get(lv_mm.getSelectedItem());
                    }
                    else {
                        mm = mmList.get(lv_mm.getSelectedItem());
                    }

                    onDateSelectListener.onDateSelect(
                            yearsList.get(lv_years.getSelectedItem()),
                            month,
                            day,
                            hh,
                            mm
                    );
                }
                dismiss();
            }


        }
    }


    public interface OnDateSelectListener {
        void onDateSelect(String year, String month, String day, String hh, String mm);
    }

    private OnDateSelectListener onDateSelectListener;

    public void setOnDateSelectListener(OnDateSelectListener onDateSelectListener) {
        this.onDateSelectListener = onDateSelectListener;
    }
}
