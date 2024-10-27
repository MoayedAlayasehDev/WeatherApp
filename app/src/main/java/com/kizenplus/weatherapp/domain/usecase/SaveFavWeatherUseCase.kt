package com.kizenplus.weatherapp.domain.usecase

import com.kizenplus.weatherapp.domain.repositries.CommonRepository
import com.kizenplus.weatherapp.network.model.WeatherResponse
import javax.inject.Inject

class SaveFavWeatherUseCase @Inject constructor(private val commonRepository: CommonRepository) {
    suspend operator fun invoke(query: WeatherResponse.WeatherDataResponse.CurrentConditionResponse) {
//        return commonRepository.saveFavWeather(query)
    }
}