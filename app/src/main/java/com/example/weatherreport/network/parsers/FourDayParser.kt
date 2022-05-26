package com.example.weatherreport.network.parsers

import com.example.weatherreport.network.types.FourDayForecast

class FourDayParser(res: FourDayForecast.Response): ForecastParser() {
    private val res: FourDayForecast.Response

    init {
        this.res = res
    }

    fun getGeneralAvgTemperature(day_index: Int): Int {
        val low = res.items[0].forecasts[day_index].temperature.low
        val high = res.items[0].forecasts[day_index].temperature.high
        return (low + high) / 2
    }

    fun getRelativeDate(day_index: Int): String {
        return res.items[0].forecasts[day_index].date.format("d/M")
    }

    fun getGeneralForecast(day_index: Int): String {
        return res.items[0].forecasts[day_index].forecast
    }

    fun isApiHealthy(): Boolean {
        return res.api_info.status == "healthy"
    }
}
