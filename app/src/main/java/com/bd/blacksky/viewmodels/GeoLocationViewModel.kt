package com.bd.blacksky.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.bd.blacksky.data.database.entities.CurrentWeatherEntity
import com.bd.blacksky.data.database.entities.GeoLocationEntity
import com.bd.blacksky.data.database.entities.WeeklyDayWeatherEntity
import com.bd.blacksky.data.network.datamodels.GeoLocationDataModel
import com.bd.blacksky.data.network.datamodels.WeeklyWeatherDataModel
import com.bd.blacksky.repositories.GeoLocationRepository
import com.bd.blacksky.utils.SingleLiveEvent
import kotlinx.coroutines.*
import retrofit2.Response

class GeoLocationViewModel(
        private val geoLocationRepository: GeoLocationRepository
): ViewModel() {

    val isEventFinishedGeoLocationViewModel = SingleLiveEvent<Boolean>()
    private lateinit var getGeoLocation_job: CompletableJob
    private val TAG: String = "AppDebug"

    fun getGeoLocation(){
        if(!::getGeoLocation_job.isInitialized){
            initjob()
        }
        runCoroutine (geoLocationRepository)
    }


    fun initjob(){

        getGeoLocation_job = Job()
        getGeoLocation_job.invokeOnCompletion {
            it?.message.let{
                var msg = it
                if(msg.isNullOrBlank()){
                    msg = "Unknown cancellation error."
                }
                Log.e(TAG, "${getGeoLocation_job} was cancelled. Reason: ${msg}")
            }
        }

    }

    fun runCoroutine (geoLocationRepository: GeoLocationRepository){

        CoroutineScope(Dispatchers.IO + getGeoLocation_job).launch {
            Log.d(TAG, "getGeoLocation_job coroutine ${this} is activated with job ${getGeoLocation_job}.")


            try{

                val geolocationResponse: Response<GeoLocationDataModel> = geoLocationRepository.getGeoLocationFromAPI()
                //Log.e("test", "GeoLocationViewModel " + geolocationResponse.body()?.country_name.toString())

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
                isEventFinishedGeoLocationViewModel.postValue(true)

            }catch (e: Exception){
                Log.d(TAG, e.toString())
                getGeoLocation_job.cancel(CancellationException("getGeoLocation_job " + e.toString()))
            }

        }

    }

    fun getGeoLocationFromDM(): LiveData<GeoLocationEntity>{
        return geoLocationRepository.getGeoLocationFromDM()
    }


}