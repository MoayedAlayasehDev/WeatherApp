package com.kizenplus.weatherapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kizenplus.weatherapp.R
import com.kizenplus.weatherapp.data.ApiStatus
import com.kizenplus.weatherapp.data.Resource
import com.kizenplus.weatherapp.network.model.WeatherResponse
import com.kizenplus.weatherapp.ui.components.DayWeatherScreen
import com.kizenplus.weatherapp.ui.theme.BlueColor
import com.kizenplus.weatherapp.ui.theme.GrayColor

@Composable
fun HomeScreen(weatherResource: Resource<WeatherResponse>?) {
    when (weatherResource?.status) {
        ApiStatus.LOADING -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(
                    color = BlueColor,
                    strokeWidth = 4.dp
                )
            }
        }

        ApiStatus.SUCCESS -> {
            TabScreen(
                weatherResource.data
            )
        }

        ApiStatus.ERROR -> {
            Text(text = "Error:")
        }

        null -> {
            Text(text = "No weather data available.")
        }
    }
}

@Composable
fun TabScreen(data: WeatherResponse?) {
    var tabIndex by remember { mutableIntStateOf(0) }
    val tabs = listOf(
        stringResource(R.string.today_label),
        stringResource(R.string.tomorrow_label)
    )
    Column(modifier = Modifier.fillMaxSize()) {
        Spacer(modifier = Modifier.height(50.dp))
        TabRow(
            selectedTabIndex = tabIndex,
            modifier = Modifier
                .padding(horizontal = 50.dp)
                .height(60.dp),
            backgroundColor = Color.Transparent,
            contentColor = Color.Transparent,
            divider = {},
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    text = {
                        Text(
                            title,
                            color = if (tabIndex == index) Color.White else Color.Gray,
                            style = MaterialTheme.typography.body1.copy(fontSize = 12.sp)
                        )
                    },
                    selected = tabIndex == index,
                    onClick = { tabIndex = index },
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .background(
                            if (tabIndex == index) BlueColor
                            else GrayColor, shape = RoundedCornerShape(8.dp)
                        )
                        .padding(horizontal = 24.dp, vertical = 6.dp)
                )
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        when (tabIndex) {
            0 -> DayWeatherScreen(
                weather = data?.weatherDataResponse?.currentConditionResponse?.first(),
                city = data?.weatherDataResponse?.nearestAreaResponse?.first()?.region?.first()?.value.toString(),
                hourlyResponse = data?.weatherDataResponse?.weatherItemResponse?.first()?.hourlyResponse
            )

            1 ->
                DayWeatherScreen(
                    weather = data?.weatherDataResponse?.currentConditionResponse?.first(),
                    city = data?.weatherDataResponse?.nearestAreaResponse?.first()?.region?.first()?.value.toString(),
                    hourlyResponse = data?.weatherDataResponse?.weatherItemResponse?.get(tabIndex)?.hourlyResponse
                )
        }
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreen(null)
}