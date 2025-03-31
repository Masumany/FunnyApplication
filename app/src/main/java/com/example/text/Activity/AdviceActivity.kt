package com.example.text.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.text.AdviceViewModel
import com.example.text.R

class AdviceActivity : AppCompatActivity() {
    private lateinit var adviceEditText: EditText
    private lateinit var commitBtn: Button
    private lateinit var backTextView: TextView
    private lateinit var adviceViewModel: AdviceViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_advice)

        adviceViewModel = ViewModelProvider(this).get(AdviceViewModel::class.java)

        adviceEditText = findViewById(R.id.et)
        commitBtn = findViewById(R.id.bt)
        backTextView = findViewById(R.id.net_back)

        // 设置返回按钮的点击事件
        backTextView.setOnClickListener {
            val intent = Intent(
                this@AdviceActivity,
                MineActivity::class.java
            )
            startActivity(intent)
        }

        // 设置提交按钮的点击事件
        commitBtn.setOnClickListener {
            val advice = adviceEditText.text.toString()
            adviceViewModel.submitAdvice(advice)
        }

        // 观察提交结果
        adviceViewModel.submitResult.observe(this, Observer { result ->
            if (result) {
                Toast.makeText(this@AdviceActivity, "提交成功", Toast.LENGTH_SHORT).show()
                adviceEditText.setText("")
            } else {
                Toast.makeText(this@AdviceActivity, "请输入反馈信息", Toast.LENGTH_SHORT).show()
            }
        })
    }
}