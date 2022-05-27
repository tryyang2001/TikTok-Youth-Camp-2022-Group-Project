package com.example.weatherreport

import androidx.lifecycle.ViewModel

class RegionInfoViewModel : ViewModel() {
    var txtDate = ""
    var txtMorningWeatherCondition = ""
    var txtAfternoonWeatherCondition = ""
    var txtEveningWeatherCondition = ""
    var txtNightWeatherCondition = ""
    var imgMorningWeatherCondition = R.drawable.sunny
    var imgAfternoonWeatherCondition = R.drawable.sunny
    var imgEveningWeatherCondition = R.drawable.fair_moon
    var imgNightWeatherCondition = R.drawable.fair_moon
    var txtNext1Date = ""
    var txtNext2Date = ""
    var txtNext3Date = ""
    var txtNext4Date = ""
    var imgNext1DateWeatherCondition = R.drawable.sunny
    var imgNext1DateTag = -1
    var imgNext2DateWeatherCondition = R.drawable.sunny
    var imgNext2DateTag = -1
    var imgNext3DateWeatherCondition = R.drawable.sunny
    var imgNext3DateTag = -1
    var imgNext4DateWeatherCondition = R.drawable.sunny
    var imgNext4DateTag = -1
    var txtNext1DateTemp = ""
    var txtNext2DateTemp = ""
    var txtNext3DateTemp = ""
    var txtNext4DateTemp = ""
}