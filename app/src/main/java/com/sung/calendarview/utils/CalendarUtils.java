package com.sung.calendarview.utils;

import android.content.Context;

import com.sung.calendarview.adapter.DateObject;
import com.sung.calendarview.provider.ProviderMannager;
import com.sung.calendarview.view.CalendarView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

/**
 * Created by sung on 2017/7/19.
 * 1 获取当前页的日期数据
 * 2 获取当前时间年、月、日
 */

public class CalendarUtils {
    //大月
    private static int[] months_31_days = {0, 2, 4, 6, 7, 9, 11};
    //小月
    private static int[] months_30_days = {3, 5, 8, 10};

    /**
     * 获取该月第一天在grid显示的position
     */
    private static int FirstDayPosition(int month) {
        try {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-d");
            Date date = dateFormat.parse(calendar.get(Calendar.YEAR) + "-" + month + "-1");
            calendar.setTime(date);
            return calendar.get(Calendar.DAY_OF_WEEK) - 1;
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 获取显示在grid上的天数数组
     *
     * @param month 月
     * @param year  年
     * @param pagerIndex 日期所属页标（pager中）
     */
    public static List<DateObject> DateList(Context context, int year, int month, int pagerIndex) {
        List<DateObject> dates = new ArrayList<>();

        //先查询判断数据库有没有存当前数据
        List<DateObject> result = ProviderMannager.query(context, pagerIndex, true);
        if (result.size() != 0){
            dates = result;
            return dates;
        }

        //未存重新初始化数据
        int firstDayPosition = FirstDayPosition(month);
        int lastMonth = month - 1;
        //当前1月 上月年份-1
        if (month == 0) {
            year = year - 1;
            lastMonth = 11;
        }

        //上月分总天数
        int lastTotalNum = TotalDays(lastMonth);
        //当前月份总天数
        int currentTotalNum = TotalDays(month);

        for (int i = 0; i < 35; i++) {
            int day;
            boolean currentMonth = false;
            if (i < firstDayPosition) {
                //上月位置
                day = lastTotalNum - (firstDayPosition - 1 - i);
            } else if (i >= firstDayPosition && i <= (firstDayPosition + currentTotalNum)) {
                //当月位置
                day = i + 1 - firstDayPosition;
                currentMonth = true;
            } else {
                //下月位置
                day = i - currentTotalNum - firstDayPosition;
            }
            DateObject date = new DateObject(year, month, day);
            date.position = i;
            date.currentMonth = currentMonth;
            date.pagerIndex = pagerIndex;//传入月份在pager中的页数位置

            dates.add(date);
        }
        return dates;
    }

    /**
     * 获取该月总天数
     *
     * @param month 月份
     */
    private static int TotalDays(int month) {
        //小
        if (isContainMonth(month, months_30_days)) {
            return 30;
        }
        //大
        else if (isContainMonth(month, months_31_days)) {
            return 31;
        }
        //特殊
        else if (month == 1) {
            Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
            int year = calendar.get(Calendar.YEAR);
            if (year % 4 == 0) {//闰
                return 29;
            } else { //平
                return 28;
            }
        }
        //以防万一
        else {
            return 30;
        }
    }

    /**
     * 该月属哪个类别
     *
     * @param arr   大月／小月／特殊月
     * @param month 要查询的月份
     */
    private static boolean isContainMonth(int month, int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == month)
                return true;
        }
        return false;
    }

    /**
     * 获取传入月份与当前月份的差值
     *
     * @param month 需要查询的月份
     */
    private static int getDvalueOfCurrentmonth(int month) {
        int dValue = 0;
        int currentMonth = CurrentMonth();
        Log.d("current month:" + currentMonth + "\tquery month:" + month);
        return dValue;
    }

    public static int CurrentYear() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        //Log.d(calendar.get(Calendar.YEAR) + "");
        return calendar.get(Calendar.YEAR);
    }

    public static int CurrentMonth() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        //Log.d(calendar.get(Calendar.MONTH) + "");
        return calendar.get(Calendar.MONTH) + 1;
    }

    public static int CurrentDay() {
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT+08:00"));
        //Log.d(calendar.get(Calendar.DAY_OF_MONTH) + "");
        return calendar.get(Calendar.DAY_OF_MONTH);
    }
}
