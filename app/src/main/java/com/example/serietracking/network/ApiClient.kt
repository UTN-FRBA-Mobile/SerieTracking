package com.example.serietracking.network
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {
    var apiInterface: ApiInterface
    private val retrofit: Retrofit
    private val DEFAULT_TIMEOUT = 5L
    private val okHttpClient: OkHttpClient

    init {
        //SOLO PARA LOGEAR.
        val longging = Interceptor { chain ->
            val request = chain.request()
            chain.proceed(request)
        }
        okHttpClient = OkHttpClient.Builder()
            .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(longging) //Si este desaparece no pasa nada
            .build()
        val gson: Gson = GsonBuilder()
            .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
            .create()
        retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .baseUrl(HttpConstants.BASE_URL)
            .build()
        apiInterface = retrofit.create(ApiInterface::class.java)
    }
}