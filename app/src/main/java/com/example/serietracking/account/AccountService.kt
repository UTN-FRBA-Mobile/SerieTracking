package com.example.serietracking.account

import com.example.serietracking.RichEpisode
import com.example.serietracking.multicall.MultiCallProccesor
import com.example.serietracking.TVModel
import com.example.serietracking.account.dto.AccountResponse
import com.example.serietracking.multicall.Callable
import com.example.serietracking.network.AddToFavoriteRequest
import com.example.serietracking.network.AddToFavoriteResponse
import com.example.serietracking.network.ApiClient
import com.example.serietracking.network.ErrorLoggingCallback
import com.example.serietracking.network.HttpConstants.API_KEY
import retrofit2.Call
import retrofit2.Response
import java.time.LocalDate

object AccountService {
    private var sessionId: String? = null
    private var accountID: Long? = null

    fun setSessionId(sessionId: String) {
        this.sessionId = sessionId
    }


    fun getFavorite(callback: ErrorLoggingCallback<TVModel>) {
        //TODO: Esto se tiene que hacer por separado, agregar el account desde el principio
        ApiClient.apiInterface.getAccount(API_KEY, sessionId!!).enqueue(object: ErrorLoggingCallback<AccountResponse>() {
            override fun onResponse(call: Call<AccountResponse>, response: Response<AccountResponse>) {
//                this.accountID = response.body().id
                accountID = response.body().id
                ApiClient.apiInterface.getFavorite(accountID!! , API_KEY, sessionId!!).enqueue(callback)
            }
        })
    }

    fun addToFavorite(mediaType: String, mediaId: Long, favorite: Boolean, callback: ErrorLoggingCallback<AddToFavoriteResponse>) {
        ApiClient.apiInterface.addFavorite(accountID!!, API_KEY, sessionId!!, AddToFavoriteRequest(mediaType, mediaId, favorite)).enqueue(callback)
    }


    fun getFavoriteNextCaps(callback: (List<RichEpisode>) -> Unit) {
        this.getFavorite(object: ErrorLoggingCallback<TVModel>() {
            override fun onResponse(call: Call<TVModel>, response: Response<TVModel>) {
                this@AccountService.getNextCaps(response.body(), callback)
            }
        })
    }

    fun getNextCaps(tvModel: TVModel, callback: (List<RichEpisode>) -> Unit) {
        val today = LocalDate.now()
        val calls = tvModel.results.map { Callable(ApiClient.apiInterface.getTV(it.id, API_KEY), {r ->
            val episode = RichEpisode()
            episode.tv = r
            return@Callable episode
        })}
        val processor = MultiCallProccesor(calls)
        processor.enqueue { results ->
            val seasonCalls = results.filter { it.tv!!.inProduction }.map {
                Callable(ApiClient.apiInterface.getSeason(it.tv!!.id, it.tv!!.seasons.last().seasonNumber, API_KEY), { s ->
                    it.season = s
                    return@Callable it
                })
            }
            val richEpisodesProccesor = MultiCallProccesor(seasonCalls)
            richEpisodesProccesor.enqueue { richEpisodes ->
                richEpisodes.forEach { richEpisode ->
                    richEpisode.season!!.episodes.find { LocalDate.parse(it.airDate).compareTo(today) >= 0 }.let {
                        richEpisode.episode = it
                    }
                }
                val seasonsWithUpdates = richEpisodes.filter { it.episode != null }
                callback(seasonsWithUpdates)
            }
        }
    }
}