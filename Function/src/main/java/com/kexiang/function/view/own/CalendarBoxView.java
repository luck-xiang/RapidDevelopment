package com.kexiang.function.view.own;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
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

public class CalendarBoxView extends View {
    public CalendarBoxView(Context context) {
        super(context);
        initView();
    }

    public CalendarBoxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public CalendarBoxView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }


    private Paint paint;
    private Paint paintText;
    private List<LunarCalendarUtils.Solar> solarList;
    private List<LunarCalendarUtils.Lunar> lunarList;

    private void initView() {


        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CANADA);
        String[] calendar = format.format(date).split("-");

        solarList = getSolarData(Integer.parseInt(calendar[0]), Integer.parseInt(calendar[1]));
        lunarList = new ArrayList<>();

        for (int i = 0; i < solarList.size(); i++) {
            lunarList.add(LunarCalendarUtils.solarToLunar(solarList.get(i)));

            LogUtils.toE("lunarList", ""
                    + lunarList.get(i).getLunarYear() + "-"
                    + lunarList.get(i).getLunarMonth() + "-"
                    + lunarList.get(i).getLunarDay()
            );

        }

        paint = new Paint();
        paint.setColor(getResources().getColor(R.color.color_ffa200));
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);

        weekText = new String[]{
                "日",
                "一",
                "二",
                "三",
                "四",
                "五",
                "六"

        };
        paintText = new Paint();
        paintText.setAntiAlias(true);
        initLine();
        judgeSelect(
                Integer.parseInt(calendar[0]),
                Integer.parseInt(calendar[1]),
                Integer.parseInt(calendar[2])
        );
        LogUtils.toE("CalendarBoxView", "initView");
    }

    private void judgeSelect(int year, int month, int day) {

        for (int i = 0; i < solarList.size(); i++) {
            LunarCalendarUtils.Solar solar = solarList.get(i);
            if (day == solar.getSolarDay() &&
                    month == solar.getSolarMonth() &&
                    year == solar.getSolarYear()
                    ) {

                selectY = i / (HeightNumber - 1);
                selectX = i % WidthNumber;
            }

        }

    }


    private int selectX = 0;
    private int selectY = 0;

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        LogUtils.toE("CalendarBoxView", "onDraw");
//        drawVerticalLine(canvas);
//        drawHorizontalLine(canvas);
        drawWeek(canvas);
        drawSolarLunarCalendar(canvas);
