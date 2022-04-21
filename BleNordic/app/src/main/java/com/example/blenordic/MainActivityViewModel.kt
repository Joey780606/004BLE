package com.example.blenordic

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.content.ContextCompat.checkSelfPermission
import androidx.lifecycle.ViewModel

class MainActivityViewModel: ViewModel() {
    val TAG = this.javaClass.simpleName //重要

    override fun onCleared() {
        super.onCleared()
        Log.v(TAG, "viewModel is close")
    }

    fun start() {
        Log.v(TAG, "viewModel is start")
    }

    fun initializeBluetoothOrRequestPermission(mainActivity: MainActivity) {    // Permission
        val requiredPermissions = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.S) {
            listOf(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            listOf(Manifest.permission.BLUETOOTH_CONNECT, Manifest.permission.BLUETOOTH_SCAN)
        }

        val missingPermissions = requiredPermissions.filter { permission ->
            checkSelfPermission(MainApp.appContext, permission) != PackageManager.PERMISSION_GRANTED
        }
        if (missingPermissions.isEmpty()) {
            initializeBluetooth()
        } else {
            requestPermissions(mainActivity, missingPermissions.toTypedArray(), MainActivity.BLUETOOTH_PERMISSION_REQUEST_CODE)
        }
    }

    public fun initializeBluetooth() {  // Permission
        Log.v(TAG, "Bluetooth permission is OK")
    }
}