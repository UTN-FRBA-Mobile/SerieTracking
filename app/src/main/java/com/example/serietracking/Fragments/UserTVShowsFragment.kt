package com.example.serietracking.Fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.serietracking.Adapters.UserRecyclerAdapter
import com.example.serietracking.TVModel
import com.example.serietracking.TVShow
import kotlinx.android.synthetic.main.fragment_user_tvshows.*
import com.example.serietracking.Adapters.UserTvShowListener


class UserTVShowsFragment : Fragment() {
    //TODO: si no tiene series propias, mostrar un label que diga que no tenes series.

    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var adapter: UserRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.example.serietracking.R.layout.fragment_user_tvshows, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments!!
        val tvs: TVModel = args.getSerializable("tvs") as TVModel

        val tvShows: List<TVShow> = tvs.results
        adapter = UserRecyclerAdapter(tvShows, object :UserTvShowListener {
            override fun onTvShowUserSelected(tvShow: TVShow) {
                val intent = Intent(getActivity(), DetailsTvShowActivity::class.java)
                intent.putExtra("tvShow", tvShow)
                getActivity()!!.startActivity(intent)
            }
        })
        userRecyclerView.adapter = adapter

        linearLayoutManager = LinearLayoutManager(view.context)
        userRecyclerView.layoutManager = linearLayoutManager
    }
}
