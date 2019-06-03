package com.example.serietracking.Fragments

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.serietracking.Adapters.UserRecyclerAdapter
import com.example.serietracking.R
import com.example.serietracking.TVModel
import com.example.serietracking.TVShow
import com.example.serietracking.account.AccountService
import com.example.serietracking.network.ErrorLoggingCallback
import kotlinx.android.synthetic.main.fragment_user_tvshows.*
import retrofit2.Call
import retrofit2.Response

class UserTVShowsFragment : FragmentActivity() {
    //TODO: si no tiene series propias, mostrar un label que diga que no tenes series.

    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var adapter: UserRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_user_tvshows)

        val thisActivity = this

        val callback = object: ErrorLoggingCallback<TVModel>() {
            override fun onResponse(call: Call<TVModel>?, response: Response<TVModel>?) {
                if (response!!.isSuccessful) {

                    linearLayoutManager = LinearLayoutManager(thisActivity)
                    userRecyclerView.layoutManager = linearLayoutManager

                    val tvShows: List<TVShow> = response.body().results!!
                    adapter = UserRecyclerAdapter(tvShows)
                    userRecyclerView.adapter = adapter

                    //                    AccountService.getNextCaps(tvs, {(_) -> Unit})
                }
            }
        }
        AccountService.getFavorite(callback)
    }
}
