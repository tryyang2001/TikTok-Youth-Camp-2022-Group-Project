package com.example.weatherreport.network.parsers

import com.example.weatherreport.network.NetworkModule
import com.example.weatherreport.network.WeatherAPI
import com.example.weatherreport.network.types.FourDayForecast
import org.junit.Assert.*

import org.junit.Test
import java.time.LocalDate

import retrofit2.Callback
import retrofit2.Call
import retrofit2.Response

class FourDayParserTest {

    private val weatherAPI: WeatherAPI = NetworkModule.weatherAPI

    @Test
    fun getGeneralAvgTemperature_ValidTemperatureRange() {
       val res = weatherAPI.getFourDayForecast(LocalDate.now().toString() + "T00:00:00").execute()
        val result = res.body() ?: return
        val fourdayParser = FourDayParser(result)
        val temp = fourdayParser.getGeneralAvgTemperature(0)
        assertFalse(temp > 100)
        assertFalse(temp < 0)
    }
}