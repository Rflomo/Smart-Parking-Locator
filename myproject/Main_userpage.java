package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;

public class Main_userpage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_userpage);


        CardView exit = findViewById(R.id.card6);
        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Main_userpage.this,loginpage.class);
                startActivity(i);
            }
        });

        CardView book =findViewById(R.id.cardBook);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Main_userpage.this,BookingDetailInterface.class);
                startActivity(i);
            }
        });

        CardView location =findViewById(R.id.card_locator);
        location.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplication(),GPSMapActivity.class);
                startActivity(i);
            }
        });
        CardView About_us =findViewById(R.id.card5);
        About_us.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Main_userpage.this,AboutUs.class);
                startActivity(i);
            }
        });
    }
    }