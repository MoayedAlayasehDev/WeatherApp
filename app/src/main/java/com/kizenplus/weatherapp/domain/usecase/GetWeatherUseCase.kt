package com.kizenplus.weatherapp.domain.usecase

import com.kizenplus.weatherapp.data.Resource
import com.kizenplus.weatherapp.domain.repositries.CommonRepository
import com.kizenplus.weatherapp.network.model.WeatherResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetWeatherUseCase @Inject constructor(private val commonRepository: CommonRepository) {
    suspend operator fun invoke(query: String): Flow<Resource<WeatherResponse>> {
        return commonRepository.getWeather(query)
    }
}