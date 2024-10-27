package com.kizenplus.weatherapp.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kizenplus.weatherapp.data.Resource
import com.kizenplus.weatherapp.domain.usecase.GetWeatherUseCase
import com.kizenplus.weatherapp.network.model.WeatherResponse
import com.kizenplus.weatherapp.utils.SharedPreferencesHelper
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
) : ViewModel() {

    private val _weatherFlow = MutableStateFlow<Resource<WeatherResponse>>(Resource.loading(null))
    val weatherFlow: StateFlow<Resource<WeatherResponse>> = _weatherFlow

    fun fetchWeather(query: String) {
        viewModelScope.launch {
            getWeatherUseCase(query).collectLatest { resource ->
                _weatherFlow.value = resource
            }
        }
    }

    private val _searchWeatherFlow =
        MutableStateFlow<Resource<WeatherResponse>>(Resource.loading(null))
    val searchWeatherFlow: StateFlow<Resource<WeatherResponse>> = _searchWeatherFlow

    fun searchWeather(query: String) {
        viewModelScope.launch {
            getWeatherUseCase(query).collectLatest { resource ->
                _searchWeatherFlow.value = resource
            }
        }
    }

    private val _favoriteWeathers = MutableStateFlow<List<WeatherResponse.WeatherDataResponse.CurrentConditionResponse?>>(emptyList())
    val favoriteWeathers: StateFlow<List<WeatherResponse.WeatherDataResponse.CurrentConditionResponse?>> = _favoriteWeathers

    fun getFavoriteWeathers(context: Context) {
        viewModelScope.launch {
            _favoriteWeathers.value = SharedPreferencesHelper.loadFavWeathers(context) ?: emptyList()
        }
    }

    fun saveFavorite(
        context: Context,
        weather: WeatherResponse.WeatherDataResponse.CurrentConditionResponse
    ) {
        viewModelScope.launch {
            val weatherList: MutableList<WeatherResponse.WeatherDataResponse.CurrentConditionResponse?> =
                SharedPreferencesHelper.loadFavWeathers(context)?.toMutableList() ?: mutableListOf()
            weatherList.add(weather)
            SharedPreferencesHelper.saveFavWeather(context, weatherList)
        }
    }
}