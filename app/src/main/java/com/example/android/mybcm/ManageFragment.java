package com.example.android.mybcm;

import android.app.LoaderManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.mybcm.data.BCMContract;


public class ManageFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final int BCM_LOADER =0;

    BCMCursorAdapter mCursorAdapter;
    public ManageFragment(){}
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.fragment_manage, container, false);

        FloatingActionButton fab = (FloatingActionButton) rootView.findViewById(R.id.fab) ;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), EditorActivity.class);
                startActivity(intent);
            }
        });

        ListView BCMListView = (ListView) rootView.findViewById(R.id.list);

        mCursorAdapter = new BCMCursorAdapter(getActivity(),null);
        BCMListView.setAdapter(mCursorAdapter);

        BCMListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Intent intent = new Intent(getActivity(), EditorActivity.class);

                Uri currentBCMUri = ContentUris.withAppendedId(BCMContract.BCMEntry.CONTENT_URI, id);


                intent.setData(currentBCMUri);


                startActivity(intent);
            }
        });

        getActivity().getLoaderManager().initLoader(BCM_LOADER, null, this);
        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {BCMContract.BCMEntry._ID, BCMContract.BCMEntry.COLUMN_BCM_NAME, BCMContract.BCMEntry.COLUMN_BCM_COMPANY};
        return new CursorLoader(getActivity(),
                BCMContract.BCMEntry.CONTENT_URI,
                projection,
                null,
                null,
                null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mCursorAdapter.swapCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mCursorAdapter.swapCursor(null);
    }
}
