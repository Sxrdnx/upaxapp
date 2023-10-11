package com.example.pokemonexample

import android.app.Application
import com.google.firebase.FirebaseApp
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class PokemonApplication:Application() {
    override fun onCreate() {
        super.onCreate()
       FirebaseApp.initializeApp(this)
    }
}