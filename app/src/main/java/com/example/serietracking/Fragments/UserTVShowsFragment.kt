package com.example.serietracking.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serietracking.Adapters.UserRecyclerAdapter
import com.example.serietracking.R
import com.example.serietracking.TVModel
import com.example.serietracking.TVShow
import com.example.serietracking.account.AccountService
import com.example.serietracking.network.ErrorLoggingCallback
import kotlinx.android.synthetic.main.fragment_user_tvshows.*



class UserTVShowsFragment : Fragment() {
    //TODO: si no tiene series propias, mostrar un label que diga que no tenes series.

    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var adapter: UserRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //super.onCreateView(inflater, container, savedInstanceState)
        //setContentView(R.layout.fragment_user_tvshows)



        /*val callback = object: ErrorLoggingCallback<TVModel>() {
            override fun onResponse(call: Call<TVModel>?, response: Response<TVModel>?) {
                if (response!!.isSuccessful) {
                    val tvs = response.body()
                    val tvShows: List<TVShow> = tvs.results
                    adapter = UserRecyclerAdapter(tvShows)
                    userRecyclerView.adapter = adapter

                    linearLayoutManager = LinearLayoutManager(inflater.context)
                    userRecyclerView.layoutManager = linearLayoutManager

                    inflater.inflate(com.example.serietracking.R.layout.fragment_user_tvshows, container, false)

                    //                    AccountService.getNextCaps(tvs, {(_) -> Unit})
                }
            }
        }
        AccountService.getFavorite(callback)*/

        return inflater.inflate(com.example.serietracking.R.layout.fragment_user_tvshows, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments!!
        val tvs: TVModel = args.getSerializable("tvs") as TVModel

        val tvShows: List<TVShow> = tvs.results
        adapter = UserRecyclerAdapter(tvShows)
        userRecyclerView.adapter = adapter

        linearLayoutManager = LinearLayoutManager(view.context)
        userRecyclerView.layoutManager = linearLayoutManager
    }
}
