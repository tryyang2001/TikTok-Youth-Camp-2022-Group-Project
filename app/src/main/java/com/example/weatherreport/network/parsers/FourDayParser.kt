package com.example.weatherreport.network.parsers

import com.example.weatherreport.network.types.FourDayForecast
import java.time.LocalDate

class FourDayParser(date: LocalDate, res: FourDayForecast.Response) {
    private val date: LocalDate
    private val res: FourDayForecast.Response

    init {
        this.date = date
        this.res = res
    }

    fun getCurrentDate(): String {
        return date.toString()
    }

    fun getGeneralAvgTemperature(day_index: Int): Double {
        val low = res.items[0].forecasts[day_index].temperature.low
        val high = res.items[0].forecasts[day_index].temperature.high
        return (low + high) / 2.0
    }

    fun getDate(day_index: Int): String {
        return res.items[0].forecasts[day_index].date
    }

    fun getGeneralForecast(day_index: Int): String {
        return res.items[0].forecasts[day_index].forecast
    }

    fun isApiHealthy(): Boolean {
        return res.api_info.status == "healthy"
    }
}
