package com.example.blenordic

import android.util.Log
import androidx.lifecycle.ViewModel

class ThirdViewModel: ViewModel() {
    val TAG = this.javaClass.simpleName //重要
    override fun onCleared() {
        super.onCleared()
        Log.v(TAG, "viewModel is close")
    }

    fun start() {
        Log.v(TAG, "viewModel is start")
    }

    fun scanBLE() {
        TODO("Not yet implemented")
    }
}