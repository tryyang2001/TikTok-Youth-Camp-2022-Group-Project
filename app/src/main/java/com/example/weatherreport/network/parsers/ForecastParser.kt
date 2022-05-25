package com.example.weatherreport.network.parsers

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

open class ForecastParser {
    companion object {
        const val DATE_FORMAT: String = "yyyy-MM-dd"
    }
    private val datetime: LocalDateTime = LocalDateTime.now()

    fun getCurrentDateTime(): String {
        return datetime.toString()
    }

    fun getCurrentDate(): String {
        val formatter = DateTimeFormatter.ofPattern(DATE_FORMAT)
        return datetime.format(formatter)
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
