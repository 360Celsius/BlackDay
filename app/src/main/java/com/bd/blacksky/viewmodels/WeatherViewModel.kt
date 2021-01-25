package com.bd.blacksky.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bd.blacksky.data.database.entities.CurrentWeatherEntity
import com.bd.blacksky.data.database.entities.WeeklyDayWeatherEntity
import com.bd.blacksky.data.network.datamodels.WeeklyWeatherDataModel
import com.bd.blacksky.repositories.WeatherRepository
import com.bd.blacksky.utils.Coroutines
import com.bd.blacksky.utils.SingleLiveEvent
import retrofit2.Response

class WeatherViewModel(
        private val weatherRepository: WeatherRepository
): ViewModel() {

    val isEventFinishedWeatherViewModel = SingleLiveEvent<Boolean>()


    fun getWeather(lat: String,lon: String,appid: String,units: String){
        Coroutines.backGround {
            try{
                val weatherResponce: Response<WeeklyWeatherDataModel>  = weatherRepository.getWeatherFromAPI(lat,lon,appid,units)

                val currurentWeatherEntityRandomId: Int =  (0..100).random()
                val currentWeatherEntity: CurrentWeatherEntity = CurrentWeatherEntity(
                    0,
                        currurentWeatherEntityRandomId,
                        weatherResponce.body()?.current?.temp,
                        weatherResponce.body()?.current?.wind_speed,
                        weatherResponce.body()?.current?.weather?.get(0)?.description,
                        weatherResponce.body()?.current?.weather?.get(0)?.main,
                        weatherResponce.body()?.current?.weather?.get(0)?.id
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
                            weatherResponce.body()?.daily?.get(i)?.weather?.get(0)?.main.toString(),
                            weatherResponce.body()?.daily?.get(i)?.weather?.get(0)?.id
                    )
                    weatherRepository.saveWeeklyDayWeatherToDB(weeklyDayWeatherEntity)
                }

                isEventFinishedWeatherViewModel.value = true

                //Log.e("test", "WeatherViewModel "+ weatherResponce.body()?.timezone.toString())
            }catch (e: Exception){
                Log.e("Coroutines Error", e.toString())
            }
        }
    }

    fun getCurrentWeatherFromDB(): LiveData<CurrentWeatherEntity> {
        return weatherRepository.getCurrentWeatherFromDM()
    }

    fun getWeeklyWeatherFromDB(id:Int): LiveData<List<WeeklyDayWeatherEntity>> {
        return weatherRepository.getWeeklyWeatherFromDM(id)
    }

    fun getAllWeeklyWeatherFromDB(): LiveData<List<WeeklyDayWeatherEntity>> {
        return weatherRepository.getAllWeeklyWeatherFromDM()
    }
}