package com.example.serietracking.Adapters

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.serietracking.R
import com.example.serietracking.TVShow
import com.example.serietracking.inflate
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.tvshow_item.view.*
import android.content.Intent
import com.example.serietracking.Fragments.DetailsTvShowActivity


class UserRecyclerAdapter(private val tvShows: List<TVShow>): RecyclerView.Adapter<UserRecyclerAdapter.TVUserHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TVUserHolder {
        val inflatedView = parent.inflate(R.layout.tvshow_item, false)
        return TVUserHolder(inflatedView)
    }

    override fun getItemCount() = tvShows.size

    override fun onBindViewHolder(holder: TVUserHolder, position: Int) {
        val itemTVShow = tvShows[position]
        holder.bindTVShow(itemTVShow)
    }

    class TVUserHolder(v: View): RecyclerView.ViewHolder(v), View.OnClickListener {
        private var view: View = v
        private var tvShow: TVShow? = null

        override fun onClick(v: View) {
//                getActivity()!!.startActivity(intent)
        }

        fun bindTVShow(tvShow: TVShow) {
            this.tvShow = tvShow
            Picasso.with(view.context).load("https://image.tmdb.org/t/p/w500" + tvShow.posterPath).into(view.itemImage)
            view.itemTitle.text = tvShow.name
            view.itemDescription.text = tvShow.overview

            view.setOnClickListener {
                val context = itemView.context
                val intent = Intent(context.applicationContext, DetailsTvShowActivity::class.java)
                intent.putExtra("tvShow", tvShow)
                context.startActivity(intent)
            }
        }


        companion object {
            //5
            private val PHOTO_KEY = "PHOTO"
        }

    }
}