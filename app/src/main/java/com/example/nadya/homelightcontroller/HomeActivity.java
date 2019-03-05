package com.example.nadya.homelightcontroller;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.SystemClock;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.TextView;
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
import java.util.Date;

public class HomeActivity extends AppCompatActivity {
    
    GridLayout mainGrid;
    TextView tvTeras, tvBedroom, tvLiving, tvCloset;

    EditText dimLamp2, dimLamp3;

    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference myRef= mRef.child("lampu");

//    FirebaseDatabase database = FirebaseDatabase.getInstance().getReference();
//    DatabaseReference myRef = database.child("lampu");

    final DatabaseReference statusLamp1 = myRef.child("lamp1").child("status");
    final DatabaseReference statusLamp2 = myRef.child("lamp2").child("status");
    final DatabaseReference statusLamp3 = myRef.child("lamp3").child("status");
    final DatabaseReference statusLamp4 = myRef.child("lamp4").child("status");

    final DatabaseReference dimmerLamp2 = myRef.child("lamp2").child("dimmer");
    final DatabaseReference dimmerLamp3 = myRef.child("lamp3").child("dimmer");

    final DatabaseReference pirStatus = myRef.child("lamp4").child("pirStatus");

    final DatabaseReference durationLamp1 = myRef.child("lamp1").child("durationOn");
    final DatabaseReference durationLamp2 = myRef.child("lamp2").child("durationOn");
    final DatabaseReference durationLamp3 = myRef.child("lamp3").child("durationOn");
    final DatabaseReference durationLamp4 = myRef.child("lamp4").child("durationOn");

    private static final String Job_Tag = "my_job_tag";
    private FirebaseJobDispatcher jobDispatcher;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        jobDispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(this));
        
        mainGrid = findViewById(R.id.mainGrid);
        tvTeras = findViewById(R.id.tv_teras);
        tvBedroom = findViewById(R.id.tv_bed);
        tvLiving = findViewById(R.id.tv_living);
        tvCloset = findViewById(R.id.tv_closet);

        setSingleEvent(mainGrid);

        statusLamp1.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d("file", "Value is: " + value);
                tvTeras.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("file", "Failed to read value.", databaseError.toException());
            }
        });

        statusLamp2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d("file", "Value is: " + value);
                tvBedroom.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("file", "Failed to read value.", databaseError.toException());
            }
        });

        statusLamp3.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d("file", "Value is: " + value);
                tvLiving.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("file", "Failed to read value.", databaseError.toException());
            }
        });

        statusLamp4.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue(String.class);
                Log.d("file", "Value is: " + value);
                tvCloset.setText(value);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w("file", "Failed to read value.", databaseError.toException());
            }
        });

//        dimmerLamp2.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Long value = dataSnapshot.getValue(Long.class);
//                Log.d("file", "Value is: " + value);
//                dimLamp2.setText(value.toString());
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                Log.w("file", "Failed to read value.", databaseError.toException());
//            }
//        });

    }

    int click = 0;
    long startTime, endTime, timeInterval, durationOn, durationOn1;
