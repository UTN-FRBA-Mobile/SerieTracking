package com.example.serietracking
import android.content.Intent
import kotlinx.android.synthetic.main.activity_main.*

import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity


import com.example.serietracking.network.ApiClient
import com.example.serietracking.network.HttpConstants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper

import androidx.recyclerview.widget.RecyclerView


import com.bumptech.glide.Glide
import com.example.serietracking.Adapters.ListAdapter
import com.example.serietracking.Fragments.ExploreTVShowsFragment
import com.example.serietracking.Fragments.UserTVShowsFragment

//data class Capitulo(val title: String, val year: Int, val image: String)


//TODO: ESta impoementacion del callback no deberia ir aca, pero se hizo aca para probar que la conexion con la BD funcione bien.
class MainActivity : AppCompatActivity(), Callback<SeasonModel> {
    private val capitulos = mutableListOf<Capitulo>()

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                //message.setText(R.string.title_home)

                val intent = Intent(this, UserTVShowsFragment::class.java)
                startActivity(intent)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                //message.setText(R.string.title_dashboard)
                val intent = Intent(this, ExploreTVShowsFragment::class.java)
                startActivity(intent)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                //message.setText(R.string.title_notifications)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    val viewAdapter = ListAdapter(capitulos)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val viewManager = LinearLayoutManager(this)

        list_recycler_view.apply {
            layoutManager = viewManager
            adapter = viewAdapter
        }

        //remover
        Glide.with(this).load("https://image.tmdb.org/t/p/w500/iflq7ZJfso6WC7gk9l1tD3unWK.jpg").into(imageView)
        initSwipe()
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        ApiClient.apiInterface.getSeasonOfGOT(HttpConstants.API_KEY).enqueue(this)
    }


    private fun initSwipe() {
        val simpleItemTouchCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition

                var prefs = this@MainActivity.getSharedPreferences("preferencias", 0)
                var editor = prefs.edit()
                val capVistos = prefs!!.getStringSet("capVistos",HashSet<String>())

                if (direction == ItemTouchHelper.LEFT) {
                    capitulos[position].seen = true;
                    capVistos.add(capitulos[position].id);
                    viewAdapter.notifyDataSetChanged();
                } else {
                    capVistos.remove(capitulos[position].id);
                    capitulos[position].seen = false;
                    viewAdapter.notifyDataSetChanged();

                }
                editor.clear();
                editor.putStringSet("capVistos", capVistos );
                editor.commit();
            }
        }

        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(list_recycler_view)
        }

    override fun onResponse(call: Call<SeasonModel>?, response: Response<SeasonModel>?) {
        if (response!!.isSuccessful) {


            var prefs = this.getSharedPreferences("preferencias", 0)
            val capVistos = prefs!!.getStringSet("capVistos",HashSet<String>())

            for (item: Episode in response.body().episodes) {
                var capitulo = Capitulo(item.id.toString(), item.name.toString(),"",item.episodeNumber.toString());
                if(capVistos.contains(item.id.toString())) capitulo.seen = true;
                capitulos.add(capitulo)
            }

            viewAdapter.notifyDataSetChanged();
        }
    }

    override fun onFailure(call: Call<SeasonModel>?, t: Throwable?) {
        print("error")
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
