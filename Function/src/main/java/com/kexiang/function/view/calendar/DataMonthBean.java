package com.kexiang.function.view.calendar;

import java.io.Serializable;
import java.util.List;

/**
 * 项目名称:UserDefinedView
 * 创建人:kexiang
 * 创建时间:2016/10/21 12:52
 * 当日的信息
 */

public class DataMonthBean implements Serializable {

    private String month;
    private List<DayBean> day;

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public List<DayBean> getDay() {
        return day;
    }

    public void setDay(List<DayBean> day) {
        this.day = day;
    }

    public static class DayBean implements Serializable {

        private String solarCalendar;
        private String lunarCalendar;
        private boolean dimBright;
        private int lunarYear;
        private String lunarMonth;

        public int getLunarYear() {
            return lunarYear;
        }

        public void setLunarYear(int lunarYear) {
            this.lunarYear = lunarYear;
        }

        public String getLunarMonth() {
            return lunarMonth;
        }

        public void setLunarMonth(String lunarMonth) {
            this.lunarMonth = lunarMonth;
        }

        public boolean isDimBright() {
            return dimBright;
        }

        public void setDimBright(boolean dimBright) {
            this.dimBright = dimBright;
        }

        /**
         * 阳历
         *
         * @return
         */
        public String getSolarCalendar() {
            return solarCalendar;
        }

        public void setSolarCalendar(String solarCalendar) {
            this.solarCalendar = solarCalendar;
        }

        /**
         * 阴历
         *
         * @return
         */
        public String getLunarCalendar() {
            return lunarCalendar;
        }

        public void setLunarCalendar(String lunarCalendar) {
            this.lunarCalendar = lunarCalendar;
        }
    }

}
