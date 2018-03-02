package com.sung.calendarview.provider;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import java.util.HashMap;

/**
 * Created by sung on 2017/7/25.
 */

public class CalendarProvider extends ContentProvider {
    private static final UriMatcher mUriMatcher;
    private static HashMap<String, String> mDatesProjectionMap;

    //all data
    private static final int DATES_DIR = 1;
    //single data
    private static final int DATES_ID = 2;

    private DatabaseHelper mDatabaseHelper;

    static {
        mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
        //和CONTENT_URI保持一致
        mUriMatcher.addURI(Provider.AUTHORITY, "dates", DATES_DIR);
        mUriMatcher.addURI(Provider.AUTHORITY, "dates/#", DATES_ID);

        mDatesProjectionMap = new HashMap<>();
        mDatesProjectionMap.put(Provider.DatesColumns._ID,Provider.DatesColumns._ID);
        mDatesProjectionMap.put(Provider.DatesColumns.POSITION,Provider.DatesColumns.POSITION);
        mDatesProjectionMap.put(Provider.DatesColumns.PAGER_INDEX,Provider.DatesColumns.PAGER_INDEX);
        mDatesProjectionMap.put(Provider.DatesColumns.YEAR,Provider.DatesColumns.YEAR);
        mDatesProjectionMap.put(Provider.DatesColumns.MONTH,Provider.DatesColumns.MONTH);
        mDatesProjectionMap.put(Provider.DatesColumns.DAY,Provider.DatesColumns.DAY);
        mDatesProjectionMap.put(Provider.DatesColumns.CURRENT_MONTH,Provider.DatesColumns.CURRENT_MONTH);
        mDatesProjectionMap.put(Provider.DatesColumns.SELLECT_STATUS,Provider.DatesColumns.SELLECT_STATUS);
        mDatesProjectionMap.put(Provider.DatesColumns.YYMMDD,Provider.DatesColumns.YYMMDD);
        mDatesProjectionMap.put(Provider.DatesColumns.YYMM,Provider.DatesColumns.YYMM);
    }

    @Override
    public boolean onCreate() {
        mDatabaseHelper = new DatabaseHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        switch (mUriMatcher.match(uri)){
            case DATES_DIR:
                return Provider.CONTENT_TYPE;
            case DATES_ID:
                return Provider.CONTENT_ITEM_TYPE;
            default:
                throw new IllegalArgumentException("Unknown URI"+uri);
        }
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
        qb.setTables(Provider.DatesColumns.TABLE_NAME);

        switch (mUriMatcher.match(uri)) {
            case DATES_DIR:
                qb.setProjectionMap(mDatesProjectionMap);
                break;

            case DATES_ID:
                qb.setProjectionMap(mDatesProjectionMap);
                qb.appendWhere(Provider.DatesColumns._ID + "=" + uri.getPathSegments().get(1));
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        // If no sort order is specified use the default
        String orderBy;
        if (TextUtils.isEmpty(sortOrder)) {
            orderBy = Provider.DatesColumns.DEFAULT_SORT_ORDER;
        } else {
            orderBy = sortOrder;
        }

        // Get the database and run the query
        SQLiteDatabase db = mDatabaseHelper.getReadableDatabase();
        Cursor c = qb.query(db, projection, selection, selectionArgs, null, null, orderBy);

        // Tell the cursor what uri to watch, so it knows when its source data changes
        c.setNotificationUri(getContext().getContentResolver(), uri);
        return c;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        if (mUriMatcher.match(uri) != DATES_DIR){
            throw new IllegalArgumentException("Unknown URI"+uri);
        }

        ContentValues values;
        if (contentValues != null){
            values = new ContentValues(contentValues);
        }else {
            values = new ContentValues();
        }

        if (values.containsKey(Provider.DatesColumns.POSITION) == false)
            values.put(Provider.DatesColumns.POSITION,0);

        if (values.containsKey(Provider.DatesColumns.PAGER_INDEX) == false)
            values.put(Provider.DatesColumns.PAGER_INDEX,0);

        if (values.containsKey(Provider.DatesColumns.YEAR) == false)
            values.put(Provider.DatesColumns.YEAR,1993);

        if (values.containsKey(Provider.DatesColumns.MONTH) == false)
            values.put(Provider.DatesColumns.MONTH,1);

        if (values.containsKey(Provider.DatesColumns.DAY) == false)
            values.put(Provider.DatesColumns.DAY,1);

        //0 = false ; 1 = true
        if (values.containsKey(Provider.DatesColumns.CURRENT_MONTH) == false)
            values.put(Provider.DatesColumns.CURRENT_MONTH,0);
        if (values.containsKey(Provider.DatesColumns.SELLECT_STATUS) == false)
            values.put(Provider.DatesColumns.SELLECT_STATUS,0);

        if (values.containsKey(Provider.DatesColumns.YYMMDD) == false)
            values.put(Provider.DatesColumns.YYMMDD,"");
        if (values.containsKey(Provider.DatesColumns.YYMM) == false)
            values.put(Provider.DatesColumns.YYMM,"");

        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        long insert = db.insert(Provider.DatesColumns.TABLE_NAME, Provider.DatesColumns.POSITION, values);
        if (insert > 0 ){
            Uri uri1 = ContentUris.withAppendedId(Provider.DatesColumns.CONTENT_URI, insert);
            getContext().getContentResolver().notifyChange(uri1,null);
            return uri1;
        }
//        return null;
        throw new SQLException("Failed to insert row into " + uri);
    }

    @Override
    public int delete(Uri uri, String where, String[] whereArgs) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        int count;
        switch (mUriMatcher.match(uri)){
            case DATES_DIR:
                count = db.delete(Provider.DatesColumns.TABLE_NAME,where,whereArgs);
                break;
            case DATES_ID:
                String noteId = uri.getPathSegments().get(1);
                count = db.delete(Provider.DatesColumns.TABLE_NAME, Provider.DatesColumns._ID + "=" + noteId
                        + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
                break;
            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }
        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }

    @Override
    public int update(Uri uri, ContentValues values, String where, String[] whereArgs) {
        SQLiteDatabase db = mDatabaseHelper.getWritableDatabase();
        int count;
        switch (mUriMatcher.match(uri)) {
            case DATES_DIR:
                count = db.update(Provider.DatesColumns.TABLE_NAME, values, where, whereArgs);
                break;

            case DATES_ID:
                String noteId = uri.getPathSegments().get(1);
                count = db.update(Provider.DatesColumns.TABLE_NAME, values, Provider.DatesColumns._ID + "=" + noteId
                        + (!TextUtils.isEmpty(where) ? " AND (" + where + ')' : ""), whereArgs);
                break;

            default:
                throw new IllegalArgumentException("Unknown URI " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return count;
    }
}
