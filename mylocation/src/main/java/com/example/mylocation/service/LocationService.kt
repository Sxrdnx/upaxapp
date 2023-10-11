package com.example.mylocation.service

import android.app.Service
import android.content.Intent
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

class LocationService : Service() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate() {
        super.onCreate()
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        sendLocationPeriodically()
        return START_STICKY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    private fun sendLocationPeriodically() {
        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                // Get the user's location
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        // Send the location to your server here
                    }
                }

                // Schedule the next location update after 2 minutes
                handler.postDelayed(this, 2 * 60 * 1000)
            }
        })
    }
}