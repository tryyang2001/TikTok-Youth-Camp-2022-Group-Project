package com.example.weatherreport.network.parsers

import com.example.weatherreport.network.NetworkModule
import com.example.weatherreport.network.WeatherAPI
import org.junit.Assert.*

import org.junit.Test

class FourDayParserTest {

    private val weatherAPI: WeatherAPI = NetworkModule.weatherAPI

    @Test
    fun getGeneralAvgTemperature_ValidTemperatureRange() {
       val res = weatherAPI.getFourDayForecast().execute()
        val result = res.body() ?: return
        val fourdayParser = FourDayParser(result)
        val temp = fourdayParser.getGeneralAvgTemperature(0)
        assertFalse(temp > 100)
        assertFalse(temp < 0)
    }
}