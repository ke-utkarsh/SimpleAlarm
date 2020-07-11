package com.example.utkalarmv2;

import android.annotation.SuppressLint;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.os.Build;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    AlarmManager alarman;
    PendingIntent pi;
    TimePicker tp;
    Button button1;
    Button button2;
    TextView tv;
    private Context context;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        alarman=(AlarmManager)getSystemService(ALARM_SERVICE);
        tp=(TimePicker)findViewById(R.id.picker);
        tp.setIs24HourView(true);
        tv=(TextView)findViewById(R.id.textv);
        final Calendar cal=Calendar.getInstance();
        final Intent my_intent=new Intent(this,AlertReceiver.class);

        Button button1=(Button)findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onClick(View v) {
                cal.set(Calendar.HOUR_OF_DAY,tp.getHour());
                cal.set(Calendar.MINUTE,tp.getMinute());
                cal.set(Calendar.SECOND,0);
                set_tv(cal);
                my_intent.putExtra("extra","on");
                pi=PendingIntent.getBroadcast(MainActivity.this,1,my_intent,PendingIntent.FLAG_UPDATE_CURRENT);
                alarman.setExact(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pi);
            }
        });
        Button button2=(Button)findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_tv2();
                my_intent.putExtra("extra","off");
                alarman.setExact(AlarmManager.RTC_WAKEUP,cal.getTimeInMillis(),pi);
                pi=PendingIntent.getBroadcast(MainActivity.this,1,my_intent,PendingIntent.FLAG_UPDATE_CURRENT);
                alarman.cancel(pi);
                sendBroadcast(my_intent);
            }
        });

    }

    @SuppressLint("SetTextI18n")
    private void set_tv2() {
        tv.setText("Alarm Off!!");
    }

    private void set_tv(Calendar cal){
        String timeText="Alarm set for: ";
        timeText+= DateFormat.getTimeInstance(DateFormat.SHORT).format(cal.getTime());
        tv.setText(timeText);
    }
}
