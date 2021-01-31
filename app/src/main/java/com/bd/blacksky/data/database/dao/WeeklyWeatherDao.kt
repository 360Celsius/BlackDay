package com.bd.blacksky.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bd.blacksky.data.database.entities.WeeklyDayWeatherEntity

@Dao
interface WeeklyWeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeeklyWeatherToDB(weeklyDayWeatherEntity: WeeklyDayWeatherEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    @JvmSuppressWildcards
    suspend fun bulkInsertWeeklyWeatherToDB(listWeeklyDayWeatherEntity: List<WeeklyDayWeatherEntity>)

    @Query("SELECT * FROM weekly_weather_entity WHERE id IN(:id) ")
    fun getWeeklyWeatherFromDB(id: Int): LiveData<List<WeeklyDayWeatherEntity>>

    @Query("SELECT * FROM weekly_weather_entity")
    fun getAllWeeklyWeatherFromDB(): LiveData<List<WeeklyDayWeatherEntity>>
}