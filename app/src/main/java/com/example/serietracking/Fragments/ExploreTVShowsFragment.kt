package com.example.serietracking.Fragments

import android.os.Bundle
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.serietracking.Adapters.ExploreRecyclerAdapter
import com.example.serietracking.R
import com.example.serietracking.TVModel
import com.example.serietracking.network.ApiClient
import com.example.serietracking.network.ErrorLoggingCallback
import com.example.serietracking.network.HttpConstants
import kotlinx.android.synthetic.main.fragment_explore_tvshows.*
import retrofit2.Call
import retrofit2.Response

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
class ExploreTVShowsFragment : FragmentActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var adapter: ExploreRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_explore_tvshows)

        val thisActivity = this

        val callback = object: ErrorLoggingCallback<TVModel>() {
            override fun onResponse(call: Call<TVModel>?, response: Response<TVModel>?) {
                if (response!!.isSuccessful) {

                    linearLayoutManager = LinearLayoutManager(thisActivity)
                    exploreRecyclerView.layoutManager = linearLayoutManager

                    val tvShows: List<TVModel.TVShow> = response.body().results!!
                    adapter = ExploreRecyclerAdapter(tvShows)
                    exploreRecyclerView.adapter = adapter
                }
            }
        }
        ApiClient.apiInterface.getPopular(HttpConstants.API_KEY).enqueue(callback)
    }

    fun addTvShowToFavorite() {
        //TODO: Agregar la query posta
//        ApiClient.apiInterface.addTVSerie
    }


}


