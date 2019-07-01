package com.example.serietracking.widget

import android.widget.RemoteViewsService
import android.widget.RemoteViews
import com.example.serietracking.R
import android.content.Intent
import com.example.serietracking.RichEpisode
import com.example.serietracking.account.AccountService
import java.util.concurrent.CopyOnWriteArrayList
import android.appwidget.AppWidgetManager
import android.content.Context


class SerieTrackingRemoteViewsFactory(val context: Context, intent: Intent): RemoteViewsService.RemoteViewsFactory {
    private val appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID)
    private var episodes: List<RichEpisode> = AccountService.getCachedFavoriteNextCaps()

    override fun onCreate() {
        episodes = AccountService.getCachedFavoriteNextCaps()
    }

    override fun onDestroy() {
        // In onDestroy() you should tear down anything that was setup for your data source,
        // eg. cursors, connections, etc.
        //episodes.clear()
    }

    override fun getCount(): Int {
        return episodes.size
    }

    override fun getViewAt(position: Int): RemoteViews {
        val remoteView = RemoteViews(context.packageName, R.layout.tvshow_details_episode)
        val episode = episodes.get(position)
        remoteView.setTextViewText(R.id.tituloSerie, episode.tv!!.name)
        remoteView.setTextViewText(R.id.numeroEpisodio, "S" + episode.episode!!.seasonNumber + " | " + "E" + episode.episode!!.episodeNumber)
        remoteView.setTextViewText(R.id.tituloEpisodio, episode.episode!!.name)

        return remoteView
    }

    override fun getLoadingView(): RemoteViews? {
        // You can create a custom loading view (for instance when getViewAt() is slow.) If you
        // return null here, you will get the default loading view.
        return null
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    override fun onDataSetChanged() {
        // This is triggered when you call AppWidgetManager notifyAppWidgetViewDataChanged
        // on the collection view corresponding to this factory. You can do heaving lifting in
        // here, synchronously. For example, if you need to process an image, fetch something
        // from the network, etc., it is ok to do it here, synchronously. The widget will remain
        // in its current state while work is being done here, so you don't need to worry about
        // locking up the widget.
    }
}