package com.kizenplus.weatherapp.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

fun <T> fromJson(json: String?, type: Type): T? {
    return Gson().fromJson(json, type)
}

fun <T> toJson(obj: T, type: Type): String? {
    return Gson().toJson(obj, type)
}

inline fun <reified T> type(): Type = object : TypeToken<T>() {}.type
