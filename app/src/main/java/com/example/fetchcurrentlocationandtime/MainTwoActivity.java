package com.example.fetchcurrentlocationandtime;

import android.content.pm.PackageManager;
import android.location.*;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class MainTwoActivity extends AppCompatActivity {


    private TextView mTime;
    private TextView mLocation;
    private Button mRefreshButton;
    FusedLocationProviderClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTime = findViewById(R.id.time);
        mLocation = findViewById(R.id.location);
        mRefreshButton = findViewById(R.id.refreshButton);

        requestPermission();
        fetchCurrentTime();
        fetchCurrentLocation();
        setOnClickListener();
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(this,new String[]{ACCESS_FINE_LOCATION},1);
    }

    private void fetchCurrentTime() {
        String date = new SimpleDateFormat("dd-MM-yyyy   HH:mm:ss", Locale.getDefault()).format(new Date());
        mTime.setText(date);
    }

    private void fetchCurrentLocation() {
        client = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        client.getLastLocation().addOnSuccessListener(MainTwoActivity.this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    Double latitude = location.getLatitude();
                    Double longitude = location.getLatitude();
                    String address = null;
                    try {
                        address = generateAddressFromLatLong(latitude, longitude);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mLocation.setText(address);
                }else{
                    mLocation.setText("Cant fetch your location...." + " Please try again");
                }
            }
        });

    }

    private String generateAddressFromLatLong(Double latitude, Double longitude) throws IOException {
        Geocoder geocoder;
        String yourAddress = "";
        List<Address> yourAddresses;
        geocoder = new Geocoder(this, Locale.getDefault());
        yourAddresses= geocoder.getFromLocation(latitude, longitude, 1);

        if (yourAddresses.size() > 0)
        {
            yourAddress =  yourAddresses.get(0).getAddressLine(0);
        }
        return yourAddress;
    }

    private void setOnClickListener() {
        mRefreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mTime.setText("Fetch time....");
                mLocation.setText("Fetch location....");
                fetchCurrentTime();
                fetchCurrentLocation();
            }
        });
    }


}
