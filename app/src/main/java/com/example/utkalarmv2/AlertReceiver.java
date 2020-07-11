package com.example.utkalarmv2;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.widget.Button;

import java.util.Objects;

public class AlertReceiver extends BroadcastReceiver {

    Button button2;
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent intent) {
        String s= Objects.requireNonNull(intent.getExtras()).getString("extra");
        Uri note = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
        final Ringtone r = RingtoneManager.getRingtone(context.getApplicationContext(), note);

        assert s != null;
        if(s.equals("on")) {
            r.play();

            NotificationHelper notificationHelper = new NotificationHelper(context);
            NotificationCompat.Builder nb = notificationHelper.getChannelNotification();
            notificationHelper.getManager().notify(1, nb.build());


            new CountDownTimer(30000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {

                }

                @Override
                public void onFinish() {
                    if(r.isPlaying()){
                        r.stop();
                    }
                }
            }.start();
        }else{
            if (r.isPlaying()){
                r.stop();
            }
        }

    }
}
