package com.example.text

import HistoryAdapter
import HistoryFortune
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class HistoryActivity : AppCompatActivity() {
    private val API_URL = "https://v3.alapi.cn/api/eventHistory"
    private val API_KEY = "hnq0tkp4bowkjcqtbn5xxd4qy1kjoj"

    private lateinit var backTextView: TextView
    private lateinit var recyclerView: RecyclerView

    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        backTextView = findViewById(R.id.back)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // 发起网络请求获取历史事件数据
        fetchHistoryData()

        backTextView.setOnClickListener {
            val intent = Intent(this@HistoryActivity, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun fetchHistoryData() {
        val url = "$API_URL?key=$API_KEY"
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $API_KEY") // 添加包含密钥的请求头
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                    Toast.makeText(this@HistoryActivity, "请求失败：${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseData = response.body?.string()
                    Log.d("API_RESPONSE", "Raw Response Data: $responseData")
                    val gson = Gson()
                    val historyFortune = gson.fromJson(responseData, HistoryFortune::class.java)
                    if (historyFortune.success) {
                        val historyDataList = historyFortune.data
                        runOnUiThread {
                            val adapter = HistoryAdapter(historyDataList)
                            recyclerView.adapter = adapter
                        }
                    } else {
                        runOnUiThread {
                            Toast.makeText(this@HistoryActivity, "获取数据失败：${historyFortune.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    runOnUiThread {
                        Toast.makeText(this@HistoryActivity, "请求失败，状态码：${response.code}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}