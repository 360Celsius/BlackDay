package com.bd.blacksky.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bd.blacksky.data.database.entities.GeoLocationEntity

@Dao
interface GeoLocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGeoLocationToDB(geoLocationEntity: GeoLocationEntity)

    @Query("SELECT * FROM geo_location_entity")
    fun getGetGeoLocationFromDB(): LiveData<GeoLocationEntity>

}