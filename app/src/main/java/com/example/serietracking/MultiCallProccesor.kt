package com.example.serietracking

import com.example.serietracking.network.ErrorLoggingCallback
import retrofit2.Call
import retrofit2.Response
import java.util.concurrent.CopyOnWriteArrayList

class MultiCallProccesor<T>(val calls: List<Call<T>>) {
    val results: CopyOnWriteArrayList<T> = CopyOnWriteArrayList()

    fun enqueue(callback: (List<T>) -> Unit) {
        calls.forEach { call ->
            call.enqueue(object: ErrorLoggingCallback<T>() {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    results.add(response.body())
                    if (results.size.equals(calls.size)) {
                        callback(results)
                    }
                }
            })
        }
    }
}