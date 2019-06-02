package com.example.serietracking.account

import com.example.serietracking.TVCompleteModel
import com.example.serietracking.TVModel
import com.example.serietracking.account.dto.AccountResponse
import com.example.serietracking.network.ApiClient
import com.example.serietracking.network.ErrorLoggingCallback
import com.example.serietracking.network.HttpConstants.API_KEY
import retrofit2.Call
import retrofit2.Response

object AccountService {
    private var sessionId: String? = null

    fun setSessionId(sessionId: String) {
        this.sessionId = sessionId
    }

    fun getFavorite(callback: ErrorLoggingCallback<TVModel>) {
        ApiClient.apiInterface.getAccount(API_KEY, sessionId!!).enqueue(object: ErrorLoggingCallback<AccountResponse>() {
            override fun onResponse(call: Call<AccountResponse>, response: Response<AccountResponse>) {
                ApiClient.apiInterface.getFavorite(response.body().id, API_KEY, sessionId!!).enqueue(callback)
            }
        })
    }

    fun getNextCaps(tvModel: TVModel) {
        tvModel.results!!.forEach { show ->
            ApiClient.apiInterface.getTV(show.id, API_KEY).enqueue(object: ErrorLoggingCallback<TVCompleteModel>() {
                override fun onResponse(call: Call<TVCompleteModel>, response: Response<TVCompleteModel>) {
                    val tvCompleteModel = response.body()
                    if (tvCompleteModel.inProduction) {
                        tvCompleteModel.seasons.last().seasonNumber
                    }
                }
            })
        }
    }
}