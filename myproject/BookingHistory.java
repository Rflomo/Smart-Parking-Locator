package com.example.myproject;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;

import java.util.ArrayList;
import java.util.List;

public class BookingHistory extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;
    private DatabaseReference database;
    private ArrayList<User> list;
    private FirebaseDatabase mDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_history);


        recyclerView = findViewById(R.id.user_list);
        mDatabase = FirebaseDatabase.getInstance();
        database = mDatabase.getReference("Customer");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(BookingHistory.this));


        list = new ArrayList<User>();
        myAdapter = new MyAdapter(this,list);
        recyclerView.setAdapter(myAdapter);

        database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Retrieve the slot_Number from the snapshot key
                String slotNumber = snapshot.getKey();

                // Retrieve all children nodes under the slot_Number using another database reference
                DatabaseReference slotRef = database.child(slotNumber);
                slotRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {

                            User user = dataSnapshot.getValue(User.class);

                            // Add the User object to your list
                            list.add(user);

                            // Notify your adapter of the changes
                            myAdapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(BookingHistory.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Handle changes to the data
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {
                // Handle removed data
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                // Handle moved data
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(BookingHistory.this, "Failed to retrieve data", Toast.LENGTH_SHORT).show();
            }
        });

        }
    }