package com.bd.blacksky.data.network

import com.bd.blacksky.data.network.datamodels.GeoLocationDataModel
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ExternalIpAPI {

    @GET("json/")
    suspend fun getGeoLocation(
    ): Response<GeoLocationDataModel>

    companion object{
        operator fun invoke(): ExternalIpAPI {
            return Retrofit.Builder()
                .baseUrl("https://geolocation-db.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ExternalIpAPI::class.java)
        }
    }
}