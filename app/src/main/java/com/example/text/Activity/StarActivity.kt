package com.example.text.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.text.R
import com.example.text.ViewModel.StarViewModel

class StarActivity : AppCompatActivity() {

    private lateinit var constellationTextView: TextView
    private lateinit var todayFortuneAllTextView: TextView
    private lateinit var todayFortuneLoveTextView: TextView
    private lateinit var todayFortuneWorkTextView: TextView
    private lateinit var todayFortuneMoneyTextView: TextView
    private lateinit var todayFortuneHealthTextView: TextView
    private lateinit var searchView: SearchView
    private lateinit var backTextView: TextView
    private lateinit var starViewModel: StarViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_star)

        starViewModel = ViewModelProvider(this).get(StarViewModel::class.java)

        constellationTextView = findViewById(R.id.constellation_text)
        todayFortuneAllTextView = findViewById(R.id.today_fortune_all_text)
        todayFortuneLoveTextView = findViewById(R.id.today_fortune_love_text)
        todayFortuneWorkTextView = findViewById(R.id.today_fortune_work_text)
        todayFortuneMoneyTextView = findViewById(R.id.today_fortune_money_text)
        todayFortuneHealthTextView = findViewById(R.id.today_fortune_health_text)
        searchView = findViewById(R.id.searchView)
        backTextView = findViewById(R.id.back)

        observeViewModel()

        // 初始请求
        starViewModel.performSearch("scorpio")

        backTextView.setOnClickListener {
            val intent = Intent(this@StarActivity, MainActivity::class.java)
            startActivity(intent)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    starViewModel.performSearch(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun observeViewModel() {
        starViewModel.starFortune.observe(this, Observer { starFortune ->
            if (starFortune != null && starFortune.success && starFortune.data != null && starFortune.data.day != null) {
                constellationTextView.text = "${starViewModel.currentStarSign} 星座运势"
                val dayFortune = starFortune.data.day
                todayFortuneAllTextView.text = "综合运势：${dayFortune.all_text ?: "无数据"}"
                todayFortuneLoveTextView.text = "爱情运势：${dayFortune.love_text ?: "无数据"}"
                todayFortuneWorkTextView.text = "工作运势：${dayFortune.work_text ?: "无数据"}"
                todayFortuneMoneyTextView.text = "财运运势：${dayFortune.money_text ?: "无数据"}"
                todayFortuneHealthTextView.text = "健康运势：${dayFortune.health_text ?: "无数据"}"
            }
        })

        starViewModel.errorMessage.observe(this, Observer { error ->
            if (error.isNotEmpty()) {
                Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            }
        })
    }
}