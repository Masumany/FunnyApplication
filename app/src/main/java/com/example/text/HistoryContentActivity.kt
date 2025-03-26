package com.example.text
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class HistoryContentActivity : AppCompatActivity() {

    private lateinit var titleTextView: TextView
    private lateinit var timeTextView: TextView
    private lateinit var descTextView: TextView
    private lateinit var backTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_content)

        titleTextView = findViewById(R.id.title_text_view)
        timeTextView = findViewById(R.id.date)
        descTextView = findViewById(R.id.desc)
        backTextView = findViewById(R.id.net_back)


        val title = intent.getStringExtra("title")
        val time = intent.getStringExtra("date")
        val desc = intent.getStringExtra("desc")
        titleTextView.text = title
       timeTextView.text = time
        descTextView.text = desc


        backTextView.setOnClickListener{
            val intent= Intent(this, HistoryActivity::class.java)
            startActivity(intent)
        }

    }
}

