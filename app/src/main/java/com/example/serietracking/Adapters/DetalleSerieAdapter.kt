package com.example.serietracking.Adapters
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.serietracking.Capitulo
import com.example.serietracking.R
import com.example.serietracking.inflate
import kotlinx.android.synthetic.main.tvshow_details_episode.view.*

class ListAdapter(private val list: List<Capitulo>): RecyclerView.Adapter<MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val inflatedView = parent.inflate(R.layout.tvshow_details_episode, false)
        return MovieViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie: Capitulo = list[position]
        holder.bind(movie)
    }
    override fun getItemCount(): Int = list.size
}

class MovieViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
    private var view: View = v
    private var episode: Capitulo? = null

    init {
        v.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun bind(episode: Capitulo) {
        this.episode = episode
        view.titleTextView.text = episode.titulo
        view.episodeTextView.text = ""
        view.numberOfEpisodeTextView.text = episode.episodio
        view.contenedor.setBackgroundColor(if (episode.seen) Color.GREEN else Color.WHITE )
    }
}