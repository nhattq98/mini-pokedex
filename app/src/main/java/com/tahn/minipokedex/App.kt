package com.tahn.minipokedex

import android.app.Application
import com.tahn.data.di.dataModules
import com.tahn.minipokedex.di.useCaseModule
import com.tahn.minipokedex.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        initLogger()
        initKoin()
    }

    private fun initLogger() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    dataModules,
                    useCaseModule,
                    viewModelModule,
                ),
            )
        }
    }
}
