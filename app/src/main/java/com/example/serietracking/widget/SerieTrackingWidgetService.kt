package com.example.serietracking.widget

import android.widget.RemoteViewsService
import android.content.Intent



class SerieTrackingWidgetService: RemoteViewsService() {
    override fun onGetViewFactory(intent: Intent): RemoteViewsService.RemoteViewsFactory {
        return SerieTrackingRemoteViewsFactory(this.applicationContext, intent)
    }
}