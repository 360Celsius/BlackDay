package com.bd.blacksky.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weekly_weather_entity")
data class WeeklyDayWeatherEntity (
        @PrimaryKey
        var id: Int,
        @ColumnInfo(name = "current_day_id") var current_day_id: Int?,
        @ColumnInfo(name = "dt") var dt: Int?,
        @ColumnInfo(name = "min") var min: Double?,
        @ColumnInfo(name = "max") var max: Double?,
        @ColumnInfo(name = "description") var description: String?,
        @ColumnInfo(name = "main") var main: String?,
)