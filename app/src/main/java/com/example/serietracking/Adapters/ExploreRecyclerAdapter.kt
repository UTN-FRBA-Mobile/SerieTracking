package com.example.serietracking.Adapters

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.serietracking.R
import com.example.serietracking.TVModel
import com.example.serietracking.TVShow
import com.example.serietracking.inflate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.explore_tvshow_item.view.*
import kotlinx.android.synthetic.main.tvshow_item.view.*
import java.util.logging.Logger

class ExploreRecyclerAdapter(private val tvShows: List<TVShow>,
                             private val favoritesId: MutableList<Long>,
        private val listener: TvShowListener
) : RecyclerView.Adapter<ExploreRecyclerAdapter.TVHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVHolder {
        val inflatedView = parent.inflate(R.layout.explore_tvshow_item, false)
        return TVHolder(inflatedView, favoritesId)
    }

    override fun getItemCount() = tvShows.size

    override fun onBindViewHolder(holder: TVHolder, position: Int) {
        val itemTVShow = tvShows[position]
        holder.bindTVShow(itemTVShow, listener)
    }

    class TVHolder(v: View, favoritesId: MutableList<Long>) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var tvShow: TVShow? = null
        private var favoritesId: MutableList<Long> = favoritesId

        //4
        //TODO: El click de la celda
        override fun onClick(v: View) {
            Log.d("RecyclerView", "CLICK!")
        }

        fun bindTVShow(tvShow: TVShow, listener: TvShowListener) {
            this.tvShow = tvShow
            Picasso.with(view.context).load("https://image.tmdb.org/t/p/w500" + tvShow.posterPath).into(view.itemImage)

            view.itemTitle.text = tvShow.name
            view.itemDescription.text = tvShow.overview
            view.addButton.text = "AGREGAR SERIE A FAVORITOS"
            if (tvShowIsInFavoriteList(tvShow)) {
                view.addButton.text = "REMOVER SERIE DE MIS FAVORITOS"
            }

            view.addButton.setOnClickListener {

                if (tvShowIsInFavoriteList(tvShow)) {
                    view.addButton.text = "AGREGAR SERIE A FAVORITOS"
                }
                else {
                    view.addButton.text = "REMOVER SERIE DE MIS FAVORITOS"

                }
                listener.onTvShowSelected(tvShow, tvShowIsInFavoriteList(tvShow))
            }
        }


        companion object {
            //5
            private val PHOTO_KEY = "PHOTO"
        }

        //TODO: Pasarlo a una clase afuera asi no hay tanto pasamanos?
        fun tvShowIsInFavoriteList(tvShow: TVShow): Boolean {
            return favoritesId.contains(tvShow.id)
        }
    }
}

interface TvShowListener {
    fun onTvShowSelected(tvShow: TVShow, isInFavorite: Boolean)
}