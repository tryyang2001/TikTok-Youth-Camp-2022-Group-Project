package com.example.weatherreport.network

import com.example.weatherreport.network.types.FourDayForecast
import com.example.weatherreport.network.types.TwentyFourHourForecast
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.Response
import retrofit2.Retrofit

interface WeatherApiService {
    // Singleton pattern
    companion object {
        private const val API_URL = "https://api.data.gov.sg/v1/environment/"
        private var weatherApiService: WeatherApiService? = null

        fun getInstance(): WeatherApiService {
            if (weatherApiService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl(API_URL)
                    .addConverterFactory(MoshiConverterFactory.create())
                    .build()

                weatherApiService = retrofit.create(WeatherApiService::class.java)
            }
            return weatherApiService!!
        }
    }

    // Endpoints
    @GET("24-hour-weather-forecast")
    suspend fun getTwentyFourHourForecast(@Query("date_time") date_time: String):
            Response<TwentyFourHourForecast.Response>

    @GET("4-day-weather-forecast")
    suspend fun getFourDayForecast(): Response<FourDayForecast.Response>
}
