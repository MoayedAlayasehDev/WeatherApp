package com.kizenplus.weatherapp.network.model


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class WeatherResponse(
    @SerializedName("data")
    var weatherDataResponse: WeatherDataResponse? = WeatherDataResponse()
) {
    @Keep
    data class WeatherDataResponse(
        @SerializedName("ClimateAverages")
        var climateAverageResponses: List<ClimateAverageResponse?>? = listOf(),
        @SerializedName("current_condition")
        var currentConditionResponse: List<CurrentConditionResponse?>? = listOf(),
        @SerializedName("request")
        var requestResponse: List<RequestResponse?>? = listOf(),
        @SerializedName("weather")
        var weatherItemResponse: List<WeatherItemResponse?>? = listOf(),
        @SerializedName("nearest_area")
        var nearestAreaResponse: List<NearestAreaResponse?>? = listOf()
    ) {

        @Keep
        data class NearestAreaResponse(
            @SerializedName("areaName")
            var areaName: List<AreaName?>? = listOf(),
            @SerializedName("country")
            var country: List<Country?>? = listOf(),
            @SerializedName("latitude")
            var latitude: String? = "",
            @SerializedName("longitude")
            var longitude: String? = "",
            @SerializedName("population")
            var population: String? = "",
            @SerializedName("region")
            var region: List<Region?>? = listOf(),
            @SerializedName("weatherUrl")
            var weatherUrl: List<WeatherUrl?>? = listOf()
        ) {
            @Keep
            data class AreaName(
                @SerializedName("value")
                var value: String? = ""
            )

            @Keep
            data class Country(
                @SerializedName("value")
                var value: String? = ""
            )

            @Keep
            data class Region(
                @SerializedName("value")
                var value: String? = ""
            )

            @Keep
            data class WeatherUrl(
                @SerializedName("value")
                var value: String? = ""
            )
        }

        @Keep
        data class ClimateAverageResponse(
            @SerializedName("month")
            var monthResponse: List<MonthResponse?>? = listOf()
        ) {
            @Keep
            data class MonthResponse(
                @SerializedName("absMaxTemp")
                var absMaxTemp: String? = "",
                @SerializedName("absMaxTemp_F")
                var absMaxTempF: String? = "",
                @SerializedName("avgDailyRainfall")
                var avgDailyRainfall: String? = "",
                @SerializedName("avgMinTemp")
                var avgMinTemp: String? = "",
                @SerializedName("avgMinTemp_F")
                var avgMinTempF: String? = "",
                @SerializedName("index")
                var index: String? = "",
                @SerializedName("name")
                var name: String? = ""
            )
        }

        @Keep
        data class CurrentConditionResponse(
            @SerializedName("cloudcover")
            var cloudcover: String? = "",
            @SerializedName("FeelsLikeC")
            var feelsLikeC: String? = "",
            @SerializedName("FeelsLikeF")
            var feelsLikeF: String? = "",
            @SerializedName("humidity")
            var humidity: String? = "",
            @SerializedName("localObsDateTime")
            var localObsDateTime: String? = "",
            @SerializedName("observation_time")
            var observationTime: String? = "",
            @SerializedName("precipInches")
            var precipInches: String? = "",
            @SerializedName("precipMM")
            var precipMM: String? = "",
            @SerializedName("pressure")
            var pressure: String? = "",
            @SerializedName("pressureInches")
            var pressureInches: String? = "",
            @SerializedName("temp_C")
            var tempC: String? = "",
            @SerializedName("temp_F")
            var tempF: String? = "",
            @SerializedName("uvIndex")
            var uvIndex: String? = "",
            @SerializedName("visibility")
            var visibility: String? = "",
            @SerializedName("visibilityMiles")
            var visibilityMiles: String? = "",
            @SerializedName("weatherCode")
            var weatherCode: String? = "",
            @SerializedName("weatherDesc")
            var weatherDesc: List<WeatherDesc?>? = listOf(),
            @SerializedName("weatherIconUrl")
            var weatherIconUrl: List<WeatherIconUrl?>? = listOf(),
            @SerializedName("winddir16Point")
            var winddir16Point: String? = "",
            @SerializedName("winddirDegree")
            var winddirDegree: String? = "",
            @SerializedName("windspeedKmph")
            var windspeedKmph: String? = "",
            @SerializedName("windspeedMiles")
            var windspeedMiles: String? = ""
        ) {
            @Keep
            data class WeatherDesc(
                @SerializedName("value")
                var value: String? = ""
            )

            @Keep
            data class WeatherIconUrl(
                @SerializedName("value")
                var value: String? = ""
            )
        }

        @Keep
        data class RequestResponse(
            @SerializedName("query")
            var query: String? = "",
            @SerializedName("type")
            var type: String? = ""
        )

        @Keep
        data class WeatherItemResponse(
            @SerializedName("astronomy")
            var astronomyResponse: List<AstronomyResponse?>? = listOf(),
            @SerializedName("avgtempC")
            var avgtempC: String? = "",
            @SerializedName("avgtempF")
            var avgtempF: String? = "",
            @SerializedName("date")
            var date: String? = "",
            @SerializedName("hourly")
            var hourlyResponse: List<HourlyResponse?>? = listOf(),
            @SerializedName("maxtempC")
            var maxtempC: String? = "",
            @SerializedName("maxtempF")
            var maxtempF: String? = "",
            @SerializedName("mintempC")
            var mintempC: String? = "",
            @SerializedName("mintempF")
            var mintempF: String? = "",
            @SerializedName("sunHour")
            var sunHour: String? = "",
            @SerializedName("totalSnow_cm")
            var totalSnowCm: String? = "",
            @SerializedName("uvIndex")
            var uvIndex: String? = ""
        ) {
            @Keep
            data class AstronomyResponse(
                @SerializedName("moon_illumination")
                var moonIllumination: String? = "",
                @SerializedName("moon_phase")
                var moonPhase: String? = "",
                @SerializedName("moonrise")
                var moonrise: String? = "",
                @SerializedName("moonset")
                var moonset: String? = "",
                @SerializedName("sunrise")
                var sunrise: String? = "",
                @SerializedName("sunset")
                var sunset: String? = ""
            )

            @Keep
            data class HourlyResponse(
                @SerializedName("chanceoffog")
                var chanceoffog: String? = "",
                @SerializedName("chanceoffrost")
                var chanceoffrost: String? = "",
                @SerializedName("chanceofhightemp")
                var chanceofhightemp: String? = "",
                @SerializedName("chanceofovercast")
                var chanceofovercast: String? = "",
                @SerializedName("chanceofrain")
                var chanceofrain: String? = "",
                @SerializedName("chanceofremdry")
                var chanceofremdry: String? = "",
                @SerializedName("chanceofsnow")
                var chanceofsnow: String? = "",
                @SerializedName("chanceofsunshine")
                var chanceofsunshine: String? = "",
                @SerializedName("chanceofthunder")
                var chanceofthunder: String? = "",
                @SerializedName("chanceofwindy")
                var chanceofwindy: String? = "",
                @SerializedName("cloudcover")
                var cloudcover: String? = "",
                @SerializedName("DewPointC")
                var dewPointC: String? = "",
                @SerializedName("DewPointF")
                var dewPointF: String? = "",
                @SerializedName("diffRad")
                var diffRad: String? = "",
                @SerializedName("FeelsLikeC")
                var feelsLikeC: String? = "",
                @SerializedName("FeelsLikeF")
                var feelsLikeF: String? = "",
                @SerializedName("HeatIndexC")
                var heatIndexC: String? = "",
                @SerializedName("HeatIndexF")
                var heatIndexF: String? = "",
                @SerializedName("humidity")
                var humidity: String? = "",
                @SerializedName("precipInches")
                var precipInches: String? = "",
                @SerializedName("precipMM")
                var precipMM: String? = "",
                @SerializedName("pressure")
                var pressure: String? = "",
                @SerializedName("pressureInches")
                var pressureInches: String? = "",
                @SerializedName("shortRad")
                var shortRad: String? = "",
                @SerializedName("tempC")
                var tempC: String? = "",
                @SerializedName("tempF")
                var tempF: String? = "",
                @SerializedName("time")
                var time: String? = "",
                @SerializedName("uvIndex")
                var uvIndex: String? = "",
                @SerializedName("visibility")
                var visibility: String? = "",
                @SerializedName("visibilityMiles")
                var visibilityMiles: String? = "",
                @SerializedName("weatherCode")
                var weatherCode: String? = "",
                @SerializedName("weatherDesc")
                var weatherDescResponse: List<WeatherDescResponse?>? = listOf(),
                @SerializedName("weatherIconUrl")
                var weatherIconUrlResponse: List<WeatherIconUrlResponse?>? = listOf(),
                @SerializedName("WindChillC")
                var windChillC: String? = "",
                @SerializedName("WindChillF")
                var windChillF: String? = "",
                @SerializedName("WindGustKmph")
                var windGustKmph: String? = "",
                @SerializedName("WindGustMiles")
                var windGustMiles: String? = "",
                @SerializedName("winddir16Point")
                var winddir16Point: String? = "",
                @SerializedName("winddirDegree")
                var winddirDegree: String? = "",
                @SerializedName("windspeedKmph")
                var windspeedKmph: String? = "",
                @SerializedName("windspeedMiles")
                var windspeedMiles: String? = ""
            ) {
                @Keep
                data class WeatherDescResponse(
                    @SerializedName("value")
                    var value: String? = ""
                )

                @Keep
                data class WeatherIconUrlResponse(
                    @SerializedName("value")
                    var value: String? = ""
                )
            }
        }
    }
}