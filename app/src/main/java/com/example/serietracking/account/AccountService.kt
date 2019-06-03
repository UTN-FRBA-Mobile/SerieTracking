package com.example.serietracking.account

import com.example.serietracking.MultiCallProccesor
import com.example.serietracking.SeasonModel
import com.example.serietracking.TVCompleteModel
import com.example.serietracking.TVModel
import com.example.serietracking.account.dto.AccountResponse
import com.example.serietracking.network.ApiClient
import com.example.serietracking.network.ErrorLoggingCallback
import com.example.serietracking.network.HttpConstants.API_KEY
import retrofit2.Call
import retrofit2.Response
import java.time.LocalDate

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

    fun getNextCaps(tvModel: TVModel, callback: (List<SeasonModel>) -> Unit) {
        val today = LocalDate.now()
        val calls = tvModel.results.map { ApiClient.apiInterface.getTV(it.id, API_KEY) }
        val processor = MultiCallProccesor(calls)
        processor.enqueue { results ->
            val seasonCalls = results.filter { it.inProduction }.map {
                ApiClient.apiInterface.getSeason(it.id, it.seasons.last().seasonNumber, API_KEY)
            }
            val seasonProcessor = MultiCallProccesor(seasonCalls)
            seasonProcessor.enqueue { seasons ->
                val seasonsWithUpdates = seasons.filter { season ->
                    season.episodes.any { LocalDate.parse(it.airDate).compareTo(today) >= 0 }
                }
                callback(seasonsWithUpdates)
            }
        }
    }
}