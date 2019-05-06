package com.example.serietracking
import kotlinx.android.synthetic.main.activity_main.*

import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


import com.example.serietracking.network.ApiClient
import com.example.serietracking.network.HttpConstants
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide

//data class Capitulo(val title: String, val year: Int, val image: String)

//TODO: ESta impoementacion del callback no deberia ir aca, pero se hizo aca para probar que la conexion con la BD funcione bien.
class MainActivity : AppCompatActivity(), Callback<TVModel> {


    private val capitulos  = listOf(
        Capitulo("Raising Arizona", "GOT","S03E04"),
        Capitulo("ME VOY A LLEVAR ANDROID A MARZO", "UTN","S03E04")
    ).toMutableList()



    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                //message.setText(R.string.title_home)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                //message.setText(R.string.title_dashboard)
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

        Log.d("log","iniciando")


        val viewManager = LinearLayoutManager(this)

        list_recycler_view.apply {
            // set a LinearLayoutManager to handle Android
            // RecyclerView behavior

            layoutManager = viewManager
            // set the custom adapter to the RecyclerView
            adapter = viewAdapter
        }


        Glide.with(this).load("https://image.tmdb.org/t/p/w500/iflq7ZJfso6WC7gk9l1tD3unWK.jpg").into(imageView)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        ApiClient.apiInterface.getPopular(HttpConstants.API_KEY).enqueue(this)


    }

    override fun onResponse(call: Call<TVModel>?, response: Response<TVModel>?) {
        if (response!!.isSuccessful) {
            //en response esta todo lo que se necesita ya modelado. ahi hay un for de como ir uno por uno viendo algo que necesitemos
            Log.d("log","response")



           for (item: TVModel.ResultsEntity in response.body().results!!) {
             capitulos.add(Capitulo(item.original_name.toString(),"123","123"))
               Log.d("log",item.backdrop_path.toString())
              // println(item.original_name)
            }

            viewAdapter.notifyDataSetChanged();
//            var recyclerAdapter = RecyclerViewAdapter(this, response.body().results!!)
//            recyclerView!!.adapter = recyclerAdapter
        }
    }

    override fun onFailure(call: Call<TVModel>?, t: Throwable?) {
        print("error")
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
