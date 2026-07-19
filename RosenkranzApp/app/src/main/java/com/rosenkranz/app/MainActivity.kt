package com.rosenkranz.app

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationManagerCompat
import com.rosenkranz.app.reminder.ReminderHelper

class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        webView = findViewById(R.id.webview)
        setupWebView()
        setupJSBridge()

        // Lade lokale HTML-Datei
        webView.loadUrl("file:///android_asset/rosenkranz.html")

        // Prüfe Benachrichtigungsberechtigung (Android 13+)
        if (!NotificationManagerCompat.from(this).areNotificationsEnabled()) {
            Toast.makeText(this, "Bitte aktiviere Benachrichtigungen für die Gebets-Erinnerung", Toast.LENGTH_LONG).show()
        }

        // Erinnerung prüfen / initialisieren
        ReminderHelper.checkAndSchedule(this)
    }

    private fun setupWebView() {
        webView.settings.apply {
            javaScriptEnabled = true
            domStorageEnabled = true
            databaseEnabled = true
            cacheMode = WebSettings.LOAD_DEFAULT
            useWideViewPort = true
            loadWithOverviewMode = true
            setSupportZoom(true)
            builtInZoomControls = true
            displayZoomControls = false
        }
        webView.webChromeClient = WebChromeClient()
        webView.webViewClient = WebViewClient()
        webView.setBackgroundColor(0xFFFAF9F5.toInt())
    }

    private fun setupJSBridge() {
        webView.addJavascriptInterface(WebAppInterface(this), "AndroidInterface")
    }

    inner class WebAppInterface(private val context: Context) {
        @JavascriptInterface
        fun openSettings() {
            runOnUiThread {
                context.startActivity(Intent(context, SettingsActivity::class.java))
            }
        }
    }

    override fun onBackPressed() {
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        // Wenn die App über Widget oder Benachrichtigung geöffnet wird
        intent?.getStringExtra("open_tab")?.let { tab ->
            webView.evaluateJavascript("window.switchToTab('$tab')", null)
        }
    }
}
