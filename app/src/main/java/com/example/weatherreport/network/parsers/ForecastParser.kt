package com.example.weatherreport.network.parsers

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

open class ForecastParser {
    protected val datetime: LocalDateTime = LocalDateTime.now()

    open fun getCurrentDateTime(): String {
        return datetime.toString()
    }

    open fun getCurrentDate(): String {
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        return datetime.format(formatter)
    }

    open fun getPeriodIndex(): Int {
       return datetime.hour / 6
    }

    open fun getForecastCategory(forecast: String): String? {
        if (forecast.lowercase().contains("thunder")) {
            return "Thundery"
        } else if (forecast.lowercase().contains("cloud")) {
            return "Cloudy"
        } else if (forecast.lowercase().contains("fair")) {
            return "Fair"
        } else if (forecast.lowercase().contains("rain")) {
            return "Rainy"
        }
        return null
    }
}
