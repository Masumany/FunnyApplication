package com.example.text.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.text.Data.HeartFortune
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class HeartViewModel : ViewModel() {
    private val client = OkHttpClient()
    private val API_URL = "https://v3.alapi.cn/api/soul"
    private val API_KEY = "hnq0tkp4bowkjcqtbn5xxd4qy1kjoj"

    private val _coupletResult = MutableLiveData<String>()
    val coupletResult: LiveData<String> = _coupletResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun fetchCouplet() {
        val url = "$API_URL?key=$API_KEY"
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $API_KEY")
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: Call, e: IOException) {
                _errorMessage.postValue(e.message ?: "未知错误")
                _coupletResult.postValue("")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseData = response.body?.string()
                    val gson = com.google.gson.Gson()
                    val heartFortune = gson.fromJson(responseData, HeartFortune::class.java)
                    if (heartFortune!= null && heartFortune.success == true) {
                        _coupletResult.postValue(heartFortune.data?.content ?: "")
                    } else {
                        _errorMessage.postValue("数据解析失败或请求未成功")
                        _coupletResult.postValue("")
                    }
                } else {
                    _errorMessage.postValue("请求失败，状态码：${response.code}")
                    _coupletResult.postValue("")
                }
            }
        })
    }
}