package com.example.blenordic

import android.app.Application
import android.content.Context

class MainApp : Application() {
    companion object {
        lateinit var appContext: Context
        var initialized = false
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }
}