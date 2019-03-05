package com.example.nadya.homelightcontroller;

import android.app.AlarmManager;
import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;

public class MyService extends com.firebase.jobdispatcher.JobService{

    BackgroundTask backgroundTask;
    long startTime, endTime, duration, durationOn1, durasinyala;
    int getDateOn,getDateOff,getDateNow;

    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference myRef= mRef.child("lampu");

    final DatabaseReference durationLamp1 = myRef.child("lamp1").child("durationOn");

    @Override
    public boolean onStartJob(@NonNull final com.firebase.jobdispatcher.JobParameters job) {
//        backgroundTask = new BackgroundTask(){
//            @Override
//            protected void onPostExecute(String s) {
//
//                Toast.makeText(getApplicationContext(), "Msg : " + s, Toast.LENGTH_LONG).show();
//                jobFinished(job, false); //reschedule the same job again-true, no-false
//            }
//        };
//
//        backgroundTask.execute();
////
////        startTime = Calendar.getInstance().getTimeInMillis();
////        getDateOn = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        return true;
    }

    @Override
    public boolean onStopJob(@NonNull com.firebase.jobdispatcher.JobParameters job) {
        endTime = Calendar.getInstance().getTimeInMillis();
        getDateOff = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        duration = endTime-startTime;

        durationLamp1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long val = dataSnapshot.getValue(Long.class);
                Log.d("file", "val : " + val);
                durationOn1 = val;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("file", "val error");
            }
        });

        durasinyala = durationOn1 + duration;

        return false; //true- retry the job if fail
    }

    public void setAlarm(long alarm) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
    }

    public class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        @Override
        public String doInBackground(Void... voids) {
            return "hello from background";
        }
    }
}
