package com.pranjal.bustransit;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference = database.getReference().child("cities");
    RecyclerView recyclerViewCities;
    ArrayList<Integer> images = new ArrayList<>();
    ArrayList<String> cities = new ArrayList<>();
    ArrayList<CitiesModel> cityList = new ArrayList<>();
    ShimmerFrameLayout container;
    RecyclerViewCitiesAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Objects.requireNonNull(getSupportActionBar()).setTitle(R.string.select_city);
        setContentView(R.layout.activity_main);

        recyclerViewCities = findViewById(R.id.recyclerViewCities);
        container = findViewById(R.id.shimmerEffect);
        container.startShimmer();

        images.add(R.drawable.bangalore);
        images.add(R.drawable.delhi);
        images.add(R.drawable.mumbai);
        images.add(R.drawable.bangalore);
        images.add(R.drawable.delhi);
        images.add(R.drawable.mumbai);
        images.add(R.drawable.bangalore);
        images.add(R.drawable.delhi);
        images.add(R.drawable.mumbai);



            reference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    int i=0;
                    for (DataSnapshot dsp : snapshot.getChildren()) {
                        cities.add(String.valueOf(dsp.getKey()));
                        cityList.add(new CitiesModel(images.get(i),String.valueOf(dsp.getKey())));
                        i++;
                    }

                    recyclerViewCities.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    adapter = new RecyclerViewCitiesAdapter(MainActivity.this,cityList);
                    recyclerViewCities.setAdapter(adapter);
                    container.stopShimmer();
                    container.setVisibility(View.GONE);
                    recyclerViewCities.setVisibility(View.VISIBLE);

                    adapter.setOnItemClickListner(new RecyclerViewCitiesAdapter.onItemClickListner() {
                        @Override
                        public void onItemClick(String cityName) {
                            Intent intent = new Intent(MainActivity.this,WorkActivity.class);
                            if(cityName != null){
                                intent.putExtra("cityName",cityName);
                                startActivity(intent);
                            }else{
                                Toast.makeText(MainActivity.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
    }
}