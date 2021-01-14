package com.bd.blacksky.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bd.blacksky.data.database.entities.CurrentWeatherEntity

@Dao
interface CurrentWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrentWeatherToDB(currentWeatherEntity: CurrentWeatherEntity)

    @Query("SELECT * FROM current_weather_entity")
    fun getCurrentWeatherFromDB(): LiveData<CurrentWeatherEntity>
}