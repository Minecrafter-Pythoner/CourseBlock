package com.dujiajun.courseblock

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.dujiajun.courseblock.helper.CourseManager

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setNavigationOnClickListener { v: View? -> finish() }
        val webView = findViewById<WebView>(R.id.webview)
        val settings = webView.settings
        settings.userAgentString = "Mozilla/5.0 (iPhone; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1 Edg/89.0.4389.72"
        settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
                val uri = request.url
                try {
                    if ("jaccount" == uri.scheme) {
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        startActivity(intent)
                        return true
                    }
                } catch (e: Exception) {
                    return true
                }
                return false
            }

            override fun onLoadResource(view: WebView, url: String) {
                super.onLoadResource(view, url)
                if (url.contains("index_initMenu.html") || url.contains("http://yjs.sjtu.edu.cn:81")) {
                    Toast.makeText(this@LoginActivity, R.string.already_logged_in, Toast.LENGTH_SHORT).show()
                    finish()
                }
            }
        }
        val courseManager = CourseManager.getInstance(applicationContext)
        val loginUrl = courseManager?.loginUrl
        if (loginUrl != null) {
            webView.loadUrl(loginUrl)
        }
    }
}