package com.example.myproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GPSMapActivity extends AppCompatActivity implements OnMapReadyCallback {

    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    Button getLocationBtn;

    GoogleMap gMap;
    LatLng globalLatLng;
    BasicUtils utils=new BasicUtils();

    private FirebaseAuth auth;
    private FirebaseDatabase db;

    LatLng globalLatLngIntent=null;
    MarkerOptions optionsIntent;

    LatLng CrystalMall = new LatLng (22.2798218,70.7631888);
    LatLng RajkotAirport = new LatLng (22.3089497, 70.782264);
    LatLng Dmart = new LatLng (22.2841019, 70.7937246);
    LatLng RelianceMall = new LatLng (22.2793674, 70.774859);

    private Marker mCrystalMall;
    private Marker mRajkotAirport;
    private Marker mDmart;
    private Marker mRelianceMall;


    AppConstants globalClass;

    String nameIntent;
    double latitudeIntent,longitudeIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gpsmap);

        initComponents();
        attachListeners();

        getPreCurrentLocation();
        if(!utils.isNetworkAvailable(getApplication())){
            Toast.makeText(GPSMapActivity.this, "No Network Available!", Toast.LENGTH_SHORT).show();
        }
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);  //slide from left to right
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);  //slide from left to right
    }

    private void initComponents() {
        globalClass=(AppConstants)getApplicationContext();

        getSupportActionBar().setTitle("GPS Maps");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        auth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();

        Intent intent=getIntent();
        nameIntent=intent.getStringExtra("LOCATION_NAME");
        latitudeIntent=intent.getDoubleExtra("LOCATION_LATITUDE",-1);
        longitudeIntent= intent.getDoubleExtra("LOCATION_LONGITUDE",-1);

        if(latitudeIntent != -1){
            globalLatLngIntent=new LatLng(latitudeIntent,longitudeIntent);
            optionsIntent=new MarkerOptions().position(globalLatLngIntent)
                    .title(nameIntent);
        }

        supportMapFragment=(SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.google_map);
        supportMapFragment.getMapAsync(this);
        client= LocationServices.getFusedLocationProviderClient(GPSMapActivity.this);

        getLocationBtn=findViewById(R.id.getLocationBtn);
    }

    private void attachListeners() {
        getLocationBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(GoogleMap googleMap) {
                        gMap = googleMap;
                        gMap.clear();
                        getPreCurrentLocation();
                    }
                });
            }
        });
    }
    private void getPreCurrentLocation() {
        if(ActivityCompat.checkSelfPermission(GPSMapActivity.this,
                android.Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED){
            getCurrentLocation(true);
        }else{
            ActivityCompat.requestPermissions(GPSMapActivity.this,new String[]
                    {android.Manifest.permission.ACCESS_FINE_LOCATION},AppConstants.LOCATION_REQUEST_CODE);
        }
    }

    private void getCurrentLocation(final Boolean zoom) {
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]
                    {android.Manifest.permission.ACCESS_FINE_LOCATION},AppConstants.LOCATION_REQUEST_CODE);
        }
        Task<Location> task= client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(final Location location) {
                if(location!=null){
                    supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                        @Override
                        public void onMapReady(GoogleMap googleMap) {
                            gMap=googleMap;
                            globalLatLng=new LatLng(location.getLatitude(),
                                    location.getLongitude());
                            googleMap.addMarker(new MarkerOptions().position(globalLatLng).title("I am here"));
                            if(zoom){
                                gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(globalLatLng,30));
                            }
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == AppConstants.LOCATION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getCurrentLocation(true);
            }
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        gMap = googleMap;

        List<Marker> markerList = new ArrayList<>();

        mCrystalMall = gMap.addMarker(new MarkerOptions()
                .position(CrystalMall).title("CRYSTAL MALL")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mCrystalMall.setTag(0);
        markerList.add(mCrystalMall);

        mRajkotAirport = gMap.addMarker(new MarkerOptions()
                .position(RajkotAirport).title("RAJKOT AIRPORT")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mRajkotAirport.setTag(0);
        markerList.add(mRajkotAirport);

        mDmart = gMap.addMarker(new MarkerOptions()
                .position(Dmart).title("Dmart shopping Mall")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mDmart.setTag(0);
        markerList.add(mDmart);

        mRelianceMall = gMap.addMarker(new MarkerOptions()
                .position(RelianceMall).title("Reliance Mall")
                .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
        mRelianceMall.setTag(0);
        markerList.add(mRelianceMall);

        //gMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
        gMap.setOnMarkerClickListener(this::onMarkerClick);


        for (Marker m : markerList) {
            LatLng latLng = new LatLng(m.getPosition().latitude, m.getPosition().longitude);
            gMap.addMarker(new MarkerOptions().position(latLng));
            gMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 5));
        }


    }
    public boolean onMarkerClick(Marker marker) {

        Toast.makeText(this,marker.getTitle(),Toast.LENGTH_SHORT).show();

        return false;
    }
}