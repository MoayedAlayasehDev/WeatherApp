package com.kizenplus.weatherapp.ui.activites

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.kizenplus.weatherapp.R
import com.kizenplus.weatherapp.application.MyApp
import com.kizenplus.weatherapp.ui.WeatherViewModel
import com.kizenplus.weatherapp.ui.screens.FavoritesScreen
import com.kizenplus.weatherapp.ui.screens.HomeScreen
import com.kizenplus.weatherapp.ui.screens.SearchScreen
import com.kizenplus.weatherapp.ui.screens.WeatherAppScreens
import com.kizenplus.weatherapp.ui.theme.AppColor
import com.kizenplus.weatherapp.ui.theme.BlueColor
import com.kizenplus.weatherapp.ui.theme.WeatherAppTheme
import com.kizenplus.weatherapp.utils.SharedPreferencesHelper
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MyApp.instance.updateLanguage(this)
        setContent {
            val navController = rememberNavController()
            WeatherAppTheme(dynamicColor = false) {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding()
                ) {
                    MainScreen(navController, context = LocalContext.current)
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NavigationGraph(navController: NavHostController) {
    val locationPermissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )
    var permissionsGranted by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val weatherViewModel: WeatherViewModel = hiltViewModel()

    LaunchedEffect(locationPermissionsState) {
        // Request permissions on initial launch
        if (!locationPermissionsState.allPermissionsGranted) {
            locationPermissionsState.launchMultiplePermissionRequest()
        }
    }

    LaunchedEffect(locationPermissionsState.allPermissionsGranted) {
        if (locationPermissionsState.allPermissionsGranted && !permissionsGranted) {
            permissionsGranted = true
            fetchWeatherIfPermissionsGranted(context, weatherViewModel)
        }
    }

    NavHost(navController, startDestination = WeatherAppScreens.HomeScreen.route) {
        composable(route = WeatherAppScreens.HomeScreen.route) {
            val weatherResource by weatherViewModel.weatherFlow.collectAsState()
            HomeScreen(weatherResource)
        }
        composable(WeatherAppScreens.SearchScreen.route) {
            val weatherResource by weatherViewModel.searchWeatherFlow.collectAsState()
            SearchScreen(weatherResource, onSearch = {
                weatherViewModel.searchWeather(it)
            }, onFavoriteClick = {
                weatherViewModel.saveFavorite(context = context, it!!)
            })

        }
        composable(WeatherAppScreens.FavoriteScreen.route) {
            LaunchedEffect(Unit) {
                weatherViewModel.getFavoriteWeathers(context)
            }
            val favoriteWeathers = weatherViewModel.favoriteWeathers.collectAsState().value
            FavoritesScreen(favoriteWeathers)
        }
    }
}

private suspend fun fetchWeatherIfPermissionsGranted(
    context: Context, weatherViewModel: WeatherViewModel
) {
    val currentLocation = getCurrentLocation(context)
    if (currentLocation != null) {
        val latitude = currentLocation.latitude.toString()
        val longitude = currentLocation.longitude.toString()
        weatherViewModel.fetchWeather("$latitude,$longitude")
    } else {
        Toast.makeText(
            context, "Unable to retrieve current location.", Toast.LENGTH_SHORT
        ).show()
    }
}

private suspend fun getCurrentLocation(context: Context): android.location.Location? {
    val fusedLocationClient: FusedLocationProviderClient =
        LocationServices.getFusedLocationProviderClient(context)
    return suspendCancellableCoroutine { continuation ->
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            continuation.resume(location)
        }.addOnFailureListener { _ ->
            continuation.resume(null)
        }
    }
}


@Composable
fun MainScreen(navController: NavHostController, context: Context) {
    val selectedItem = remember { mutableIntStateOf(0) }
    Scaffold(
        contentColor = AppColor,
        containerColor = AppColor,
        topBar = {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(16.dp),
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.language),
                        modifier = Modifier
                            .size(24.dp)
                            .clickable {
                                updateAppLanguage(context)
                            },
                        contentDescription = "changeLanguage",
                        tint = Color.White
                    )
                    Text(
                        text = stringResource(R.string.weather_label),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentWidth(Alignment.CenterHorizontally)
                    )
                }
            }
        },
        bottomBar = {
            BottomNavigationBar(selectedItem = selectedItem.intValue, onItemSelected = {
                selectedItem.intValue = it
                handleNavigation(it, navController)
            })
        },
    ) {
        Box(
            modifier = Modifier.padding(it)
        ) {
            NavigationGraph(navController = navController)
        }
    }
}

fun updateAppLanguage(context: Context) {
    val lang: String = SharedPreferencesHelper.getString(
        context, SharedPreferencesHelper.LANGUAGE_KEY, SharedPreferencesHelper.DEFAULT_LANGUAGE
    )
    if (lang == SharedPreferencesHelper.DEFAULT_LANGUAGE) {
        MyApp.instance.updateLanguage(context, SharedPreferencesHelper.ARABIC_LANGUAGE)
    } else {
        MyApp.instance.updateLanguage(context, SharedPreferencesHelper.LANGUAGE_KEY)
    }

    context.startActivity(Intent(context, MainActivity::class.java))
}

private fun handleNavigation(index: Int, navController: NavHostController) {
    when (index) {
        0 -> navController.navigate(WeatherAppScreens.HomeScreen.route)
        1 -> navController.navigate(WeatherAppScreens.SearchScreen.route)
        2 -> navController.navigate(WeatherAppScreens.FavoriteScreen.route)
    }
}

@Composable
fun BottomNavigationBar(
    selectedItem: Int, onItemSelected: (Int) -> Unit
) {
    val context = LocalContext.current
    val labels = listOf(
        context.getString(R.string.home_label),
        context.getString(R.string.search_label),
        context.getString(R.string.fav_label),
    )
    val icons = listOf(
        R.drawable.ic_home,
        R.drawable.ic_search,
        R.drawable.ic_favourite,
    )

    BottomNavigation(
        elevation = 4.dp, backgroundColor = Color.White
    ) {
        icons.forEachIndexed { index, iconRes ->
            BottomNavigationItem(icon = {
                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(22.dp)
                )
            },
                label = { Text(text = labels[index], fontSize = 11.sp) },
                selected = selectedItem == index,
                selectedContentColor = BlueColor,
                unselectedContentColor = Color.Gray,
                onClick = { onItemSelected(index) })
        }
    }
}

@Preview
@Composable
fun MainScreenPreview() {
    HomeScreen(null)
}