package com.example.serietracking.Fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.serietracking.Adapters.ExploreRecyclerAdapter
import com.example.serietracking.Adapters.TvShowListener
import com.example.serietracking.TVModel
import com.example.serietracking.TVShow
import com.example.serietracking.network.ApiClient
import com.example.serietracking.network.ApiInterface
import kotlinx.android.synthetic.main.fragment_explore_tvshows.*
import java.text.FieldPosition

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
        adapter = ExploreRecyclerAdapter(tvShows, object : TvShowListener {
            override fun onTvShowSelected(tvShow: TVShow, position: Int) {
                //TODO: Agregar a favoritos
//                ApiClient.apiInterface.
                Log.d("asd", "tv show selected")
            }
        })
        exploreRecyclerView.adapter = adapter

        linearLayoutManager = LinearLayoutManager(view.context)
        exploreRecyclerView.layoutManager = linearLayoutManager
    }
}


