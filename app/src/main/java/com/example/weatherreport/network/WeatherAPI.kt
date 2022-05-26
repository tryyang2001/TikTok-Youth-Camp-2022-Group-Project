package com.example.weatherreport.network

import com.example.weatherreport.network.types.FourDayForecast
import com.example.weatherreport.network.types.TwentyFourHourForecast
import retrofit2.Call
import retrofit2.http.GET

interface WeatherAPI {
    @GET("24-hour-weather-forecast")
    fun getTwentyFourHourForecast(): Call<TwentyFourHourForecast.Response>

    @GET("4-day-weather-forecast")
    fun getFourDayForecast(): Call<FourDayForecast.Response>
}
