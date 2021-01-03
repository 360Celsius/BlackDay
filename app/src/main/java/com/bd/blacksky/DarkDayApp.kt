package com.bd.blacksky

import android.app.Application
import com.bd.blacksky.viewmodels.factories.LiveActivityToLiveFragmentSharedViewModelFactory
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidCoreModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider

class DarkDayApp : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidCoreModule(this@DarkDayApp))


        bind() from provider { LiveActivityToLiveFragmentSharedViewModelFactory() }
    }
}