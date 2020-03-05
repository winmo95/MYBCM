package com.example.android.mybcm.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.util.Log;

public class BCMProvider extends ContentProvider {

    public static final String LOG_TAG = BCMProvider.class.getSimpleName();

    private static final int BCM = 100;

    private static final int BCM_ID = 101;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        sUriMatcher.addURI(BCMContract.CONTENT_AUTHORITY, BCMContract.PATH_BCM, BCM);
        sUriMatcher.addURI(BCMContract.CONTENT_AUTHORITY,  BCMContract.PATH_BCM + "/#", BCM_ID);
    }

    private BCMDbHelper mdbHelper;

    public boolean onCreate(){
        mdbHelper = new BCMDbHelper(getContext());
        return true;
    }

    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder){
        SQLiteDatabase database = mdbHelper.getReadableDatabase();
        Cursor cursor;

        int match = sUriMatcher.match(uri);

        switch (match){
            case BCM:
                cursor = database.query(BCMContract.BCMEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            case BCM_ID:
                selection = BCMContract.BCMEntry._ID + "=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                cursor = database.query(BCMContract.BCMEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;

            default:
                throw new IllegalArgumentException("Cannot query unknown URI " + uri);
        }

        cursor.setNotificationUri(getContext().getContentResolver(), uri);
        return cursor;
    }

    public Uri insert(Uri uri, ContentValues contentValues) {
        final int match = sUriMatcher.match(uri);
        switch (match) {
            case BCM:
                return insertBCM(uri, contentValues);
            default:
                throw new IllegalArgumentException("Insertion is not supported for " + uri);
        }
    }

    private Uri insertBCM(Uri uri, ContentValues values){
        String name = values.getAsString(BCMContract.BCMEntry.COLUMN_BCM_NAME);
        if(name == null){
            throw new IllegalArgumentException("BCM requires a name");
        }

        String company = values.getAsString(BCMContract.BCMEntry.COLUMN_BCM_COMPANY);
        if(company == null){
            throw new IllegalArgumentException("BCM requires a company");
        }

        String rank = values.getAsString(BCMContract.BCMEntry.COLUMN_BCM_rank);
        if(rank == null){
            throw new IllegalArgumentException("BCM requires a rank");
        }

        String address = values.getAsString(BCMContract.BCMEntry.COLUMN_BCM_ADDRESS);
        if(address == null){
            throw new IllegalArgumentException("BCM requires a address");
        }


        String tel = values.getAsString(BCMContract.BCMEntry.COLUMN_BCM_TEL);
        if(tel == null){
            throw new IllegalArgumentException("BCM requires a tel");
        }

        String phone = values.getAsString(BCMContract.BCMEntry.COLUMN_BCM_PHONE);
        if(phone == null){
            throw new IllegalArgumentException("BCM requires a phone");
        }

        String fax = values.getAsString(BCMContract.BCMEntry.COLUMN_BCM_FAX);
        if(fax == null){
            throw new IllegalArgumentException("BCM requires a fax");
        }

        String email = values.getAsString(BCMContract.BCMEntry.COLUMN_BCM_EMAIL);
        if(email == null){
            throw new IllegalArgumentException("BCM requires a email");
        }

        SQLiteDatabase database = mdbHelper.getReadableDatabase();

        long id = database.insert(BCMContract.BCMEntry.TABLE_NAME, null, values);
        if (id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for " + uri);
            return null;
        }

        getContext().getContentResolver().notifyChange(uri,null);

        return ContentUris.withAppendedId(uri,id);
    }

    public int update(Uri uri, ContentValues contentValues, String selection,
                      String[] selectionArgs){
        final int match = sUriMatcher.match(uri);
        switch (match){
            case BCM:
                return updateBCM(uri, contentValues, selection, selectionArgs);

            case BCM_ID:
                selection = BCMContract.BCMEntry._ID +"=?";
                selectionArgs = new String[] { String.valueOf(ContentUris.parseId(uri)) };
                return updateBCM(uri,contentValues,selection,selectionArgs);

            default:
                throw new IllegalArgumentException("Update is not supported for " + uri);
        }
    }


    private int updateBCM(Uri uri, ContentValues values, String selection, String[] selectionArgs){

        if(values.containsKey(BCMContract.BCMEntry.COLUMN_BCM_NAME)){
            String name = values.getAsString(BCMContract.BCMEntry.COLUMN_BCM_NAME);
            if(name == null){
                throw new IllegalArgumentException("BCM requires a name");
            }
        }

        if(values.containsKey(BCMContract.BCMEntry.COLUMN_BCM_COMPANY)){
            String company = values.getAsString(BCMContract.BCMEntry.COLUMN_BCM_COMPANY);
            if(company == null){
                throw new IllegalArgumentException("BCM requires a company");
            }
        }

        if(values.containsKey(BCMContract.BCMEntry.COLUMN_BCM_rank)){
            String rank = values.getAsString(BCMContract.BCMEntry.COLUMN_BCM_rank);
            if(rank == null){
                throw new IllegalArgumentException("BCM requires a rank");
            }
        }

        if(values.containsKey(BCMContract.BCMEntry.COLUMN_BCM_ADDRESS)){
            String address = values.getAsString(BCMContract.BCMEntry.COLUMN_BCM_ADDRESS);
            if(address == null){
                throw new IllegalArgumentException("BCM requires a address");
            }
        }

        if(values.containsKey(BCMContract.BCMEntry.COLUMN_BCM_TEL)){
            String tel = values.getAsString(BCMContract.BCMEntry.COLUMN_BCM_TEL);
            if(tel == null){
                throw new IllegalArgumentException("BCM requires a tel");
            }
        }

        if(values.containsKey(BCMContract.BCMEntry.COLUMN_BCM_PHONE)){
            String phone = values.getAsString(BCMContract.BCMEntry.COLUMN_BCM_PHONE);
            if(phone == null){
                throw new IllegalArgumentException("BCM requires a phone");
            }
        }


        if(values.containsKey(BCMContract.BCMEntry.COLUMN_BCM_FAX)){
            String fax = values.getAsString(BCMContract.BCMEntry.COLUMN_BCM_FAX);
            if(fax == null){
                throw new IllegalArgumentException("BCM requires a tel");
            }
        }

        if(values.containsKey(BCMContract.BCMEntry.COLUMN_BCM_EMAIL)){
            String email = values.getAsString(BCMContract.BCMEntry.COLUMN_BCM_EMAIL);
            if(email == null){
                throw new IllegalArgumentException("BCM requires a email");
            }
        }

        if (values.size() == 0) {
            return 0;
        }

        SQLiteDatabase database = mdbHelper.getWritableDatabase();
        int rowsUpdated = database.update(BCMContract.BCMEntry.TABLE_NAME, values, selection, selectionArgs);

        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return  rowsUpdated;
    }

    public int delete(Uri uri, String selection, String[] selectionArgs){
        SQLiteDatabase database = mdbHelper.getWritableDatabase();
        int rowsDeleted;

        final int match = sUriMatcher.match(uri);
        switch (match){
            case BCM:
                rowsDeleted = database.delete(BCMContract.BCMEntry.TABLE_NAME, selection, selectionArgs);
                break;

            case BCM_ID:
                selection = BCMContract.BCMEntry._ID + "=?";
                selectionArgs = new String[]{ String.valueOf(ContentUris.parseId(uri))};
                rowsDeleted = database.delete(BCMContract.BCMEntry.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new IllegalArgumentException("Deletion is not supported for " + uri);
        }

        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    public String getType(Uri uri){
        final int match = sUriMatcher.match(uri);
        switch (match){
            case BCM:
                return BCMContract.BCMEntry.CONTENT_LIST_TYPE;

            case BCM_ID:
                return BCMContract.BCMEntry.CONTENT_ITEM_TYPE;

            default:
                throw new IllegalArgumentException("Unknown URI "+ uri +" with match "+ match);
        }
    }
}
