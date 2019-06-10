package com.example.serietracking.multicall

import com.example.serietracking.network.ErrorLoggingCallback
import retrofit2.Call
import retrofit2.Response
import java.util.concurrent.CopyOnWriteArrayList

class MultiCallProccesor<T, V>(val callables: List<Callable<T, V>>) {
    val results: CopyOnWriteArrayList<V> = CopyOnWriteArrayList()

    fun enqueue(callback: (List<V>) -> Unit) {
        callables.forEach { callable ->
            callable.call.enqueue(object: ErrorLoggingCallback<T>() {
                override fun onResponse(call: Call<T>, response: Response<T>) {
                    results.add(callable.mapper(response.body()))
                    if (results.size.equals(callables.size)) {
                        callback(results)
                    }
                }
            })
        }
    }
}