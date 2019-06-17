package com.example.serietracking.network

import com.example.serietracking.*
import com.example.serietracking.account.dto.AccountResponse
import com.example.serietracking.login.dto.CreateSessionRequest
import com.example.serietracking.login.dto.CreateSessionResponse
import com.example.serietracking.login.dto.RequestTokenResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @GET("tv/popular")
    fun getPopular(@Query("api_key") api_key: String): Call<TVModel>

    @GET("tv/1399/season/1")
    fun getSeasonOfGOT(@Query("api_key") api_key: String): Call<SeasonModel>

    @GET("tv/{tv_id}")
    fun getTV(@Path("tv_id") tv_id: Long, @Query("api_key") api_key: String): Call<TVCompleteModel>

    @GET("search/tv")
    fun searchTV(@Query("query") query: String, @Query("api_key") api_key: String): Call<TVModel>

    @GET("tv/{tv_id}/season/{season_id}")
    fun getSeason(@Path("tv_id") tv_id: Long, @Path("season_id") season_id: Long,
                  @Query("api_key") api_key: String): Call<SeasonModel>

    @GET("authentication/token/new")
    fun createRequestToken(@Query("api_key") api_key: String): Call<RequestTokenResponse>

    @POST("authentication/session/new")
    fun createSession(@Query("api_key") api_key: String, @Body body: CreateSessionRequest): Call<CreateSessionResponse>

    @GET("account")
    fun getAccount(@Query("api_key") api_key: String, @Query("session_id") session_id: String): Call<AccountResponse>

    @GET("account/{account_id}/favorite/tv")
    fun getFavorite(@Path("account_id") account_id: Long, @Query("api_key") api_key: String,
                    @Query("session_id") session_id: String): Call<TVModel>

    @POST("account/{account_id}/favorite/")
    fun addFavorite(@Path("account_id") account_id: Long,  @Query("api_key") api_key: String,  @Query("session_id") session_id: String, @Body body: AddToFavoriteRequest): Call<AddToFavoriteResponse>

}