package com.example.nadya.homelightcontroller;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;

public class MyAlarm extends BroadcastReceiver{

    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference lampu= mRef.child("lampu");
    DatabaseReference history = mRef.child("history");

    final DatabaseReference durationlamp1 = lampu.child("lamp1").child("durationOn");
    final DatabaseReference historyduration1 = history.child(String.valueOf(ServerValue.TIMESTAMP));

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("file","UPLOAD TO FIREBASE");

        durationlamp1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Long value = dataSnapshot.getValue(Long.class);
                Log.d("file", "Value is: " + value);
                historyduration1.child("duration1").setValue(value);
                durationlamp1.setValue(0);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}