//    Date startTime, endTime;
//    long timeInterval, durationOn, durationOn1;

    private void setSingleEvent(GridLayout mainGrid) {
        //Loop all child item on Main Grid
        for(int i=0;i<mainGrid.getChildCount();i++){
            final CardView cardView = (CardView) mainGrid.getChildAt(i);
            final int finalI = i;

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    click++;
//                    startTime = 0;
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(click == 1){
                                if(finalI == 0) {
                                    statusLamp1.setValue("OFF");
//                                    endTime = System.currentTimeMillis();
//                                    endTime = Calendar.getInstance().getTimeInMillis();
//                                    Log.d ("file", "endTime: " + endTime);
//                                    timeInterval = (endTime - startTime) / 1000;
//                                    Log.d ("file", "interval: " + timeInterval);
//                                    startTime = 0;
                                    Job job = jobDispatcher.newJobBuilder().
                                            setService(MyService.class).
                                            setLifetime(Lifetime.FOREVER).
                                            setRecurring(true).setTag(Job_Tag).
//                                            setTrigger(Trigger.executionWindow( 10, 15)).
                                            setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL).
                                            setReplaceCurrent(false).
                                            setConstraints(Constraint.ON_ANY_NETWORK).
                                            build();
                                    jobDispatcher.mustSchedule(job);
                                    Toast.makeText(getApplicationContext(), "Job scheduled", Toast.LENGTH_SHORT).show();

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

                                    durationOn = (durationOn1 + timeInterval);
                                    durationLamp1.setValue(durationOn);

                                }
                                if(finalI == 1){
                                    statusLamp2.setValue("OFF");
                                }
                                if(finalI == 2){
                                    statusLamp3.setValue("OFF");
                                }
                                if(finalI == 3){
//                                    AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
//                                    alertDialog.setTitle("Reminder");
//                                    alertDialog.setMessage("This room is using PIR Motion Detection. The light can't be controlled mannually");
//                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialogInterface, int i) {
//                                            dialogInterface.dismiss();
//                                        }
//                                    });
//                                    alertDialog.show();
                                    pirStatus.setValue("OFF");
                                }
                            }
                            else if(click == 2){
                                if(finalI == 0) {
//                                    startTime = 0;
                                    statusLamp1.setValue("ON");
//                                    startTime = System.currentTimeMillis();
                                    startTime = Calendar.getInstance().getTimeInMillis();
                                    Log.d ("file", "startTime: " + startTime);
                                }
                                if(finalI == 1){
                                    statusLamp2.setValue("ON");
                                    dimmerLamp2.setValue(100);
                                }
                                if(finalI == 2){
                                    statusLamp3.setValue("ON");
                                    dimmerLamp3.setValue(100);
                                }
                                if(finalI == 3){
//                                    AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
//                                    alertDialog.setTitle("Reminder");
//                                    alertDialog.setMessage("This room is using PIR Motion Detection. The light can't be controlled mannually");
//                                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
//                                        @Override
//                                        public void onClick(DialogInterface dialogInterface, int i) {
//                                            dialogInterface.dismiss();
//                                        }
//                                    });
//                                    alertDialog.show();
                                    pirStatus.setValue("ON");
                                }
                            }
                            click = 0;
                        }
                    }, 500);
                }
            });

            cardView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if(finalI == 0){
                        Intent goTimer = new Intent(HomeActivity.this, SetTimerActivity.class);
                        startActivity(goTimer);
                    }
                    if(finalI == 1){
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
                        alertDialog.setTitle("Dimmer");
                        alertDialog.setMessage("Please input the dimmer value : % ");
                        dimLamp2 = new EditText(HomeActivity.this);
                        alertDialog.setView(dimLamp2);

                        dimmerLamp2.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Long value = dataSnapshot.getValue(Long.class);
                                Log.d("file", "Value is: " + value);
                                dimLamp2.setText(value.toString());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.w("file", "Failed to read value.", databaseError.toException());
                            }
                        });

                        alertDialog.setPositiveButton("SET", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String value1 = dimLamp2.getText().toString();
                                if(dimLamp2.length() == 0){
                                    dimLamp2.setFocusable(true);
                                    dimLamp2.setError("Dimmer is required!");
                                    dimLamp2.requestFocus();
                                    Log.d("file","Error");
                                }else {
                                    long val1 = Long.parseLong(value1);
                                    dimmerLamp2.setValue(val1);
                                    dialogInterface.dismiss();
                                }
                            }
                        });
                        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        alertDialog.create().show();
                    }
                    if(finalI == 2){
                        AlertDialog.Builder alertDialog = new AlertDialog.Builder(HomeActivity.this);
                        alertDialog.setTitle("Dimmer");
                        alertDialog.setMessage("Please input the dimmer value : % ");
                        dimLamp3 = new EditText(HomeActivity.this);
                        alertDialog.setView(dimLamp3);

                        dimmerLamp3.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Long value = dataSnapshot.getValue(Long.class);
                                Log.d("file", "Value is: " + value);
                                dimLamp3.setText(value.toString());
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {
                                Log.w("file", "Failed to read value.", databaseError.toException());
                            }
                        });

                        alertDialog.setPositiveButton("SET", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String value1 = dimLamp3.getText().toString();
                                if(dimLamp3.length() == 0){
                                    dimLamp3.setFocusable(true);
                                    dimLamp3.setError("Dimmer is required!");
                                    dimLamp3.requestFocus();
                                    Log.d("file","Error");
                                }else {
                                long val1 = Long.parseLong(value1);
                                dimmerLamp3.setValue(val1);
                                dialogInterface.dismiss();
                                }
                            }
                        });
                        alertDialog.setNegativeButton("CANCEL", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        alertDialog.create().show();
                    }
                    if(finalI == 3){
                        AlertDialog alertDialog = new AlertDialog.Builder(HomeActivity.this).create();
                        alertDialog.setTitle("Reminder");
                        alertDialog.setMessage("This room is using PIR Motion Detection. Double tap to turn on the sensor and one tap to turn off the sensor.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        alertDialog.show();
                    }
                    return true;
                }
            });
        }

    }
}
