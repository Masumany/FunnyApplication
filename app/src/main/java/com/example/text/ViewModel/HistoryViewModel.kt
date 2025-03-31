package com.example.text.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.text.Data.HistoryFortune
import okhttp3.Call
import okhttp3.Callback
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class HistoryViewModel : ViewModel() {
    private val client = OkHttpClient()
    private val API_URL = "https://v3.alapi.cn/api/eventHistory"
    private val API_KEY = "hnq0tkp4bowkjcqtbn5xxd4qy1kjoj"

    private val _historyDataList = MutableLiveData<List<HistoryFortune.HistoryData>>()
    val historyDataList: LiveData<List<HistoryFortune.HistoryData>> = _historyDataList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun fetchHistoryData() {
        val url = "$API_URL?key=$API_KEY"
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $API_KEY")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                _errorMessage.postValue(e.message ?: "未知错误")
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseData = response.body?.string()
                    if (responseData != null) {
                        try {
                            val gson = com.google.gson.Gson()
                            val historyFortune = gson.fromJson(responseData, HistoryFortune::class.java)
                            if (historyFortune.success) {
                                _historyDataList.postValue(historyFortune.data)
                            } else {
                                _errorMessage.postValue("获取数据失败：${historyFortune.message}")
                            }
                        } catch (e: Exception) {
                            _errorMessage.postValue("数据解析出错：${e.message}")
                        }
                    } else {
                        _errorMessage.postValue("未获取到有效响应数据")
                    }
                } else {
                    _errorMessage.postValue("请求失败，状态码：${response.code}")
                }
            }
        })
    }
}