package com.example.weatherreport.network.parsers

import com.example.weatherreport.network.types.TwentyFourHourForecast
import java.time.LocalDate

class TwentyFourHourParser(date: LocalDate, res: TwentyFourHourForecast.Response): ForecastParser() {
    private val date: LocalDate
    private val res: TwentyFourHourForecast.Response

    init {
        this.date = date
        this.res = res
    }

    fun getCurrentDate(): String {
        return date.toString()
    }

    fun getGeneralForecast(): String {
        return res.items[0].general.forecast
    }

    fun getGeneralAvgTemperature(): Double {
        val low = res.items[0].general.temperature.low
        val high = res.items[0].general.temperature.high
        return (low + high) / 2.0
    }

    fun getMorningWestForecast(): String {
        return res.items[0].periods[0].regions.west
    }

    fun getMorningEastForecast(): String {
        return res.items[0].periods[0].regions.east
    }

    fun getMorningCentralForecast(): String {
        return res.items[0].periods[0].regions.central
    }

    fun getMorningSouthForecast(): String {
        return res.items[0].periods[0].regions.south
    }

    fun getMorningNorthForecast(): String {
        return res.items[0].periods[0].regions.north
    }

    fun getNoonWestForecast(): String {
        return res.items[0].periods[1].regions.west
    }

    fun getNoonEastForecast(): String {
        return res.items[0].periods[1].regions.east
    }

    fun getNoonCentralForecast(): String {
        return res.items[0].periods[1].regions.central
    }

    fun getNoonSouthForecast(): String {
        return res.items[0].periods[1].regions.south
    }

    fun getNoonNorthForecast(): String {
        return res.items[0].periods[1].regions.north
    }

    fun getNightWestForecast(): String {
        return res.items[0].periods[2].regions.west
    }

    fun getNightEastForecast(): String {
        return res.items[0].periods[2].regions.east
    }

    fun getNightCentralForecast(): String {
        return res.items[0].periods[2].regions.central
    }

    fun getNightSouthForecast(): String {
        return res.items[0].periods[2].regions.south
    }

    fun getNightNorthForecast(): String {
        return res.items[0].periods[2].regions.north
    }

    fun isApiHealthy(): Boolean {
        return res.api_info.status == "healthy"
    }
}
