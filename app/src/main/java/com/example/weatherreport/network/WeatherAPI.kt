package com.example.weatherreport.network

import com.example.weatherreport.network.types.FourDayForecast
import com.example.weatherreport.network.types.TwentyFourHourForecast
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import java.time.LocalDate

interface WeatherAPI {
    @GET("24-hour-weather-forecast")
    fun getTwentyFourHourForecast(@Query("date") date: LocalDate): Call<TwentyFourHourForecast.Response>

    @GET("4-day-weather-forecast")
    fun getFourDayForecast(@Query("date") date: LocalDate): Call<FourDayForecast.Response>
}
