package com.example.nadya.homelightcontroller;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private long monthDurationArr[]=new long[4];
    private int wattArr[]= new int[4];
    private long kwhArr[]=new long[4];
    private long totalKWH;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference historyRef= mRef.child("history");

        final Spinner spinner = findViewById(R.id.spinner_month);
        final LinearLayout layoutkwh = findViewById(R.id.tv_kwh);

//
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.month, R.layout.support_simple_spinner_dropdown_item);
//        spinner.setAdapter(adapter);
        final String[] items = new String[] {"january", "february", "march"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, items);
        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String month = String.valueOf(spinner.getItemAtPosition(i));
                if(!month.isEmpty()){
                    //complete
                    DatabaseReference finesRef = FirebaseDatabase.getInstance().getReference().child("history");
                    finesRef.orderByChild("month").equalTo(month).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            //check complete
                            if(dataSnapshot.exists()){
                                for(int i=0;i<monthDurationArr.length;i++){
                                    monthDurationArr[i]=0;
                                }

                                for(DataSnapshot data: dataSnapshot.getChildren()) {
                                    Log.d("month existence", data.toString());
                                    History history = data.getValue(History.class);
                                    monthDurationArr[0] +=history.getDuration1();
                                    monthDurationArr[1] +=history.getDuration2();
                                    monthDurationArr[2] +=history.getDuration3();
                                    monthDurationArr[3] +=history.getDuration4();
                                }

                                Log.d("history", dataSnapshot.toString()) ;

                                BarChart barChart = (BarChart) findViewById(R.id.barchart);
                                ArrayList<BarEntry> entries = new ArrayList<>();
                                entries.add(new BarEntry(monthDurationArr[0], 0));
                                entries.add(new BarEntry(monthDurationArr[1], 1));
                                entries.add(new BarEntry(monthDurationArr[2], 2));
                                entries.add(new BarEntry(monthDurationArr[3], 3));


                                BarDataSet bardataset = new BarDataSet(entries, "Duration in hour");

                                ArrayList<String> labels = new ArrayList<String>();
                                labels.add("Lamp1");
                                labels.add("lamp2");
                                labels.add("Lamp3");
                                labels.add("Lamp4");

                                BarData data = new BarData(labels, bardataset);
                                barChart.setData(data); // set the data and list of lables into chart
                                barChart.invalidate();

                                barChart.setDescription("Time usage in a month");  // set the description

                                bardataset.setColors(ColorTemplate.COLORFUL_COLORS);

                                barChart.animateY(3000);
                                layoutkwh.setVisibility(View.VISIBLE);
                                getWatt();

                            } else {
                                //Toast toast = Toast.makeText(getApplicationContext(), "The Complete report data is not exist " , Toast.LENGTH_SHORT); toast.show();
                                BarChart barChart = (BarChart) findViewById(R.id.barchart);
                                barChart.clear();
                                layoutkwh.setVisibility(View.INVISIBLE);
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError)  {
                            Toast toast = Toast.makeText(getApplicationContext(), "Error on retrieveing data " , Toast.LENGTH_SHORT); toast.show();
                        }
                    });
                }else{
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void getWatt(){
        DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference lampuRef= mRef.child("lampu");

        //get value from database
        lampuRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {

                    for(DataSnapshot data: dataSnapshot.getChildren()){
                        Lampu lampu= data.getValue(Lampu.class);

                        String jenisLampu=data.getKey();

                        switch (jenisLampu) {
                            case "lamp1":  wattArr[0] =lampu.getWatt();
                                break;
                            case "lamp2":  wattArr[1] =lampu.getWatt();
                                break;
                            case "lamp3":  wattArr[2]=lampu.getWatt();
                                break;
                            case "lamp4":  wattArr[3] =lampu.getWatt();
                                break;
                        }
                    }
                    calculateTotalKWH();
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void calculateTotalKWH(){
        for(int i=0;i<kwhArr.length;i++){
            kwhArr[i]=0;
        }
        totalKWH=0;
        for(int i=0;i<wattArr.length;i++){
            kwhArr[i]=monthDurationArr[i]*wattArr[0];
            totalKWH+=kwhArr[i];
        }

        TextView tvtotalKWH=findViewById(R.id.totalKWH);
        tvtotalKWH.setText(Long.toString(totalKWH));

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
