package com.kexiang.function.view.own;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.kexiang.function.utils.LogUtils;
import com.kexiang.function.view.own.calendar.CalendarUtils;
import com.kexiang.function.view.own.calendar.LunarCalendarUtils;
import com.kxiang.function.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * 项目名称:RapidDevelopment
 * 创建人:kexiang
 * 创建时间:2017/5/5 9:36
 */

public class CalendarAttenceView extends View {
    public CalendarAttenceView(Context context) {
        super(context);
        initView();
    }


    public CalendarAttenceView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CalendarAttenceView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();

    }

    public CalendarAttenceView(Context context, int year, int month, int selectDay) {
        super(context);
        initPaith();
        setDate(year, month);

    }

    public CalendarAttenceView(Context context, int year, int month) {
        super(context);
        initPaith();
        setDate(year, month, month);
    }


    //日期画笔
    private Paint paintWeek;
    //阴历，阳历画笔
    private Paint paintSolarlLunar;

    //星期信息
    private String[] weekText;

    //阳历信息
    private List<LunarCalendarUtils.Solar> solarList;
    //阴历信息
    private List<LunarCalendarUtils.Lunar> lunarList;
    private int[] attenceStatus;
    //选中颜色
    private int selectColor;
    //默认颜色
    private int defaultColor;
    //点击位置
    private int selectX = SELECT_NO;
    private int selectY = SELECT_NO;

    //当前页面是否是当日所在月的位置
    private int todayX = SELECT_NO;
    private int todayY = SELECT_NO;

    private static final int SELECT_NO = -1;


    private void initView() {

        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
        String[] calendar = format.format(date).split("-");
        solarList = getSolarData(Integer.parseInt(calendar[0]), Integer.parseInt(calendar[1]));
        lunarList = new ArrayList<>();
        for (int i = 0; i < solarList.size(); i++) {
            lunarList.add(LunarCalendarUtils.solarToLunar(solarList.get(i)));

        }

        attenceStatus = new int[42];
        for (int i = 0; i < attenceStatus.length; i++) {
            if (i % 7 == 0) {
                attenceStatus[i] = 1;
                if (i > 0)
                    attenceStatus[i - 1] = 1;
            }
            else {
                attenceStatus[i] = 0;
            }
        }

        LogUtils.toE("calendar", "" + calendar[0] + calendar[1] + calendar[2]);

        judgeSelectToday(
                Integer.parseInt(calendar[0]),
                Integer.parseInt(calendar[1]),
                Integer.parseInt(calendar[2])
        );


        initPaith();
    }

    private void initPaith() {
        weekText = new String[]{
                "日",
                "一",
                "二",
                "三",
                "四",
                "五",
                "六"

        };
        selectColor = getResources().getColor(R.color.color_27c4c4);
        defaultColor = getResources().getColor(R.color.color_474747);
        paintSolarlLunar = new Paint();
        paintSolarlLunar.setAntiAlias(true);
        paintWeek = new Paint();
    }


    public void setDate(int year, int month) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
        String[] calendar = format.format(date).split("-");
        solarList = getSolarData(year, month);
        lunarList = new ArrayList<>();
        for (int i = 0; i < solarList.size(); i++) {
            lunarList.add(LunarCalendarUtils.solarToLunar(solarList.get(i)));

        }

        attenceStatus = new int[42];
        for (int i = 0; i < attenceStatus.length; i++) {
            if (i % 7 == 0) {
                attenceStatus[i] = 1;
                if (i > 0)
                    attenceStatus[i - 1] = 1;
            }
            else {
                attenceStatus[i] = 0;
            }
        }

        judgeSelectToday(
                Integer.parseInt(calendar[0]),
                Integer.parseInt(calendar[1]),
                Integer.parseInt(calendar[2])
        );

        invalidate();
    }

    public void setDate(int year, int month, int selectDay) {
        setDate(year, month);
        judgeSelect(year, month, selectDay);
    }

    /**
     * 初始化位置，判断该月是否是当前月，如果是就定位当日位置
     */

    private void judgeSelectToday(int year, int month, int day) {

        boolean temp = true;
        for (int i = 0; i < solarList.size(); i++) {
            LunarCalendarUtils.Solar solar = solarList.get(i);
            if (day == solar.getSolarDay() &&
                    month == solar.getSolarMonth() &&
                    year == solar.getSolarYear()) {

                todayX = i % WidthNumber;
                todayY = i / HeightNumber;
                temp = false;
                break;
            }

        }

        if (temp) {
            todayX = SELECT_NO;
            todayY = SELECT_NO;
            selectX = SELECT_NO;
            selectY = SELECT_NO;
        }
    }

    /**
     * 初始化选中位置
     *
     * @param year
     * @param month
     * @param day
     */

    private void judgeSelect(int year, int month, int day) {

        for (int i = 0; i < solarList.size(); i++) {
            LunarCalendarUtils.Solar solar = solarList.get(i);
            if (day == solar.getSolarDay() &&
                    month == solar.getSolarMonth() &&
                    year == solar.getSolarYear()
                    ) {

                selectX = i % WidthNumber;
                selectY = i / WidthNumber;

                LogUtils.toE("selectX", "" + selectY);
                LogUtils.toE("selectY", "" + selectY);
                break;
            }

        }

    }


    public void setAttenceStatus(int[] attenceStatus) {
        this.attenceStatus = attenceStatus;
    }


    private static final int WidthNumber = 7;
    private static final int HeightNumber = 7;


    private float weekSize;
    private float widthSingle;
    private float heightSingle;

    //星期的画笔宽度
    private float weekTextSize;
    //阴历阳历的字体大小
    private float dayTextSize;
    //阴历阳历的画笔宽度
    private float dayPaintStrokeWidth;


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);


        float heigh = h - 5;
        widthSingle = w / WidthNumber;
        float tempH = heigh / HeightNumber;
        weekSize = tempH * 1.2f;
        weekTextSize = weekSize * 0.35f;
        float heighRTemp = (heigh - weekSize) / (HeightNumber - 1);
        dayTextSize = heighRTemp * 0.30f;
        dayPaintStrokeWidth = dayTextSize * 0.05f;
        heightSingle = heighRTemp;


        paintWeek.setStyle(Paint.Style.FILL);
        paintWeek.setTextSize(weekTextSize);
        paintWeek.setStrokeWidth(weekTextSize * 0.04f);
        paintWeek.setColor(defaultColor);
        paintWeek.setAntiAlias(true);
        paintWeek.setTextAlign(Paint.Align.CENTER);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        drawVerticalLine(canvas);
