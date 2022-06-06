package com.pranjal.bustransit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewCitiesAdapter extends RecyclerView.Adapter<RecyclerViewCitiesAdapter.CitiesViewHolder> {
    ArrayList<CitiesModel> citiesModels;
    Context context;
    onItemClickListner listner;

    public RecyclerViewCitiesAdapter(Context context, ArrayList<CitiesModel> citiesModels) {
        this.citiesModels = citiesModels;
        this.context = context;
    }

    @NonNull
    @Override
    public CitiesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.city_cardview,parent,false);
        return new CitiesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CitiesViewHolder holder, int position) {
            String s = citiesModels.get(position).getCityName();
            holder.imageViewCities.setImageResource(citiesModels.get(position).getImage());
            holder.textViewCities.setText(s);
    }

    @Override
    public int getItemCount() {
        return citiesModels.size();
    }

    class CitiesViewHolder extends RecyclerView.ViewHolder {
        ImageView imageViewCities;
        TextView textViewCities;

        public CitiesViewHolder(@NonNull View itemView) {
            super(itemView);

            imageViewCities = itemView.findViewById(R.id.imageViewCities);
            textViewCities = itemView.findViewById(R.id.textViewCities);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(listner != null && (position != RecyclerView.NO_POSITION)){
                        listner.onItemClick(citiesModels.get(position).getCityName());
                    }
                }
            });
        }
    }

    public interface onItemClickListner{
        void onItemClick(String cityName);
    }

    public void setOnItemClickListner(onItemClickListner listner){
        this.listner = listner;
    }
}
