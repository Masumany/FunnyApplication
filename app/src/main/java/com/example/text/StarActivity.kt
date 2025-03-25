package com.example.text

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import com.example.text.R
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class StarActivity : AppCompatActivity() {

    private val API_URL = "https://v3.alapi.cn/api/star"
    private val API_KEY = "hnq0tkp4bowkjcqtbn5xxd4qy1kjoj" // 请替换为你的实际 API 密钥
    private var STAR_SIGN = "scorpio" // 可根据实际情况修改星座

    private lateinit var constellationTextView: TextView
    private lateinit var todayFortuneAllTextView: TextView
    private lateinit var todayFortuneLoveTextView: TextView
    private lateinit var todayFortuneWorkTextView: TextView
    private lateinit var todayFortuneMoneyTextView: TextView
    private lateinit var todayFortuneHealthTextView: TextView
    private lateinit var searchView: SearchView
    private lateinit var backTextView: TextView

    private val client = OkHttpClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_star)

        constellationTextView = findViewById(R.id.constellation_text)
        todayFortuneAllTextView = findViewById(R.id.today_fortune_all_text)
        todayFortuneLoveTextView = findViewById(R.id.today_fortune_love_text)
        todayFortuneWorkTextView = findViewById(R.id.today_fortune_work_text)
        todayFortuneMoneyTextView = findViewById(R.id.today_fortune_money_text)
        todayFortuneHealthTextView = findViewById(R.id.today_fortune_health_text)
        searchView = findViewById(R.id.searchView)
        backTextView=findViewById(R.id.back)

        // 初始请求
        performSearch(STAR_SIGN)

        backTextView.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
              val intent=Intent(this@StarActivity,MainActivity::class.java)
                startActivity(intent)
            }

        })

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    performSearch(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun performSearch(query: String) {
        STAR_SIGN = query
        val url = "$API_URL?date=today&star=$STAR_SIGN"
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $API_KEY")
            .build()


        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                runOnUiThread {

                    Toast.makeText(this@StarActivity, "请求失败：${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseData = response.body?.string()
                    Log.d("API_RESPONSE", "Raw Response Data: $responseData")
                    val gson = Gson()
                    val starFortune = gson.fromJson(responseData, StarFortune::class.java)

                    runOnUiThread {
                        if (starFortune != null && starFortune.success && starFortune.data != null && starFortune.data.day != null) {
                            constellationTextView.text = "$STAR_SIGN 星座运势"
                            val dayFortune = starFortune.data.day
                            todayFortuneAllTextView.text = "综合运势：${dayFortune.all_text ?: "无数据"}"
                            todayFortuneLoveTextView.text = "爱情运势：${dayFortune.love_text ?: "无数据"}"
                            todayFortuneWorkTextView.text = "工作运势：${dayFortune.work_text ?: "无数据"}"
                            todayFortuneMoneyTextView.text = "财运运势：${dayFortune.money_text ?: "无数据"}"
                            todayFortuneHealthTextView.text = "健康运势：${dayFortune.health_text ?: "无数据"}"
                        } else {

                        }
                    }
                } else {
                    runOnUiThread {

                        Toast.makeText(this@StarActivity, "请求失败：${response.code}", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}