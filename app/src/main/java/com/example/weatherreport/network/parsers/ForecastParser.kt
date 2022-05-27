package com.example.weatherreport.network.parsers

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

open class ForecastParser {
    enum class Period { MORNING, NOON, EVENING, NIGHT }
    enum class Region { WEST, EAST, CENTRAL, SOUTH, NORTH }
    protected val datetime: LocalDateTime = LocalDateTime.now()
    private val dateFormat: String = "yyyy-MM-dd"

    open fun getCurrentDate(): String {
        val formatter = DateTimeFormatter.ofPattern(dateFormat)
        return datetime.format(formatter)
    }

    open fun getForecastCategory(forecast: String): String {
        if (forecast.lowercase().contains("thunder")) {
            return "Thundery"
        } else if (forecast.lowercase().contains("cloud")) {
            return "Cloudy"
        } else if (forecast.lowercase().contains("fair")) {
            return "Fair"
        }
        return "Rainy" // assume last category to bring rainy
    }

    open fun getCurrentPeriod(): Period {
        val periodIdx: Int = datetime.hour / 6
        return Period.values()[periodIdx]
    }
}
