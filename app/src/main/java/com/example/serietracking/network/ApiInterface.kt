package com.example.serietracking.network

import com.example.serietracking.CreateSessionBody
import com.example.serietracking.CreateSessionModel
import com.example.serietracking.RequestTokenModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.serietracking.TVModel
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @GET("tv/popular")
    fun getPopular(@Query("api_key") api_key: String): Call<TVModel>

    @GET("authentication/token/new")
    fun createRequestToken(@Query("api_key") api_key: String): Call<RequestTokenModel>

    @POST("authentication/session/new")
    fun createSession(@Query("api_key") api_key: String, @Body body: CreateSessionBody): Call<CreateSessionModel>
}