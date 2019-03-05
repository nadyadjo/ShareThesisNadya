package com.example.nadya.homelightcontroller;

import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class SetTimerActivity extends AppCompatActivity {

//    FirebaseDatabase database = FirebaseDatabase.getInstance();
//    DatabaseReference myRef = database.getReference();

    DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
    DatabaseReference myRef= mRef.child("lampu");

    final DatabaseReference timerOnHour = myRef.child("lamp1").child("onHour");
    final DatabaseReference timerOnMin = myRef.child("lamp1").child("onMin");
    final DatabaseReference timerOffHour = myRef.child("lamp1").child("offHour");
    final DatabaseReference timerOffMin = myRef.child("lamp1").child("offMin");
    final DatabaseReference timerStatus = myRef.child("lamp1").child("timerStatus");

    EditText timeOn, timeOff;
    Button setTimerOnOff, resetTimer;
    private int onHour, onMin, offHour, offMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_timer);

        timeOn = findViewById(R.id.et_setTimerOn);
        timeOff = findViewById(R.id.et_setTimerOff);
        setTimerOnOff = findViewById(R.id.btn_setTimer);
        resetTimer = findViewById(R.id.btn_resetTimer);

        timeOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                onHour = c.get(Calendar.HOUR_OF_DAY);
                onMin = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(SetTimerActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        timeOn.setText(convertDate(hour) + ":" + convertDate(minute));
                    }
                }, onHour, onMin, true);
                timePickerDialog.show();
            }
        });

        timeOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                offHour = c.get(Calendar.HOUR_OF_DAY);
                offMin = c.get(Calendar.MINUTE);

                TimePickerDialog timePickerDialog = new TimePickerDialog(SetTimerActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int hour, int minute) {
                        timeOff.setText(convertDate(hour) + ":" + convertDate(minute));
                    }
                }, offHour, offMin, true);
                timePickerDialog.show();
            }
        });

        setTimerOnOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(TextUtils.isEmpty(timeOff.getText()) && !TextUtils.isEmpty(timeOn.getText())){
                    String valueonhour = timeOn.getText().toString().substring(0,2);
                    int val1 = Integer.parseInt(valueonhour);
                    timerOnHour.setValue(convertDate(val1));
                    String valueonmin = timeOn.getText().toString().substring(3);
                    int val2 = Integer.parseInt(valueonmin);
                    timerOnMin.setValue(convertDate(val2));

//                    String valueoffhour = "00";
//                    int val3 = Integer.parseInt(valueoffhour);
//                    timerOffHour.setValue(convertDate(val3));
//                    String valueoffmin = "00";
//                    int val4 = Integer.parseInt(valueoffmin);
//                    timerOffMin.setValue(convertDate(val4));
                    timerStatus.setValue("ON");
                    Toast.makeText(SetTimerActivity.this, "TIMER ON DIPASANG", Toast.LENGTH_SHORT).show();
                }
                else if(!TextUtils.isEmpty(timeOff.getText()) && TextUtils.isEmpty(timeOn.getText())){
                    String valueoffhour = timeOff.getText().toString().substring(0,2);
                    int val1 = Integer.parseInt(valueoffhour);
                    timerOffHour.setValue(convertDate(val1));
                    String valueoffmin = timeOff.getText().toString().substring(3);
                    int val2 = Integer.parseInt(valueoffmin);
                    timerOffMin.setValue(convertDate(val2));

//                    String valueonhour = "00";
//                    int val3 = Integer.parseInt(valueonhour);
//                    timerOffHour.setValue(convertDate(val3));
//                    String valueonmin = "00";
//                    int val4 = Integer.parseInt(valueonmin);
//                    timerOffMin.setValue(convertDate(val4));
                    timerStatus.setValue("ON");
                    Toast.makeText(SetTimerActivity.this, "TIMER OFF DIPASANG", Toast.LENGTH_SHORT).show();
                }
                else if(!TextUtils.isEmpty(timeOn.getText()) && !TextUtils.isEmpty(timeOff.getText())){
                    String valueonhour = timeOn.getText().toString().substring(0,2);
                    int val1 = Integer.parseInt(valueonhour);
                    timerOnHour.setValue(convertDate(val1));
                    String valueonmin = timeOn.getText().toString().substring(3);
                    int val2 = Integer.parseInt(valueonmin);
                    timerOnMin.setValue(convertDate(val2));
                    String valueoffhour = timeOff.getText().toString().substring(0,2);
                    int val3 = Integer.parseInt(valueoffhour);
                    timerOffHour.setValue(convertDate(val3));
                    String valueoffmin = timeOff.getText().toString().substring(3);
                    int val4 = Integer.parseInt(valueoffmin);
                    timerOffMin.setValue(convertDate(val4));
                    timerStatus.setValue("ON");
                    Toast.makeText(SetTimerActivity.this, "TIMER DIPASANG", Toast.LENGTH_SHORT).show();
                }
            }
        });

        resetTimer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String valueonhour = "00";
                int val1 = Integer.parseInt(valueonhour);
                timerOnHour.setValue(convertDate(val1));
                String valueonmin = "00";
                int val2 = Integer.parseInt(valueonmin);
                timerOnMin.setValue(convertDate(val2));
                String valueoffhour = "00";
                int val3 = Integer.parseInt(valueoffhour);
                timerOffHour.setValue(convertDate(val3));
                String valueoffmin = "00";
                int val4 = Integer.parseInt(valueoffmin);
                timerOffMin.setValue(convertDate(val4));
                timerStatus.setValue("OFF");
                Toast.makeText(SetTimerActivity.this, "TIMER DIMATIKAN", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public String convertDate(long input) {
        if (input >= 10) {
            return String.valueOf(input);
        } else {
            return "0" + String.valueOf(input);
        }
    }
}
