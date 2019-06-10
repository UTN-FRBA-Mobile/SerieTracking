package com.example.serietracking.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.serietracking.Adapters.ListAdapter
import com.example.serietracking.Capitulo
import com.example.serietracking.RichEpisode
import kotlinx.android.synthetic.main.fragment_details_tvshow.*

class NextEpisodesFragment : Fragment() {
    private lateinit var linearLayoutManager: LinearLayoutManager

    private lateinit var adapter: ListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(com.example.serietracking.R.layout.fragment_next_episodes, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments!!
        val episodes: List<RichEpisode> = args.getSerializable("episodes") as List<RichEpisode>

        val capitulos = episodes.map {richEpisode ->
            val episode = richEpisode.episode!!
            Capitulo(
                episode.id.toString(),
                episode.name,
                richEpisode.tv!!.name,
                episode.episodeNumber.toString()
            )
        }

        adapter = ListAdapter(capitulos)
        detailsRecyclerView.adapter = adapter

        linearLayoutManager = LinearLayoutManager(view.context)
        detailsRecyclerView.layoutManager = linearLayoutManager
    }
}