package com.example.myproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.myproject.databinding.ActivityUserHistoryBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

public class UserHistory extends AppCompatActivity {

    ActivityUserHistoryBinding binding;
    DatabaseReference reference;
    FirebaseDatabase database = FirebaseDatabase.getInstance();




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_history);
        binding = ActivityUserHistoryBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.updatedataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(UserHistory.this,updateUserdata.class);

                i.putExtra("name",binding.tvFirstName.getText().toString());
                i.putExtra("Mobile_no",binding.tvmobile.getText().toString());
                i.putExtra("slot_number",binding.tvslotNumber.getText().toString());
                i.putExtra("current_time",binding.tvCurrenttime.getText().toString());
                i.putExtra("end_time",binding.tvendtime.getText().toString());
                i.putExtra("Vehicle_number",binding.tvVeichleNumber.getText().toString());
                startActivity(i);
            }
        });

        binding.readdataBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Mobile = binding.etusername.getText().toString();
                String slotNumber = binding.etuserslot.getText().toString();
                if (!Mobile.isEmpty() && !slotNumber.isEmpty()){

                    readData(Mobile, slotNumber);
                    binding.etusername.setText("");

                }else{

                    Toast.makeText(UserHistory.this,"PLease Enter Username",Toast.LENGTH_SHORT).show();
                }

            }
        });

    binding.UnBookBtn.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        String slotNumber = binding.etuserslot.getText().toString().trim();
        String phoneNumber = binding.etusername.getText().toString().trim();

        DatabaseReference customerRef = database.getReference("Customer");

        customerRef.child(slotNumber).child(phoneNumber).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    // Confirm the slot number and phone number
                    dataSnapshot.getRef().child("confirmed").setValue(true);
                    Toast.makeText(UserHistory.this, "Slot confirmed", Toast.LENGTH_SHORT).show();

                    // create an input dialog box to confirm unbooking
                    AlertDialog.Builder builder = new AlertDialog.Builder(UserHistory.this);
                    builder.setTitle("Confirm Unbooking");
                    builder.setMessage("Are you sure you want to unbook slot " + slotNumber + "?");

                    builder.setPositiveButton("Unbook", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // update the SlotStatus in the Firebase Realtime Database
                            DatabaseReference slotRef = FirebaseDatabase.getInstance().getReference("ParkingArea").child(slotNumber);
                            slotRef.child("slotStatus").setValue("Free");

                            Intent intent = new Intent(UserHistory.this, BookingSlotActivity.class);
                            startActivity(intent);

                        }

                    });

                    builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.show();

                } else {
                    // No matching record found
                    Toast.makeText(UserHistory.this, "Invalid slot number or phone number", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle errors here
            }
        });
    }
});


    }

    private void readData( String mobile, String slot_Number) {
        reference= FirebaseDatabase.getInstance().getReference("Customer")
                .child(slot_Number).child(mobile);
             reference.get().addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DataSnapshot> task) {

                if (task.isSuccessful()){

                    if (task.getResult().exists()){

                        DataSnapshot dataSnapshot = task.getResult();
                        String name = String.valueOf(dataSnapshot.child("name").getValue());
                        String Mobile_no = String.valueOf(dataSnapshot.child("mobile").getValue());
                        String slot_number = String.valueOf(dataSnapshot.child("slot_Number").getValue());
                        String current_time = String.valueOf(dataSnapshot.child("starting_duration").getValue());
                        String end_time = String.valueOf(dataSnapshot.child("ending_time").getValue());
                        String Vehicle_number = String.valueOf(dataSnapshot.child("veichle_number").getValue());

                        binding.tvFirstName.setText(name);
                        binding.tvmobile.setText(Mobile_no);
                        binding.tvslotNumber.setText(slot_number);
                        binding.tvCurrenttime.setText(current_time);
                        binding.tvendtime.setText(end_time);
                        binding.tvVeichleNumber.setText(Vehicle_number);
                        Toast.makeText(UserHistory.this,"Successfully Read",Toast.LENGTH_SHORT).show();
                    }else {

                        Toast.makeText(UserHistory.this,"User Doesn't Exist",Toast.LENGTH_SHORT).show();

                    }


                }else {

                    Toast.makeText(UserHistory.this,"Failed to read",Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
    }