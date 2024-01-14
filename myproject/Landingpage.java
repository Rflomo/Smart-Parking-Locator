package com.example.myproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.VideoView;

public class Landingpage extends AppCompatActivity {
    Button start_button;
    private VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landingpage);

        videoView = findViewById(R.id.videoView);

        start_button=findViewById(R.id.start_buttn);

        start_button.setOnClickListener(view ->{
            Intent i = new Intent(Landingpage.this,loginpage.class);
            startActivity(i);
        });

        // Hide the status bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);


        // Set the video URI
        Uri videoUri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.my_video);
        videoView.setVideoURI(videoUri);

        // Set an OnPreparedListener to start the video as soon as it's ready
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // Hide the app logo and start the video
                //imageView.setVisibility(View.GONE);
                mp.setLooping(true);
                videoView.start();

            }
        });

    }
}