package com.bd.blacksky.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "geo_location_entity")
data class GeoLocationEntity (
        @PrimaryKey
        var id: Int,
        @ColumnInfo(name = "country_code") var country_code: String?,
        @ColumnInfo(name = "country_name") var country_name: String?,
        @ColumnInfo(name = "city") var city: String?,
        @ColumnInfo(name = "postal") var postal: Double?,
        @ColumnInfo(name = "latitude") var latitude: Double?,
        @ColumnInfo(name = "longitude") var longitude: Double?,
        @ColumnInfo(name = "IPv4") var IPv4: String?,
        @ColumnInfo(name = "state") var state: String?,
)