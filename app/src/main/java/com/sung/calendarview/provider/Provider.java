package com.sung.calendarview.provider;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by sung on 2017/7/25.
 * provider常量
 */

public class Provider {
    // 这个是每个Provider的标识，在Manifest中使用
    public static final String AUTHORITY = "com.sung.provider.calendarview";

    public static final String CONTENT_TYPE = "vnd.android.cursor.dir/vnd.com.sung.provider.calendarview";

    public static final String CONTENT_ITEM_TYPE = "vnd.android.cursor.item/vnd.com.sung.provider.calendarview";

    public static final class DatesColumns implements BaseColumns{

        public static final Uri CONTENT_URI = Uri.parse("content://"+AUTHORITY+"/dates");//通用uri
        public static final String TABLE_NAME = "dates";
        public static final String DEFAULT_SORT_ORDER = "position desc";

        public static final String POSITION = "position";
        public static final String PAGER_INDEX = "pager_index";
        public static final String YEAR = "year";
        public static final String MONTH = "month";
        public static final String DAY = "day";
        public static final String CURRENT_MONTH = "currentmonth";
        public static final String SELLECT_STATUS = "sellectstatus";
        public static final String YYMMDD = "yy_mm_dd";
        public static final String YYMM = "yy_mm";

    }
}
