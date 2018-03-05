package com.sung.calendarview.provider;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.sung.calendarview.adapter.DateObject;
import com.sung.calendarview.utils.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sung on 2017/7/25.
 */

public class ProviderMannager {

    /***************              增              **************/

    /**
     * 增
     * */
    public static int insert(Context context, DateObject date) {
        ContentValues values = new ContentValues();
        values.put(Provider.DatesColumns.POSITION, date.position);
        values.put(Provider.DatesColumns.PAGER_INDEX, date.pagerIndex);
        values.put(Provider.DatesColumns.YEAR, date.year);
        values.put(Provider.DatesColumns.MONTH, date.month);
        values.put(Provider.DatesColumns.DAY, date.day);
        values.put(Provider.DatesColumns.CURRENT_MONTH, date.currentMonth ? 1 : 0);
        values.put(Provider.DatesColumns.SELLECT_STATUS, date.sellectStatus ? 1 : 0);
        values.put(Provider.DatesColumns.YYMMDD, date.YYMMDD);
        values.put(Provider.DatesColumns.YYMM, date.YYMM);
        Uri uri = context.getContentResolver().insert(Provider.DatesColumns.CONTENT_URI, values);
        //Log.d("insert uri=" + uri);
        String lastPath = uri.getLastPathSegment();
        int _id = -1;
        if (TextUtils.isEmpty(lastPath)) {
            Log.d("insert failure!");
        } else {
            Log.d("insert success! the id is " + lastPath);
        }
        return _id;
    }

    /***************              增end              **************/




    /***************              删              **************/

    /**
     * 删除全部
     * */
    public static void deleteAll(Context context){
        int result = context.getContentResolver().delete(Provider.DatesColumns.CONTENT_URI, null, null);
        if (result == 1){
            Log.d("delete succ !!");
        }
    }

    /***************              删除end              **************/



    /***************              改              **************/

    /**
     * 更新当前id的选中状态
     *
     * @param context 上下文
     * @param id 需要更改项的id
     * @param sellectStatus 选中的状态
     * */
    public static void update(Context context, int id, boolean sellectStatus){
        ContentValues values = new ContentValues();
        values.put(Provider.DatesColumns.SELLECT_STATUS,sellectStatus);
        Uri uri = ContentUris.withAppendedId(Provider.DatesColumns.CONTENT_URI,id);
        int update = context.getContentResolver().update(uri, values, null, null);
        if (update == 1) {
            Log.d("update id " + id + " succ !!");
        }
    }

    /***************              改end              **************/






    /***************              按条件查询              **************/

    /**
     * 查全部
     * */
    public static List<DateObject> query(Context context) {
        List<DateObject> dates = new ArrayList<>();
        Cursor c = context.getContentResolver().query(Provider.DatesColumns.CONTENT_URI,
                new String[]{Provider.DatesColumns._ID,
                        Provider.DatesColumns.POSITION,
                        Provider.DatesColumns.PAGER_INDEX,
                        Provider.DatesColumns.YEAR,
                        Provider.DatesColumns.MONTH,
                        Provider.DatesColumns.DAY,
                        Provider.DatesColumns.CURRENT_MONTH,
                        Provider.DatesColumns.SELLECT_STATUS,
                        Provider.DatesColumns.YYMMDD,
                        Provider.DatesColumns.YYMM},
                null, null, null);
        while (c != null && c.moveToNext()) {
            DateObject date = new DateObject();
            date._id = c.getInt(c.getColumnIndexOrThrow(Provider.DatesColumns._ID));
            date.position = c.getInt(c.getColumnIndexOrThrow(Provider.DatesColumns.POSITION));
            date.pagerIndex = c.getInt(c.getColumnIndexOrThrow(Provider.DatesColumns.PAGER_INDEX));
            date.year = c.getInt(c.getColumnIndexOrThrow(Provider.DatesColumns.YEAR));
            date.month = c.getInt(c.getColumnIndexOrThrow(Provider.DatesColumns.MONTH));
            date.day = c.getInt(c.getColumnIndexOrThrow(Provider.DatesColumns.DAY));
            date.currentMonth = c.getInt(c.getColumnIndexOrThrow(Provider.DatesColumns.CURRENT_MONTH)) == 0 ? false : true;
            date.sellectStatus = c.getInt(c.getColumnIndexOrThrow(Provider.DatesColumns.SELLECT_STATUS)) == 0 ? false : true;
            date.YYMMDD = c.getString(c.getColumnIndexOrThrow(Provider.DatesColumns.YYMMDD));
            date.YYMM = c.getString(c.getColumnIndexOrThrow(Provider.DatesColumns.YYMM));

//            Log.d("query result:"+date.toString());
            dates.add(date);
        }
        return dates;
    }

