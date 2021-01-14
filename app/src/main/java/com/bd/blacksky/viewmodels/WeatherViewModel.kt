package com.bd.blacksky.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.bd.blacksky.data.database.entities.CurrentWeatherEntity
import com.bd.blacksky.data.database.entities.WeeklyDayWeatherEntity
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

                val currurentWeatherEntityRandomId: Int =  (0..100).random()
                val currentWeatherEntity: CurrentWeatherEntity = CurrentWeatherEntity(
                    0,
                        currurentWeatherEntityRandomId,
                        weatherResponce.body()?.current?.temp,
                        weatherResponce.body()?.current?.wind_speed,
                        weatherResponce.body()?.current?.weather?.get(0)?.description,
                        weatherResponce.body()?.current?.weather?.get(0)?.main
                )

                weatherRepository.saveCurrentWeatherToDB(currentWeatherEntity)


                val weeklyArraySize: Int? =  weatherResponce.body()?.daily?.size?.minus(1)
                for(i in 0..(weeklyArraySize?:0)){
                    val weeklyDayWeatherEntity: WeeklyDayWeatherEntity = WeeklyDayWeatherEntity(
                            i,
                            currurentWeatherEntityRandomId,
                            weatherResponce.body()?.daily?.get(i)?.dt,
                            weatherResponce.body()?.daily?.get(i)?.temp?.min,
                            weatherResponce.body()?.daily?.get(i)?.temp?.max,
                            weatherResponce.body()?.daily?.get(i)?.weather?.get(0)?.description.toString(),
                            weatherResponce.body()?.daily?.get(i)?.weather?.get(0)?.main.toString()
                    )
                    weatherRepository.saveWeeklyDayWeatherToDB(weeklyDayWeatherEntity)
                }

                Log.e("test", "WeatherViewModel "+ weatherResponce.body()?.timezone.toString())
            }catch (e: Exception){
                Log.e("Coroutines Error", e.toString())
            }
        }
    }

}