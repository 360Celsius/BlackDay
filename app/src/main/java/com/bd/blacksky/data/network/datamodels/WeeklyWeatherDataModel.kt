package com.bd.blacksky.data.network.datamodels

data class WeeklyWeatherDataModel(
        var lat: Double?,
        var lon: Double?,
        var timezone: String?,
        var timezone_offset: String?,
        var current: Current?,
        var hourly: ArrayList<Hourly>?,
        var daily: ArrayList<Daily>?,
)

data class Current(
        var dt: Int?,
        var sunrise: Double?,
        var sunset: Double?,
        var temp: Double?,
        var feels_like: Double?,
        var pressure: Double?,
        var humidity: Double?,
        var dew_point: Double?,
        var uvi: Double?,
        var clouds: Double?,
        var visibility: Double?,
        var wind_speed: Double?,
        var wind_deg: Double?,
        var weather: List<Weather>?
)

data class Weather(
        var id: Int?,
        var main: String?,
        var description: String?,
        var icon: String?,
)

data class Hourly(
        var dt: Int?,
        var temp: Double?,
        var feels_like: Double?,
        var pressure: Double?,
        var humidity: Double?,
        var dew_point: Double?,
        var uvi: Double?,
        var clouds: Double?,
        var visibility: Double?,
        var wind_speed: Double?,
        var wind_deg: Double?,
        var weather: List<Weather>?,
)

data class Daily (
        var dt: Int?,
        var sunrise: Double?,
        var sunset: Double?,
        var temp: Temp?,
        var feels_like: FeelsLike?,
        var pressure: Double?,
        var humidity: Double?,
        var dew_point: Double?,
        var wind_speed: Double?,
        var weather: List<Weather>?,
        var clouds: Double?,
        var pop: Double?,
        var uvi: Double?,
)

data class Temp (
        var day: Double?,
        var min: Double?,
        var max: Double?,
        var night: Double?,
        var eve: Double?,
        var morn: Double?,
)

data class FeelsLike(
        var day: Double?,
        var night: Double?,
        var eve: Double?,
        var morn: Double?,
)