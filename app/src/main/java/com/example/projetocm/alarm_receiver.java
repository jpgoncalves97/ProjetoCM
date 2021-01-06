package com.example.projetocm;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.core.app.TaskStackBuilder;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class alarm_receiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent inte) {
        if(inte != null && context != null){

            Intent intent = new Intent();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationChannel mChannel = new NotificationChannel(
                        "shop_notification", "shop notification", NotificationManager.IMPORTANCE_HIGH);
                notificationManager.createNotificationChannel(mChannel);
            }

            NotificationCompat.Builder mBuilder =
                    new NotificationCompat.Builder(context, "shop_notification")
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle("time to buy some food")
                            .setContentText("dont forget to buy food!!!!");
            mBuilder.setPriority(NotificationCompat.PRIORITY_MAX);
            mBuilder.setAutoCancel(true);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(MainActivity.class);
            stackBuilder.addNextIntent(intent);

            PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(2, PendingIntent.FLAG_UPDATE_CURRENT);
            mBuilder.setContentIntent(resultPendingIntent);

            NotificationManager mNotificationManager = (NotificationManager)
                    context.getSystemService(Context.NOTIFICATION_SERVICE);
            mNotificationManager.notify(1, mBuilder.build());

        }
    }

    public static void setAlarm(Context context,int year, int month, int day,int hours, int mins, int secs) {
        boolean alarmUp = (PendingIntent.getBroadcast(context, 0,
                new Intent(context, alarm_receiver.class),
                PendingIntent.FLAG_NO_CREATE) != null);
        if (!alarmUp) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(System.currentTimeMillis());
            calendar.set(Calendar.HOUR_OF_DAY, hours);
            calendar.set(Calendar.MINUTE, mins);
            calendar.set(Calendar.SECOND, secs);
            calendar.set(Calendar.DAY_OF_MONTH,day);
            calendar.set(Calendar.YEAR,year);
            calendar.set(Calendar.MONTH,month);

            Intent intent = new Intent(context, alarm_receiver.class);
            PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            if (alarmManager != null) {
                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
            }
        }
    }
}
