package com.sung.calendarview.utils;

/**
 * Created by sung on 2017/7/20.
 */

public class Log {
    private static final String TAG = "com.sung.log";
    
    public static void d(String msg){
        android.util.Log.d(TAG, msg);
    }

    public static void e(String msg){
        android.util.Log.e(TAG, msg );
    }

    public static void i(String msg){
        android.util.Log.i(TAG, msg );
    }

    public static void w(String msg){
        android.util.Log.w(TAG, msg );
    }

    public static void v(String msg){
        android.util.Log.v(TAG, msg );
    }

    public static void d(Class activity, String msg){
        android.util.Log.d(TAG, activity.getSimpleName().toString().trim()+": "+msg);
    }
}
