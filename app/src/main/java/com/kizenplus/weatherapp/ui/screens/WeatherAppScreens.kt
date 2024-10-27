package com.kizenplus.weatherapp.ui.screens


sealed class WeatherAppScreens(val route: String) {
    object HomeScreen : WeatherAppScreens("home")
    object SearchScreen : WeatherAppScreens("search")
    object FavoriteScreen : WeatherAppScreens("favorite")

}
