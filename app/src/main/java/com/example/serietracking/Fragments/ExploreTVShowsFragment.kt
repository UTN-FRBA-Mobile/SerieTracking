package com.example.serietracking.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.serietracking.Adapters.ExploreRecyclerAdapter
import com.example.serietracking.R
import com.example.serietracking.TVModel
import com.example.serietracking.TVShow
import com.example.serietracking.network.ApiClient
import com.example.serietracking.network.ErrorLoggingCallback
import com.example.serietracking.network.HttpConstants
import kotlinx.android.synthetic.main.fragment_explore_tvshows.*
import retrofit2.Call
import retrofit2.Response

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
class ExploreTVShowsFragment : Fragment() {
    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var adapter: ExploreRecyclerAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.example.serietracking.R.layout.fragment_explore_tvshows, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments!!
        val tvs: TVModel = args.getSerializable("tvs") as TVModel

        val tvShows: List<TVShow> = tvs.results
        adapter = ExploreRecyclerAdapter(tvShows)
        exploreRecyclerView.adapter = adapter

        linearLayoutManager = LinearLayoutManager(view.context)
        exploreRecyclerView.layoutManager = linearLayoutManager
    }

    fun addTvShowToFavorite() {
        //TODO: Agregar la query posta
//        ApiClient.apiInterface.addTVSerie
    }


}


