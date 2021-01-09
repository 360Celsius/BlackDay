package com.bd.blacksky.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bd.blacksky.data.database.dao.GeoLocationDao
import com.bd.blacksky.data.database.entities.GeoLocationEntity

@Database(
        entities = [GeoLocationEntity::class],
        version = 1
)

abstract class BlackDayDataBase : RoomDatabase() {

    abstract fun getGeoLocationDao(): GeoLocationDao


    companion object{

        @Volatile
        private var instance: BlackDayDataBase? = null
        private val LOCK = Any() //Make sure to have one instance of the DB

        operator fun invoke (context: Context) = instance ?: synchronized(LOCK){
            instance ?: buildDataBase(context).also {
                instance = it
            }
        }

        private fun buildDataBase(context: Context) =
                Room.databaseBuilder(
                        context.applicationContext,
                        BlackDayDataBase::class.java,
                        "BlackDayDataBase.db")
                        .build()
    }

}