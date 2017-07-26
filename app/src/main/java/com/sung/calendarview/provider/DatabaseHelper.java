package com.sung.calendarview.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by sung on 2017/7/25.
 * 表单操作辅助类
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "calendar_sung.db";
    private static final int DATABASE_VERSION = 1;

    public DatabaseHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE "+Provider.DatesColumns.TABLE_NAME+" ("
                +Provider.DatesColumns._ID+" INTEGER PRIMARY KEY,"
                +Provider.DatesColumns.POSITION + " INTEGER,"
                +Provider.DatesColumns.YEAR + " INTEGER,"
                +Provider.DatesColumns.MONTH + " INTEGER,"
                +Provider.DatesColumns.DAY + " INTEGER,"
                +Provider.DatesColumns.CURRENT_MONTH + " INTEGER,"
                +Provider.DatesColumns.SELLECT_STATUS + " INTEGER"
                +");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Provider.DatesColumns.TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
}
