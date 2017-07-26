package com.sung.calendarview.provider;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;

import com.sung.calendarview.adapter.DateObject;
import com.sung.calendarview.utils.Log;

/**
 * Created by sung on 2017/7/25.
 */

public class ProviderMannager {

    public static int insert(Context context, DateObject date) {
        ContentValues values = new ContentValues();
        values.put(Provider.DatesColumns.POSITION, date.position);
        values.put(Provider.DatesColumns.YEAR, date.year);
        values.put(Provider.DatesColumns.MONTH, date.month);
        values.put(Provider.DatesColumns.DAY, date.day);
        values.put(Provider.DatesColumns.CURRENT_MONTH, date.currentMonth ? 1 : 0);
        values.put(Provider.DatesColumns.SELLECT_STATUS, date.sellectStatus ? 1 : 0);
        Uri uri = context.getContentResolver().insert(Provider.DatesColumns.CONTENT_URI, values);
        Log.d("insert uri="+uri);
        String lastPath = uri.getLastPathSegment();
        if (TextUtils.isEmpty(lastPath)) {
            Log.d("insert failure!");
        } else {
            Log.d("insert success! the id is " + lastPath);
        }

        return Integer.parseInt(lastPath);
    }

    public static void query(Context context, int id) {
        Cursor c = context.getContentResolver().query(Provider.DatesColumns.CONTENT_URI,
                new String[] { Provider.DatesColumns.POSITION,
                                Provider.DatesColumns.YEAR,
                                Provider.DatesColumns.MONTH,
                                Provider.DatesColumns.DAY,
                                Provider.DatesColumns.CURRENT_MONTH,
                                Provider.DatesColumns.SELLECT_STATUS },
                Provider.DatesColumns._ID + "=?", new String[] { id + "" }, null);
        if (c != null && c.moveToFirst()) {
            DateObject date = new DateObject();
            date.position = c.getInt(c.getColumnIndexOrThrow(Provider.DatesColumns.POSITION));
            date.year = c.getInt(c.getColumnIndexOrThrow(Provider.DatesColumns.YEAR));
            date.month = c.getInt(c.getColumnIndexOrThrow(Provider.DatesColumns.MONTH));
            date.day = c.getInt(c.getColumnIndexOrThrow(Provider.DatesColumns.DAY));
            date.currentMonth = c.getInt(c.getColumnIndexOrThrow(Provider.DatesColumns.CURRENT_MONTH)) == 0 ? false : true;
            date.sellectStatus = c.getInt(c.getColumnIndexOrThrow(Provider.DatesColumns.SELLECT_STATUS)) == 0 ? false : true;
            Log.d("date.position:"+date.position+"-date.year:"
                    +date.year+"-date.month:"+date.month+"-date.day:"
                    +date.day+"-date.currentmonth:"+date.currentMonth
                    +"-date.sellect:"+date.sellectStatus);
        } else {
            Log.i("query failure!");
        }
    }
}
