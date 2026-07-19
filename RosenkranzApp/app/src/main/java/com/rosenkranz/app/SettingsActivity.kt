package com.rosenkranz.app

import android.app.TimePickerDialog
import android.os.Bundle
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.rosenkranz.app.reminder.ReminderHelper
import java.util.Calendar

class SettingsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val switchReminder = findViewById<Switch>(R.id.switch_reminder)
        val tvTime = findViewById<TextView>(R.id.tv_reminder_time)

        val prefs = getSharedPreferences("rosenkranz_prefs", MODE_PRIVATE)
        val enabled = prefs.getBoolean("reminder_enabled", true)
        val hour = prefs.getInt("reminder_hour", 20)
        val minute = prefs.getInt("reminder_minute", 0)

        switchReminder.isChecked = enabled
        tvTime.text = String.format("%02d:%02d", hour, minute)

        switchReminder.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("reminder_enabled", isChecked).apply()
            if (isChecked) {
                ReminderHelper.schedule(this, hour, minute)
                Toast.makeText(this, "Erinnerung aktiviert", Toast.LENGTH_SHORT).show()
            } else {
                ReminderHelper.cancel(this)
                Toast.makeText(this, "Erinnerung deaktiviert", Toast.LENGTH_SHORT).show()
            }
        }

        tvTime.setOnClickListener {
            TimePickerDialog(this, { _, h, m ->
                prefs.edit().putInt("reminder_hour", h).putInt("reminder_minute", m).apply()
                tvTime.text = String.format("%02d:%02d", h, m)
                if (switchReminder.isChecked) {
                    ReminderHelper.schedule(this, h, m)
                }
                Toast.makeText(this, "Uhrzeit gespeichert", Toast.LENGTH_SHORT).show()
            }, hour, minute, true).show()
        }
    }
}
