package com.example.serietracking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.serietracking.network.ApiClient
import com.example.serietracking.network.ErrorLoggingCallback
import com.example.serietracking.network.HttpConstants
import kotlinx.android.synthetic.main.activity_tv_shows_general.*
import retrofit2.Call
import retrofit2.Response

class TVShowsGeneralActivity : AppCompatActivity() {

    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_shows_general)

        val thisActivity = this

        ApiClient.apiInterface.getPopular(HttpConstants.API_KEY).enqueue(object: ErrorLoggingCallback<TVModel>() {
            override fun onResponse(call: Call<TVModel>?, response: Response<TVModel>?) {
                if (response!!.isSuccessful) {

                    linearLayoutManager = LinearLayoutManager(thisActivity)
                    series_recylcer.layoutManager = linearLayoutManager

                    val tvShows: List<TVModel.TVShow> = response.body().results!!
                    adapter = RecyclerAdapter(tvShows)
                    series_recylcer.adapter = adapter
                }
            }
        })
    }
}
