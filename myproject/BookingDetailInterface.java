package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class BookingDetailInterface extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking_detail_interface);

        CardView Detail_history = findViewById(R.id.cardHistory);
        Detail_history.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent i = new Intent(BookingDetailInterface.this,UserHistory.class);
                startActivity(i);
            }
        });

        CardView book =findViewById(R.id.spot_book);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(BookingDetailInterface.this,ParkingSpaces.class);
                startActivity(i);
            }
        });
    }
}