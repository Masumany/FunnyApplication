package com.example.text

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AdviceViewModel : ViewModel() {
    private val _submitResult = MutableLiveData<Boolean>()
    val submitResult: LiveData<Boolean> = _submitResult

    fun submitAdvice(advice: String) {
        if (advice.isNotEmpty()) {
            _submitResult.value = true
        } else {
            _submitResult.value = false
        }
    }
}