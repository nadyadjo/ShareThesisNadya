package com.example.nadya.homelightcontroller;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    Button home, history, settings, about;

    private static final String Job_Tag = "my_job_tag";
    private FirebaseJobDispatcher jobDispatcher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        jobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));

        home = findViewById(R.id.home_btn);
        history = findViewById(R.id.info_btn);
        settings = findViewById(R.id.settings_btn);
        about = findViewById(R.id.about_btn);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.MINUTE, 15);
        calendar.add(Calendar.HOUR_OF_DAY, 30);

        setAlarm(calendar.getTimeInMillis());

        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Job job = jobDispatcher.newJobBuilder().
//                        setService(MyService.class).
//                        setLifetime(Lifetime.FOREVER).
//                        setRecurring(true).setTag(Job_Tag).
//                        setTrigger(Trigger.executionWindow( 10, 15)).
//                        setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL).
//                        setReplaceCurrent(false).
//                        setConstraints(Constraint.ON_ANY_NETWORK).
//                        build();
//                jobDispatcher.mustSchedule(job);
//                Toast.makeText(getApplicationContext(), "Job scheduled", Toast.LENGTH_SHORT).show();

                Intent goHome = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(goHome);
            }
        });

        history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goHistory = new Intent(MainActivity.this, HistoryActivity.class);
                startActivity(goHistory);
            }
        });

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent goSettings = new Intent(MainActivity.this, SettingsActivity.class);
                startActivity(goSettings);
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                jobDispatcher.cancel(Job_Tag);
//                Toast.makeText(getApplicationContext(), "Job cancelled", Toast.LENGTH_SHORT).show();

                Intent goAbout = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(goAbout);
            }
        });
    }

    private void setAlarm(long timeInMillis) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(this, MyAlarm.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, timeInMillis, AlarmManager.INTERVAL_DAY, pendingIntent);

        Toast.makeText(this, "Alarm set", Toast.LENGTH_SHORT).show();
    }
}
