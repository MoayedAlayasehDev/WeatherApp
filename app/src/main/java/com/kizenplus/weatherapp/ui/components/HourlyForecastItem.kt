package com.kizenplus.weatherapp.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kizenplus.weatherapp.R
import com.kizenplus.weatherapp.network.model.WeatherResponse
import com.kizenplus.weatherapp.ui.theme.BlueColor
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

@Composable
fun HourlyForecastItem(forecast: WeatherResponse.WeatherDataResponse.WeatherItemResponse.HourlyResponse?) {
    Column(
        modifier = Modifier
            .width(80.dp)
            .padding(horizontal = 8.dp)
            .background(
                color = BlueColor,
                shape = RoundedCornerShape(16.dp)
            )
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(forecast?.weatherIconUrlResponse?.first()?.value)
                .crossfade(true)
                .build(),
            contentDescription = stringResource(R.string.app_name),
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(30.dp),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "${forecast?.tempC}°C",
            color = Color.White,
            style = MaterialTheme.typography.body1.copy(fontWeight = FontWeight.Bold)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = forecast?.time?.toInt()?.convertToReadableTime().toString(),
            color = Color.Black,
            style = MaterialTheme.typography.body2
        )
    }
}

fun Int.convertToReadableTime(): String {
    val militaryTime = String.format("%04d", this)
    val hourPart = militaryTime.substring(0, 2).toInt()
    val minutePart = militaryTime.substring(2, 4).toInt()

    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, hourPart)
    calendar.set(Calendar.MINUTE, minutePart)

    val format = SimpleDateFormat("h:mm a", Locale.getDefault())
    return format.format(calendar.time)
}

@Preview
@Composable
fun HourlyForecastItemPreview() {
    HourlyForecastItem(null)
}
