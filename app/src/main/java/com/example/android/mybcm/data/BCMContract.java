package com.example.android.mybcm.data;


import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class BCMContract {

    private BCMContract(){}

    public static final String CONTENT_AUTHORITY = "com.example.android.mybcm";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://"+CONTENT_AUTHORITY);
    public static final String PATH_BCM ="BCM";

    public static final class BCMEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI,PATH_BCM);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY +"/" + PATH_BCM;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_BCM;


        public final static String TABLE_NAME = "BCM";

        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_BCM_NAME ="name";
        public final static String COLUMN_BCM_COMPANY = "company";
        public final static String COLUMN_BCM_rank = "rank";
        public final static String COLUMN_BCM_ADDRESS = "address";
        public final static String COLUMN_BCM_TEL ="tel";
        public final static String COLUMN_BCM_PHONE ="phone";
        public final static String COLUMN_BCM_FAX ="fax";

        public final static String COLUMN_BCM_EMAIL ="email";

        }
}
