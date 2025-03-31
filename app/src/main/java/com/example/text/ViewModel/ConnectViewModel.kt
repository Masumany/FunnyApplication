package com.example.text.Activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class ConnectViewModel : ViewModel() {

    private val client = OkHttpClient()
    private val API = "https://v3.alapi.cn/api/ai/couplet"
    private val API_KEY = "hnq0tkp4bowkjcqtbn5xxd4qy1kjoj"

    private val _coupletResult = MutableLiveData<String>()
    val coupletResult: LiveData<String> = _coupletResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun fetchCouplet(keyword: String) {
        val url = if (keyword.isEmpty()) API else "$API?keyword=$keyword"
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $API_KEY")
            .build()

        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                _errorMessage.postValue("网络请求失败：${e.message}")
                _coupletResult.postValue("")
            }

            override fun onResponse(call: Call, response: Response) {
                try {
                    val responseBody = response.body?.string()
                    if (responseBody != null) {
                        val gson = Gson()
                        val apiData = gson.fromJson(responseBody, ApiData::class.java)
                        if (apiData.success) {
                            _coupletResult.postValue(apiData.data?.text ?: "")
                        }
                    } else {
                        _errorMessage.postValue("未获取到有效响应数据")
                        _coupletResult.postValue("")
                    }
                } catch (e: Exception) {
                    _errorMessage.postValue("数据解析出错：${e.message}")
                    _coupletResult.postValue("")
                }
            }
        })
    }
}