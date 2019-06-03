package com.example.serietracking

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.serietracking.account.AccountService
import com.example.serietracking.network.ApiClient
import com.example.serietracking.network.ErrorLoggingCallback
import com.example.serietracking.network.HttpConstants
import kotlinx.android.synthetic.main.activity_tv_shows_general.*
import retrofit2.Call
import retrofit2.Response

class UserTVShowsFragment : FragmentActivity() {
    //TODO: Sacar la Query GetPopular, si no tiene series propias, mostrar un label que diga que no tenes series.

    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_shows_general)

        val thisActivity = this

        // Getting all extras
        val extras = intent.extras
        val strategy = extras.getString("strategy", "all")

        val callback = object: ErrorLoggingCallback<TVModel>() {
            override fun onResponse(call: Call<TVModel>?, response: Response<TVModel>?) {
                if (response!!.isSuccessful) {

                    linearLayoutManager = LinearLayoutManager(thisActivity)
                    series_recylcer.layoutManager = linearLayoutManager

                    val tvs = response.body()
                    val tvShows: List<TVShow> = tvs.results
                    adapter = RecyclerAdapter(tvShows)
                    series_recylcer.adapter = adapter

                    AccountService.getNextCaps(tvs, {(_) -> Unit})
                }
            }
        }

        when(strategy) {
            "user" -> AccountService.getFavorite(callback)
            else -> ApiClient.apiInterface.getPopular(HttpConstants.API_KEY).enqueue(callback)
        }
    }
}
