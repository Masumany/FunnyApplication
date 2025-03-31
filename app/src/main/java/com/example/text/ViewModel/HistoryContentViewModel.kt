package com.example.text.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class HistoryContentViewModel : ViewModel() {

    private val _title = MutableLiveData<String>()
    val title: LiveData<String> = _title

    private val _time = MutableLiveData<String>()
    val time: LiveData<String> = _time

    private val _desc = MutableLiveData<String>()
    val desc: LiveData<String> = _desc

    fun setHistoryContent(title: String?, time: String?, desc: String?) {
        _title.value = title
        _time.value = time
        _desc.value = desc
    }
}