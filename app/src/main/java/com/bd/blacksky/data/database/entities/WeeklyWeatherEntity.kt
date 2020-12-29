package com.bd.blacksky.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weekly_weather_entity")
data class WeeklyWeatherEntity (
        @PrimaryKey
        var id: Int,
        @ColumnInfo(name = "date") var date: String?,
        @ColumnInfo(name = "image") var image: String?,
        @ColumnInfo(name = "temp") var temp: String?,
)