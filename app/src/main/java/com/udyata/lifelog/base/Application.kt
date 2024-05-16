package com.udyata.lifelog.base

import android.app.Application
import com.udyata.lifelog.di.appDataBaseModule
import com.udyata.lifelog.di.appModule
import com.udyata.lifelog.di.daoModule
import com.udyata.lifelog.di.repositoryModule
import com.udyata.lifelog.di.useCaseModule
import com.udyata.lifelog.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class Application : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@Application)
            modules(listOf(appModule, appDataBaseModule, daoModule, repositoryModule,
                viewModelModule, useCaseModule))

        }
    }
}