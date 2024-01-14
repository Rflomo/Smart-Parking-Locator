package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class LoggedinUsers extends AppCompatActivity {

    private RecyclerView myrecyclerView;
    private MyuserAdapter myuserAdapter;
    private DatabaseReference userdatabase;
    private ArrayList<Users> list;
    private FirebaseDatabase userDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loggedin_users);


        myrecyclerView = findViewById(R.id.userList);
        userDatabase = FirebaseDatabase.getInstance();
        userdatabase = userDatabase.getReference("User_Accounts");
        myrecyclerView.setHasFixedSize(true);
        myrecyclerView.setLayoutManager(new LinearLayoutManager(LoggedinUsers.this));

        list = new ArrayList<Users>();
        myuserAdapter = new MyuserAdapter(LoggedinUsers.this,list,userdatabase);
        myrecyclerView.setAdapter(myuserAdapter);



        userdatabase.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@org.checkerframework.checker.nullness.qual.NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Users users = dataSnapshot.getValue(Users.class);
                    list.add(users);
                }
                myuserAdapter.notifyDataSetChanged();
                Toast.makeText(LoggedinUsers.this, "Users retrieved successfully", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@org.checkerframework.checker.nullness.qual.NonNull DatabaseError error) {
                Toast.makeText(LoggedinUsers.this, "Failed to retrieve Users", Toast.LENGTH_SHORT).show();
            }
        });
    }
}