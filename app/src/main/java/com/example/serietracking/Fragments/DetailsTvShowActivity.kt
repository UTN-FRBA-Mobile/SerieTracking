package com.example.serietracking.Fragments

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.serietracking.*
import com.example.serietracking.Adapters.ListAdapter
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

        val callback = object : ErrorLoggingCallback<SeasonModel>() {
            override fun onResponse(call: Call<SeasonModel>?, response: Response<SeasonModel>?) {
                if (response!!.isSuccessful) {

                    linearLayoutManager = LinearLayoutManager(thisActivity)
                    detailsRecyclerView.layoutManager = linearLayoutManager

//                    val myPrefs = getSharedPreferences("myPrefs", Context.MODE_WORLD_READABLE)

                    var prefs = getSharedPreferences("preferencias", 0)
                    val capVistos = prefs!!.getStringSet("capVistos", HashSet<String>())

                    for (episode in response.body().episodes) {
                        var capitulo = Capitulo(
                            episode.id.toString(),
                            episode.name.toString(),
                            "",
                            episode.episodeNumber.toString()
                        );
                        if (capVistos.contains(episode.id.toString())) capitulo.seen = true;
                        capitulos.add(capitulo)
                    }
                    adapter = ListAdapter(capitulos, tvShow)
                    detailsRecyclerView.adapter = adapter
                }
            }
        }
        initSwipe()
//        getSeriesAndEpisodes()
        ApiClient.apiInterface.getSeasonOfGOT(HttpConstants.API_KEY).enqueue(callback)
    }

    private fun initSwipe() {
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                var prefs = getSharedPreferences("preferencias", 0)
                var editor = prefs.edit()
                val capVistos = prefs!!.getStringSet("capVistos",HashSet<String>())

                if (direction == ItemTouchHelper.LEFT) {
                    capitulos[position].seen = true;
                    capVistos.add(capitulos[position].id);
                    adapter.notifyDataSetChanged();
                } else {
                    capVistos.remove(capitulos[position].id);
                    capitulos[position].seen = false;
                    adapter.notifyDataSetChanged();

                }
                editor.clear();
                editor.putStringSet("capVistos", capVistos );
                editor.commit();
            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(detailsRecyclerView)
    }


//    var seasons: List<SeasonModel>
    private fun getSeriesAndEpisodes() {
        val callback = object: ErrorLoggingCallback<TVCompleteModel>() {
            override fun onResponse(call: Call<TVCompleteModel>?, response: Response<TVCompleteModel>?) {
                if (response!!.isSuccessful) {
                    val tvCompleteModelSeason = response.body()
                    val _seasons:List<TVCompleteSeasonModel> = tvCompleteModelSeason.seasons


//                    val callback2 = object : ErrorLoggingCallback<SeasonModel>() {
//                        override fun onResponse(call: Call<SeasonModel>?, response: Response<SeasonModel>?) {
//                            if (response!!.isSuccessful) {
//
//                            }
//                        }
//                    }
//
//                    for (season in _seasons) {
//                        ApiClient.apiInterface.getSeason(tvShow!!.id, season.seasonNumber, HttpConstants.API_KEY).enqueue(callback2)
//                    }

                }
            }
        }

        ApiClient.apiInterface.getTV(tvShow!!.id, HttpConstants.API_KEY).enqueue(callback)

    }
}