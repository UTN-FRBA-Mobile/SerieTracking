package com.example.serietracking.Fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.SearchView
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
import kotlin.math.exp

// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
class ExploreTVShowsFragment : Fragment() {
    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var adapter: ExploreRecyclerAdapter

    private lateinit var exploreTVShows: TVModel
    private lateinit var favoritesTvs: TVModel

    private lateinit var tvSeriesSearchView: SearchView

    private var page: Long = 1

    private var isSearching = false

    private var textToSearch = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.example.serietracking.R.layout.fragment_explore_tvshows, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val args = arguments!!
        exploreTVShows = args.getSerializable("exploreTvs") as TVModel
        favoritesTvs = args.getSerializable("favoritesTvs") as TVModel
        tvSeriesSearchView = searchView

        setAdapter()

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextChange(s: String): Boolean {
                // make a server call
                return true
            }
            override fun onQueryTextSubmit(s: String): Boolean {
                var firstSearch = false
                if (!isSearching || s != textToSearch) {
                    page = 1
                    isSearching = true
                    firstSearch = true
                    val imm = activity!!.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                    imm.hideSoftInputFromWindow(view!!.getWindowToken(), 0)
                }
                textToSearch = s
                getSearchService(textToSearch, firstSearch)
                return true
            }
        })
    }

    fun setAdapter() {
        adapter = ExploreRecyclerAdapter(exploreTVShows.results, favoritesTvs.results ,object : TvShowListener {
            override fun onTvShowSelected(tvShow: TVShow, isInFavorite: Boolean) {
                val callback = object: ErrorLoggingCallback<AddToFavoriteResponse>() {
                    override fun onResponse(call: Call<AddToFavoriteResponse>, response: Response<AddToFavoriteResponse>) {
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
            }

            override fun getMorePages() {
                moreTvShows()
            }

            override fun onTvShowUserSelected(tvShow: TVShow) {
                val intent = Intent(getActivity(), DetailsTvShowActivity::class.java)
                intent.putExtra("tvShow", tvShow)
                getActivity()!!.startActivity(intent)
            }

        })
        exploreRecyclerView.adapter = adapter
        linearLayoutManager = LinearLayoutManager(activity!!)
        if (page > 1) {
            linearLayoutManager.scrollToPosition(((page.toInt() - 1) * 20) - 3)
        }
        exploreRecyclerView.layoutManager = linearLayoutManager
    }


    fun getSearchService(textToSearch: String, firstSearch: Boolean) {
        val callbackSearch = object: ErrorLoggingCallback<TVModel>() {
            override fun onResponse(call: Call<TVModel>?, response: Response<TVModel>?) {
                if (response!!.isSuccessful) {
                    if (firstSearch) {
                        exploreTVShows = response.body()
                    }
                    else {
                        exploreTVShows.results.addAll(response.body().results)
                    }
                    setAdapter()
                }
            }
        }
        ApiClient.apiInterface.searchTvShows(HttpConstants.API_KEY, textToSearch, page).enqueue(callbackSearch)
    }

    fun moreTvShows(){
        if (page.toInt() == exploreTVShows.totalPages) {
            return //Llego a la ultima página
        }

        page = page + 1
        if (isSearching) {
            getSearchService(textToSearch, false)
        }
        else {
            val callbackTV = object : ErrorLoggingCallback<TVModel>() {
                override fun onResponse(call: Call<TVModel>?, response: Response<TVModel>?) {
                    if (response!!.isSuccessful) {
                        val moreTvShows = response.body().results
                        exploreTVShows.results.addAll(moreTvShows)
                        setAdapter()
                    }
                }
            }
            ApiClient.apiInterface.getPopular(HttpConstants.API_KEY, page = page).enqueue(callbackTV)
        }
    }

}


