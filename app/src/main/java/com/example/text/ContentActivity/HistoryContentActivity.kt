package com.example.text.ContentActivity

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.text.Activity.HistoryActivity
import com.example.text.R
import com.example.text.ViewModel.HistoryContentViewModel

class HistoryContentActivity : AppCompatActivity() {

    private lateinit var titleTextView: TextView
    private lateinit var timeTextView: TextView
    private lateinit var descTextView: TextView
    private lateinit var backTextView: TextView
    private lateinit var historyContentViewModel: HistoryContentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_content)

        historyContentViewModel = ViewModelProvider(this).get(HistoryContentViewModel::class.java)

        titleTextView = findViewById(R.id.title_text_view)
        timeTextView = findViewById(R.id.date)
        descTextView = findViewById(R.id.desc)
        backTextView = findViewById(R.id.net_back)

        observeViewModel()

        val title = intent.getStringExtra("title")
        val time = intent.getStringExtra("date")
        val desc = intent.getStringExtra("desc")
        historyContentViewModel.setHistoryContent(title, time, desc)

        backTextView.setOnClickListener {
            val intent = Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }
    }

    private fun observeViewModel() {
        historyContentViewModel.title.observe(this, Observer { title ->
            titleTextView.text = title
        })

        historyContentViewModel.time.observe(this, Observer { time ->
            timeTextView.text = time
        })

        historyContentViewModel.desc.observe(this, Observer { desc ->
            descTextView.text = desc
        })
    }
}