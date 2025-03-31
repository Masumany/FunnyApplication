package com.example.text.Activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.text.Activity.MainActivity
import com.example.text.Activity.MineActivity
import com.example.text.R

class CheckViewModel : ViewModel() {

    private val _navigationEvent = MutableLiveData<Class<*>?>()
    val navigationEvent: LiveData<Class<*>?> = _navigationEvent

    fun onNavigationItemSelected(itemId: Int) {
        when (itemId) {
            R.id.menu_item_main -> {
                _navigationEvent.value = MainActivity::class.java
            }
            R.id.menu_item_check -> {
                // 可以添加相应逻辑
            }
            R.id.menu_item_mine -> {
                _navigationEvent.value = MineActivity::class.java
            }
        }
    }
}