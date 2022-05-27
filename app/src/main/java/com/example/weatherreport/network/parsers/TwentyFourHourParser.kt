package com.example.weatherreport.network.parsers

import com.example.weatherreport.network.types.TwentyFourHourForecast
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

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

    fun getGeneralAvgTemperature(): Int {
        val low = res.items[0].general.temperature.low
        val high = res.items[0].general.temperature.high
        return ((low + high) / 2.0).roundToInt()
    }

    fun getCurrentWestForecast(): String {
        val periodIndex = getPeriodIndex()
        return res.items[0].periods[periodIndex].regions.west
    }

    fun getCurrentEastForecast(): String {
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

    fun getNightWestForecast(): String {
        return res.items[0].periods[0].regions.west
    }

    fun getNightEastForecast(): String {
        return res.items[0].periods[0].regions.east
    }

    fun getNightCentralForecast(): String {
        return res.items[0].periods[0].regions.central
    }

    fun getNightSouthForecast(): String {
        return res.items[0].periods[0].regions.south
    }

    fun getNightNorthForecast(): String {
        return res.items[0].periods[0].regions.north
    }

    fun getMorningWestForecast(): String {
        return res.items[0].periods[1].regions.west
    }

    fun getMorningEastForecast(): String {
        return res.items[0].periods[1].regions.east
    }

    fun getMorningCentralForecast(): String {
        return res.items[0].periods[1].regions.central
    }

    fun getMorningSouthForecast(): String {
        return res.items[0].periods[1].regions.south
    }

    fun getMorningNorthForecast(): String {
        return res.items[0].periods[1].regions.north
    }

    fun getNoonWestForecast(): String {
        return res.items[0].periods[2].regions.west
    }

    fun getNoonEastForecast(): String {
        return res.items[0].periods[2].regions.east
    }

    fun getNoonCentralForecast(): String {
        return res.items[0].periods[2].regions.central
    }

    fun getNoonSouthForecast(): String {
        return res.items[0].periods[2].regions.south
    }

    fun getNoonNorthForecast(): String {
        return res.items[0].periods[2].regions.north
    }

    fun getEveningWestForecast(): String {
        return res.items[0].periods[3].regions.west
    }

    fun getEveningEastForecast(): String {
        return res.items[0].periods[3].regions.east
    }

    fun getEveningCentralForecast(): String {
        return res.items[0].periods[3].regions.central
    }

    fun getEveningSouthForecast(): String {
        return res.items[0].periods[3].regions.south
    }

    fun getEveningNorthForecast(): String {
        return res.items[0].periods[3].regions.north
    }
}
