package com.example.text

import android.content.Intent
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class CheckContentActivity : AppCompatActivity() {
    private lateinit var descTextView: WebView
    private lateinit var backTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.check_content)

        descTextView = findViewById(R.id.desc)
        backTextView = findViewById(R.id.net_back)

        descTextView.setWebViewClient(WebViewClient())


        // 启用JavaScript
        val webSettings: WebSettings = descTextView.getSettings()
        webSettings.javaScriptEnabled = true

        backTextView.setOnClickListener {
            val intent = Intent(this, CheckActivity::class.java)
            startActivity(intent)
        }


        // 加载网页，替换为你要加载的网页URL
        descTextView.loadUrl("https://www.example.com")

        // 设置 WebViewClient 以在 WebView 中打开链接
        descTextView.webViewClient = WebViewClient()

    }

    override fun onDestroy() {
        super.onDestroy()
        // 正确处理 WebView 的销毁
        descTextView.destroy()
    }
}