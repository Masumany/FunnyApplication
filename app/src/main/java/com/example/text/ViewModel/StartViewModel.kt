package com.example.text.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.text.Data.StarFortune
import okhttp3.*
import java.io.IOException

class StarViewModel : ViewModel() {

    private val client = OkHttpClient()
    private val API_URL = "https://v3.alapi.cn/api/star"
    private val API_KEY = "hnq0tkp4bowkjcqtbn5xxd4qy1kjoj"
    var currentStarSign = ""

    private val _starFortune = MutableLiveData<StarFortune>()
    val starFortune: LiveData<StarFortune> = _starFortune

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun performSearch(query: String) {
        currentStarSign = query
        val url = "$API_URL?date=today&star=$currentStarSign"
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $API_KEY")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                _errorMessage.postValue("请求失败：${e.message}")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseData = response.body?.string()
                    if (responseData != null) {
                        try {
                            val gson = com.google.gson.Gson()
                            val starFortune = gson.fromJson(responseData, StarFortune::class.java)
                            _starFortune.postValue(starFortune)
                        } catch (e: Exception) {
                            _errorMessage.postValue("数据解析出错：${e.message}")
                        }
                    } else {
                        _errorMessage.postValue("未获取到有效响应数据")
                    }
                } else {
                    _errorMessage.postValue("请求失败：${response.code}")
                }
            }
        })
    }
}