package com.example.fetchcurrentlocationandtime

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.location.Location
import android.net.sip.SipSession
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import kotlinx.android.synthetic.main.activity_main.*
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.*

class MainActivity : AppCompatActivity() {

    lateinit var client: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        requestPermission()
        fetchCurrentTime()
        fetchCurrentLocation()
        setOnClickListener()

    }

    private fun requestPermission() {
//        ActivityCompat.requestPermissions(this,  String[]{ACCESS_FINE_LOCATION}, 1);
    }


    private fun fetchCurrentTime() {
        val date = SimpleDateFormat("dd-MM-yyyy  HH:mm:ss", Locale.getDefault()).format(Date())
        time.setText(date)
    }

    private fun fetchCurrentLocation() {
        client = LocationServices.getFusedLocationProviderClient(this)
        if(ActivityCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            return
        }
        
    }

    private fun setOnClickListener() {
        refreshButton.setOnClickListener {
            time.setText("Fetching time...")
            location.setText("Fetching location...")
        }
    }

}
