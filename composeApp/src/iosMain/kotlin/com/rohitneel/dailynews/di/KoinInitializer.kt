package com.rohitneel.dailynews.di

import org.koin.core.context.startKoin

fun initKoin() {
    val modules = sharedModule + databaseModule
    startKoin {
        modules(modules)
    }
}