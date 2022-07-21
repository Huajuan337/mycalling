package com.callingchj.mycalling

import android.content.Intent
import android.util.Log
import android.widget.RemoteViewsService

class WidgetRemoteViewsService : RemoteViewsService() {
    private val tag = "WidgetRemoteViewsService"
    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return WidgetRemoteViewFactory(applicationContext, intent!!)
    }
}