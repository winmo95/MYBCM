package com.example.android.mybcm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class alarmReciver extends BroadcastReceiver {
    Context context;
    static final int ALARM_ok= 1;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        Intent service_intent = new Intent(context, notification.class);
        service_intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);


        service_intent.putExtra("NAME",intent.getStringExtra("NAME"));
        service_intent.putExtra("DOIT",intent.getStringExtra("DOIT"));
        Toast.makeText(context,""+intent.getStringExtra("NAME")+"/"+ intent.getStringExtra("DOIT") , Toast.LENGTH_SHORT).show();
        context.startActivity(service_intent);
    }

}
