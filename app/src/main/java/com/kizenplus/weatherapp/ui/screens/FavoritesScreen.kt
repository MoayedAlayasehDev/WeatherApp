package com.kizenplus.weatherapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kizenplus.weatherapp.network.model.WeatherResponse
import com.kizenplus.weatherapp.ui.components.WeatherListItem
import com.kizenplus.weatherapp.ui.theme.AppColor

@Composable
fun FavoritesScreen(
    weatherResource: List<WeatherResponse.WeatherDataResponse.CurrentConditionResponse?>,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColor)
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(weatherResource) {
                WeatherListItem(it, it?.localObsDateTime, isFavorite = true, onFavoriteClick = { fav ->
                })
            }
        }
    }
}

@Preview
@Composable
fun FavoritesScreenPreview() {
    FavoritesScreen(emptyList())
}
