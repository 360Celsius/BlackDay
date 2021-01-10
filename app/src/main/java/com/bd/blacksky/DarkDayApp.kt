package com.bd.blacksky

import android.app.Application
import com.bd.blacksky.data.database.BlackDayDataBase
import com.bd.blacksky.data.network.ExternalIpAPI
import com.bd.blacksky.data.network.WeatherAPI
import com.bd.blacksky.repositories.GeoLocationRepository
import com.bd.blacksky.repositories.WeatherRepository
import com.bd.blacksky.viewmodels.factories.GeoLocationViewModelFactory
import com.bd.blacksky.viewmodels.factories.WeatherViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidCoreModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider
import org.kodein.di.generic.singleton

class DarkDayApp : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidCoreModule(this@DarkDayApp))

        bind() from singleton { ExternalIpAPI() }
        bind() from singleton { WeatherAPI() }

        bind() from singleton { BlackDayDataBase(instance()) }

        bind() from singleton { GeoLocationRepository(instance(),instance()) }
        bind() from singleton { WeatherRepository(instance()) }

        bind() from singleton { GeoLocationViewModelFactory(instance()) }
        bind() from singleton { WeatherViewModelFactory(instance()) }

    }
}