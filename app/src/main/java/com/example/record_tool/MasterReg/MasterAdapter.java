package com.example.record_tool.MasterReg;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.record_tool.ClientActivity;
import com.example.record_tool.R;

import java.util.ArrayList;
import java.util.List;

public  class MasterAdapter extends RecyclerView.Adapter<MasterAdapter.MasterViewHolder> {

    private List<Master> masterList;


    public MasterAdapter(List<Master> masterList) {
        this.masterList = (masterList != null) ? masterList : new ArrayList<>();
    }




    @NonNull
    @Override
    public MasterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.example_list_master, parent, false);
        return new MasterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MasterViewHolder holder, int position) {
        Master master = masterList.get(position);
        holder.bind(master);
    }

    @Override
    public int getItemCount() {
        return (masterList != null) ? masterList.size() : 0;
    }

    public static class MasterViewHolder extends RecyclerView.ViewHolder {
        private static  TextView nameTextView;
        private final TextView addressTextView;
        public MasterViewHolder(@NonNull View itemView) {


            super(itemView);

            nameTextView = itemView.findViewById(R.id.nameTextView);

            addressTextView = itemView.findViewById(R.id.addressTextView);


            itemView.setOnClickListener(v -> {
                Context context = itemView.getContext(); // Получаем контекст
                Intent intent = new Intent(context, ClientActivity.class); // Задаем целевую Activity
                context.startActivity(intent); // Запускаем Activity
            });
        }


        public static String getMasterName(){
            return nameTextView.toString();
        }

        public void bind(Master master) {
            nameTextView.setText(master.getName());
            addressTextView.setText(master.getAddress());
        }




    }
}
