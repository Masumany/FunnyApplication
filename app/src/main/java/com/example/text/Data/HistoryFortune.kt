package com.example.text.Data

data class HistoryFortune(
    val request_id: String,
    val success: Boolean,
    val message: String,
    val code: Int,
    val data: List<HistoryData>,
    val time: Long,
    val usage: Int
) {
    class HistoryData {
        val id: String? = null
        val title: String? = null
        val year: Int? = null
        val month: Int?= null
        val day: Int? = null
        val desc: String? = null
        val date: String? = null


    }
}

