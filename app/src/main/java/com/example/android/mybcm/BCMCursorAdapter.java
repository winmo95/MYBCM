package com.example.android.mybcm;


import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;

import com.example.android.mybcm.data.BCMContract;

public class BCMCursorAdapter extends CursorAdapter {

    public BCMCursorAdapter(Context context, Cursor c){
        super(context,c,0);
    }

    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        return LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
    }

    public void bindView(View view, Context context, Cursor cursor) {
        TextView nameTextView = (TextView) view.findViewById(R.id.name);
        TextView summaryTextView = (TextView) view.findViewById(R.id.summary);

        int nameColumnIndex = cursor.getColumnIndex(BCMContract.BCMEntry.COLUMN_BCM_NAME);
        int companyColumnIndex = cursor.getColumnIndex(BCMContract.BCMEntry.COLUMN_BCM_COMPANY);

        String BCMName = cursor.getString(nameColumnIndex);
        String BCMcompany = cursor.getString(companyColumnIndex);

        nameTextView.setText(BCMName);
        summaryTextView.setText(BCMcompany);
    }
}
