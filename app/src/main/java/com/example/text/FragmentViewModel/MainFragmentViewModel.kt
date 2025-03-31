package com.example.text.ViewModel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.example.text.Activity.ConnectActivity
import com.example.text.Activity.HeartActivity
import com.example.text.Activity.HistoryActivity
import com.example.text.Activity.StarActivity

class MainFragmentViewModel : ViewModel() {
    fun navigateToConnectActivity(context: Context) {
        val intent = Intent(context, ConnectActivity::class.java)
        context.startActivity(intent)
    }

    fun navigateToHeartActivity(context: Context) {
        val intent = Intent(context, HeartActivity::class.java)
        context.startActivity(intent)
    }

    fun navigateToStarActivity(context: Context) {
        val intent = Intent(context, StarActivity::class.java)
        context.startActivity(intent)
    }

    fun navigateToHistoryActivity(context: Context) {
        val intent = Intent(context, HistoryActivity::class.java)
        context.startActivity(intent)
    }
}