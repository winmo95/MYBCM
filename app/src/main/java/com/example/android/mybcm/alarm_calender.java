package com.example.android.mybcm;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class alarm_calender extends AppCompatActivity {

    CalendarView calendarView;
    TimePicker timePicker;
    EditText dotext;


    long mnow;
    Date mdate;
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd-HH-mm");

    String name;
    String doit;

    int syear;
    int smonth;
    int sday;
    int shour;
    int smin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_calender);
        final Intent intent = getIntent();

        name = intent.getStringExtra("NAME");
        calendarView = (CalendarView) findViewById(R.id.calendarView);
        timePicker = (TimePicker)findViewById(R.id.clock);
        dotext = findViewById(R.id.doit);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.set) ;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doit = dotext.getText().toString();
                AlarmManager am =(AlarmManager)getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(getBaseContext(),alarmReciver.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.putExtra("NAME", name);
                intent.putExtra("DOIT",doit);

                PendingIntent sender = PendingIntent.getBroadcast(getBaseContext(),0,intent,0);
                Calendar calendar = Calendar.getInstance();
                calendar.set(Calendar.YEAR,syear);
                calendar.set(Calendar.MONTH,smonth);
                calendar.set(Calendar.DAY_OF_MONTH,sday);
                calendar.set(Calendar.HOUR_OF_DAY,shour);
                calendar.set(Calendar.MINUTE,smin);
                calendar.set(Calendar.SECOND,0);
                am.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),sender);

                finish();
            }
        });

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int year, int month, int dayOfMonth) {
                syear =year;
                smonth = month;
                sday = dayOfMonth;
            }
        });

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker timePicker, int hour, int min) {
                 shour =hour;
                 smin = min;
            }
        });

    }

    private String getTime(){
        mnow = System.currentTimeMillis();
        mdate = new Date(mnow);
        return sdf.format(mdate);
    }

}
