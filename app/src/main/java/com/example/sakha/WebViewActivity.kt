package com.example.sakha

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class WebViewActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_webview)

        val titleText: TextView = findViewById(R.id.webviewTitle)
        val webView: WebView = findViewById(R.id.mainWebView)

        // Get values passed
        val url = intent.getStringExtra("URL")
        val title = intent.getStringExtra("TITLE")

        // Set title
        titleText.text = title ?: "Details"

        // Configure WebView
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        if (url != null) {
            webView.loadUrl(url)
        }
    }

    override fun onBackPressed() {
        val webView: WebView = findViewById(R.id.mainWebView)
        if (webView.canGoBack()) {
            webView.goBack()
        } else {
            super.onBackPressed()
        }
    }
}
