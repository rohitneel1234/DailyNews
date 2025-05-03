package com.rohitneel.dailynews

import android.app.Application
import com.rohitneel.dailynews.di.databaseModule
import com.rohitneel.dailynews.di.sharedModule
import com.rohitneel.dailynews.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class DailyNewsApp: Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        val modules = sharedModule + viewModelModule + databaseModule
        startKoin{
            androidContext(this@DailyNewsApp)
            modules(modules)
        }
    }
}