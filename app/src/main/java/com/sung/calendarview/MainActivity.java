package com.sung.calendarview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.sung.calendarview.adapter.DateObject;
import com.sung.calendarview.provider.Provider;
import com.sung.calendarview.provider.ProviderMannager;

import java.util.Calendar;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();
        test();
    }

    private void test(){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        DateObject date = new DateObject(calendar);
        date.position = 0;
        date.currentMonth = true;
        date.sellectStatus = false;
        int insert = ProviderMannager.insert(this, date);
        ProviderMannager.query(this, insert);
    }
}
