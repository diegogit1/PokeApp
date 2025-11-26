package com.dam.trabajo_recuperacion_2025

import android.app.Application
import com.google.firebase.ktx.Firebase
import com.google.firebase.FirebaseApp

class ApplicationK: Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}