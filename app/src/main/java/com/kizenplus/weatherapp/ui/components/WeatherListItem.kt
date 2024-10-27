package com.kizenplus.weatherapp.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.kizenplus.weatherapp.ui.theme.GrayColor

@Composable
fun WeatherListItem(
    weather: WeatherResponse.WeatherDataResponse.CurrentConditionResponse?,
    city: String?,
    isFavorite: Boolean,
    onFavoriteClick: (WeatherResponse.WeatherDataResponse.CurrentConditionResponse?) -> Unit
) {
    var favorite by remember { mutableStateOf(isFavorite) }
    Spacer(modifier = Modifier.height(8.dp))

    Card(
        shape = RoundedCornerShape(16.dp),
        backgroundColor = GrayColor,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = if (favorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                contentDescription = "Favorite Icon",
                modifier = Modifier
                    .size(32.dp)
                    .padding(end = 8.dp)
                    .clickable {
                        favorite = !favorite
                        if (favorite) onFavoriteClick(weather) // Save to DB on favorite click
                    },
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "${weather?.tempC}°C",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = city.toString(), color = Color.Gray, fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.width(8.dp))
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(weather?.weatherIconUrl?.first()?.value).crossfade(true).build(),
                contentDescription = stringResource(R.string.app_name),
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(80.dp),
            )
        }
    }
}


@Preview
@Composable
fun WeatherListItemPreview() {
    WeatherListItem(null, "amman", isFavorite = true, onFavoriteClick = {

    })
}