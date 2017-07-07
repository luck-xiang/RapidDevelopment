package com.kexiang.function.view.calendar;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.kexiang.function.utils.LogUtils;
import com.kexiang.function.view.own.CalendarAttenceView;
import com.kxiang.function.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 项目名称:JobLogging
 * 创建人:kexiang
 * 创建时间:2016/10/25 9:43
 */

public class DialogCalender extends DialogFragment implements View.OnClickListener {


    public static final String YEAR = "year";
    public static final String MONTH = "month";
    public static final String DAY = "day";

    public static DialogCalender newInstance(int year, int month, int day) {
        DialogCalender dialogCalender = new DialogCalender();
        Bundle args = new Bundle();
        args.putInt(YEAR, year);
        args.putInt(MONTH, month);
        args.putInt(DAY, day);
        dialogCalender.setArguments(args);
        return dialogCalender;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            year = getArguments().getInt(YEAR, -1);
            month = getArguments().getInt(MONTH, -1);
            day = getArguments().getInt(DAY, -1);
        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        return inflater.inflate(R.layout.fun_dialog_calendar, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initVpCalender();
    }


    private ViewPager vp_calender;
    private int year;
    private int month;
    private int day;
    private Calendar calender;

    private final int pagerItem = 960;
    private int maxMonth;
    private TextView tv_calendar_year;
    private TextView tv_calendar_month;
    //初始化农历算法
    private LunarCalendar lunarCalendar;


    private void initVpCalender() {

        calender = Calendar.getInstance();

        if (year == -1 || month == -1 || day == -1) {
            year = calender.get(Calendar.YEAR);
            month = calender.get(Calendar.MONTH) + 1;
            day = calender.get(Calendar.DAY_OF_MONTH);
        }
        LogUtils.toE("initVpCalender", "year+:" + year);
        LogUtils.toE("initVpCalender", "month:" + month);
        LogUtils.toE("initVpCalender", "day:" + day);


        maxMonth = (2050 - year + 1) * 12 - month + pagerItem + 1;

        lunarCalendar = new LunarCalendar();
        vp_calender = (ViewPager) getView().findViewById(R.id.vp_calender);
        tv_calendar_year = (TextView) getView().findViewById(R.id.tv_calendar_year);
        tv_calendar_month = (TextView) getView().findViewById(R.id.tv_calendar_month);
        tv_calendar_year.setText(year + "");
        tv_calendar_month.setText(month + "");

        getView().findViewById(R.id.btn_last).setOnClickListener(this);
        getView().findViewById(R.id.btn_next).setOnClickListener(this);
        vp_calender.setAdapter(new CalenderPageAdapter());
        vp_calender.setCurrentItem(pagerItem);

        vp_calender.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {


                int tempYear = (month + position - pagerItem - 1);
                int yearTemp;
                if (tempYear > 0) {
                    yearTemp = year + tempYear / 12;
                }
                else {
                    yearTemp = year + (tempYear - 12 + 1) / 12;
                }

                int monthTemp = (month + position - 1) % 12 + 1;
                tv_calendar_year.setText(yearTemp+"" );
                tv_calendar_month.setText(monthTemp+"");
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        tv_calendar_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> listTxt = new ArrayList<>();
                for (int i = 1970; i <= 2050; i++) {
                    listTxt.add(i + "");
                }
                final int selectYear=Integer.parseInt(tv_calendar_year.getText().toString())-1970;
                PopDropDownBox box = new PopDropDownBox(getContext(), tv_calendar_year.getWidth(),
                        tv_calendar_year, listTxt,
                        selectYear-3
                        );
                box.showAsDropDown(tv_calendar_year);
                box.setOnItemSelcectListener(new PopDropDownBox.OnItemSelcectListener() {
                    @Override
                    public void OnItemSelcect(int item) {

                        vp_calender.setCurrentItem(vp_calender.getCurrentItem() + (item) * 12);
                    }
                });
            }
        });
        tv_calendar_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<String> listTxt = new ArrayList<>();
                for (int i = 1; i <= 12; i++) {
                    listTxt.add(i + "");
                }
                final int selectMonth=Integer.parseInt(tv_calendar_month.getText().toString());
                PopDropDownBox box = new PopDropDownBox(getContext(), tv_calendar_month.getWidth(),
                        tv_calendar_month, listTxt,
                        selectMonth-3
                );
                box.showAsDropDown(tv_calendar_month);
                box.setOnItemSelcectListener(new PopDropDownBox.OnItemSelcectListener() {
                    @Override
                    public void OnItemSelcect(int item) {
                        vp_calender.setCurrentItem(vp_calender.getCurrentItem() + item);
                    }
                });
            }
        });

    }



    @Override
    public void onClick(View v) {

        int i = v.getId();
        if (i == R.id.btn_last) {
            vp_calender.setCurrentItem(vp_calender.getCurrentItem() - 1);

        }
        else if (i == R.id.btn_next) {
            if (getInt(tv_calendar_year.getText() + "") == 2050
                    &&
                    getInt(tv_calendar_month.getText() + "") == 12
                    ) {
                return;
            }
            vp_calender.setCurrentItem(vp_calender.getCurrentItem() + 1);

        }
    }


    public class CalenderPageAdapter extends PagerAdapter {

        @Override
        public Object instantiateItem(ViewGroup container, int position) {

            int tempYear = (month + position - pagerItem - 1);
            int yearTemp;
            if (tempYear > 0) {
                yearTemp = year + tempYear / 12;
            }
            else {
                yearTemp = year + (tempYear - 12 + 1) / 12;
            }

            int monthTemp = (month + position - 1) % 12 + 1;
            int dayTemp;
            if (yearTemp == (calender.get(Calendar.YEAR)) &&
                    monthTemp == (calender.get(Calendar.MONTH) + 1)) {
                dayTemp = calender.get(Calendar.DAY_OF_MONTH);
            }
            else {
                dayTemp = CalendarSingleView.NO_MONTH;
            }


//            CalendarSingleView view = new CalendarSingleView(getContext(), yearTemp, monthTemp,
//                    dayTemp, lunarCalendar);
//            view.setOnItemClickListener(calendarItemClickListener);
            CalendarAttenceView view = new CalendarAttenceView(getContext(), yearTemp, monthTemp,
                    dayTemp);
//            view.setOnItemClickListener(calendarItemClickListener);

            container.addView(view);
            return view;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {

            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return maxMonth;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }


    private CalendarItemClickListener calendarItemClickListener = new CalendarItemClickListener();

    class CalendarItemClickListener implements CalendarSingleView.OnItemClickListener {
        @Override
        public void OnItemClick(DataMonthBean.DayBean dayBean) {

            if (onItemClickListener != null)
                onItemClickListener.OnItemClick(
                        getInt(tv_calendar_year.getText() + ""),
                        getInt(tv_calendar_month.getText() + ""),
                        dayBean
                );


        }
    }

    private int getInt(String str) {

        return Integer.parseInt(str);

    }

    private OnItemClickListener onItemClickListener;

    //给控件设置监听事件
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //监听接口
    public interface OnItemClickListener {
        void OnItemClick(int year, int month, DataMonthBean.DayBean dayBean);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
    }

}
