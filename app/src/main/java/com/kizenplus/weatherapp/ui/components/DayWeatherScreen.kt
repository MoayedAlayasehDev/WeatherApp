package com.kizenplus.weatherapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kizenplus.weatherapp.R
import com.kizenplus.weatherapp.network.model.WeatherResponse
import com.kizenplus.weatherapp.ui.screens.TabScreen
import com.kizenplus.weatherapp.ui.theme.GrayColor

@Composable
fun DayWeatherScreen(
    weather: WeatherResponse.WeatherDataResponse.CurrentConditionResponse?,
    city: String,
    hourlyResponse: List<WeatherResponse.WeatherDataResponse.WeatherItemResponse.HourlyResponse?>?
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        item {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = city,
                    color = Color.White,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 16.dp)
                )
                Text(
                    text = weather?.localObsDateTime.toString(),
                    color = Color.LightGray,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Spacer(modifier = Modifier.height(30.dp))
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(weather?.weatherIconUrl?.first()?.value)
                        .crossfade(true)
                        .build(),
                    contentDescription = stringResource(R.string.app_name),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(80.dp),
                )
                Text(
                    text = "${weather?.tempC.toString()}°C",
                    color = Color.White,
                    fontSize = 40.sp,
                    modifier = Modifier.padding(top = 12.dp)
                )
                Text(
                    text = weather?.weatherDesc?.first()?.value.toString(),
                    color = Color.LightGray,
                    fontSize = 12.sp,
                    modifier = Modifier
                        .padding(top = 16.dp)
                        .background(color = GrayColor, shape = RoundedCornerShape(8.dp))
                        .padding(horizontal = 25.dp, vertical = 8.dp)
                )
                Spacer(modifier = Modifier.height(30.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    WeatherDetailItem(
                        title = stringResource(R.string.cloudy_label),
                        value = "${weather?.cloudcover.toString()} km/h"
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    WeatherDetailItem(
                        title = stringResource(R.string.humidity_label),
                        value = "${weather?.humidity.toString()} %"

                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    WeatherDetailItem(
                        title = stringResource(R.string.visibility_label),
                        value = "${weather?.visibility.toString()} %"
                    )
                }
            }
        }
        item {
            LazyRow(Modifier.padding(horizontal = 12.dp)) {
                items(hourlyResponse!!) {
                    HourlyForecastItem(it)
                }
            }
        }
    }
}


@Composable
fun WeatherDetailItem(title: String, value: String) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.body2.copy(
                color = Color.Gray,
                fontWeight = FontWeight.Medium
            )
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.h6.copy(
                color = Color.White,
                fontWeight = FontWeight.Bold
            )
        )
    }
}

@Preview
@Composable
fun TabScreenPreview() {
    TabScreen(null)
}

@Preview
@Composable
fun DayWeatherScreenPreview() {
    DayWeatherScreen(null, "Amman", hourlyResponse = null)
}