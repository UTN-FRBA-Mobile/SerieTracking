package com.example.serietracking

import android.content.Intent
import androidx.fragment.app.Fragment
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import com.example.serietracking.Fragments.UserTVShowsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class NavigationFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.navigation_fragment, container, false)

        val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_home -> {
                    //message.setText(R.string.title_home)

                    val intent = Intent(activity, UserTVShowsFragment::class.java)
                    intent.putExtra("strategy", "all")
                    startActivity(intent)

                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_dashboard -> {
                    //message.setText(R.string.title_dashboard)
                    return@OnNavigationItemSelectedListener true
                }
                R.id.navigation_notifications -> {
                    val intent = Intent(activity, UserTVShowsFragment::class.java)
                    intent.putExtra("strategy", "user")
                    startActivity(intent)

                    //message.setText(R.string.title_notifications)
                    return@OnNavigationItemSelectedListener true
                }
            }
            false
        }

        view.findViewById<BottomNavigationView>(R.id.navigation).setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        return view
    }
}