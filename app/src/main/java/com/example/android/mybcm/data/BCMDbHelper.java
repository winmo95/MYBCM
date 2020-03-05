package com.example.android.mybcm.data;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BCMDbHelper extends SQLiteOpenHelper {

    public static final String LOG_TAG = BCMDbHelper.class.getSimpleName();
    private static final String DATABASE_NAME = "shelter1.db";
    private static final int DATABASE_VERSION = 1;

    public BCMDbHelper(Context context){
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_BCM_TABLE = "CREATE TABLE " + BCMContract.BCMEntry.TABLE_NAME + " ("
                + BCMContract.BCMEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + BCMContract.BCMEntry.COLUMN_BCM_NAME + " TEXT NOT NULL, "
                + BCMContract.BCMEntry.COLUMN_BCM_COMPANY + " TEXT NOT NULL, "
                + BCMContract.BCMEntry.COLUMN_BCM_rank + " TEXT NOT NULL, "
                + BCMContract.BCMEntry.COLUMN_BCM_ADDRESS + " TEXT NOT NULL, "
                + BCMContract.BCMEntry.COLUMN_BCM_TEL  + " TEXT NOT NULL, "
                + BCMContract.BCMEntry.COLUMN_BCM_PHONE  + " TEXT NOT NULL, "
                + BCMContract.BCMEntry.COLUMN_BCM_FAX  + " TEXT NOT NULL, "
                + BCMContract.BCMEntry.COLUMN_BCM_EMAIL + " TEXT NOT NULL);";

        db.execSQL(SQL_CREATE_BCM_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
