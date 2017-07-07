package com.kxiang.quick.function.activity;

import android.os.Bundle;

import com.kexiang.function.view.own.CalendarAttenceView;
import com.kxiang.quick.R;
import com.kxiang.quick.base.BaseActivity;

public class BeautifulCalendarActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beautiful_calendar);
    }

    CalendarAttenceView calendar_attence;
    @Override
    protected void initView() {
        calendar_attence= (CalendarAttenceView) findViewById(R.id.calendar_attence);

       calendar_attence.setDate(2017,7,1);

    }
}
