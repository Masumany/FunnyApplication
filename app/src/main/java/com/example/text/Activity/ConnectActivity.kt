package com.example.text.Activity

import android.content.Intent
import android.os.Bundle
import android.view.KeyEvent
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.text.R

class ConnectActivity : AppCompatActivity() {

    private lateinit var printEditTextView: EditText
    private lateinit var resultTextView: TextView
    private lateinit var backTextView: TextView
    private lateinit var connectViewModel: ConnectViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_connect)

        connectViewModel = ViewModelProvider(this).get(ConnectViewModel::class.java)

        printEditTextView = findViewById(R.id.print)
        resultTextView = findViewById(R.id.result)
        backTextView = findViewById(R.id.back)

        backTextView.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        setupInputListeners()
        observeViewModel()

        // 初始加载数据
        connectViewModel.fetchCouplet("")
    }

    private fun setupInputListeners() {
        printEditTextView.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                val keyword = printEditTextView.text.toString()
                if (keyword.isNotEmpty()) {
                    connectViewModel.fetchCouplet(keyword)
                }
            }
        }

        printEditTextView.setOnKeyListener { _, keyCode, event ->
            if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
                val keyword = printEditTextView.text.toString()
                if (keyword.isNotEmpty()) {
                    connectViewModel.fetchCouplet(keyword)
                }
                return@setOnKeyListener true
            }
            return@setOnKeyListener false
        }
    }

    private fun observeViewModel() {
        connectViewModel.coupletResult.observe(this, Observer { result ->
            resultTextView.text = if (result.isNotEmpty()) "下联：$result" else ""
        })

        connectViewModel.errorMessage.observe(this, Observer { error ->
            if (error.isNotEmpty()) {
                 Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
            }
        })
    }
}