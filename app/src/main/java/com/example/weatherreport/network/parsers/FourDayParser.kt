package com.example.weatherreport.network.parsers

import com.example.weatherreport.network.types.FourDayForecast
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

class FourDayParser(res: FourDayForecast.Response): ForecastParser() {
    private val res: FourDayForecast.Response
    private val dateFormat: String = "d/M"

    init {
        this.res = res
    }

    override fun getCurrentDate(): String {
        val formatter = DateTimeFormatter.ofPattern(dateFormat)
        return datetime.format(formatter)
    }

    fun getGeneralAvgTemperature(day_index: Int): Int {
        val low = res.items[0].forecasts[day_index].temperature.low
        val high = res.items[0].forecasts[day_index].temperature.high
        return ((low + high) / 2.0).roundToInt()
    }

    fun getRelativeDate(day_index: Int): String {
        val dateString = res.items[0].forecasts[day_index].date
        val formatter = DateTimeFormatter.ofPattern(dateFormat)
        return LocalDate.parse(dateString).format(formatter)
    }

    fun getGeneralForecast(day_index: Int): String {
        return res.items[0].forecasts[day_index].forecast
    }
}
