package com.rosenkranz.app.widget

import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.rosenkranz.app.R
import java.util.Calendar
import java.util.Locale

object WidgetDataHelper {

    private val WOCHENTAGE = arrayOf("Sonntag", "Montag", "Dienstag", "Mittwoch", "Donnerstag", "Freitag", "Samstag")
    private val MONATE = arrayOf("Januar", "Februar", "März", "April", "Mai", "Juni", "Juli", "August", "September", "Oktober", "November", "Dezember")

    // Zuordnung: So & Mi = glorreich | Mo & Sa = freudenreich | Di & Fr = schmerzhaft | Do = lichtreich
    private val TAG_ZU_REIHE = arrayOf("glorreich", "freudenreich", "schmerzhaft", "glorreich", "lichtreich", "schmerzhaft", "freudenreich")

    private val GEHEIMNISSE = mapOf(
        "freudenreich" to mapOf(
            "name" to "Freudenreiche Geheimnisse",
            "adj" to "freudenreich",
            "gesetze" to listOf(
                "Die Verkündigung des Herrn",
                "Die Heimsuchung Mariens",
                "Die Geburt Christi",
                "Die Darstellung Jesu im Tempel",
                "Das Wiederfinden Jesu im Tempel"
            )
        ),
        "schmerzhaft" to mapOf(
            "name" to "Schmerzhafte Geheimnisse",
            "adj" to "schmerzhaft",
            "gesetze" to listOf(
                "Das Todesgebet Jesu am Ölberg",
                "Die Geißelung Jesu",
                "Die Dornenkrönung Jesu",
                "Die Kreuztragung Jesu",
                "Die Kreuzigung Jesu"
            )
        ),
        "glorreich" to mapOf(
            "name" to "Glorreiche Geheimnisse",
            "adj" to "glorreich",
            "gesetze" to listOf(
                "Die Auferstehung Jesu",
                "Die Himmelfahrt Jesu",
                "Die Sendung des Heiligen Geistes",
                "Die Aufnahme Mariens in den Himmel",
                "Die Krönung Mariens"
            )
        ),
        "lichtreich" to mapOf(
            "name" to "Lichtreiche Geheimnisse",
            "adj" to "lichtreich",
            "gesetze" to listOf(
                "Die Taufe Jesu im Jordan",
                "Die Hochzeit zu Kana",
                "Die Verkündigung des Reiches Gottes",
                "Die Verklärung Jesu",
                "Die Einsetzung der Eucharistie"
            )
        )
    )

    fun getTodayData(): TodayData {
        val cal = Calendar.getInstance()
        val dayOfWeek = cal.get(Calendar.DAY_OF_WEEK) - 1 // 0 = Sonntag
        val reiheKey = TAG_ZU_REIHE[dayOfWeek]
        val reihe = GEHEIMNISSE[reiheKey]!!

        return TodayData(
            weekday = WOCHENTAGE[dayOfWeek],
            date = "${cal.get(Calendar.DAY_OF_MONTH)}. ${MONATE[cal.get(Calendar.MONTH)]} ${cal.get(Calendar.YEAR)}",
            reiheName = reihe["name"] as String,
            reiheAdj = reihe["adj"] as String,
            gesetze = reihe["gesetze"] as List<String>,
            reiheKey = reiheKey
        )
    }

    fun createOpenAppIntent(context: Context): android.app.PendingIntent {
        val intent = android.content.Intent(context, com.rosenkranz.app.MainActivity::class.java).apply {
            flags = android.content.Intent.FLAG_ACTIVITY_NEW_TASK or android.content.Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        return android.app.PendingIntent.getActivity(
            context, 0, intent,
            android.app.PendingIntent.FLAG_UPDATE_CURRENT or android.app.PendingIntent.FLAG_IMMUTABLE
        )
    }

    fun updateAllWidgets(context: Context) {
        val manager = AppWidgetManager.getInstance(context)

        // Update Small Widget
        val smallIds = manager.getAppWidgetIds(ComponentName(context, SmallWidgetProvider::class.java))
        if (smallIds.isNotEmpty()) SmallWidgetProvider().onUpdate(context, manager, smallIds)

        // Update Medium Widget
        val mediumIds = manager.getAppWidgetIds(ComponentName(context, MediumWidgetProvider::class.java))
        if (mediumIds.isNotEmpty()) MediumWidgetProvider().onUpdate(context, manager, mediumIds)

        // Update Large Widget
        val largeIds = manager.getAppWidgetIds(ComponentName(context, LargeWidgetProvider::class.java))
        if (largeIds.isNotEmpty()) LargeWidgetProvider().onUpdate(context, manager, largeIds)

        // Update Reminder Widget
        val reminderIds = manager.getAppWidgetIds(ComponentName(context, ReminderWidgetProvider::class.java))
        if (reminderIds.isNotEmpty()) ReminderWidgetProvider().onUpdate(context, manager, reminderIds)
    }

    data class TodayData(
        val weekday: String,
        val date: String,
        val reiheName: String,
        val reiheAdj: String,
        val gesetze: List<String>,
        val reiheKey: String
    )
}
