package com.bd.blacksky.data.network

import com.bd.blacksky.data.network.datamodels.GeoLocationDataModel
import com.bd.blacksky.data.network.datamodels.WeeklyWeatherDataModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherAPI {

    @GET("onecall")
    suspend fun getWeeklyWeather(
            @Query("lat") lat: String,
            @Query("lon") lon: String,
            @Query("appid") appid: String,
            @Query("units") units: String
    ): Response<WeeklyWeatherDataModel>

    companion object{
        operator fun invoke(): WeatherAPI {
            return Retrofit.Builder()
                    .baseUrl("https://api.openweathermap.org/data/2.5/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(WeatherAPI::class.java)
        }
    }
}