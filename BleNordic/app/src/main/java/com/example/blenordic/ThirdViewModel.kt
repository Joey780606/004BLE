package com.example.blenordic

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import no.nordicsemi.android.support.v18.scanner.*


class ThirdViewModel(): ViewModel() {
    val TAG = this.javaClass.simpleName //重要

    val scanInfoLiveData: LiveData<List<ScanResult>>
        get() = _scanInfoLiveData

    private val _scanInfoLiveData = MutableLiveData<List<ScanResult>>()

    var scanCallback = MyScanCallback(_scanInfoLiveData)  //Scan

    override fun onCleared() {
        super.onCleared()
        Log.v(TAG, "viewModel is close")
    }

    fun start() {
        Log.v(TAG, "viewModel is start")
    }

    fun scanBLE(status: Int) {  //Scan
        //TODO: Scan process should be divided to one special file.
        when (status) {
            MainActivity.SCAN_START -> {
                val scanner = BluetoothLeScannerCompat.getScanner()
                val settings: ScanSettings = ScanSettings.Builder()
                    .setLegacy(false)
                    .setScanMode(ScanSettings.SCAN_MODE_LOW_LATENCY)
                    .setReportDelay(3000)
                    .setUseHardwareBatchingIfSupported(true)
                    .build()
                val filters: MutableList<ScanFilter> = ArrayList()
                //filters.add(ScanFilter.Builder().setServiceUuid(mUuid).build())
                scanner.startScan(filters, settings, scanCallback)
            }
            MainActivity.SCAN_STOP -> {
                val scanner = BluetoothLeScannerCompat.getScanner()
                scanner.stopScan(scanCallback)
            }
        }
    }

    class MyScanCallback(info: MutableLiveData<List<ScanResult>>): ScanCallback() {  //Scan
        var infoData = info
        override fun onScanResult(callbackType: Int, result: ScanResult) {
            super.onScanResult(callbackType, result)
            Log.v("Test", "scanCallback type: $callbackType ${result.device.address}")
        }

        override fun onBatchScanResults(results: MutableList<ScanResult>) {
            super.onBatchScanResults(results)
            infoData.value = results
            Log.v("Test", "scanCallback onBatch: ${results.size}")
        }

        override fun onScanFailed(errorCode: Int) {
            super.onScanFailed(errorCode)
            Log.v("Test", "scanCallback onFail: $errorCode")
        }
    }
}

