package com.sung.calendarview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.sung.calendarview.adapter.DateObject;
import com.sung.calendarview.provider.Provider;
import com.sung.calendarview.provider.ProviderMannager;
import com.sung.calendarview.utils.Log;
import com.sung.calendarview.view.CalendarView;

import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        init();
        //test();
    }

    private void init() {
        final CalendarView calendarView = (CalendarView) findViewById(R.id.cv_calendar);
        Button query = (Button) findViewById(R.id.btn_query);
        Button delete = (Button) findViewById(R.id.btn_delete);
        query.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<DateObject> result = ProviderMannager.query(MainActivity.this, calendarView.getCurrentPagerSelect(), true);
                if (result.size() == 0){
                    Log.d("query empty !!");
                }
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProviderMannager.deleteAll(MainActivity.this);
            }
        });
    }

    private void test() {
        for (int i = 0; i < 5; i++) {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
            DateObject date = new DateObject(calendar);
            date.position = 10;
            date.pagerIndex = i;
            date.currentMonth = true;
            date.sellectStatus = false;
            ProviderMannager.insert(this, date);
        }
        Log.d("------------ query all ---------------");
        ProviderMannager.query(this);
        Log.d("------------query pager index : 0 ---------------");
        ProviderMannager.query(this, 0, true);
        Log.d("---------------------------");
    }
}
