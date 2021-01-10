package com.bd.blacksky.repositories

import com.bd.blacksky.data.network.WeatherAPI
import com.bd.blacksky.data.network.datamodels.WeeklyWeatherDataModel
import retrofit2.Response

class WeatherRepository(
        private val weatherAPI: WeatherAPI
) {
    suspend fun getWeatherFromAPI(lat: String,lon: String,appid: String): Response<WeeklyWeatherDataModel>{
        val weatherFromAPI: Response<WeeklyWeatherDataModel> = weatherAPI.getWeeklyWeather(lat,lon,appid)
        return weatherFromAPI
    }
}