//        drawHorizontalLine(canvas);
        drawWeek(canvas);
        drawSolarLunarCalendar(canvas);
        if (todayX != SELECT_NO && todayY != SELECT_NO) {
            drawSelecCircle(canvas, todayX, todayY);
        }
        if (selectX != SELECT_NO && selectY != SELECT_NO) {
            drawSelecCircle(canvas, selectX, selectY);
        }


//        if (selectX != todayX && selectY != todayY) {
//            drawSelecCircle(canvas, selectX, selectY);
//        }

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        boolean select = false;
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                select = true;
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                showSelect(event.getX(), event.getY());
                select = super.onTouchEvent(event);
                break;


        }
        return select;
    }


    /**
     * 画星期
     *
     * @param canvas
     */
    private void drawWeek(Canvas canvas) {

        Paint.FontMetrics fontMetrics = paintWeek.getFontMetrics();
        float baseline = weekSize / 2f + (fontMetrics.bottom - fontMetrics.top) / 2f
                - fontMetrics.bottom;
        for (int i = 0; i < weekText.length; i++) {
            canvas.drawText(weekText[i], widthSingle / 2f + widthSingle * i, baseline, paintWeek);
        }
    }

    /**
     * 画阳历和阴历
     *
     * @param canvas
     */
    private void drawSolarLunarCalendar(Canvas canvas) {

        paintSolarlLunar.setStyle(Paint.Style.FILL);
        paintSolarlLunar.setTextSize(dayTextSize);
        paintSolarlLunar.setStrokeWidth(dayPaintStrokeWidth);
        paintSolarlLunar.setTextAlign(Paint.Align.CENTER);

        Paint.FontMetrics fontMetrics = paintSolarlLunar.getFontMetrics();

        for (int i = 0; i < solarList.size(); i++) {

            if (solarList.get(i).isChick()) {
                paintSolarlLunar.setColor(defaultColor);
                //公司需求
//                drawSolarLunarCalendar(i, fontMetrics, canvas);
            }
            else {
                paintSolarlLunar.setColor(getResources().getColor(R.color.darkgray));
            }

            //画阴历执行方法
            drawSolarLunarCalendar(i, fontMetrics, canvas);
        }
    }

    private void drawSolarLunarCalendar(int i, Paint.FontMetrics fontMetrics,
                                        Canvas canvas) {
        float baselineSolar = (weekSize + heightSingle / 4f +
                heightSingle * (i / WidthNumber)) +
                (fontMetrics.bottom - fontMetrics.top) / 2f
                - fontMetrics.bottom;

        float baselineLunar = (weekSize + heightSingle * 0.75f +
                heightSingle * (i / WidthNumber)) +
                (fontMetrics.bottom - fontMetrics.top) / 2f
                - fontMetrics.bottom;
        float top = baselineLunar + fontMetrics.top;
        float bottom = baselineSolar + fontMetrics.bottom;
        float topToBottom = (bottom - top) * 0.6f;

        baselineSolar = baselineSolar - topToBottom;
        baselineLunar = baselineLunar + topToBottom;

//        baselineSolar = baselineSolar - topToBottom + 5;
//        baselineLunar = baselineLunar + topToBottom - 5;


        canvas.drawText(solarList.get(i).getSolarDay() + "",
                widthSingle / 2f + widthSingle * (i % WidthNumber),
                baselineSolar, paintSolarlLunar);


//        //公司需求
//        if (attenceStatus[i] == 0) {
//            canvas.drawCircle(widthSingle / 2f + widthSingle * (i % WidthNumber),
//                    baselineLunar, 5, paintSolarlLunar);
//        }

        //阴历显示
        canvas.drawText(LunarCalendarUtils.getLunarDayString(lunarList.get(i).getLunarDay()),
                widthSingle / 2f + widthSingle * (i % WidthNumber),
                baselineLunar, paintSolarlLunar);
        paintSolarlLunar.setColor(defaultColor);

    }


    private void drawSelecCircle(Canvas canvas, int widthSize, int heighSize) {


        LogUtils.toE("drawSelecCircle", "widthSize" + widthSize);
        LogUtils.toE("drawSelecCircle", "heighSize" + heighSize);
        //根据点击位置设置画笔样式
        if (widthSize == todayX && heighSize == todayY) {
            paintSolarlLunar.setStyle(Paint.Style.FILL);

        }
        else {
            paintSolarlLunar.setStyle(Paint.Style.STROKE);
        }
        paintSolarlLunar.setStrokeWidth(dayPaintStrokeWidth);
        paintSolarlLunar.setColor(selectColor);
        //画正方形
//        float zhengfan;
//        if (heightSingle > widthSingle) {
//            zhengfan = widthSingle * 0.8f;
//        }
//        else {
//            zhengfan = heightSingle * 0.8f;
//        }
//
//
//        float leftPos = widthSingle * widthSize;
//        float topPos = weekSize + heightSingle * heighSize;
//
//        float ringhtPos = leftPos + widthSingle;
//        float bottomPos = topPos + heightSingle;
//        float tempX = (ringhtPos - leftPos - zhengfan) / 2f;
//        float tempY = (bottomPos - topPos - zhengfan) / 2f;
//
//        float left = widthSingle * widthSize + tempX;
//        float top = weekSize + heightSingle * heighSize + tempY;
//        float ringht = leftPos + widthSingle - tempX;
//        float bottom = topPos + heightSingle - tempY;
//
//        canvas.drawRect(left, top, ringht, bottom, paintSolarlLunar);


        //画圆
        float width = widthSingle * 0.5f + widthSingle * widthSize;
        float heigh = weekSize + heightSingle * 0.5f + heightSingle * heighSize;

        if (widthSingle > heightSingle) {
            canvas.drawCircle(width, heigh, widthSingle * 0.45f, paintSolarlLunar);
        }
        else {
            canvas.drawCircle(width, heigh, heightSingle * 0.45f, paintSolarlLunar);
        }


        paintSolarlLunar.setStyle(Paint.Style.FILL);
        paintSolarlLunar.setTextSize(dayTextSize);
        paintSolarlLunar.setStrokeWidth(dayPaintStrokeWidth);
        if (widthSize == todayX && heighSize == todayY) {
            paintSolarlLunar.setColor(getResources().getColor(R.color.color_ffffff));
        }
        else {
            paintSolarlLunar.setColor(selectColor);
        }

        paintSolarlLunar.setStyle(Paint.Style.FILL);
        paintSolarlLunar.setTextSize(dayTextSize);
        paintSolarlLunar.setStrokeWidth(dayPaintStrokeWidth);
        paintSolarlLunar.setTextAlign(Paint.Align.CENTER);

        Paint.FontMetrics fontMetrics = paintSolarlLunar.getFontMetrics();
        int select = heighSize * WidthNumber + widthSize;

        LogUtils.toE("select", "select:" + select);
        drawSolarLunarCalendar(select, fontMetrics, canvas);
    }


    /**
     * 根据点击位置判断所选择的位置
     *
     * @param x
     * @param y
     */
    private void showSelect(float x, float y) {

        int tempX = -1;
        int tempY = -1;
        for (int i = 0; i < WidthNumber; i++) {
            if (x > widthSingle * i && x < widthSingle + widthSingle * i) {
                tempX = i;
                break;
            }
        }
        for (int i = 0; i < HeightNumber - 1; i++) {
            if (y > (heightSingle * i + weekSize) && y < (heightSingle + heightSingle * i + weekSize)) {
                tempY = i;
                break;
            }
        }

        if (tempX != -1 && tempY != -1) {
            selectX = tempX;
            selectY = tempY;
            int select = selectY * WidthNumber + selectX;
            LogUtils.toE("select", "select:" + select);
            if (solarList.get(select).isChick()) {
                if (calendarListener != null)
                    calendarListener.onCalenderListener(solarList.get(select), lunarList.get(select));
                invalidate();
            }

        }

    }


