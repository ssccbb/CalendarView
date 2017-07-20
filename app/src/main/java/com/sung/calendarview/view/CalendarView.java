package com.sung.calendarview.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sung.calendarview.adapter.DateAdapter;
import com.sung.calendarview.adapter.DateObject;
import com.sung.calendarview.adapter.PagerAdapter;
import com.sung.calendarview.R;
import com.sung.calendarview.utils.CalendarUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by sung on 2017/7/19.
 * 带日期签到的日历控件
 */

public class CalendarView extends RelativeLayout implements ViewPager.OnPageChangeListener,DateAdapter.onCalendarDayClick{
    private static final String TAG = "CalendarView";
    private Context context;

    private TextView mCalendarTime;
    private ViewPager mCalendarPager;
    private List<View> views = new ArrayList<>();
    private int LAST_PAGER_SELECT = 0;
    private int CURRENT_PAGER_SELECT = 0;
    private DateObject mDate;

    public CalendarView(Context context) {
        super(context);

        this.context = context;
        initView();
    }

    public CalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);

        this.context = context;
        initView();
    }

    public CalendarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        this.context = context;
        initView();
    }

    private void initView(){
        View view = LayoutInflater.from(context).inflate(R.layout.layout_calendar_view, null);
        mCalendarTime = (TextView) view.findViewById(R.id.calendar_time_text);
        mCalendarPager = (ViewPager) view.findViewById(R.id.calendar_view_pager);

        DataInit();
        PagerInit();
        this.addView(view);
    }

    /**
    * 共13页 中间当月 前后各六月
    * */
    private void PagerInit(){
        for (int i = 0; i < 13; i++) {
            View pager = LayoutInflater.from(context).inflate(R.layout.layout_calendar_pager, null);
            RecyclerView date = (RecyclerView) pager.findViewById(R.id.calendar_recycle);
            date.setLayoutManager(new GridLayoutManager(context,7));
            DateAdapter adapter = new DateAdapter(null);
            adapter.setOnCalendarDayClickListner(this);
            date.setAdapter(adapter);
            date.setHasFixedSize(true);
            pager.setTag(date);
            views.add(pager);
        }
        PagerAdapter adapter = new PagerAdapter(views);
        mCalendarPager.setAdapter(adapter);
        CURRENT_PAGER_SELECT = LAST_PAGER_SELECT = 6;
        mCalendarPager.setCurrentItem(6);
        mCalendarPager.setOnPageChangeListener(this);

        //notify
        reSetPagerDate();
    }

    private void DataInit(){
        mDate = getCurrentDate();
        mCalendarTime.setText(mDate.YYMM);
    }

    /**
    * 获取当前年月
    * */
    private DateObject getCurrentDate(){
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        return new DateObject(calendar);
    }

    /**
    * 设置当页的日期数据
    * 7 x 4 每页28项
    * */
    private void reSetPagerDate(){
        RecyclerView date = (RecyclerView) views.get(CURRENT_PAGER_SELECT).getTag();
        DateAdapter adapter = (DateAdapter) date.getAdapter();
        adapter.setDates(CalendarUtils.DateList(mDate.year, mDate.month),true);
    }

    /**
    * viewpager滑动时对时间显示textview的控制
    * */
    private void changeTimeLine(){
        if (LAST_PAGER_SELECT == CURRENT_PAGER_SELECT)
            return;

        int var = CURRENT_PAGER_SELECT - LAST_PAGER_SELECT;
        //加年
        if ( (mDate.month + var) >= 13 ){
            mDate.setMonth(1);
            mDate.setYear(mDate.year+1);
        }

        //减年
        else if ( (mDate.month + var) <= 0 ){
            mDate.setMonth(12);
            mDate.setYear(mDate.year-1);
        }

        //正常
        else {
            mDate.setMonth(mDate.month + var);
        }

        //重置字符串
        mDate.resetDate();
        mCalendarTime.setText(mDate.YYMM);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageSelected(int position) {
        LAST_PAGER_SELECT = CURRENT_PAGER_SELECT;
        CURRENT_PAGER_SELECT = position;

        changeTimeLine();

        //notify
        RecyclerView date = (RecyclerView) views.get(position).getTag();
        DateAdapter adapter = (DateAdapter) date.getAdapter();
        adapter.setDates(CalendarUtils.DateList(mDate.year, mDate.month),true);

    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    @Override
    public void onClick(int position) {
        Log.d(TAG, "onClick: "+position);
    }
}
