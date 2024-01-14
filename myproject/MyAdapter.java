package com.example.myproject;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.myViewHolder> {

    private Context context;
    private List<User> list;

    public MyAdapter( Context context, List<User> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item,parent,false);
        return new myViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull myViewHolder holder, int position) {
        User user = list.get(position);
        holder.endTime.setText("End Time: "+user.getEnding_time().toString());
        holder.mobile.setText("Mobile: "+user.getMobile());
        holder.name.setText("first Name: "+user.getName());
        holder.slotNum.setText("Slot Number: "+user.getSlot_Number());
        holder.startTime.setText("Current Time: "+user.getStarting_duration());
        holder.vecNumber.setText("Veichle Number: "+user.getVeichle_number());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

 public static class myViewHolder extends RecyclerView.ViewHolder{

    TextView name,mobile,vecNumber,slotNum,startTime,endTime;


    public myViewHolder(@NonNull View itemView) {
        super(itemView);
        name=itemView.findViewById(R.id.tv_Name);
        vecNumber=itemView.findViewById(R.id.tv_vecNumber);
        mobile=itemView.findViewById(R.id.tv_NumMobile);
        slotNum=itemView.findViewById(R.id.tv_slotNum);
        startTime=itemView.findViewById(R.id.tv_StartTime);
        endTime=itemView.findViewById(R.id.tv_EndTime);


    }
 }
}