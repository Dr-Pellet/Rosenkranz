package com.rosenkranz.app.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.rosenkranz.app.R

class MediumWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        val data = WidgetDataHelper.getTodayData()

        for (appWidgetId in appWidgetIds) {
            val views = RemoteViews(context.packageName, R.layout.widget_medium)

            views.setTextViewText(R.id.widget_title, data.reiheName)
            views.setTextViewText(R.id.widget_g1, "1. ${data.gesetze[0]}")
            views.setTextViewText(R.id.widget_g2, "2. ${data.gesetze[1]}")
            views.setTextViewText(R.id.widget_g3, "3. ${data.gesetze[2]}")

            views.setOnClickPendingIntent(R.id.widget_container, WidgetDataHelper.createOpenAppIntent(context))

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}
