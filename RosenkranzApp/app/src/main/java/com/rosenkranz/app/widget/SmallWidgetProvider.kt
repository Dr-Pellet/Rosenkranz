package com.rosenkranz.app.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.rosenkranz.app.R

class SmallWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        val data = WidgetDataHelper.getTodayData()

        for (appWidgetId in appWidgetIds) {
            val views = RemoteViews(context.packageName, R.layout.widget_small)

            views.setTextViewText(R.id.widget_weekday, data.weekday)
            views.setTextViewText(R.id.widget_reihe, data.reiheName)
            views.setTextViewText(R.id.widget_date, data.date)

            // Klick öffnet App
            views.setOnClickPendingIntent(R.id.widget_container, WidgetDataHelper.createOpenAppIntent(context))

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }

    override fun onEnabled(context: Context) {
        WidgetDataHelper.updateAllWidgets(context)
    }
}
