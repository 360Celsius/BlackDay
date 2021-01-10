package com.bd.blacksky.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.bd.blacksky.data.network.datamodels.WeeklyWeatherDataModel
import com.bd.blacksky.repositories.WeatherRepository
import com.bd.blacksky.utils.Coroutines
import retrofit2.Response

class WeatherViewModel(
        private val weatherRepository: WeatherRepository
): ViewModel() {

    fun getWeather(lat: String,lon: String,appid: String){
        Coroutines.backGround {
            try{
                val weatherResponce: Response<WeeklyWeatherDataModel>  = weatherRepository.getWeatherFromAPI(lat,lon,appid)
                Log.e("test", "timezone "+ weatherResponce.body()?.timezone.toString())
            }catch (e: Exception){
                Log.e("test", e.toString())
            }
        }
    }

}