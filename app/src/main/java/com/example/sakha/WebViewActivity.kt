package com.example.sakha

import android.annotation.SuppressLint
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class WebViewActivity : AppCompatActivity() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_webview)
        ViewCompat.setOnApplyWindowInsetsListener(window.decorView) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

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
            if (url.endsWith(".pdf")) {
                // Use Google Docs Viewer for PDFs
                val pdfViewerUrl = "https://docs.google.com/gview?embedded=true&url=$url"
                webView.loadUrl(pdfViewerUrl)
            } else {
                // Normal websites
                webView.loadUrl(url)
            }
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
