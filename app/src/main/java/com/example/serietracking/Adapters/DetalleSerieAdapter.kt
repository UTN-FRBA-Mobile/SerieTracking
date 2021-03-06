package com.example.serietracking.Adapters
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.serietracking.Capitulo
import com.example.serietracking.R
import com.example.serietracking.TVShow
import com.example.serietracking.inflate
import kotlinx.android.synthetic.main.tvshow_details_episode.view.*
import kotlinx.android.synthetic.main.tvshowlayout.view.*

class ListAdapter(private val list: List<Capitulo>, private val tvShow: TVShow?): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    var hasHeader = true;
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (getItemViewType(position)) {
            CellType.HEADER.ordinal -> {
                val headerViewHolder = holder as headerViewHolder
                holder.bind(tvShow)
            }
            CellType.CONTENT.ordinal -> {
                val headerViewHolder = holder as MovieViewHolder
               if(tvShow==null)    holder.bind(list[position]) else
                holder.bind(list[position - 1])

            }
            CellType.FOOTER.ordinal -> {
            }
        }

    }

    override fun getItemViewType(position: Int): Int {
        if(tvShow==null) {
           return CellType.CONTENT.ordinal
        }
       return when (position) {
           0 -> CellType.HEADER.ordinal
           list.size + 1 -> CellType.FOOTER.ordinal
           else -> CellType.CONTENT.ordinal
       }
   }

    enum class CellType(viewType: Int) {
        HEADER(0),
        FOOTER(1),
        CONTENT(2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflatedView = parent.inflate(R.layout.tvshow_details_episode, false)

        return when (viewType) {
           CellType.HEADER.ordinal -> headerViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.tvshowlayout, parent, false))
            CellType.CONTENT.ordinal -> MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.tvshow_details_episode, parent, false))
      //      CellType.FOOTER.ordinal -> FooterViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.list_item_recycler_header, parent, false))
            else -> MovieViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.tvshow_details_episode, parent, false))
        }
    }

    override fun getItemCount() : Int {
        if(tvShow==null) return list.size
        return (list.size +  1)
    }
}
class headerViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
    override fun onClick(v: View?) {
     //   TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private var tvShow: TVShow? = null


    private var view: View = v

    fun bind(tvShow: TVShow?) {

        this.tvShow = tvShow
        if(tvShow!=null){
            view.itemTitle.text = tvShow?.name
            view.itemDescription.text = tvShow?.overview
            Glide.with(view.context).load("https://image.tmdb.org/t/p/w500/"+tvShow?.posterPath!!).into(view.itemImage)
        }else{
           view.itemTitle.text = "Ponele titulo"
        }
    }

}

class MovieViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener {
    private var view: View = v
    private var episode: Capitulo? = null

    init {
        v.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
      //  TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    fun bind(episode: Capitulo) {
        this.episode = episode
        view.tituloSerie.text = episode.serie
        view.numeroEpisodio.text = "S" +episode.temporada+ " | " + "E"+episode.episodio
        view.tituloEpisodio.text = episode.titulo
        view.seen.setVisibility((if (episode.seen) View.VISIBLE else View.INVISIBLE ))
    }
}