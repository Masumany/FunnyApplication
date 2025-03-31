package com.example.text.Activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CheckContentViewModel : ViewModel() {
    private val _urlLiveData = MutableLiveData<String>()
    val urlLiveData: LiveData<String> = _urlLiveData

    fun setUrl(url: String) {
        _urlLiveData.value = url
    }
}