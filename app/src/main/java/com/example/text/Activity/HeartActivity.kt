package com.example.text.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.text.Data.HeartFortune
import com.example.text.R
import com.example.text.ViewModel.HeartViewModel
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Response
import java.io.IOException

class HeartActivity : AppCompatActivity() {
   private lateinit var heartViewModel: HeartViewModel
    private lateinit var heartTextView: TextView
    private lateinit var backTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_heart)

        heartViewModel = ViewModelProvider(this).get(HeartViewModel::class.java)

        heartTextView = findViewById(R.id.heart)
        backTextView = findViewById(R.id.back)

        backTextView.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
           heartViewModel.fetchCouplet()
            observeViewModel()





        }
    private fun observeViewModel() {
        heartViewModel.coupletResult.observe(this) { result ->
            if (result.isNotEmpty()) {
                heartTextView.text = "今日份心灵毒鸡汤：$result"
                Toast.makeText(this, "获取成功", Toast.LENGTH_SHORT).show()
            }
            heartViewModel.errorMessage.observe(this) { errorMessage ->
                if (errorMessage.isNotEmpty()) {
                    Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
                }
            }

        }
    }
}