package com.example.weatherreport.network.parsers

import com.example.weatherreport.network.types.TwentyFourHourForecast
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

class TwentyFourHourParser(res: TwentyFourHourForecast.Response): ForecastParser() {
    private val res: TwentyFourHourForecast.Response
    private val dateFormat: String = "dd/MM/yyyy"

    init {
        this.res = res
    }

    override fun getCurrentDate(): String {
        val formatter = DateTimeFormatter.ofPattern(dateFormat)
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

    fun getForecast(period: Period, region: Region): String {
        val regions = res.items[0].periods[period.ordinal].regions
        return when (region) {
            Region.WEST -> regions.west
            Region.EAST -> regions.east
            Region.CENTRAL -> regions.central
            Region.SOUTH -> regions.south
            Region.NORTH -> regions.north
        }
    }

    fun getCurrentForecast(region: Region): String {
        val period = getCurrentPeriod()
        return getForecast(period, region)
    }
}
