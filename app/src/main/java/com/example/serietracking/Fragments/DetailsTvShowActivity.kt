package com.example.serietracking.Fragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serietracking.*
import com.example.serietracking.Adapters.ListAdapter
import com.example.serietracking.Adapters.headerViewHolder
import com.example.serietracking.multicall.Callable
import com.example.serietracking.multicall.MultiCallProccesor
import com.example.serietracking.network.ApiClient
import com.example.serietracking.network.ErrorLoggingCallback
import com.example.serietracking.network.HttpConstants
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_details_tvshow.*
import retrofit2.Call
import retrofit2.Response


class DetailsTvShowActivity : AppCompatActivity() {
    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var adapter: ListAdapter
    private val capitulos = mutableListOf<Capitulo>()
    private var tvShow:TVShow? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_details_tvshow)

        val thisActivity = this
        tvShow = intent.extras.getSerializable("tvShow") as? TVShow

        initSwipe()
        getSeasons { listSeasonModel ->
            linearLayoutManager = LinearLayoutManager(thisActivity)
            detailsRecyclerView.layoutManager = linearLayoutManager

//          val myPrefs = getSharedPreferences("myPrefs", Context.MODE_WORLD_READABLE)

            var prefs = getSharedPreferences("preferencias", 0)
            val capVistos = prefs!!.getStringSet("capVistos", HashSet<String>())

            var listSeasonModelOrdered = listSeasonModel.sortedBy { seasonModel -> seasonModel.seasonNumber }
            for (season in listSeasonModelOrdered) {

                for (episode in season.episodes) {
                    var capitulo = Capitulo(
                        episode.id.toString(),
                        episode.name,
                        tvShow?.name.toString(),
                        episode.episodeNumber.toString(),
                        (episode.seasonNumber + 1).toString()
                    )
                    if (capVistos.contains(episode.id.toString())) capitulo.seen = true
                    capitulos.add(capitulo)
                }
            }
            adapter = ListAdapter(capitulos, tvShow)
            detailsRecyclerView.adapter = adapter
        }
    }

    private fun initSwipe() {
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun getSwipeDirs(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                return if (viewHolder is headerViewHolder) 0 else super.getSwipeDirs(recyclerView, viewHolder)
            }
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                var prefs = getSharedPreferences("preferencias", 0)
                var editor = prefs.edit()
                val capVistos = prefs!!.getStringSet("capVistos",HashSet<String>())

                if (direction == ItemTouchHelper.LEFT) {
                    capitulos[position-1].seen = true
                    capVistos.add(capitulos[position-1].id)
                    adapter.notifyDataSetChanged()
                } else {
                    capVistos.remove(capitulos[position-1].id)
                    capitulos[position-1].seen = false
                    adapter.notifyDataSetChanged()

                }
                editor.clear()
                editor.putStringSet("capVistos", capVistos )
                editor.commit()
            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(detailsRecyclerView)
    }

    fun getSeasons(callback: (List<SeasonModel>) -> Unit) {
        ApiClient.apiInterface.getTV(tvShow!!.id, HttpConstants.API_KEY).enqueue(object: ErrorLoggingCallback<TVCompleteModel>() {
            override fun onResponse(call: Call<TVCompleteModel>, response: Response<TVCompleteModel>) {
                val tvComplete = response.body()!!
                val seasonCalls = tvComplete.seasons.map {
                    Callable(ApiClient.apiInterface.getSeason(tvComplete.id, it.seasonNumber, HttpConstants.API_KEY), { s -> return@Callable s })
                }
                MultiCallProccesor(seasonCalls).enqueue(callback)
            }
        })
    }

}
