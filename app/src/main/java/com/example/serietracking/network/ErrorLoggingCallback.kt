package com.example.serietracking.network

import android.util.Log
import retrofit2.Call
import retrofit2.Callback

abstract class ErrorLoggingCallback<T> : Callback<T> {
    override fun onFailure(call: Call<T>, t: Throwable) {
        Log.e("log","failed to connect to the service", t)
    }
}