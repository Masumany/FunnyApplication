package com.example.text

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.util.Date

class MainActivity : AppCompatActivity() {

    private lateinit var navigationButton: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainFragment = MainFragment()
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, mainFragment)
        fragmentTransaction.commit()

        navigationButton = findViewById(R.id.bottom_navigation)

        navigationButton.setOnNavigationItemSelectedListener { item ->
            when (item.itemId) {
                R.id.menu_item_main -> {
                    // 处理主页面点击事件
                    true
                }
                R.id.menu_item_check -> {
                    val intent = Intent(this, CheckActivity::class.java)
                    startActivity(intent)
                    true
                }
                R.id.menu_item_mine -> {
                    val intent = Intent(this, MineActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}