package com.kizenplus.weatherapp.utils

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.kizenplus.weatherapp.network.model.WeatherResponse

object SharedPreferencesHelper {

    const val LANGUAGE_KEY = "language"
    const val DEFAULT_LANGUAGE = "en"
    const val ARABIC_LANGUAGE = "ar"
    const val WEATHER_KEY = "weather"

    private fun getPreferences(context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun putString(context: Context, key: String, value: String) {
        val preferences = getPreferences(context)
        with(preferences.edit()) {
            putString(key, value)
            apply() // Use apply() for asynchronous saving
        }
    }

    fun getString(context: Context, key: String, defaultValue: String): String {
        val preferences = getPreferences(context)
        return preferences.getString(key, defaultValue) ?: defaultValue
    }

    fun saveFavWeather(
        context: Context,
        weather: List<WeatherResponse.WeatherDataResponse.CurrentConditionResponse?>
    ) {
        val preferences = getPreferences(context)
        val gson = Gson()
        val json = gson.toJson(weather)
        preferences.edit().putString(WEATHER_KEY, json).apply()
    }

    fun loadFavWeathers(context: Context): List<WeatherResponse.WeatherDataResponse.CurrentConditionResponse?>? {
        val preferences = getPreferences(context)
        val json = preferences.getString(WEATHER_KEY, null) ?: return null
        val gson = Gson()
        val type = object :
            TypeToken<List<WeatherResponse.WeatherDataResponse.CurrentConditionResponse?>>() {}.type
        return gson.fromJson(json, type)
    }

}
