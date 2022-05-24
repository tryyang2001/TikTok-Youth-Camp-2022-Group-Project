package com.example.weatherreport.network.types

import com.squareup.moshi.Json

class TwentyFourHourForecast {
    data class Response(
        @field:Json(name = "items") val items: List<Item>,
        @field:Json(name = "api_info") val api_info: ApiInfo
    )

    data class Item(
        val update_timestamp: String,
        val timestamp: String,
        val valid_period: ValidPeriod,
        val general: General,
        val periods: List<Period>
    )

    data class ValidPeriod(
        val start: String,
        val end: String
    )

    data class General(
        val forecast: String,
        val relative_humidity: RelativeHumidity,
        val temperature: Temperature,
        val wind: Wind
    )

    data class RelativeHumidity(
        val low: Int,
        val high: Int
    )

    data class Temperature(
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

    data class Period(
        val time: Time,
        val region: Regions
    )

    data class Time(
        val start: String,
        val end: String
    )

    data class Regions(
        val west: String,
        val east: String,
        val central: String,
        val south: String,
        val north: String
    )

    data class ApiInfo(
        val status: String
    )
}
