package com.example.weatherreport.network.types

import com.squareup.moshi.Json

class FourDayForecast {
    data class Response(
        @field:Json(name = "items") val items: List<Item>,
        @field:Json(name = "api_info") val api_info: ApiInfo
    )

    data class Item(
        val update_timestamp: String,
        val timestamp: String,
        val forecasts: List<Forecast>
    )

    data class Forecast(
        val temperature: Temperature,
        val date: String,
        val forecast: String,
        val relative_humidity: RelativeHumidity,
        val wind: Wind,
        val timestamp: String
    )

    data class Temperature(
        val low: Int,
        val high: Int
    )

    data class RelativeHumidity(
        val low: Int,
        val high: Int
    )

    data class Wind(
        val speed: Speed,
        val direction: String
    )

    data class Speed(
        val low: Int,
        val high: Int
    )

    data class ApiInfo(
        val status: String
    )
}
