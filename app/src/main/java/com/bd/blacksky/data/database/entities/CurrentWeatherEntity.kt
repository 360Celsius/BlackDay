package com.bd.blacksky.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "current_weather_entity")
data class CurrentWeatherEntity (
        @PrimaryKey
        var id: Int,
        @ColumnInfo(name = "current_day_id") var current_day_id: Int?,
        @ColumnInfo(name = "temp") var temp: Double?,
        @ColumnInfo(name = "wind_speed") var wind_speed: Double?,
        @ColumnInfo(name = "description") var description: String?,
        @ColumnInfo(name = "main") var main: String?,
)