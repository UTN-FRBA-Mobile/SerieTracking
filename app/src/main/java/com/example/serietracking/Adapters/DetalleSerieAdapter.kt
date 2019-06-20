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

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        when (getItemViewType(position)) {
            CellType.HEADER.ordinal -> {
                val headerViewHolder = holder as headerViewHolder

                //      val headerViewHolder = holder as HeaderViewHolder
                holder.bind(tvShow)
            }
            CellType.CONTENT.ordinal -> {
                val headerViewHolder = holder as MovieViewHolder
                holder.bind(list[position - 1])
                //           headerViewHolder.bindView(listOfMovies[position - 1])

            }
            CellType.FOOTER.ordinal -> {
                //       val footerViewHolder = holder as FooterViewHolder
                //      footerViewHolder.bindView()
            }
        }

        //val movie: Capitulo = list[position]
        // holder.bind(movie)
    }


    override fun getItemViewType(position: Int): Int {
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

    //    return MovieViewHolder(inflatedView)
    }






    override fun getItemCount(): Int = list.size +  1
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
        view.titleTextView.text = episode.titulo
        view.episodeTextView.text = ""
        view.numberOfEpisodeTextView.text = episode.episodio
        view.contenedor.setBackgroundColor(if (episode.seen) Color.GREEN else Color.WHITE )
    }
}