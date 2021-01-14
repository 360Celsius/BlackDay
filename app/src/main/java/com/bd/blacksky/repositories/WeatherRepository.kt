package com.bd.blacksky.repositories

import androidx.lifecycle.LiveData
import com.bd.blacksky.data.database.BlackDayDataBase
import com.bd.blacksky.data.database.entities.CurrentWeatherEntity
import com.bd.blacksky.data.database.entities.WeeklyDayWeatherEntity
import com.bd.blacksky.data.network.WeatherAPI
import com.bd.blacksky.data.network.datamodels.WeeklyWeatherDataModel
import retrofit2.Response

class WeatherRepository(
        private val blackDayDataBase: BlackDayDataBase,
        private val weatherAPI: WeatherAPI
) {
    suspend fun getWeatherFromAPI(lat: String,lon: String,appid: String): Response<WeeklyWeatherDataModel>{
        val weatherFromAPI: Response<WeeklyWeatherDataModel> = weatherAPI.getWeeklyWeather(lat,lon,appid)
        return weatherFromAPI
    }

    suspend fun saveCurrentWeatherToDB(currentWeatherEntity: CurrentWeatherEntity){
        blackDayDataBase.getCurrentWeathernDao().insertCurrentWeatherToDB(currentWeatherEntity)
    }

    fun getCurrentWeatherFromDM(): LiveData<CurrentWeatherEntity> {
        return  blackDayDataBase.getCurrentWeathernDao().getCurrentWeatherFromDB()
    }


    suspend fun saveWeeklyDayWeatherToDB(weeklyDayWeatherEntity: WeeklyDayWeatherEntity){
        blackDayDataBase.getWeeklyWeatherDao().insertWeeklyWeatherToDB(weeklyDayWeatherEntity)
    }

    fun getWeeklyWeatherFromDM(id: Int): LiveData<List<WeeklyDayWeatherEntity>>{
        return  blackDayDataBase.getWeeklyWeatherDao().getWeeklyWeatherFromDB(id)
    }
}