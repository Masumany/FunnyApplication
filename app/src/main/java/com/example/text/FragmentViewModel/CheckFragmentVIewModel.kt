package com.example.text.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.text.Data.CheckFortune
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class CheckViewModel : ViewModel() {
    private val client = OkHttpClient()
    private val API_URL = "https://v3.alapi.cn/api/new/wbtop"
    private val API_KEY = "hnq0tkp4bowkjcqtbn5xxd4qy1kjoj"

    private val _checkFortune = MutableLiveData<CheckFortune>()
    val checkFortune: LiveData<CheckFortune> = _checkFortune

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun fetchData() {
        val url = "$API_URL?token=$API_KEY"
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $API_KEY")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("CheckViewModel", "Network request failed: ${e.message}")
                _errorMessage.postValue("请求失败：${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    try {
                        val gson = com.google.gson.Gson()
                        val checkFortune = gson.fromJson(responseBody, CheckFortune::class.java)
                        _checkFortune.postValue(checkFortune)
                    } catch (e: Exception) {
                        Log.e("CheckViewModel", "JSON parsing error: ${e.message}")
                        _errorMessage.postValue("数据解析出错：${e.message}")
                    }
                } else {
                    Log.e("CheckViewModel", "Request failed with code: ${response.code}")
                    _errorMessage.postValue("请求失败，状态码：${response.code}")
                }
            }
        })
    }
}