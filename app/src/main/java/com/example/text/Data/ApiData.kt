package com.example.text.Activity

data class ApiData(
    val success: Boolean,
    val message: String,
    val data: DataItem?
)

data class DataItem(
    val text: String?,
    val keyword: String?
)