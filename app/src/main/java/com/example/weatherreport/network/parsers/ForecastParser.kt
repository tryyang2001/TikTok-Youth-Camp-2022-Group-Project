package com.example.weatherreport.network.parsers

open class ForecastParser {

    fun getForecastCategory(forecast: String): String? {
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