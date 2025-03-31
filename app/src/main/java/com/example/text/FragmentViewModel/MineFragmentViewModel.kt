package com.example.text.ViewModel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.example.text.Activity.AdviceActivity

class MineFragmentViewModel : ViewModel() {
    fun navigateToAdviceActivity(context: Context) {
        val intent = Intent(context, AdviceActivity::class.java)
        context.startActivity(intent)
    }
}