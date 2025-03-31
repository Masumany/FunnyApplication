package com.example.text.ViewModel

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.ViewModel
import com.example.text.Activity.CheckActivity
import com.example.text.Activity.MainActivity
import com.example.text.Activity.MineActivity
import com.example.text.R

class MineViewModel : ViewModel() {
    fun handleNavigationItemSelected(context: Context, itemId: Int): Boolean {
        return when (itemId) {
            R.id.menu_item_main -> {
                val intent = Intent(context, MainActivity::class.java)
                context.startActivity(intent)
                true
            }

            R.id.menu_item_check -> {
                val intent = Intent(context, CheckActivity::class.java)
                context.startActivity(intent)
                true
            }

            R.id.menu_item_mine -> {
                true
            }

            else -> false
        }
    }

}
