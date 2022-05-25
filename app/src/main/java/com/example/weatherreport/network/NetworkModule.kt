package com.example.weatherreport.network

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkModule {
    // Create and config an instance of retrofit
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.data.gov.sg/v1/environment/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    // Create our API
    val weatherAPI: WeatherAPI = retrofit.create(WeatherAPI::class.java)
}