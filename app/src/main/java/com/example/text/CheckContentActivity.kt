package com.example.text

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CheckContentActivity : AppCompatActivity() {
    private lateinit var backTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.check_content)

        backTextView = findViewById(R.id.net_back)

        backTextView.setOnClickListener(View.OnClickListener { v: View? ->
            val intent = Intent(
                this,
                CheckActivity::class.java
            )
            startActivity(intent)
        })

        // 从启动该活动的 Intent 中获取传递过来的新闻链接
        val url = intent.getStringExtra("url")

        if (url != null) {
            // 创建一个隐式 Intent 来打开浏览器
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }
    }
}