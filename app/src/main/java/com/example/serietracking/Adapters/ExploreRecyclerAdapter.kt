package com.example.serietracking.Adapters

import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.serietracking.R
import com.example.serietracking.TVModel
import com.example.serietracking.inflate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.tvshow_item.view.*

class ExploreRecyclerAdapter(private val tvShows: List<TVModel.TVShow>) : RecyclerView.Adapter<ExploreRecyclerAdapter.TVHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVHolder {
        val inflatedView = parent.inflate(R.layout.explore_tvshow_item, false)
        return TVHolder(inflatedView)
    }

    override fun getItemCount() = tvShows.size

    override fun onBindViewHolder(holder: TVHolder, position: Int) {
        val itemTVShow = tvShows[position]
        holder.bindTVShow(itemTVShow)
    }

    class TVHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var tvShow: TVModel.TVShow? = null

        //3
        init {
            v.setOnClickListener(this)
        }

        //4
        //TODO: El click de la celda
        override fun onClick(v: View) {
            Log.d("RecyclerView", "CLICK!")

        }

        fun bindTVShow(tvShow: TVModel.TVShow) {
            this.tvShow = tvShow
            Picasso.with(view.context).load("https://image.tmdb.org/t/p/w500" + tvShow.posterPath).into(view.itemImage)
//            Glide.with(view.context).load("https://image.tmdb.org/t/p/w500/" + tvShow.posterPath).into(view.itemImage)

            view.itemTitle.text = tvShow.name
            view.itemDescription.text = tvShow.overview
        }


        companion object {
            //5
            private val PHOTO_KEY = "PHOTO"
        }

    }
}