//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        LogUtils.toE("CalendarBoxView", "onMeasure");
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//        if (widthMode == MeasureSpec.AT_MOST) {
//            //wrap_content
//
//        }
//        else if (widthMode == MeasureSpec.EXACTLY) {
//            //match_parent
//        }
//    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }


    /**
     * 创建当前页面阳历信息
     *
     * @param year：当前页面的年份
     * @param month：当前页面的月份
     * @return
     */

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
            //当前页面月份前一个月显示信息
            if (i < firstDayWeek) {
                //如果当前页面是1月，年减1
                if (mMonth == 0) {
                    solar = new LunarCalendarUtils.Solar(year - 1, 12, lastMonthDays - i + 1, false);
                }
                else {
                    solar = new LunarCalendarUtils.Solar(year, month - 1, lastMonthDays - i + 1, false);
                }

            }
            //当前页面月份后一个月显示信息
            else if (i >= (firstDayWeek + monthDays)) {
                //如果当前页面是12月，年加1
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
                //当前页面月显示信息
                solar = new LunarCalendarUtils.Solar(year, month, i - firstDayWeek + 1, true);
            }

            solarsList.add(solar);
        }

        for (int i = 0; i < solarsList.size(); i++) {
            LogUtils.toE("solarsList", ""
                    + solarsList.get(i).getSolarYear() + "-"
                    + solarsList.get(i).getSolarMonth() + "-"
                    + solarsList.get(i).getSolarDay() + "isCheck:"
                    + solarsList.get(i).isChick()
            );
        }
        return solarsList;
    }

    public interface CalendarListener {
        void onCalenderListener(LunarCalendarUtils.Solar solar, LunarCalendarUtils.Lunar lunar);
    }

    private CalendarListener calendarListener;

    public void setCalendarListener(CalendarListener calendarListener) {
        this.calendarListener = calendarListener;
    }
}
