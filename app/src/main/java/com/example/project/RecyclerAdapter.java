package com.example.project;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private ArrayList<RecyclerItem> itemList;

    @NonNull
    @Override
    public RecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapter.ViewHolder holder, int position) {
        holder.onBind(itemList.get(position));

        if (position%2 == 0) {
            holder.itemView.setBackgroundColor(Color.parseColor("#F5F5F5"));
        }
        else {
            holder.itemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

        if (holder.date.getText().toString().startsWith("Price")) {
            holder.value.setTextColor(Color.parseColor("#DC143C"));
        }
        else {
            holder.value.setTextColor(Color.parseColor("#228B22"));
        }
    }

    public void setItemList(ArrayList<RecyclerItem> list){
        this.itemList = list;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        ImageView profile;
        TextView date;
        TextView value;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            profile = (ImageView) itemView.findViewById(R.id.recycler_icon);
            date = (TextView) itemView.findViewById(R.id.recycler_date);
            value = (TextView) itemView.findViewById(R.id.recycler_value);


        }

        void onBind(RecyclerItem item){
            profile.setImageResource(item.getResourceId());
            date.setText(item.getDate());
            value.setText(item.getValue());
        }



        }
}