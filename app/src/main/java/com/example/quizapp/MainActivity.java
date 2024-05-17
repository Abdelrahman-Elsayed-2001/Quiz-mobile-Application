package com.example.quizapp;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {
    Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Get instance of the calendar
        Calendar calendar =Calendar.getInstance();

        //Determine The HOUR, the MINUTE and The SECOND to Push Notification
        calendar.set(Calendar.HOUR_OF_DAY,18);
        calendar.set(Calendar.MINUTE,40);
        calendar.set(Calendar.SECOND,0);

        //Intent to use BroadcastReceiver
        Intent intentReceiver = new Intent(MainActivity.this, MyNotificationReceiver.class);

        //A description of an Intent and target action to perform with it
        //the returned object can be handed to other applications so that they can perform the action you described on your behalf at a later time.
        PendingIntent pendingIntent=PendingIntent.getBroadcast(MainActivity.this,0,intentReceiver,0);

        //Make Alarm SERVICE and put AlarmManager.RTC_WAKEUP , calendar , pendingIntent
        //AlarmManager.RTC_WAKEUP : Wakes up the device to fire the pending intent at the specified time.

        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis() , pendingIntent);

        startButton = (Button) findViewById(R.id.Startbtn);
        startButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}