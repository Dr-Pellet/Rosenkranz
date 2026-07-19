package com.rosenkranz.app.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.rosenkranz.app.R

class LargeWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        val data = WidgetDataHelper.getTodayData()

        for (appWidgetId in appWidgetIds) {
            val views = RemoteViews(context.packageName, R.layout.widget_large)

            views.setTextViewText(R.id.widget_header, "${data.weekday} · ${data.reiheName}")

            val ids = arrayOf(R.id.widget_d1, R.id.widget_d2, R.id.widget_d3, R.id.widget_d4, R.id.widget_d5)
            for (i in 0..4) {
                views.setTextViewText(ids[i], "${i + 1}. ${data.gesetze[i]}")
            }

            views.setOnClickPendingIntent(R.id.widget_container, WidgetDataHelper.createOpenAppIntent(context))

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}
