package com.example.text

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import okhttp3.Call
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException



class CheckFragment : Fragment() {
    private val client: OkHttpClient = OkHttpClient()
    private val API_URL = "https://v3.alapi.cn/api/new/wbtop"
    private val API_KEY = "hnq0tkp4bowkjcqtbn5xxd4qy1kjoj"
    private lateinit var recyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_check, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)

        fetchData()

        return view
    }

    private fun fetchData() {
        val url = "$API_URL?token=$API_KEY"
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $API_KEY")
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: Call, e: IOException) {
                // 处理请求失败的情况
            }

            override fun onResponse(call: Call, response: Response) {
                if (response.isSuccessful) {
                    val responseBody = response.body?.string()
                    val gson = Gson()
                    val checkFortune = gson.fromJson(responseBody, CheckFortune::class.java)
                    if (checkFortune.data != null) {
                        val dataList = checkFortune.data
                        val adapter = CheckAdapter(dataList)
                        requireActivity().runOnUiThread {
                            recyclerView.adapter = adapter
                        }
                    }
                }
            }
        })
    }
}