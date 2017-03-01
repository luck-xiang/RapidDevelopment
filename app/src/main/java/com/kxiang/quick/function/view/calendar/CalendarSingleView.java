package com.kxiang.quick.function.view.calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称:JobLogging
 * 创建人:kexiang
 * 创建时间:2016/10/25 14:17
 * <p>
 * 单个 layout_height的7.5倍
 * 单个 layout_width的7倍
 */

public class CalendarSingleView extends View {

    private LunarCalendar lunarCalendar;

    public CalendarSingleView(Context context, int year, int month, int today,
                              LunarCalendar lunarCalendar) {
        super(context);
        this.lunarCalendar = lunarCalendar;
        init(year, month, today);
    }

//    public CalendarSingleView(Context context, AttributeSet attrs) {
//        super(context, attrs);
//        init(2016, 10, 25);
//    }
//
//    public CalendarSingleView(Context context, AttributeSet attrs, int defStyleAttr) {
//        super(context, attrs, defStyleAttr);
//
//        init(2016, 10, 25);
//    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画框横线
//        canvas.drawPath(utils.gridPath, utils.gridPaint);
        //星期水平加垂直居中
        Paint.FontMetricsInt fontMetrics = utils.weekPaint.getFontMetricsInt();
        float baseline = (utils.weekHeight - fontMetrics.bottom - fontMetrics.top) / 2f;
        for (int i = 0; i < utils.weekText.length; i++) {
            if (i == 0 || i == utils.weekText.length - 1) {
                utils.weekPaint.setColor(utils.weekendColor);
            }
            else {
                utils.weekPaint.setColor(utils.workdayColor);
            }
            canvas.drawText(utils.weekText[i], utils.width * (1 + i * 2) / 14f,
                    baseline, utils.weekPaint);
        }
        //选中时候的背景
        // 按下状态，选择状态背景色
        if (today == NO_MONTH && downBgIndex == weakSize) {

        }
        else {
            drawSelectBg(canvas, downBgIndex);
        }

        //日期水平加垂直居中
        Paint.FontMetricsInt fontMetricsDay = utils.weekDayPaint.getFontMetricsInt();
//        //在方框中居中显示
//        float baseDayline = utils.weekHeight + (utils.singleDayHeigh / 2f -
//                fontMetricsDay.bottom - fontMetricsDay.top) / 2f;
        //在方框中居底显示
        float baseDayline = utils.weekHeight + utils.singleDayHeigh / 2f - 2;




        //农历水平加垂直居中
        Paint.FontMetricsInt fontMetricsLunarDay = utils.weekLunarPaint.getFontMetricsInt();

        //在方框中居中显示
        float baseLunarDayline = utils.weekHeight + (utils.singleDayHeigh * 3 / 2f -
                fontMetricsLunarDay.bottom - fontMetricsLunarDay.top) / 2f;

        //在方框中居顶显示
//        float baseLunarDayline = utils.weekHeight + utils.singleDayHeigh / 2f - fontMetricsLunarDay.ascent + 2;



