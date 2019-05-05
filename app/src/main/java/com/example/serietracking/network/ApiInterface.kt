package com.example.serietracking.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.serietracking.TVModel

interface ApiInterface {
    @GET("tv/popular")
    fun getPopular(@Query("api_key") api_key: String): Call<TVModel>
}