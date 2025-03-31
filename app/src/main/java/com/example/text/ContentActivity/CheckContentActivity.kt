package com.example.text.ContentActivity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.text.Activity.CheckActivity
import com.example.text.Activity.CheckContentViewModel
import com.example.text.R

class CheckContentActivity : AppCompatActivity() {
    private lateinit var backTextView: TextView
    private lateinit var checkContentViewModel: CheckContentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.check_content)

        checkContentViewModel = ViewModelProvider(this).get(CheckContentViewModel::class.java)

        backTextView = findViewById(R.id.net_back)

        backTextView.setOnClickListener {
            val intent = Intent(
                this,
                CheckActivity::class.java
            )
            startActivity(intent)
        }

        // 从启动该活动的 Intent 中获取传递过来的新闻链接
        val url = intent.getStringExtra("url")
        if (url != null) {
            checkContentViewModel.setUrl(url)
        }

        // 观察 urlLiveData 以启动浏览器
        checkContentViewModel.urlLiveData.observe(this, Observer { url ->
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        })
    }
}