package com.example.text.ViewModel

import android.content.Context
import android.content.Intent
import androidx.lifecycle.ViewModel
import com.example.text.Activity.CheckActivity
import com.example.text.Activity.MineActivity
import com.example.text.R

class MainViewModel : ViewModel() {

    fun handleNavigationItemSelected(context: Context, itemId: Int): Boolean {
        return when (itemId) {
            R.id.menu_item_main -> {
                // 处理主页面点击事件
                true
            }
            R.id.menu_item_check -> {
                val intent = Intent(context, CheckActivity::class.java)
                context.startActivity(intent)
                true
            }
            R.id.menu_item_mine -> {
                val intent = Intent(context, MineActivity::class.java)
                context.startActivity(intent)
                true
            }
            else -> false
        }
    }
}