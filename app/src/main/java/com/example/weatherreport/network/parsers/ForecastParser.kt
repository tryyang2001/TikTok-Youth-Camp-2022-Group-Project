package com.example.weatherreport.network.parsers

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

open class ForecastParser {
    protected val datetime: LocalDateTime = LocalDateTime.now()

    open fun getCurrentDate(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return datetime.format(formatter)
    }

    open fun getForecastCategory(forecast: String?): String? {
        if (forecast?.lowercase()?.contains("thunder") == true) {
            return "Thundery"
        } else if (forecast?.lowercase()?.contains("cloud") == true) {
            return "Cloudy"
        } else if (forecast?.lowercase()?.contains("fair") == true) {
            return "Fair"
        } else if (forecast?.lowercase()?.contains("rain") == true) {
            return "Rainy"
        }
        return null
    }

    open fun getPeriodIndex(): Int {
        return datetime.hour / 6
    }
}
