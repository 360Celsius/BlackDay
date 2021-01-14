package com.bd.blacksky.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bd.blacksky.data.database.entities.GeoLocationEntity
import com.bd.blacksky.data.network.datamodels.GeoLocationDataModel
import com.bd.blacksky.repositories.GeoLocationRepository
import com.bd.blacksky.utils.Coroutines
import retrofit2.Response

class GeoLocationViewModel(
        private val geoLocationRepository: GeoLocationRepository
): ViewModel() {

    fun getGeoLocation(){
        Coroutines.backGround {
            try{

                val geolocationResponse: Response<GeoLocationDataModel> = geoLocationRepository.getGeoLocationFromAPI()
                Log.e("test", "GeoLocationViewModel " + geolocationResponse.body()?.country_name.toString())

                val geoLocationEntity: GeoLocationEntity = GeoLocationEntity(
                    0,
                    geolocationResponse.body()?.country_code.toString(),
                    geolocationResponse.body()?.country_name.toString(),
                    geolocationResponse.body()?.city.toString(),
                    geolocationResponse.body()?.postal,
                    geolocationResponse.body()?.latitude,
                    geolocationResponse.body()?.longitude,
                    geolocationResponse.body()?.IPv4.toString(),
                    geolocationResponse.body()?.state.toString()
                )
                geoLocationRepository.saveGeoLocationToDB(geoLocationEntity)

            }catch (e: Exception){
                Log.e("Coroutines Error", e.toString())
            }
        }
    }

    fun getGeoLocationFromDM(): LiveData<GeoLocationEntity>{
        return geoLocationRepository.getGeoLocationFromDM()
    }

}