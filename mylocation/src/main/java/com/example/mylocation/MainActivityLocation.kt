package com.example.mylocation

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.mylocation.common.PermissionRequester
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class MainActivityLocation : AppCompatActivity() {
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        sendLocationPeriodically()
        setContentView(R.layout.activity_main_location)
    }

    private fun sendLocationPeriodically() {
        val handler = Handler(Looper.getMainLooper())
        val db = FirebaseFirestore.getInstance()

        handler.post(object : Runnable {
            @SuppressLint("MissingPermission")
            override fun run() {
                // Get the user's location
                PermissionRequester(
                    this@MainActivityLocation,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                ).request {
                    if (it){
                        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                            if (location != null) {
                                val userLocation = hashMapOf(
                                    "latitude" to location.latitude,
                                    "longitude" to location.longitude,
                                    "timestamp" to System.currentTimeMillis()
                                )

                                // Update the user's location in Firestore
                                db.collection("locations")
                                    .add(userLocation)
                                    .addOnSuccessListener {
                                       Log.d("FIRESTT", "c mando")
                                    }
                                    .addOnFailureListener {
                                        Log.d("FIRESTT", "noo c mando")
                                    }
                            }
                        }

                        // Schedule the next location update after 2 minutes
                        handler.postDelayed(this, 10 * 1000)
                    }
                }

            }
        })
    }
}