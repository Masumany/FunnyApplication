package com.example.text.Activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.text.ContentActivity.HistoryContentActivity
import com.example.text.Data.HistoryFortune
import com.example.text.R
import com.example.text.ViewModel.HistoryViewModel

class HistoryActivity : AppCompatActivity() {
    private lateinit var backTextView: TextView
    private lateinit var recyclerView: RecyclerView
    private lateinit var historyViewModel: HistoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history)

        historyViewModel = ViewModelProvider(this).get(HistoryViewModel::class.java)

        backTextView = findViewById(R.id.back)
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        observeViewModel()
        historyViewModel.fetchHistoryData()

        backTextView.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeViewModel() {
        historyViewModel.historyDataList.observe(this, Observer { historyDataList ->
            if (historyDataList.isNotEmpty()) {
                val adapter = HistoryAdapter(historyDataList)
                recyclerView.adapter = adapter
            } else {
                Toast.makeText(this, "未获取到历史数据", Toast.LENGTH_SHORT).show()
            }
        })

        historyViewModel.errorMessage.observe(this, Observer { error ->
            if (error.isNotEmpty()) {
                Toast.makeText(this, "请求出错：$error", Toast.LENGTH_SHORT).show()
            }
        })
    }

    inner class HistoryAdapter(private val historyDataList: List<HistoryFortune.HistoryData>) :
        RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoryViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(
                R.layout.history_item_layout,
                parent,
                false
            )
            return HistoryViewHolder(view)
        }

        override fun onBindViewHolder(holder: HistoryViewHolder, position: Int) {
            val historyData = historyDataList[position]
            holder.titleTextView.text = historyData.title
            holder.timeTextView.text = "时间：${historyData.date}"
            holder.descTextView.text = "内容：${historyData.desc}"

            holder.itemView.setOnClickListener {
                val intent = Intent(holder.itemView.context, HistoryContentActivity::class.java)
                intent.putExtra("title", historyData.title)
                intent.putExtra("date", historyData.date)
                intent.putExtra("desc", historyData.desc)
                holder.itemView.context.startActivity(intent)
            }
        }

        override fun getItemCount(): Int {
            return historyDataList.size
        }

        inner class HistoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
            val titleTextView: TextView = view.findViewById(R.id.title)
            val timeTextView: TextView = view.findViewById(R.id.date)
            val descTextView: TextView = view.findViewById(R.id.desc)
        }
    }
}