//        drawSolarCalendar(canvas);
//        drawLunarCalendar(canvas);


        drawSelecCircle(canvas, selectX, selectY);


    }

    private Path linePath;

    private void initLine() {
        linePath = new Path();
    }

    private void drawHorizontalLine(Canvas canvas) {
        for (int i = 0; i < WidthNumber - 1; i++) {
            linePath.moveTo(widthSingle * (i + 1), 0);
            linePath.lineTo(widthSingle * (i + 1), heigh);
        }

        canvas.drawPath(linePath, paint);
    }

    private void drawVerticalLine(Canvas canvas) {

        linePath.moveTo(0, weekSize);
        linePath.lineTo(width, weekSize);

        for (int i = 0; i < HeightNumber - 2; i++) {
            linePath.moveTo(0, weekSize + heightSingle * (i + 1));
            linePath.lineTo(width, weekSize + heightSingle * (i + 1));
        }

        canvas.drawPath(linePath, paint);
    }

    private String[] weekText;

    private void drawWeek(Canvas canvas) {
        paintText.setStyle(Paint.Style.FILL);
        paintText.setTextSize(weekTextSize);
        paintText.setStrokeWidth(weekTextSize * 0.06f);
        paintText.setColor(getResources().getColor(R.color.color_9082BD));
        paintText.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = paintText.getFontMetrics();
        float baseline = weekSize / 2f + (fontMetrics.bottom - fontMetrics.top) / 2f
                - fontMetrics.bottom;
        for (int i = 0; i < weekText.length; i++) {
            canvas.drawText(weekText[i], widthSingle / 2f + widthSingle * i, baseline, paintText);
        }
//        drawTextFiveLine(canvas, 0, baseline, weekSize / 2, fontMetrics);
    }

    private void drawSolarLunarCalendar(Canvas canvas) {
        paintText.setStyle(Paint.Style.FILL);
        paintText.setTextSize(dayTextSize);
        paintText.setStrokeWidth(dayTextStrokeWidth);
        paintText.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = paintText.getFontMetrics();


        for (int i = 0; i < solarList.size(); i++) {
            if (solarList.get(i).isChick()) {
                paintText.setColor(getResources().getColor(R.color.dimgray));
            }
            else {
                paintText.setColor(getResources().getColor(R.color.rosybrown));
            }

            drawSolarLunarCalendar(i, fontMetrics, canvas);
        }
    }

    private void drawSolarLunarCalendar(int i, Paint.FontMetrics fontMetrics, Canvas canvas) {
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

//        //画top
//        paint.setColor(Color.BLUE);
//        canvas.drawLine(0, top, 3000, top, paint);
//        //画bottom
//        paint.setColor(Color.RED);
//        canvas.drawLine(0, bottom, 3000, bottom, paint);

        canvas.drawText(solarList.get(i).getSolarDay() + "",
                widthSingle / 2f + widthSingle * (i % WidthNumber),
                baselineSolar, paintText);

        canvas.drawText(LunarCalendarUtils.getLunarDayString(lunarList.get(i).getLunarDay()),
                widthSingle / 2f + widthSingle * (i % WidthNumber),
                baselineLunar, paintText);
    }

    private void drawSolarCalendar(Canvas canvas) {
        paintText.setStyle(Paint.Style.FILL);
        paintText.setTextSize(dayTextSize);
        paintText.setStrokeWidth(dayTextStrokeWidth);
        paintText.setColor(getResources().getColor(R.color.rosybrown));
        paintText.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = paintText.getFontMetrics();

        float baseline = weekSize + heightSingle / 4f + (fontMetrics.bottom - fontMetrics.top) / 2f
                - fontMetrics.bottom;

        for (int i = 0; i < weekText.length; i++) {
            canvas.drawText(i + "", widthSingle / 2f + widthSingle * i, baseline, paintText);
        }

//        drawTextFiveLine(canvas, 0, baseline, weekSize / 2, fontMetrics);
    }

    private void drawLunarCalendar(Canvas canvas) {
        paintText.setStyle(Paint.Style.FILL);
        paintText.setTextSize(dayTextSize);
        paintText.setStrokeWidth(dayTextStrokeWidth);
        paintText.setColor(getResources().getColor(R.color.rosybrown));
        paintText.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = paintText.getFontMetrics();

        float baseline = weekSize + heightSingle * 3 / 4 + (fontMetrics.bottom - fontMetrics.top) / 2
                - fontMetrics.bottom;

        for (int i = 0; i < weekText.length; i++) {
            canvas.drawText(weekText[i], widthSingle / 2 + widthSingle * i, baseline, paintText);
        }
    }


    private void drawTextFiveLine(
            Canvas canvas,
            int baseLineX,
            float baseLineY,
            float center,
            Paint.FontMetrics fm
    ) {
        float ascent = baseLineY + fm.ascent;
        float descent = baseLineY + fm.descent;
        float top = baseLineY + fm.top;
        float bottom = baseLineY + fm.bottom;

        Paint paint = new Paint();
        //写文字
        paint.setStrokeWidth(1);

        //画基线
        paint.setColor(Color.RED);
        canvas.drawLine(baseLineX, baseLineY, 3000, baseLineY, paint);

        //画中线
        paint.setColor(getResources().getColor(R.color.indigo));
        canvas.drawLine(baseLineX, center, 3000, center, paint);


        //画top
        paint.setColor(Color.BLUE);
        canvas.drawLine(baseLineX, top, 3000, top, paint);

        //画ascent
        paint.setColor(Color.GREEN);
        canvas.drawLine(baseLineX, ascent, 3000, ascent, paint);

        //画descent
        paint.setColor(Color.YELLOW);
        canvas.drawLine(baseLineX, descent, 3000, descent, paint);

        //画bottom
        paint.setColor(Color.RED);
        canvas.drawLine(baseLineX, bottom, 3000, bottom, paint);
    }

    private void drawSelecCircle(Canvas canvas, int widthSize, int heighSize) {


        LogUtils.toE("drawSelecCircle", "widthSize" + widthSize);
        LogUtils.toE("drawSelecCircle", "heighSize" + heighSize);
        paintText.setStyle(Paint.Style.FILL);
        paintText.setStrokeWidth(dayTextStrokeWidth);
        paintText.setColor(getResources().getColor(R.color.rosybrown));

        float width = widthSingle * 0.5f + widthSingle * widthSize;
        float heigh = weekSize + heightSingle * 0.5f + heightSingle * heighSize;

        if (widthSingle > heightSingle) {
            canvas.drawCircle(width, heigh, widthSingle * 0.45f, paintText);
        }
        else {
            canvas.drawCircle(width, heigh, heightSingle * 0.45f, paintText);
        }

        paintText.setStyle(Paint.Style.FILL);
        paintText.setTextSize(dayTextSize);
        paintText.setStrokeWidth(dayTextStrokeWidth);
        paintText.setColor(getResources().getColor(R.color.color_ffffff));
        paintText.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fontMetrics = paintText.getFontMetrics();
        int select = heighSize * WidthNumber + widthSize;
        drawSolarLunarCalendar(select, fontMetrics, canvas);
    }


