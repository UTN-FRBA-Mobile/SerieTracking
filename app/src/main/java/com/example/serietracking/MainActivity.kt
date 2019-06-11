package com.example.serietracking

import android.content.Intent
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.serietracking.Fragments.DetailsTvShowFragment
import com.example.serietracking.Fragments.ExploreTVShowsFragment
import com.example.serietracking.Fragments.UserTVShowsFragment
import kotlinx.android.synthetic.main.activity_main.*
import androidx.fragment.app.Fragment
import com.example.serietracking.Fragments.NextEpisodesFragment

import com.example.serietracking.account.AccountService
import com.example.serietracking.network.ApiClient
import com.example.serietracking.network.ErrorLoggingCallback
import com.example.serietracking.network.HttpConstants
import retrofit2.Call
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_notifications -> {
                val callback = object: ErrorLoggingCallback<TVModel>() {
                    override fun onResponse(call: Call<TVModel>?, response: Response<TVModel>?) {
                        if (response!!.isSuccessful) {
                            val tvs = response.body()
                            val fragment = UserTVShowsFragment()
                            val args = Bundle()
                            args.putSerializable("tvs", tvs)
                            fragment.arguments = args

                            openFragment(fragment)
                        }
                    }
                }
                AccountService.getFavorite(callback)


                //message.setText(R.string.title_home)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                val callback = object: ErrorLoggingCallback<TVModel>() {
                    override fun onResponse(call: Call<TVModel>?, response: Response<TVModel>?) {
                        if (response!!.isSuccessful) {
                            val tvs = response.body()
                            val fragment = ExploreTVShowsFragment()
                            val args = Bundle()
                            args.putSerializable("tvs", tvs)
                            fragment.arguments = args

                            openFragment(fragment)
                        }
                    }
                }
                ApiClient.apiInterface.getPopular(HttpConstants.API_KEY).enqueue(callback)

                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_home -> {
                openHome()
                //message.setText(R.string.title_notifications)
                //val intent = Intent(this, DetailsTvShowFragment::class.java)
                //startActivity(intent)

                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun openHome() {
        AccountService.getFavoriteNextCaps { richEpisodes ->
            val fragment = NextEpisodesFragment()
            val args = Bundle()
            args.putSerializable("episodes", richEpisodes as java.io.Serializable)
            fragment.arguments = args

            openFragment(fragment)
        }
    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(fragment_container.id, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        openHome()
    }
}
