package com.kizenplus.weatherapp.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.kizenplus.weatherapp.R
import com.kizenplus.weatherapp.data.ApiStatus
import com.kizenplus.weatherapp.data.Resource
import com.kizenplus.weatherapp.network.model.WeatherResponse
import com.kizenplus.weatherapp.ui.components.WeatherListItem
import com.kizenplus.weatherapp.ui.theme.AppColor
import com.kizenplus.weatherapp.ui.theme.GrayColor

@Composable
fun SearchScreen(
    weatherResource: Resource<WeatherResponse>?, onSearch: (String) -> Unit,
    onFavoriteClick: (WeatherResponse.WeatherDataResponse.CurrentConditionResponse?) -> Unit
) {
    var query by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(AppColor)
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = { query = it },
            placeholder = {
                Text(text = stringResource(R.string.search_label), color = Color.Gray)
            },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = "Search Icon")
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                backgroundColor = GrayColor,
                focusedBorderColor = Color.Transparent,
                unfocusedBorderColor = Color.Transparent,
                textColor = Color.White
            ),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
            keyboardActions = KeyboardActions(onSearch = {
                onSearch(query)
            })
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (weatherResource?.status == ApiStatus.SUCCESS) {
            val data = weatherResource.data
            
            val city = data?.weatherDataResponse?.requestResponse?.firstOrNull()?.query
            LazyColumn {
                items(data?.weatherDataResponse?.currentConditionResponse ?: emptyList()) {
                    WeatherListItem(it, city, isFavorite = false, onFavoriteClick = { fav ->
                        onFavoriteClick(fav)
                    })
                }
            }
        }
    }
}

@Preview
@Composable
fun SearchScreenPreview() {
    SearchScreen(null, onSearch = {
    }) {
    }
}