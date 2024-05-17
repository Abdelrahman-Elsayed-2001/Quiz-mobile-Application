package com.example.quizapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

public class MyNotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        //create Notification and set Notification's Properties.
        NotificationCompat.Builder builder =new NotificationCompat.Builder(context,"notifyLemubit")
                //set Notification Icon
                .setSmallIcon(R.drawable.ic_baseline_self_improvement_24)
                //set Notification Title
                .setContentTitle("Quiz")
                //set Notification Description
                .setContentText("Questions are waiting to be answered!")
                .setPriority(NotificationCompat.PRIORITY_MAX);
        NotificationManagerCompat notificationManagerCompat = NotificationManagerCompat.from(context);
        notificationManagerCompat.notify(200,builder.build());
    }
}