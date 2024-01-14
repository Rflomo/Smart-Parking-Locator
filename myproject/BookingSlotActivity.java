package com.example.myproject;

import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;

public class BookingSlotActivity extends AppCompatActivity{

    private SlotAdapter mAdapter;
    private ArrayList<Slot> slotList;
    private RecyclerView recyclerView;
    private DatabaseReference parkingAreaRef;
    private FirebaseDatabase mRefDatabase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_slot);

        mRefDatabase = FirebaseDatabase.getInstance();
        parkingAreaRef = mRefDatabase.getReference("ParkingArea");
        recyclerView = findViewById(R.id.slot_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(BookingSlotActivity.this));


        slotList = new ArrayList<Slot>();
        mAdapter = new SlotAdapter(slotList, this, parkingAreaRef);
        //mAdapter = new SlotAdapter(new ArrayList<Slot>(), parkingAreaRef);
        recyclerView.setAdapter(mAdapter);


        parkingAreaRef.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                slotList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Slot slot = dataSnapshot.getValue(Slot.class);
                    slotList.add(slot);
                }
                mAdapter.notifyDataSetChanged();
                Toast.makeText(BookingSlotActivity.this, "Slots retrieved successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BookingSlotActivity.this, "Failed to retrieve Slots", Toast.LENGTH_SHORT).show();
            }
        });

    }
}