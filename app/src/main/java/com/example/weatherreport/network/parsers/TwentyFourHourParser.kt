package com.example.weatherreport.network.parsers

import com.example.weatherreport.network.types.TwentyFourHourForecast
import java.time.format.DateTimeFormatter

class TwentyFourHourParser(res: TwentyFourHourForecast.Response): ForecastParser() {
    private val res: TwentyFourHourForecast.Response

    init {
        this.res = res
    }

    override fun getCurrentDate(): String {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        return datetime.format(formatter)
    }

    fun getGeneralForecast(): String {
        return res.items[0].general.forecast
    }

    fun getGeneralAvgTemperature(): Double {
        val low = res.items[0].general.temperature.low
        val high = res.items[0].general.temperature.high
        return (low + high) / 2.0
    }

    fun getCurrentWestForecast(): String {
        val periodIndex = getPeriodIndex()
        return res.items[0].periods[periodIndex].regions.west
    }

    fun getNightEastForecast(): String {
        val periodIndex = getPeriodIndex()
        return res.items[0].periods[periodIndex].regions.east
    }

    fun getCurrentCentralForecast(): String {
        val periodIndex = getPeriodIndex()
        return res.items[0].periods[periodIndex].regions.central
    }

    fun getCurrentSouthForecast(): String {
        val periodIndex = getPeriodIndex()
        return res.items[0].periods[periodIndex].regions.south
    }

    fun getCurrentNorthForecast(): String {
        val periodIndex = getPeriodIndex()
        return res.items[0].periods[periodIndex].regions.north
    }

    fun isApiHealthy(): Boolean {
        return res.api_info.status == "healthy"
    }
}
