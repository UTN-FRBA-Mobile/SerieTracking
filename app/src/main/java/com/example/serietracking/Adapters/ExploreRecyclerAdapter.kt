package com.example.serietracking.Adapters

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.serietracking.R
import com.example.serietracking.TVShow
import com.example.serietracking.inflate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.explore_tvshow_item.view.*
import kotlinx.android.synthetic.main.tvshow_item.view.*

class ExploreRecyclerAdapter(private val tvShows: MutableList<TVShow>,
                             private val favorites: MutableList<TVShow>,
        private val listener: TvShowListener, private  val scrollTo: Int = 0
) : RecyclerView.Adapter<ExploreRecyclerAdapter.TVHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVHolder {
        val inflatedView = parent.inflate(R.layout.explore_tvshow_item, false)
        return TVHolder(inflatedView)
    }

    override fun getItemCount() = tvShows.size

    override fun onBindViewHolder(holder: TVHolder, position: Int) {
        val itemTVShow = tvShows[position]
        val isFavorite = favorites.count { it.id == itemTVShow.id } != 0
        var getMore = tvShows.size - 1 == position
        if (getMore) {
            listener.getMorePages()
        }
        holder.bindTVShow(itemTVShow, isFavorite, listener)
    }



    class TVHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var tvShow: TVShow? = null

        //4
        //TODO: El click de la celda
        override fun onClick(v: View) {
            Log.d("RecyclerView", "CLICK!")
        }

        fun bindTVShow(tvShow: TVShow, isFavorite: Boolean, listener: TvShowListener) {
            this.tvShow = tvShow
            Picasso.with(view.context).load("https://image.tmdb.org/t/p/w500" + tvShow.posterPath).into(view.itemImage)

            view.itemTitle.text = tvShow.name
            view.itemDescription.text = tvShow.overview
            view.addButton.text = "AGREGAR SERIE A FAVORITOS"
            if (isFavorite) {
                view.addButton.text = "REMOVER SERIE DE MIS FAVORITOS"
            }

            view.setOnClickListener {
                listener.onTvShowUserSelected(tvShow)
            }

            view.addButton.setOnClickListener {
                listener.onTvShowSelected(tvShow, isFavorite)
            }
        }
        
        companion object {
            //5
            private val PHOTO_KEY = "PHOTO"
        }
    }
}

interface TvShowListener {
    fun onTvShowSelected(tvShow: TVShow, isInFavorite: Boolean)
    fun getMorePages()
    fun onTvShowUserSelected(tvShow: TVShow)
}