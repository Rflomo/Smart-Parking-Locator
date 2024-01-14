package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

public class ParkingSpaces extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parking_spaces);


        Button ProceedButton=findViewById(R.id.DmartButton);
        ProceedButton.setOnClickListener(view ->{
            Intent i = new Intent(ParkingSpaces.this,BookingSlotActivity.class);
            startActivity(i);
        });

        Button RelianceButton=findViewById(R.id.RelianceMallButton);
        RelianceButton.setOnClickListener(view ->{
            //Intent i = new Intent(ParkingSpaces.this,BookParkingAreaActivity.class);
           // startActivity(i);
        });

    }
}