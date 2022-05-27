package com.example.weatherreport.network.parsers

import com.example.weatherreport.network.types.FourDayForecast
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.round
import kotlin.math.roundToInt

class FourDayParser(res: FourDayForecast.Response): ForecastParser() {
    private val res: FourDayForecast.Response

    init {
        this.res = res
    }

    override fun getCurrentDate(): String {
        val formatter = DateTimeFormatter.ofPattern("d/M")
        return datetime.format(formatter)
    }

    fun getGeneralAvgTemperature(day_index: Int): Int {
        val low = res.items[0].forecasts[day_index].temperature.low
        val high = res.items[0].forecasts[day_index].temperature.high
        return ((low + high) / 2.0).roundToInt()
    }

    fun getRelativeDate(day_index: Int): String {
        val dateString = res.items[0].forecasts[day_index].date
        val formatter = DateTimeFormatter.ofPattern("d/M")
        return LocalDate.parse(dateString).format(formatter)
    }

    fun getGeneralForecast(day_index: Int): String {
        return res.items[0].forecasts[day_index].forecast
    }

    fun isApiHealthy(): Boolean {
        return res.api_info.status == "healthy"
    }
}
