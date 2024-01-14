package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class dashboard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);


        TextView title1= findViewById(R.id.title1);
        TextView title01= findViewById(R.id.title01);
        CardView slot1 =findViewById(R.id.slot1);
        slot1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(dashboard.this,booking_details.class);
                i.putExtra("title", title1.getText().toString());
                i.putExtra("title01", title01.getText().toString());

                startActivity(i);
            }
        });

        TextView title2= findViewById(R.id.title2);
        TextView title02= findViewById(R.id.title02);
        CardView slot2 =findViewById(R.id.slot2);
        slot2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(dashboard.this,booking_details.class);
                i.putExtra("title", title2.getText().toString());
                i.putExtra("title01", title02.getText().toString());
                startActivity(i);
            }
        });

        TextView title3= findViewById(R.id.title3);
        TextView title03= findViewById(R.id.title03);
        CardView slot3 =findViewById(R.id.slot3);
        slot3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(dashboard.this,booking_details.class);
                i.putExtra("title", title3.getText().toString());
                i.putExtra("title01", title03.getText().toString());
                startActivity(i);
            }
        });

        TextView title4= findViewById(R.id.title4);
        TextView title04= findViewById(R.id.title04);
        CardView slot4 =findViewById(R.id.slot4);
        slot4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(dashboard.this,booking_details.class);
                i.putExtra("title", title4.getText().toString());
                i.putExtra("title01", title04.getText().toString());
                startActivity(i);
            }
        });

        TextView title5= findViewById(R.id.title5);
        TextView title05= findViewById(R.id.title05);
        CardView slot5 =findViewById(R.id.slot5);
        slot5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(dashboard.this,booking_details.class);
                i.putExtra("title", title5.getText().toString());
                i.putExtra("title01", title05.getText().toString());
                startActivity(i);
            }
        });

        TextView title6= findViewById(R.id.title6);
        TextView title06= findViewById(R.id.title06);
        CardView slot6 = findViewById(R.id.slot6);
        slot6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(dashboard.this,booking_details.class);
                i.putExtra("title", title6.getText().toString());
                i.putExtra("title01", title06.getText().toString());
                startActivity(i);
            }
        });
    }
}