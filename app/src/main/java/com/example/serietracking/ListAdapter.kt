package com.example.serietracking


import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ListAdapter(private val list: List<Capitulo>)
    : RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return MovieViewHolder(inflater, parent)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie: Capitulo = list[position]
        holder.bind(movie)
    }

    override fun getItemCount(): Int = list.size

}

class MovieViewHolder(inflater: LayoutInflater, parent: ViewGroup) :
    RecyclerView.ViewHolder(inflater.inflate(R.layout.capitulo_item, parent, false)) {
    private var mTitleView: TextView? = null
    private var mEpisodioView: TextView? = null
    private var mSerieView: TextView? = null
    private var mContenedor: LinearLayout? = null


    init {
        mTitleView = itemView.findViewById(R.id.titulo)
        mEpisodioView = itemView.findViewById(R.id.episodio)
        mSerieView = itemView.findViewById(R.id.serie)
        mContenedor = itemView.findViewById(R.id.contenedor)


    }

    fun bind(movie: Capitulo) {
        mTitleView?.text = movie.titulo
        mEpisodioView?.text = movie.episodio
        mSerieView?.text = movie.serie
       /// mTitleView?.setTextColor(250);

        mContenedor?.setBackgroundColor(if(movie.seen) Color.GREEN else Color.WHITE);

    }

}