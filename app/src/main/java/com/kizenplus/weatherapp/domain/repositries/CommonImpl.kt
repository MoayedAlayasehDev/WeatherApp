package com.kizenplus.weatherapp.domain.repositries

import com.kizenplus.weatherapp.data.Resource
import com.kizenplus.weatherapp.di.ApiServices
import com.kizenplus.weatherapp.network.ApiConstants
import com.kizenplus.weatherapp.network.ApiConstants.Companion.API_KEY
import com.kizenplus.weatherapp.network.ApiConstants.Companion.EXTRA
import com.kizenplus.weatherapp.network.ApiConstants.Companion.FORMAT
import com.kizenplus.weatherapp.network.ApiConstants.Companion.INCLUDE_LOCATION
import com.kizenplus.weatherapp.network.ApiConstants.Companion.NUMBER_OF_DAYS
import com.kizenplus.weatherapp.network.ApiConstants.Companion.QUERY
import com.kizenplus.weatherapp.network.ApiConstants.Companion.TP
import com.kizenplus.weatherapp.network.ApiConstants.Companion.apiKey
import com.kizenplus.weatherapp.network.model.WeatherResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class CommonImpl @Inject constructor(
    private val apiServices: ApiServices,
) : CommonRepository {

    override suspend fun getWeather(query: String): Flow<Resource<WeatherResponse>> = flow {
        try {
            val response = apiServices.getWeather(
                ApiConstants.getWeather,
                params = mapOf(
                    API_KEY to apiKey,
                    QUERY to query,
                    FORMAT to "json",
                    NUMBER_OF_DAYS to "7",
                    EXTRA to "localObsTime",
                    INCLUDE_LOCATION to "yes",
                    TP to "1"
                )
            )
            if (response.isSuccessful) {
                val responseBody = response.body()
                if (responseBody != null) {
                    emit(Resource.success(responseBody))
                } else {
                    emit(Resource.error(null))
                }
            } else {
                emit(Resource.error(null))
            }
        } catch (e: Exception) {
            emit(Resource.error(null))
        }
    }
}

