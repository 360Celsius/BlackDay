package com.bd.blacksky.repositories

import androidx.lifecycle.LiveData
import com.bd.blacksky.data.database.BlackDayDataBase
import com.bd.blacksky.data.database.entities.GeoLocationEntity
import com.bd.blacksky.data.network.ExternalIpAPI
import com.bd.blacksky.data.network.datamodels.GeoLocationDataModel
import retrofit2.Response

class GeoLocationRepository(
        private val blackDayDataBase: BlackDayDataBase,
        private val externalIpAPI: ExternalIpAPI
) {
    suspend fun getGeoLocationFromAPI(): Response<GeoLocationDataModel>{
        val geoLocationFromApi: Response<GeoLocationDataModel> = externalIpAPI.getGeoLocation()
        return geoLocationFromApi
    }

    suspend fun saveGeoLocationToDB(geoLocationEntity: GeoLocationEntity){
        blackDayDataBase.getGeoLocationDao().insertGeoLocationToDB(geoLocationEntity)
    }

    fun getGeoLocationFromDM(): LiveData<GeoLocationEntity>{
        return  blackDayDataBase.getGeoLocationDao().getGetGeoLocationFromDB()
    }

}