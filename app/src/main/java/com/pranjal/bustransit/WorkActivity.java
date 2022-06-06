package com.pranjal.bustransit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class WorkActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    TextView textView,textViewRoute,textViewHeading;
    int routePosition;
    int i = 0;
    String[] allStops = new String[30];
    ArrayList<String> stops = new ArrayList<>();
    String [] routs = new String[3];
    String cityName,from,to,selectedRoutNo,secondRoute;
    RecyclerView recyclerView;
    Spinner spinner,spinnerFrom,spinnerTo;

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference,referenceStops;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work);



        textView = findViewById(R.id.textView);
        textViewHeading = findViewById(R.id.textViewHeading);
        textViewRoute = findViewById(R.id.textViewRoute);
        recyclerView = findViewById(R.id.recyclerViewWork);
        spinner = findViewById(R.id.spinner);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);

        spinner.setOnItemSelectedListener(WorkActivity.this);
        spinnerFrom.setOnItemSelectedListener(WorkActivity.this);
        spinnerTo.setOnItemSelectedListener(WorkActivity.this);




        ArrayAdapter arrayAdapterFrom
                    = new ArrayAdapter(WorkActivity.this, android.R.layout.simple_spinner_item,allStops );
        arrayAdapterFrom.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        if(arrayAdapterFrom != null && allStops != null){
            spinnerFrom.setAdapter(arrayAdapterFrom);
            spinnerTo.setAdapter(arrayAdapterFrom);
        }else{
            Toast.makeText(WorkActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
        }


        if(routs != null){

            ArrayAdapter arrayAdapter
                    = new ArrayAdapter(WorkActivity.this, android.R.layout.simple_spinner_item, routs);
            arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(arrayAdapter);
        }else{
            Toast.makeText(WorkActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
        }



            Intent intent = getIntent();
            cityName = intent.getStringExtra("cityName");
        if(cityName != null){
            Objects.requireNonNull(getSupportActionBar()).setTitle(cityName);
        }else{
            Toast.makeText(WorkActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
        }


        recyclerView.setLayoutManager(new LinearLayoutManager(WorkActivity.this));

            reference = database.getReference().child("cities").child(cityName);
            reference.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int i = 0;
                    for (DataSnapshot dsp : snapshot.getChildren()) {
                        routs[i] = String.valueOf(dsp.getKey());
                        i++;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Toast.makeText(WorkActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            getAllStops();


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

        switch(parent.getId()) {

            case R.id.spinnerFrom:
                from = allStops[position];
                selectedRoutNo = checkRout(position);
                break;

            case R.id.spinnerTo:
                to = allStops[position];
                secondRoute = checkRout(position);

                textViewHeading.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.INVISIBLE);
                textViewRoute.setVisibility(View.VISIBLE);

                if (selectedRoutNo.equals(secondRoute)) {
                    textViewRoute.setText("You should select " + selectedRoutNo);
                } else {
                    textViewRoute.setText("You should select " + selectedRoutNo + " then change to " + secondRoute);
                }
                break;


            case R.id.spinner:
                referenceStops = database.getReference().child("cities").child(cityName).child(routs[position]);
                routePosition = position;
                    try{

                        referenceStops.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for (DataSnapshot dsp : snapshot.getChildren()) {
                                    stops.add(String.valueOf(dsp.getValue()));
                                }

                                textViewHeading.setVisibility(View.VISIBLE);
                                recyclerView.setVisibility(View.VISIBLE);
                                textViewRoute.setVisibility(View.INVISIBLE);

                                textViewHeading.setText("Stops in route "+routs[position]);
                                try{
                                    RVworkAdapter adapter = new RVworkAdapter(stops, WorkActivity.this);
                                    recyclerView.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                    stops = new ArrayList<>();
                                }catch (Exception e){
                                    Log.d("stop","error in stops");
                                }



                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(WorkActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }catch (Exception e){
                        Toast.makeText(WorkActivity.this, e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                break;

        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Toast.makeText(WorkActivity.this, "Select a stop", Toast.LENGTH_SHORT).show();
    }

    public void getAllStops(){

            reference.child("r1").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dsp : snapshot.getChildren()) {
                        allStops[i] = String.valueOf(dsp.getValue());
                        i++;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Toast.makeText(WorkActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

            reference.child("r2").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dsp : snapshot.getChildren()) {
                        allStops[i] = String.valueOf(dsp.getValue());
                        i++;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Toast.makeText(WorkActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            reference.child("r3").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    for (DataSnapshot dsp : snapshot.getChildren()) {
                        allStops[i] = String.valueOf(dsp.getValue());
                        i++;
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                    Toast.makeText(WorkActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

    }

    public String checkRout(int position){

            if(position<10){
                return "r1";
            }

        if(position<20){
            return "r2";
        }

        if(position<30){
            return "r3";
        }
        return "No route found";
    }
}