package com.example.text

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException

class HeartActivity : AppCompatActivity() {

    private val API_URL = "https://v3.alapi.cn/api/soul"
    private val API_KEY = "hnq0tkp4bowkjcqtbn5xxd4qy1kjoj" // 请替换为你的实际 API 密钥

    private val client=OkHttpClient()
    private lateinit var heartTextView: TextView
    private lateinit var backTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heart)

        heartTextView = findViewById(R.id.heart)
        backTextView = findViewById(R.id.back)

        backTextView.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
        val url="$API_URL?key=$API_KEY"
        val request=okhttp3.Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $API_KEY")
            .build()

        client.newCall(request).enqueue(object : Callback {


            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {

                    Toast.makeText(this@HeartActivity, "请求失败：${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful){
                    val responseData=response.body?.string()
                    val gson= Gson()
                    val heartFortune=gson.fromJson(responseData,HeartFortune::class.java)

                    runOnUiThread{
                        if (heartFortune!=null){
                            val heart=heartFortune.data?.content
                            heartTextView.text="今日份心灵毒鸡汤：\n${heartFortune.data?.content}"

                        }                        }
                    }
                }
            })
        }
}