        int day = 0;
        for (int i = 0; i < 6; i++) {
            float weakDayLine = baseDayline + utils.singleDayHeigh * (i * 2) / 2f;

            float weakDayLunarLine = baseLunarDayline + utils.singleDayHeigh * (i * 2) / 2f;

            for (int j = 0; j < 7; j++) {


                int color = utils.noSelectTextColor;
                if (monthBean.getDay().get(day).isDimBright()) {
                    if (monthBean.getDay().get(day).getSolarCalendar().equals(today + "")) {
                        color = utils.todayTextColor;
                    }
                    else {
                        color = utils.textColor;
                    }
                }
                utils.weekDayPaint.setColor(color);
                utils.weekLunarPaint.setColor(color);

                canvas.drawText(
                        monthBean.getDay().get(day).getSolarCalendar(),
                        utils.width * (1 + j * 2) / 14f,
                        weakDayLine,
                        utils.weekDayPaint);

                canvas.drawText(
                        monthBean.getDay().get(day).getLunarCalendar(),
                        utils.width * (1 + j * 2) / 14f,
                        weakDayLunarLine,
                        utils.weekLunarPaint);


                day++;
            }
        }


    }


    private void drawSelectBg(Canvas canvas, int index) {
        int x = getXByIndex(index);
        int y = getYByIndex(index);
        float left = utils.singleDayWith * (x - 1) + 1;
        float top = utils.singleDayHeigh * (y - 1) + utils.weekHeight + 1;
//        canvas.drawRect(left, top,
//                left + utils.singleDayWith - 1,
//                top + utils.singleDayHeigh - 1,
//                utils.selectBgPaint);
        canvas.drawCircle(left + utils.singleDayWith/2,top+ utils.singleDayHeigh/2,
                utils.singleDayHeigh/2,
                utils.selectBgPaint);

    }

    private int getXByIndex(int i) {
        return i % 7 + 1; // 1 2 3 4 5 6 7
    }

    private int getYByIndex(int i) {
        return i / 7 + 1; // 1 2 3 4 5 6
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                setSelectedDateByCoor(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                invalidate();
                if (onItemClickListener != null && dayBoolean) {

                    if (monthBean.getDay().get(downDataIndex).isDimBright()) {
                        //响应监听事件
                        onItemClickListener.OnItemClick(monthBean.getDay().
                                get(downDataIndex));
                    }
                }
                break;
        }
        return true;
    }


    private int downDataIndex;
    private boolean dayBoolean = false;

    private void setSelectedDateByCoor(float x, float y) {

        if (y > utils.weekHeight) {
            dayBoolean = true;
            int m = (int) (Math.floor(x / utils.singleDayWith) + 1);
            int n = (int) (Math
                    .floor((y - (utils.weekHeight))
                            / Float.valueOf(utils.singleDayHeigh)) + 1);
            downDataIndex = (n - 1) * 7 + m - 1;
            if (monthBean.getDay().get(downDataIndex).isDimBright()) {
                downBgIndex = downDataIndex;
            }

        }
        else {
            dayBoolean = false;
        }
    }

    private class Utils {
        private String[] weekText = {"日", "一", "二", "三", "四", "五", "六"};
        private int width;
        private int height;
        private float weekHeight;
        //网格画笔
        private Paint gridPaint;
        //网格路径
        private Path gridPath;
        //星期显示画笔
        private Paint weekPaint;
        //工作日颜色
        private int workdayColor = Color.parseColor("#00CD66");
        //休息日颜色
        private int weekendColor = Color.RED;

        //具体日期画笔
        private Paint weekDayPaint;
        //农历画笔
        private Paint weekLunarPaint;
        //当天日期颜色
        private int todayTextColor = Color.RED;
        //当月日期颜色
        private int textColor = Color.BLACK;
        //非当月日期颜色
        private int noSelectTextColor = Color.parseColor("#CCCCCC");

        //选中背景颜色
        private Paint selectBgPaint;
        private int selectBgColor = Color.parseColor("#99CCFF");

        private float singleDayWith;
        private float singleDayHeigh;

        void init() {

            weekHeight = height / 7f;
            if (weekHeight < width / 7f) {
                weekHeight = width / 7f;
            }
            singleDayHeigh = (height - weekHeight) / 6f;
            singleDayWith = width / 7f;


            weekPaint = new Paint();
            weekPaint.setColor(Color.BLACK);
            weekPaint.setAntiAlias(true);
            weekPaint.setTextSize(width / 7 * 0.40f);
            weekPaint.setStyle(Paint.Style.FILL);
            weekPaint.setTextAlign(Paint.Align.CENTER);

            weekDayPaint = new Paint();
            weekDayPaint.setColor(textColor);
            weekDayPaint.setAntiAlias(true);
            weekDayPaint.setTextSize(width / 7 * 0.35f);
            weekDayPaint.setStyle(Paint.Style.FILL);
            weekDayPaint.setTextAlign(Paint.Align.CENTER);

            weekLunarPaint = new Paint();
            weekLunarPaint.setColor(textColor);
            weekLunarPaint.setAntiAlias(true);
            weekLunarPaint.setTextSize(width / 7 * 0.25f);
            weekLunarPaint.setStyle(Paint.Style.FILL);
            weekLunarPaint.setTextAlign(Paint.Align.CENTER);


            gridPaint = new Paint();
            gridPaint.setColor(Color.RED);
            gridPaint.setStyle(Paint.Style.STROKE);
            //gridPaint.setStrokeWidth(2);

            gridPath = new Path();
            gridPath.moveTo(0, weekHeight);
            gridPath.lineTo(utils.width, weekHeight);

            for (int i = 1; i < 6; i++) {
                gridPath.moveTo(0, weekHeight + singleDayHeigh * i);
                gridPath.lineTo(width, weekHeight + singleDayHeigh * i);
                gridPath.moveTo(singleDayWith * i, 0);
                gridPath.lineTo(singleDayWith * i, height);
            }
            gridPath.moveTo(singleDayWith * 6, 0);
            gridPath.lineTo(singleDayWith * 6, height);

            selectBgPaint = new Paint();
            selectBgPaint.setAntiAlias(true);
            selectBgPaint.setStyle(Paint.Style.FILL);
            selectBgPaint.setColor(selectBgColor);

        }
    }

    private Utils utils;
    private DataMonthBean monthBean;
    private SpecialCalendar specialCalendar;
    private int today;

    private void init(int year, int month, int today) {
        this.today = today;
        utils = new Utils();
        specialCalendar = new SpecialCalendar();
        monthBean = getCalenderBean(year, month, today);

        setBackgroundColor(Color.parseColor("#FFFFFF"));
    }


    public int getWeek(int y, int m, int d) {
        if (m < 3) {
            m += 12;
            --y;
        }
        return (d + 1 + 2 * m + 3 * (m + 1) / 5 + y + (y >> 2) - y / 100 + y / 400) % 7;
    }

    private int downBgIndex = 0; // 按下的格子索引
    public final static int NO_MONTH = -1;
    private int weakSize = 0;

    private DataMonthBean getCalenderBean(int year, int month, int today) {


        int lastMonth = month - 1;
        int lastMonthYear = year;
        if (month == 1) {
            lastMonth = 12;
            lastMonthYear = year - 1;
        }


        int nextMonth = month + 1;
        int nextMonthYear = year;
        if (month == 12) {
            nextMonth = 1;
            nextMonthYear = year + 1;
        }

        int firstWeak = weakSize = getWeek(year, month, 1);

        weakSize = downBgIndex = downDataIndex = today + firstWeak - 1;

        int showMonthDay = specialCalendar.getDaysOfMonth(
                specialCalendar.isLeapYear(year)
                , month
        );
        if (specialCalendar.isLeapYear(year) && month == 2) {
            showMonthDay = specialCalendar.getDaysOfMonth(
                    specialCalendar.isLeapYear(year)
                    , month
            );
        }

        int lastMonthDay = specialCalendar.getDaysOfMonth(
                specialCalendar.isLeapYear(year)
                , lastMonth
        ) + 1 - firstWeak;
        if (specialCalendar.isLeapYear(year) && (month - 1) == 2) {
            lastMonthDay = specialCalendar.getDaysOfMonth(
                    specialCalendar.isLeapYear(year)
                    , lastMonth
            ) + 1 - firstWeak;
        }


        DataMonthBean monthBean = new DataMonthBean();
        List<DataMonthBean.DayBean> beanList = new ArrayList<>();
        int next = 1;
        for (int i = 0; i < 42; i++) {

            if (firstWeak == 0) {
                //下个月
                if ((i - firstWeak) >= showMonthDay) {
                    DataMonthBean.DayBean dayBean = new DataMonthBean.DayBean();
                    //这个是设置农历的，犹豫在网上找了好久都没有找到正确的农历算法，暂时没有弄上去
                    dayBean.setLunarCalendar(
                            lunarCalendar.getLunarDate(lastMonthYear, lastMonth, next, false));
                    dayBean.setLunarYear(lunarCalendar.getYear());
                    dayBean.setLunarMonth(lunarCalendar.getLunarMonth());
                    dayBean.setSolarCalendar("" + next);
                    dayBean.setDimBright(false);

                    beanList.add(dayBean);
                    next++;
                }
                //当前月
                else {
                    DataMonthBean.DayBean dayBean = new DataMonthBean.DayBean();
                    dayBean.setLunarCalendar(
                            lunarCalendar.getLunarDate(year, month, i + 1, false));
                    dayBean.setLunarYear(lunarCalendar.getYear());
                    dayBean.setLunarMonth(lunarCalendar.getLunarMonth());
                    dayBean.setSolarCalendar("" + (i + 1));
                    dayBean.setDimBright(true);
                    beanList.add(dayBean);
                }
            }
            else {
                //上个月
                if (i < firstWeak) {
                    DataMonthBean.DayBean dayBean = new DataMonthBean.DayBean();
                    dayBean.setLunarCalendar(
                            lunarCalendar.getLunarDate(lastMonthYear, lastMonth, lastMonthDay, false));
                    dayBean.setLunarYear(lunarCalendar.getYear());
                    dayBean.setLunarMonth(lunarCalendar.getLunarMonth());
                    dayBean.setSolarCalendar("" + lastMonthDay);
                    dayBean.setDimBright(false);
                    beanList.add(dayBean);
                    lastMonthDay++;
                }
                else {
                    //下个月
                    if ((i - firstWeak) >= showMonthDay) {
                        DataMonthBean.DayBean dayBean = new DataMonthBean.DayBean();
                        dayBean.setLunarCalendar(
                                lunarCalendar.getLunarDate(nextMonthYear, nextMonth, next, false));
                        dayBean.setLunarYear(lunarCalendar.getYear());
                        dayBean.setLunarMonth(lunarCalendar.getLunarMonth());
                        dayBean.setSolarCalendar("" + next);
                        dayBean.setDimBright(false);
                        beanList.add(dayBean);
                        next++;
                    }
                    //当前月
                    else {
                        DataMonthBean.DayBean dayBean = new DataMonthBean.DayBean();
                        dayBean.setLunarCalendar(
                                lunarCalendar.getLunarDate(year, month,
                                        (i + 1 - firstWeak), false));
                        dayBean.setLunarYear(lunarCalendar.getYear());
                        dayBean.setLunarMonth(lunarCalendar.getLunarMonth());
                        dayBean.setSolarCalendar("" + (i + 1 - firstWeak));
                        dayBean.setDimBright(true);
                        beanList.add(dayBean);
                    }
                }
            }

        }
        monthBean.setDay(beanList);
        return monthBean;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {


        int tempWidth = MeasureSpec.getSize(widthMeasureSpec);
        int tempHeight = MeasureSpec.getSize(heightMeasureSpec);
        //保证每个格子不纯在小数
        utils.width = tempWidth - tempWidth % 7;
        utils.height = tempHeight - tempHeight % 15;
        widthMeasureSpec = MeasureSpec.makeMeasureSpec(utils.width,
                MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(utils.height,
                MeasureSpec.EXACTLY);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        if (changed) {
            utils.init();
        }
        super.onLayout(changed, left, top, right, bottom);
    }


    private OnItemClickListener onItemClickListener;

    //给控件设置监听事件
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    //监听接口
    public interface OnItemClickListener {
        void OnItemClick(DataMonthBean.DayBean dayBean);
    }
}
