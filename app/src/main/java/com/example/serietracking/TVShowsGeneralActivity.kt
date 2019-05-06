package com.example.serietracking

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.serietracking.network.ApiClient
import com.example.serietracking.network.HttpConstants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_tv_shows_general.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class TVShowsGeneralActivity : AppCompatActivity(), Callback<TVModel> {

    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var adapter: RecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tv_shows_general)

        ApiClient.apiInterface.getPopular(HttpConstants.API_KEY).enqueue(this)

    }


    override fun onResponse(call: Call<TVModel>?, response: Response<TVModel>?) {
        if (response!!.isSuccessful) {

            linearLayoutManager = LinearLayoutManager(this)
            series_recylcer.layoutManager = linearLayoutManager

            val tvShows: List<TVModel.TVShow> = response.body().results!!
            adapter = RecyclerAdapter(tvShows)
            series_recylcer.adapter = adapter
        }
    }

    override fun onFailure(call: Call<TVModel>?, t: Throwable?) {
        print("error")
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
