package com.rosenkranz.app.widget

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.widget.RemoteViews
import com.rosenkranz.app.R
import com.rosenkranz.app.reminder.ReminderHelper

class ReminderWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        val prefs = context.getSharedPreferences("rosenkranz_prefs", Context.MODE_PRIVATE)
        val enabled = prefs.getBoolean("reminder_enabled", true)
        val hour = prefs.getInt("reminder_hour", 20)
        val minute = prefs.getInt("reminder_minute", 0)

        for (appWidgetId in appWidgetIds) {
            val views = RemoteViews(context.packageName, R.layout.widget_reminder)

            if (enabled) {
                views.setTextViewText(R.id.widget_reminder_status, "Erinnerung: ${String.format("%02d:%02d", hour, minute)} Uhr")
                views.setTextViewText(R.id.widget_reminder_toggle, "Deaktivieren")
            } else {
                views.setTextViewText(R.id.widget_reminder_status, "Erinnerung: Deaktiviert")
                views.setTextViewText(R.id.widget_reminder_toggle, "Aktivieren")
            }

            // Öffne App
            views.setOnClickPendingIntent(R.id.widget_container, WidgetDataHelper.createOpenAppIntent(context))

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }
    }
}
