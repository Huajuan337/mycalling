package com.callingchj.mycalling

import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.database.Cursor
import android.provider.ContactsContract
import android.util.Log
import android.widget.RemoteViews
import android.widget.RemoteViewsService

class WidgetRemoteViewFactory(context: Context, intent: Intent): RemoteViewsService.RemoteViewsFactory {

    val listItem = arrayOf("item1", "item2", "item4")
    val mcontext = context

    private val TAG = "widgetRemoteViewFatory"


    override fun onCreate() {
        Log.d(TAG, "onCreate")
    }

    override fun onDataSetChanged() {
        Log.d(TAG, "onDataSetChanged")
    }

    override fun onDestroy() {
        Log.d(TAG, "onDestory")
    }

    override fun getCount(): Int {
        return listItem.size
    }

    override fun getViewAt(position: Int): RemoteViews {

        // get data into views
        val views = RemoteViews(mcontext.packageName, R.layout.widget_item)
        Log.d(TAG, "views" + views)
        views.setTextViewText(R.id.textView, listItem[position])

        return views

    }

    override fun getLoadingView(): RemoteViews? {
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

}