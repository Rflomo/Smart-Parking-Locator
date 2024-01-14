package com.example.myproject;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SlotAdapter extends RecyclerView.Adapter<SlotAdapter.SlotViewHolder> {

    private List<Slot> slotList;
    private Context context;
    private DatabaseReference databaseReference;



    public SlotAdapter(List<Slot> slotList, Context context, DatabaseReference databaseReference) {
        this.slotList = slotList;
        this.context = context;
        this.databaseReference = databaseReference;
    }


    @NonNull
    @Override
    public SlotViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.slot_item, parent, false);

        return new SlotViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SlotViewHolder holder, int position) {
        Slot slot = slotList.get(position);

        holder.slotNumberTextView.setText("Slot: " + slot.getSlotNumber());
        holder.slotStatusTextView.setText("Status: "+ slot.getSlotStatus());

        // Disable booking button if slot is already booked
        if (slot.getSlotStatus().equals("Booked")) {

            holder.bookButton.setEnabled(false);
            holder.slotStatusTextView.setText("Booked");
            //holder.bookButton.setEnabled(false);
            //holder.bookButton.setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        } else {
            holder.bookButton.setEnabled(true);
        }


        holder.locationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),GPSMapActivity.class);
                view.getContext().startActivity(intent);
            }
        });

        holder.bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Book the slot
                //databaseReference.child("slot "+slot.getSlotStatus()).setValue("Booked");
                Map<String, Object> updates = new HashMap<>();
                updates.put("slotStatus", "Booked");

                // update the child with the HashMap of updates
                databaseReference.child(slot.getSlotNumber()).updateChildren(updates);

                // start the DetailActivity
                Intent intenty = new Intent(view.getContext(), booking_details.class);
                intenty.putExtra("slotNumber", slot.getSlotNumber());
                view.getContext().startActivity(intenty);

            }
        });
    }

    @Override
    public int getItemCount() {
        return slotList.size();
    }

    public static class SlotViewHolder extends RecyclerView.ViewHolder {
        TextView slotNumberTextView;
        TextView slotStatusTextView;
        Button bookButton, locationbtn;

        public SlotViewHolder(@NonNull View itemView) {
            super(itemView);

            slotNumberTextView = itemView.findViewById(R.id.slot_number_text_view);
            slotStatusTextView = itemView.findViewById(R.id.slot_status_text_view);
            bookButton = itemView.findViewById(R.id.book_button);
            locationbtn = itemView.findViewById(R.id.location_button);


        }
    }
}
