package com.example.text.Data

data class HeartFortune (
    val request_id: String?,
    val success: Boolean?,
    val code: Int?,
    val message: String?,
    val data: HeartData?,
)

    data class HeartData (
        val content: String? = null
    )

