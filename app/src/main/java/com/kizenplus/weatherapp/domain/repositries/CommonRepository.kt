package com.kizenplus.weatherapp.domain.repositries

import com.kizenplus.weatherapp.data.Resource
import com.kizenplus.weatherapp.network.model.WeatherResponse
import kotlinx.coroutines.flow.Flow

interface CommonRepository {
    suspend fun getWeather(query: String): Flow<Resource<WeatherResponse>>
}