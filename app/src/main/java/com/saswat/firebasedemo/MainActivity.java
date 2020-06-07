package com.saswat.firebasedemo;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity  {
    private TextView textView;
    private static final int REQUEST_LOCATION=1;
    Button getLocationbtn ;
    LocationManager locationManager;
    String longitude , latitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this, new String[]
        {
            Manifest.permission.ACCESS_FINE_LOCATION
        },REQUEST_LOCATION);
        textView = findViewById(R.id.textView);
        getLocationbtn = findViewById(R.id.find_location_btn);
        getLocationbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                locationManager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
                if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                    onGPS();
                }else{
                    getLocation();
                }
            }
        });
    }

    private void getLocation() {
        if(ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(MainActivity.this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, new String[]
                    {
                            Manifest.permission.ACCESS_FINE_LOCATION
                    },REQUEST_LOCATION);
        }else{
            Location locationGps = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Location locationNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            Location locationPassive = locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);

            if(locationGps != null){
                double lat = locationGps.getLatitude();
                double longi = locationGps.getLongitude();

                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);

                textView.setText("Your Location" + "\n" + "Longitude : " + longitude + "\n" + "Latitude :" + "\n" + latitude);
            }
            else if(locationNetwork != null){
                double lat = locationNetwork.getLatitude();
                double longi = locationNetwork.getLongitude();

                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);

                textView.setText("Your Location" + "\n" + "Longitude : " + longitude + "\n" + "Latitude :" + "\n" + latitude);
            }else if(locationPassive != null){
                double lat = locationPassive.getLatitude();
                double longi = locationPassive.getLongitude();

                latitude = String.valueOf(lat);
                longitude = String.valueOf(longi);

                textView.setText("Your Location" + "\n" + "Longitude : " + longitude + "\n" + "Latitude :" + "\n" + latitude);
            }else{
                Toast.makeText(this, "Can't get your Loaction", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void onGPS() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Enable GPS").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        final AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void logout(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(MainActivity.this,Login.class));
        finish();

    }


}
