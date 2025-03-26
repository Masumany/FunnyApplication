package com.example.text

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.text.R
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class ConnectActivity : AppCompatActivity() {

    private val client = OkHttpClient()
    private val API = "https://v3.alapi.cn/api/ai/couplet"
    private val API_KEY = "hnq0tkp4bowkjcqtbn5xxd4qy1kjoj"

    private lateinit var printEditTextView: EditText
    private lateinit var resultTextView: TextView
    private lateinit var backTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect)

        printEditTextView = findViewById(R.id.print)
        resultTextView = findViewById(R.id.result)
        backTextView = findViewById(R.id.back)

        backTextView.setOnClickListener{
            val intent=Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        // 为输入框的焦点变化添加监听器，以便在用户输入完成后触发请求
        printEditTextView.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val keyword = printEditTextView.text.toString()
                if (keyword.isNotEmpty()) {
                    sendRequestWithKeyword(keyword)
                }
            }
        }

        // 为输入框添加回车事件监听器，用户按回车也能触发请求
        printEditTextView.setOnKeyListener { _, keyCode, event ->
            if (event.action == android.view.KeyEvent.ACTION_DOWN && keyCode == android.view.KeyEvent.KEYCODE_ENTER) {
                val keyword = printEditTextView.text.toString()
                if (keyword.isNotEmpty()) {
                    sendRequestWithKeyword(keyword)
                }
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }

        // 初始加载数据
        fetchData()
    }

    private fun fetchData() {
        val request = Request.Builder()
            .url(API)
            .addHeader("Authorization", "Bearer $API_KEY")
            .build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {
                }
                Log.e("ConnectActivity", "网络错误", e)
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response){

            }

        })
    }

    private fun sendRequestWithKeyword(keyword: String) {
        val url = "$API?keyword=$keyword"
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $API_KEY")
            .build()
        val call = client.newCall(request)
        call.enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {

                }
            }

            override fun onResponse(call: Call, response: Response) {
                val responseBody = response.body?.string()
                if (responseBody != null) {
                    val gson = Gson()
                    val apiData = gson.fromJson(responseBody, ApiData::class.java)
                    runOnUiThread {
                        if (apiData.success) {
                            val result = apiData.data
                            resultTextView.text = "下联：${result?.text}"
                        } else {

                        }
                    }
                }
            }
        })
    }
}

data class ApiData(
    val success: Boolean,
    val message: String,
    val data: DataItem?
)

data class DataItem(
    val text: String?,
    val keyword: String?
)