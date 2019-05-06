package com.example.serietracking.network

import com.example.serietracking.*
import com.example.serietracking.login.dto.CreateSessionRequest
import com.example.serietracking.login.dto.CreateSessionResponse
import com.example.serietracking.login.dto.RequestTokenResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiInterface {
    @GET("tv/popular")
    fun getPopular(@Query("api_key") api_key: String): Call<TVModel>

    @GET("tv/1399/season/1")
    fun getSeasonOfGOT(@Query("api_key") api_key: String): Call<SeasonModel>

    @GET("authentication/token/new")
    fun createRequestToken(@Query("api_key") api_key: String): Call<RequestTokenResponse>

    @POST("authentication/session/new")
    fun createSession(@Query("api_key") api_key: String, @Body body: CreateSessionRequest): Call<CreateSessionResponse>
}