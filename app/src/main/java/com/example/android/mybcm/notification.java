package com.example.android.mybcm;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Calendar;

public class notification extends Activity {

    EditText withdo;
    Vibrator mVib;
    Button btnok;
    Button repeat;
    String uname;
    String doit;
    TimePicker repeatPicker;
    int shour, smin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        mVib = (Vibrator)getSystemService(Context.VIBRATOR_SERVICE);
        mVib.vibrate(new long[] {100,50,200,50},0);
        Intent intent = getIntent();

        withdo = (EditText) findViewById(R.id.with);
        btnok = (Button) findViewById(R.id.bok);
        repeat = (Button) findViewById(R.id.repeat);
        repeatPicker = (TimePicker) findViewById(R.id.timerepeat);
        uname = intent.getStringExtra("NAME");
        doit = intent.getStringExtra("DOIT");
        withdo.setText(uname + "\n" +doit);


        Toast.makeText(this,""+intent.getStringExtra("NAME")+"/"+doit, Toast.LENGTH_SHORT).show();

        btnok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mVib.cancel();
               finish();
            }
        });

        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mVib.cancel();
                AlarmManager am =(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(getBaseContext(),alarmReciver.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("NAME", uname);
                intent.putExtra("DOIT",withdo.getText().toString());


                PendingIntent sender = PendingIntent.getBroadcast(getBaseContext(),0,intent,0);
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.HOUR_OF_DAY,shour);
                calendar.set(Calendar.MINUTE,smin);
                calendar.set(Calendar.SECOND,0);
                am.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),sender);

                finish();
            }
        });

        repeatPicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int min) {
                shour =hour;
                smin = min;
            }
        });
    }


}
