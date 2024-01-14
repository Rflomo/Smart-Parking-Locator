package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.stripe.model.Customer;

public class AddSlotActivity extends AppCompatActivity {


    EditText slotNumber,slotStatus ;
    Button save_slots;
    FirebaseDatabase slotfirebaseDatabase;
    DatabaseReference slotdatabaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_slot);

        slotfirebaseDatabase = FirebaseDatabase.getInstance();
        slotdatabaseReference = slotfirebaseDatabase.getReference(ParkingArea.class.getSimpleName());

        slotNumber = findViewById(R.id.tv_slotNumber);
        slotStatus= findViewById(R.id.tv_slotStatus);
        save_slots = findViewById(R.id.tv_slot_btn);

        save_slots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Slot slot = new Slot(slotNumber.getText().toString(),
                        slotStatus.getText().toString()
                );

                addslot(slot).addOnSuccessListener(suc -> {

                    Toast.makeText(AddSlotActivity.this, "slots Added", Toast.LENGTH_SHORT).show();
                }).addOnFailureListener(er -> {
                    Toast.makeText(AddSlotActivity.this, "" + er.getMessage(), Toast.LENGTH_SHORT).show();
                });

            }
        });
    }

    public Task<Void> addslot(Slot slot)
    {
        return slotdatabaseReference.child(slotNumber.getText().toString()).setValue(slot);
    }
}