package com.example.android.mybcm;

import android.app.AlertDialog;
import android.app.LoaderManager;
import android.content.ContentValues;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.android.mybcm.data.BCMContract;

public class EditorActivity  extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor>{
    private static final int EXISTING_BCM_LOADER = 0;
    private Uri mCurrentBCMUri;
    private EditText mNameEditText;
    private EditText mCompanyEditText;
    private EditText mRankEditText;
    private EditText mAddressEditText;
    private EditText mTelEditText;
    private EditText mPhoneEditText;
    private EditText mFAXEditText;
    private EditText mEmailEditText;
    private Button mCall;
    private String mNum;

    private boolean mBCMhasChanged =false;

    private View.OnTouchListener mTouchListener = new View.OnTouchListener(){
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            mBCMhasChanged = false;
            return false;
        }
    };

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        mCall = (Button) findViewById(R.id.btnCall);

        final Intent intent = getIntent();
        mCurrentBCMUri = intent.getData();

        if(mCurrentBCMUri == null){
            setTitle("BCM 추가");
            invalidateOptionsMenu();
            mCall.setVisibility(View.GONE);
        }else{
            setTitle("BCM 수정");
            getLoaderManager().initLoader(EXISTING_BCM_LOADER, null, this);
        }

        mNameEditText = (EditText) findViewById(R.id.edit_bcm_name);
        mCompanyEditText = (EditText) findViewById(R.id.edit_bcm_company);
        mRankEditText = (EditText) findViewById(R.id.edit_bcm_rank);
        mAddressEditText = (EditText) findViewById(R.id.edit_bcm_address);
        mTelEditText = (EditText) findViewById(R.id.edit_bcm_tel);
        mPhoneEditText = (EditText) findViewById(R.id.edit_bcm_phone);
        mFAXEditText = (EditText) findViewById(R.id.edit_bcm_fax);
        mEmailEditText = (EditText) findViewById(R.id.edit_bcm_email);


        mNameEditText.setText(intent.getStringExtra("NAME"));
        mCompanyEditText.setText(intent.getStringExtra("COMPANY"));
        mRankEditText.setText(intent.getStringExtra("RANK"));
        mAddressEditText.setText(intent.getStringExtra("ADDRESS"));
        mTelEditText.setText(intent.getStringExtra("TEL"));
        mPhoneEditText.setText(intent.getStringExtra("PHONE"));
        mFAXEditText.setText(intent.getStringExtra("FAX"));
        mEmailEditText.setText(intent.getStringExtra("EMAIL"));


        mNameEditText.setOnTouchListener(mTouchListener);
        mCompanyEditText.setOnTouchListener(mTouchListener);
        mRankEditText.setOnTouchListener(mTouchListener);
        mAddressEditText.setOnTouchListener(mTouchListener);
        mTelEditText.setOnTouchListener(mTouchListener);
        mPhoneEditText.setOnTouchListener(mTouchListener);
        mFAXEditText.setOnTouchListener(mTouchListener);
        mEmailEditText.setOnTouchListener(mTouchListener);

        mCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mNum = mPhoneEditText.getText().toString();
                String tel = "tel:"+mNum;
                startActivity(new Intent(Intent.ACTION_DIAL, Uri.parse(tel)));
            }
        });
    }



    private void saveBCM(){
        String nameString = mNameEditText.getText().toString().trim();
        String companyString = mCompanyEditText.getText().toString().trim();
        String rankString = mRankEditText.getText().toString().trim();
        String addressString = mAddressEditText.getText().toString().trim();
        String telString = mTelEditText.getText().toString().trim();
        String phoneString = mPhoneEditText.getText().toString().trim();
        String faxString = mFAXEditText.getText().toString().trim();
        String emailString = mEmailEditText.getText().toString().trim();

        if(mCurrentBCMUri == null &&
                TextUtils.isEmpty(nameString) && TextUtils.isEmpty(companyString) && TextUtils.isEmpty(telString) && TextUtils.isEmpty(emailString)) {
            return;
        }

        ContentValues values = new ContentValues();
        values.put(BCMContract.BCMEntry.COLUMN_BCM_NAME, nameString);
        values.put(BCMContract.BCMEntry.COLUMN_BCM_COMPANY, companyString);
        values.put(BCMContract.BCMEntry.COLUMN_BCM_rank, rankString);
        values.put(BCMContract.BCMEntry.COLUMN_BCM_ADDRESS, addressString);
        values.put(BCMContract.BCMEntry.COLUMN_BCM_TEL,telString);
        values.put(BCMContract.BCMEntry.COLUMN_BCM_PHONE, phoneString);
        values.put(BCMContract.BCMEntry.COLUMN_BCM_FAX, faxString);
        values.put(BCMContract.BCMEntry.COLUMN_BCM_EMAIL,emailString);


        if (mCurrentBCMUri == null){
            Uri newUri = getContentResolver().insert(BCMContract.BCMEntry.CONTENT_URI,values);

            if (newUri == null){
                Toast.makeText(this, "Error with saving BCM", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "BCM 저장 완료", Toast.LENGTH_SHORT).show();
            }
        }else {
            int rowsAffected = getContentResolver().update(mCurrentBCMUri,values,null,null);
            if (rowsAffected == 0){
                Toast.makeText(this, "Error with updating BCM", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, " BCM 업데이트 완료", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        if (mCurrentBCMUri == null) {
            MenuItem menuItem_del = menu.findItem(R.id.action_delete);
            menuItem_del.setVisible(false);
            MenuItem menuItem_bluetooth = menu.findItem(R.id.action_bluetooth);
            menuItem_bluetooth.setVisible(false);
            MenuItem menuItem_alarm = menu.findItem(R.id.action_alarm);
            menuItem_alarm.setVisible(false);
        }
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_save:
                saveBCM();
                finish();
                return true;

            case R.id.action_delete:
                showDeleteConfirmationDialog();
                return true;

            case R.id. action_bluetooth:
                Intent intentBluetooth = new Intent(this,  Bluetooth.class);
                intentBluetooth.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intentBluetooth.putExtra("NAME", mNameEditText.getText().toString());
                intentBluetooth.putExtra("COMPANY", mCompanyEditText.getText().toString());
                intentBluetooth.putExtra("RANK", mRankEditText.getText().toString());
                intentBluetooth.putExtra("ADDRESS", mAddressEditText.getText().toString());
                intentBluetooth.putExtra("TEL", mTelEditText.getText().toString());
                intentBluetooth.putExtra("PHONE", mPhoneEditText.getText().toString());
                intentBluetooth.putExtra("FAX", mFAXEditText.getText().toString());
                intentBluetooth.putExtra("EMAIL", mEmailEditText.getText().toString());

                startActivity(intentBluetooth);
                return true;

            case R.id. action_alarm: {
                Intent intent = new Intent(this,alarm_calender.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("NAME", mNameEditText.getText().toString());
                startActivity(intent);
                return true;
            }
            case android.R.id.home:
                if (!mBCMhasChanged) {
                    NavUtils.navigateUpFromSameTask(EditorActivity.this);
                    return true;
                }

                DialogInterface.OnClickListener discardButtonClickListener =
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                NavUtils.navigateUpFromSameTask(EditorActivity.this);
                            }
                        };

                showUnsavedChangesDialog(discardButtonClickListener);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onBackPressed() {
        if (!mBCMhasChanged) {
            super.onBackPressed();
            return;
        }

        DialogInterface.OnClickListener discardButtonClickListener =
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        finish();
                    }
                };

        showUnsavedChangesDialog(discardButtonClickListener);
    }

    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        // Since the editor shows all pet attributes, define a projection that contains
        // all columns from the pet table
        String[] projection = {
                BCMContract.BCMEntry._ID,
                BCMContract.BCMEntry.COLUMN_BCM_NAME,
                BCMContract.BCMEntry.COLUMN_BCM_COMPANY,
                BCMContract.BCMEntry.COLUMN_BCM_rank,
                BCMContract.BCMEntry.COLUMN_BCM_ADDRESS,
                BCMContract.BCMEntry.COLUMN_BCM_TEL,
                BCMContract.BCMEntry.COLUMN_BCM_PHONE,
                BCMContract.BCMEntry.COLUMN_BCM_FAX,
                BCMContract.BCMEntry.COLUMN_BCM_EMAIL};

        return new CursorLoader(this,   // Parent activity context
                mCurrentBCMUri,         // Query the content URI for the current pet
                projection,             // Columns to include in the resulting Cursor
                null,                   // No selection clause
                null,                   // No selection arguments
                null);
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor == null || cursor.getCount() < 1) {
            return;
        }

        if (cursor.moveToFirst()) {
            int nameColumnIndex = cursor.getColumnIndex(BCMContract.BCMEntry.COLUMN_BCM_NAME);
            int companyColumnIndex = cursor.getColumnIndex(BCMContract.BCMEntry.COLUMN_BCM_COMPANY);
            int rankColumnIndex = cursor.getColumnIndex(BCMContract.BCMEntry.COLUMN_BCM_rank);
            int addressColumnIndex = cursor.getColumnIndex(BCMContract.BCMEntry.COLUMN_BCM_ADDRESS);
            int telColumnIndex = cursor.getColumnIndex(BCMContract.BCMEntry.COLUMN_BCM_TEL);
            int phoneColumnIndex = cursor.getColumnIndex(BCMContract.BCMEntry.COLUMN_BCM_PHONE);
            int faxColumnIndex = cursor.getColumnIndex(BCMContract.BCMEntry.COLUMN_BCM_FAX);
            int emailColumnIndex = cursor.getColumnIndex(BCMContract.BCMEntry.COLUMN_BCM_EMAIL);

            String name = cursor.getString(nameColumnIndex);
            String company = cursor.getString(companyColumnIndex);
            String rank = cursor.getString(rankColumnIndex);
            String address = cursor.getString(addressColumnIndex);
            String tel = cursor.getString(telColumnIndex);
            String phone = cursor.getString(phoneColumnIndex);
            String fax = cursor.getString(faxColumnIndex);
            String email = cursor.getString(emailColumnIndex);

            mNameEditText.setText(name);
            mCompanyEditText.setText(company);
            mRankEditText.setText(rank);
            mAddressEditText.setText(address);
            mTelEditText.setText(tel);
            mPhoneEditText.setText(phone);
            mFAXEditText.setText(fax);
            mEmailEditText.setText(email);

        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        mNameEditText.setText("");
        mCompanyEditText.setText("");
        mRankEditText.setText("");
        mAddressEditText.setText("");
        mTelEditText.setText("");
        mPhoneEditText.setText("");
        mFAXEditText.setText("");
        mEmailEditText.setText("");
    }

    private void showUnsavedChangesDialog(
            DialogInterface.OnClickListener discardButtonClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.unsaved_changes_dialog_msg);
        builder.setPositiveButton(R.string.discard, discardButtonClickListener);
        builder.setNegativeButton(R.string.keep_editing, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        // Create and show the AlertDialog
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void showDeleteConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg);
        builder.setPositiveButton(R.string.delete, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                deleteBCM();
            }
        });
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                if (dialog != null) {
                    dialog.dismiss();
                }
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void deleteBCM() {
        if (mCurrentBCMUri != null) {
            int rowsDeleted = getContentResolver().delete(mCurrentBCMUri, null, null);

            if (rowsDeleted == 0) {
                Toast.makeText(this, getString(R.string.editor_delete_BCM_failed),
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, getString(R.string.editor_delete_BCM_successful),
                        Toast.LENGTH_SHORT).show();
            }
        }

        finish();
    }

}
