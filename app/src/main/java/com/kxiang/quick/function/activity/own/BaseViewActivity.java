package com.kxiang.quick.function.activity.own;

import android.os.Bundle;

import com.kexiang.function.utils.LogUtils;
import com.kexiang.function.view.own.calendar.CalendarUtils;
import com.kexiang.function.view.own.calendar.LunarCalendarUtils;
import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

public class BaseViewActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base_view);
        initStatusBarColor();
        initView();


    }


    @Override
    protected void initView() {
//        LogUtils.toE("BaseViewActivity", "getFirstDayWeek:" + CalendarUtils.getFirstDayWeek(2017, 4));
//
//        List<LunarCalendarUtils.Solar> solarList = getSolarData(2017, 5);
//
//        List<LunarCalendarUtils.Lunar> lunarList = new ArrayList<>();
//        for (int i = 0; i < solarList.size(); i++) {
//            lunarList.add(LunarCalendarUtils.solarToLunar(solarList.get(i)));
//
//            LogUtils.toE("lunarList", ""
//                    + lunarList.get(i).getLunarYear() + "-"
//                    + lunarList.get(i).getLunarMonth() + "-"
//                    + lunarList.get(i).getLunarDay()
//            );
//
//        }
    }


    public List<LunarCalendarUtils.Solar> getSolarData(int year, int month) {

        int mMonth = month - 1;
        List<LunarCalendarUtils.Solar> solarsList = new ArrayList<>();
        int position = 42;
        int lastMonthDays;

        if (mMonth == 0 || mMonth == 11) {
            lastMonthDays = 31;
        }
        else {
            lastMonthDays = CalendarUtils.getMonthDays(year, mMonth - 1);
        }

        int monthDays = CalendarUtils.getMonthDays(year, mMonth);
        int firstDayWeek = CalendarUtils.getFirstDayWeek(year, mMonth);
        for (int i = 1; i <= position; i++) {
            LunarCalendarUtils.Solar solar;
            if (i < firstDayWeek) {
                if (mMonth == 0) {
                    solar = new LunarCalendarUtils.Solar(year - 1, 12, lastMonthDays - i + 1, false);
                }
                else {
                    solar = new LunarCalendarUtils.Solar(year, month - 1, lastMonthDays - i + 1, false);
                }

            }
            else if (i >= (firstDayWeek + monthDays)) {
                if (mMonth == 11) {
                    solar = new LunarCalendarUtils.Solar(year + 1, 1,
                            i - firstDayWeek - monthDays + 1, false);
                }
                else {
                    solar = new LunarCalendarUtils.Solar(year, month + 1,
                            i - firstDayWeek - monthDays + 1, false);
                }


            }
            else {
                solar = new LunarCalendarUtils.Solar(year, month, i - firstDayWeek + 1, true);
            }

            solarsList.add(solar);
        }

        for (int i = 0; i < solarsList.size(); i++) {
            LogUtils.toE("solarsList", ""
                    + solarsList.get(i).getSolarYear() + "-"
                    + solarsList.get(i).getSolarMonth() + "-"
                    + solarsList.get(i).getSolarDay()
            );
        }
        return solarsList;
    }
}
