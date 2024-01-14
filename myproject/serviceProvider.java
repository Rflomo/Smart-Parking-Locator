package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class serviceProvider extends AppCompatActivity {

    public Button btn_Space,reservation,space;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_provider);

        reservation = findViewById(R.id.btn_reservation);
        reservation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(serviceProvider.this, BookingHistory.class);
                startActivity(intent);
            }
        });

        space = findViewById(R.id.btn_Space);
        space.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(serviceProvider.this, AddSlotActivity.class);
                startActivity(intent);
            }
        });
        btn_Space = findViewById(R.id.btn_customer_support);
        btn_Space.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(serviceProvider.this, LoggedinUsers.class);
                startActivity(intent);
            }
        });
    }
}