    /**
     * 根据id查
     * */
    public static DateObject query(Context context, int id) {
        Cursor c = context.getContentResolver().query(Provider.DatesColumns.CONTENT_URI,
                new String[]{Provider.DatesColumns._ID,
                        Provider.DatesColumns.POSITION,
                        Provider.DatesColumns.PAGER_INDEX,
                        Provider.DatesColumns.YEAR,
                        Provider.DatesColumns.MONTH,
                        Provider.DatesColumns.DAY,
                        Provider.DatesColumns.CURRENT_MONTH,
                        Provider.DatesColumns.SELLECT_STATUS,
                        Provider.DatesColumns.YYMMDD,
                        Provider.DatesColumns.YYMM},
                Provider.DatesColumns._ID + "=?", new String[]{id + ""}, null);
        if (c != null && c.moveToFirst()) {
            DateObject date = new DateObject();
            date._id = c.getInt(c.getColumnIndexOrThrow(Provider.DatesColumns._ID));
            date.position = c.getInt(c.getColumnIndexOrThrow(Provider.DatesColumns.POSITION));
            date.pagerIndex = c.getInt(c.getColumnIndexOrThrow(Provider.DatesColumns.PAGER_INDEX));
            date.year = c.getInt(c.getColumnIndexOrThrow(Provider.DatesColumns.YEAR));
            date.month = c.getInt(c.getColumnIndexOrThrow(Provider.DatesColumns.MONTH));
            date.day = c.getInt(c.getColumnIndexOrThrow(Provider.DatesColumns.DAY));
            date.currentMonth = c.getInt(c.getColumnIndexOrThrow(Provider.DatesColumns.CURRENT_MONTH)) == 0 ? false : true;
            date.sellectStatus = c.getInt(c.getColumnIndexOrThrow(Provider.DatesColumns.SELLECT_STATUS)) == 0 ? false : true;
            date.YYMMDD = c.getString(c.getColumnIndexOrThrow(Provider.DatesColumns.YYMMDD));
            date.YYMM = c.getString(c.getColumnIndexOrThrow(Provider.DatesColumns.YYMM));

//            Log.d("query result:"+date.toString());
            return date;
        } else {
            Log.i("query failure!");
            return null;
        }
    }

    /**
     * 根据pager index查整页
     * */
    public static List<DateObject> query(Context context, int pagerIndex, boolean queryAll) {
        List<DateObject> dates = new ArrayList<>();
        Cursor c = context.getContentResolver().query(Provider.DatesColumns.CONTENT_URI,
                new String[]{Provider.DatesColumns._ID,
                        Provider.DatesColumns.POSITION,
                        Provider.DatesColumns.PAGER_INDEX,
                        Provider.DatesColumns.YEAR,
                        Provider.DatesColumns.MONTH,
                        Provider.DatesColumns.DAY,
                        Provider.DatesColumns.CURRENT_MONTH,
                        Provider.DatesColumns.SELLECT_STATUS,
                        Provider.DatesColumns.YYMMDD,
                        Provider.DatesColumns.YYMM},
                Provider.DatesColumns.PAGER_INDEX + " = " + pagerIndex + ")" + " group by ( " + Provider.DatesColumns.POSITION,
                null, Provider.DatesColumns.POSITION + " asc");
        while (c != null && c.moveToNext()) {
            DateObject date = new DateObject();
            date._id = c.getInt(c.getColumnIndexOrThrow(Provider.DatesColumns._ID));
            date.position = c.getInt(c.getColumnIndexOrThrow(Provider.DatesColumns.POSITION));
            date.pagerIndex = c.getInt(c.getColumnIndexOrThrow(Provider.DatesColumns.PAGER_INDEX));
            date.year = c.getInt(c.getColumnIndexOrThrow(Provider.DatesColumns.YEAR));
            date.month = c.getInt(c.getColumnIndexOrThrow(Provider.DatesColumns.MONTH));
            date.day = c.getInt(c.getColumnIndexOrThrow(Provider.DatesColumns.DAY));
            date.currentMonth = c.getInt(c.getColumnIndexOrThrow(Provider.DatesColumns.CURRENT_MONTH)) == 0 ? false : true;
            date.sellectStatus = c.getInt(c.getColumnIndexOrThrow(Provider.DatesColumns.SELLECT_STATUS)) == 0 ? false : true;
            date.YYMMDD = c.getString(c.getColumnIndexOrThrow(Provider.DatesColumns.YYMMDD));
            date.YYMM = c.getString(c.getColumnIndexOrThrow(Provider.DatesColumns.YYMM));

//            Log.d("query result:"+date.toString());
            dates.add(date);
        }
        return dates;
    }

    /***************              查询end              **************/

}
