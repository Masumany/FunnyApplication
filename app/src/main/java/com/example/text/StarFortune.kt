package com.example.text

data class StarFortune(
    val request_id: String,
    val success: Boolean,
    val message: String,
    val code: Int,
    val data: FortuneData?,
    val time: Long,
    val usage: Int
)

data class FortuneData(
    val day: DayFortune?,
    val tomorrow: TomorrowFortune?,
    val week: WeekFortune?,
    val month: MonthFortune?,
    val year: YearFortune?
)

data class DayFortune(
    val ji: String,
    val yi: String,
    val all: String,
    val date: String,
    val love: String,
    val work: String,
    val money: String,
    val health: String,
    val notice: String,
    val discuss: String,
    val all_text: String,
    val love_text: String,
    val work_text: String,
    val lucky_star: String,
    val money_text: String,
    val health_text: String,
    val lucky_color: String,
    val lucky_number: String
)

data class TomorrowFortune(
    val ji: String,
    val yi: String,
    val all: String,
    val date: String,
    val love: String,
    val work: String,
    val money: String,
    val health: String,
    val notice: String,
    val discuss: String,
    val all_text: String,
    val love_text: String,
    val take_star: String,
    val work_text: String,
    val lucky_star: String,
    val money_text: String,
    val health_text: String,
    val lucky_color: String,
    val lucky_number: String
)

data class WeekFortune(
    val ji: String,
    val yi: String,
    val all: String,
    val date: String,
    val love: String,
    val work: String,
    val money: String,
    val health: String,
    val notice: String,
    val all_text: String,
    val love_text: String,
    val work_text: String,
    val money_text: String,
    val health_text: String
)

data class MonthFortune(
    val ji: String,
    val yi: String,
    val all: String,
    val date: String,
    val love: String,
    val work: String,
    val money: String,
    val health: String,
    val notice: String,
    val all_text: String,
    val love_text: String,
    val work_text: String,
    val money_text: String,
    val health_text: String
)

data class YearFortune(
    val ji: String,
    val yi: String,
    val all: String,
    val date: String,
    val love: String,
    val work: String,
    val money: String,
    val health: String,
    val notice: String,
    val all_text: String,
    val love_text: String,
    val work_text: String,
    val money_text: String,
    val health_text: String
)