package com.example.weatherreport

import androidx.lifecycle.ViewModel

class RegionInfoViewModel : ViewModel() {
    var txtDate = ""
    var txtMorningWeatherCondition = ""
    var txtAfternoonWeatherCondition = ""
    var txtNightWeatherCondition = ""
    var imgMorningWeatherCondition = R.drawable.sunny
    var imgAfternoonWeatherCondition = R.drawable.sunny
    var imgNightWeatherCondition = R.drawable.fair_moon
    var txtNext1Date = ""
    var txtNext2Date = ""
    var txtNext3Date = ""
    var txtNext4Date = ""
    var imgNext1DateWeatherCondition = R.drawable.sunny
    var imgNext2DateWeatherCondition = R.drawable.sunny
    var imgNext3DateWeatherCondition = R.drawable.sunny
    var imgNext4DateWeatherCondition = R.drawable.sunny
}