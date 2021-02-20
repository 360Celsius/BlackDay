package com.bd.blacksky.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bd.blacksky.data.database.entities.CurrentWeatherEntity
import com.bd.blacksky.data.database.entities.WeeklyDayWeatherEntity
import com.bd.blacksky.data.network.datamodels.WeeklyWeatherDataModel
import com.bd.blacksky.repositories.GeoLocationRepository
import com.bd.blacksky.repositories.WeatherRepository
import com.bd.blacksky.utils.SingleLiveEvent
import kotlinx.coroutines.*
import retrofit2.Response

class WeatherViewModel(
        private val weatherRepository: WeatherRepository
): ViewModel() {

    val isEventFinishedWeatherViewModel = SingleLiveEvent<Boolean>()
    private lateinit var getWeather_job: CompletableJob
    private val TAG: String = "AppDebug"

    fun getWeather(lat: String,lon: String,appid: String,units: String){
        if(!::getWeather_job.isInitialized){
            initjob()
        }
        runCoroutine (weatherRepository,lat,lon,appid,units)
    }


    fun initjob(){

        getWeather_job = Job()
        getWeather_job.invokeOnCompletion {
            it?.message.let{
                var msg = it
                if(msg.isNullOrBlank()){
                    msg = "Unknown cancellation error."
                }
                Log.e(TAG, "${getWeather_job} was cancelled. Reason: ${msg}")
            }
        }

    }


    fun runCoroutine (weatherRepository: WeatherRepository,lat: String,lon: String,appid: String,units: String){

        CoroutineScope(Dispatchers.IO + getWeather_job).launch {
            Log.d(TAG, "getWeather_job coroutine ${this} is activated with job ${getWeather_job}.")


            try {
                val weatherResponce: Response<WeeklyWeatherDataModel> =
                        weatherRepository.getWeatherFromAPI(lat, lon, appid, units)

                val currurentWeatherEntityRandomId: Int = (0..100).random()
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


                val weeklyArraySize: Int? = weatherResponce.body()?.daily?.size?.minus(1)
                var weeklyWeatherList: ArrayList<WeeklyDayWeatherEntity> = ArrayList()

                for (i in 0..(weeklyArraySize ?: 0)) {
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
                    weeklyWeatherList.add(weeklyDayWeatherEntity)
                    //weatherRepository.saveWeeklyDayWeatherToDB(weeklyDayWeatherEntity)
                }
                weatherRepository.saveBulkWeeklyDayWeatherToDB(weeklyWeatherList)
                isEventFinishedWeatherViewModel.postValue(true)


                //Log.d(TAG, "WeatherViewModel " + weatherResponce.body()?.timezone.toString())
            } catch (e: Exception) {
                Log.d(TAG, e.toString())
                getWeather_job.cancel(CancellationException("getWeather_job " + e.toString()))
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