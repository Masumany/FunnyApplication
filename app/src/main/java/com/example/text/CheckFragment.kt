package com.example.text

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
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
    private lateinit var swipeRefresh: SwipeRefreshLayout
    private lateinit var floatButton: FloatingActionButton
    private var isScrollingUp = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_check, container, false)
        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(context)
        swipeRefresh = view.findViewById(R.id.swipe_refresh)
        floatButton = view.findViewById(R.id.floatButton)
        floatButton.hide() // 初始时隐藏按钮
        fetchData()
        setupRefreshListener()
        setupScrollListener()
        floatButton.setOnClickListener {
            toTop()
        }
        return view
    }

    private fun toTop() {
        recyclerView.smoothScrollToPosition(0)
    }

    private fun setupRefreshListener() {
        swipeRefresh.setOnRefreshListener {
            fetchData()
        }
    }

    private fun setupScrollListener() {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                if (dy > 0) {
                    // 向下滚动
                    isScrollingUp = false
                    if (firstVisibleItemPosition > 0) {
                        floatButton.hide()
                    }
                } else if (dy < 0) {
                    // 向上滚动
                    isScrollingUp = true
                    floatButton.show()
                }
            }

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE && isScrollingUp) {
                    // 滚动停止且是向上滚动，显示按钮
                    floatButton.show()
                }
            }
        })
    }

    private fun fetchData() {
        val url = "$API_URL?token=$API_KEY"
        val request = Request.Builder()
            .url(url)
            .addHeader("Authorization", "Bearer $API_KEY")
            .build()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: Call, e: IOException) {
                // 处理请求失败的情况，停止刷新动画
                requireActivity().runOnUiThread {
                    swipeRefresh.isRefreshing = false
                }
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
                            adapter.updateData(dataList)
                            // 数据加载成功，停止刷新动画
                            swipeRefresh.isRefreshing = false
                            recyclerView.scrollToPosition(0)
                            Log.d("Network", "Response body: $responseBody")
                        }
                    }
                } else {
                    // 请求不成功，停止刷新动画
                    requireActivity().runOnUiThread {
                        swipeRefresh.isRefreshing = false
                    }
                }
            }
        })
    }
}