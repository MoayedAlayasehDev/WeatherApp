package com.kizenplus.weatherapp.di

import com.kizenplus.weatherapp.network.model.WeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap
import retrofit2.http.Url


interface ApiServices {

    @GET
    suspend fun getWeather(
        @Url apiName: String,
        @QueryMap params: Map<String, String>
    ): Response<WeatherResponse>

}