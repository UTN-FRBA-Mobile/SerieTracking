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
import com.example.serietracking.account.AccountService
import com.example.serietracking.network.AddToFavoriteResponse
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

    private lateinit var exploreTVShows: TVModel
    private lateinit var favoritesTvs: TVModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.example.serietracking.R.layout.fragment_explore_tvshows, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments!!
        exploreTVShows = args.getSerializable("exploreTvs") as TVModel
        favoritesTvs = args.getSerializable("favoritesTvs") as TVModel

        setAdapter()
    }

    fun setAdapter() {
        adapter = ExploreRecyclerAdapter(exploreTVShows.results, favoritesTvs.results ,object : TvShowListener {
            override fun onTvShowSelected(tvShow: TVShow, isInFavorite: Boolean) {
                val callback = object: ErrorLoggingCallback<AddToFavoriteResponse>() {
                    override fun onResponse(call: Call<AddToFavoriteResponse>, response: Response<AddToFavoriteResponse>) {
                        // TODO: Ver si tengo que sacar o meter el tvshow
                        if (isInFavorite) {
                            favoritesTvs.results.remove(tvShow)
                        }
                        else {
                            favoritesTvs.results.add(tvShow)
                        }

                        activity!!.runOnUiThread { adapter.notifyDataSetChanged() }
                    }
                }

                AccountService.addToFavorite("tv", tvShow.id, !isInFavorite, callback)
                Log.d("asd", "tv show selected")
            }
        })
        exploreRecyclerView.adapter = adapter

        linearLayoutManager = LinearLayoutManager(activity!!)
        exploreRecyclerView.layoutManager = linearLayoutManager
    }

}


