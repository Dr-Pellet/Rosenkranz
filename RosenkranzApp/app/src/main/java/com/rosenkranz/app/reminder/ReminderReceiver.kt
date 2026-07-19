package com.rosenkranz.app.reminder

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.rosenkranz.app.MainActivity
import com.rosenkranz.app.R

class ReminderReceiver : BroadcastReceiver() {

    companion object {
        const val CHANNEL_ID = "rosenkranz_daily"
        const val NOTIFICATION_ID = 1001
    }

    override fun onReceive(context: Context, intent: Intent) {
        showNotification(context)
        // Nächste Erinnerung planen
        ReminderHelper.scheduleNext(context)
    }

    private fun showNotification(context: Context) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Kanal erstellen (Android 8+)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                "Täglicher Rosenkranz",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Erinnerung zum täglichen Rosenkranzgebet"
                enableLights(true)
                enableVibration(true)
            }
            notificationManager.createNotificationChannel(channel)
        }

        val openIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra("open_tab", "gebet")
        }
        val pendingIntent = PendingIntent.getActivity(
            context, 0, openIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_prayer)
            .setContentTitle("Zeit für den Rosenkranz")
            .setContentText("Heute warten die ${getTodayMysteryName(context)} auf dich.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .build()

        notificationManager.notify(NOTIFICATION_ID, notification)
    }

    private fun getTodayMysteryName(context: Context): String {
        val cal = java.util.Calendar.getInstance()
        val day = cal.get(java.util.Calendar.DAY_OF_WEEK) - 1
        val mapping = arrayOf("glorreichen", "freudenreichen", "schmerzhaften", "glorreichen", "lichtreichen", "schmerzhaften", "freudenreichen")
        val names = mapOf(
            "glorreich" to "glorreichen Geheimnisse",
            "freudenreich" to "freudenreichen Geheimnisse",
            "schmerzhaft" to "schmerzhaften Geheimnisse",
            "lichtreich" to "lichtreichen Geheimnisse"
        )
        return names[mapping[day]] ?: "heutigen Geheimnisse"
    }
}
