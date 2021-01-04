package com.bd.blacksky

import android.app.Application
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.androidCoreModule

class DarkDayApp : Application(), KodeinAware {

    override val kodein: Kodein = Kodein.lazy {
        import(androidCoreModule(this@DarkDayApp))


    }
}