//        float base = weekSize + heightSingle / 4 + heightSingle * heighSize +
//                (fontMetrics.bottom - fontMetrics.top) / 2
//                - fontMetrics.bottom;
//        canvas.drawText(heighSize + "", widthSingle / 2 + widthSingle * widthSize, base, paintText);
//
//
//        float baseline = weekSize + heightSingle * 3 / 4 + heightSingle * heighSize +
//                (fontMetrics.bottom - fontMetrics.top) / 2
//                - fontMetrics.bottom;
//        canvas.drawText(weekText[widthSize], widthSingle / 2 + widthSingle * widthSize,
//                baseline, paintText);


    float weekTextSize;
    float dayTextSize;
    float dayTextStrokeWidth;
    float width;
    float heigh;


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        LogUtils.toE("CalendarBoxView", "onSizeChanged");
        width = w;
        heigh = h - 5;
        widthSingle = w / WidthNumber;

        float tempH = heigh / HeightNumber;
        weekSize = tempH * 1.2f;
        weekTextSize = weekSize * 0.4f;
        float heighRTemp = (heigh - weekSize) / (HeightNumber - 1);
        dayTextSize = heighRTemp * 0.30f;
        dayTextStrokeWidth = dayTextSize * 0.05f;
        heightSingle = heighRTemp;


    }

    private float moveX = 0;
    private float moveY = 0;

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
//                float x = event.getX();
//                float y = event.getY();
                showSelect(event.getX(), event.getY());
                select = super.onTouchEvent(event);
                break;


        }

        return select;
    }


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
//            LogUtils.toE("showSelect","selectX:"+selectX);
//            LogUtils.toE("showSelect","selectY:"+selectY);
//            LogUtils.toE("showSelect","select:"+select);
            if (solarList.get(select).isChick()) {
                invalidate();
            }

        }


    }


    private static final int WidthNumber = 7;
    private static final int HeightNumber = 7;
    private float widthSingle;
    private float heightSingle;
    private float weekSize;


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        LogUtils.toE("CalendarBoxView", "onMeasure");
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (widthMode == MeasureSpec.AT_MOST) {
            //wrap_content

        }
        else if (widthMode == MeasureSpec.EXACTLY) {
            //match_parent
        }
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        LogUtils.toE("CalendarBoxView", "onFinishInflate");
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
