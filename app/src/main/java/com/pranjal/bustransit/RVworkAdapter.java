package com.pranjal.bustransit;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RVworkAdapter extends RecyclerView.Adapter<RVworkAdapter.RVworkViewHolder> {
    ArrayList<String> listItem;
    Context context;

    public RVworkAdapter(ArrayList<String> listItem, Context context) {
        this.listItem = listItem;
        this.context = context;
    }

    @NonNull
    @Override
    public RVworkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.work_cardview,parent,false);
        return new RVworkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RVworkViewHolder holder, int position) {
        holder.textViewWork.setText(listItem.get(position));

    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    class RVworkViewHolder extends RecyclerView.ViewHolder {
        TextView textViewWork;

        public RVworkViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewWork = itemView.findViewById(R.id.textViewWork);
        }
    }
}
