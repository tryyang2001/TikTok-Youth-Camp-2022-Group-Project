package com.example.weatherreport

import androidx.lifecycle.ViewModel

class RegionInfoViewModel : ViewModel() {
    data class WeatherCondition(var txt: String, var img: Int)
    data class NextDate(var txt: String, var img: Int, var tag: Int, var dateTemp: String)

    var txtDate: String = ""
    var weatherCondition: List<WeatherCondition> = listOf(
        WeatherCondition("", R.drawable.sunny), // morning
        WeatherCondition("", R.drawable.sunny), // noon
        WeatherCondition("", R.drawable.fair_moon), // evening
        WeatherCondition("", R.drawable.fair_moon), // night
    )
    var nextDate: List<NextDate> = listOf(
        NextDate("", R.drawable.sunny, -1, ""), // date 1
        NextDate("", R.drawable.sunny, -1, ""), // date 2
        NextDate("", R.drawable.sunny, -1, ""), // date 3
        NextDate("", R.drawable.sunny, -1, ""), // date 4
    )
}
