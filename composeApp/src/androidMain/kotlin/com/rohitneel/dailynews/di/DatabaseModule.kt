package com.rohitneel.dailynews.di

import app.cash.sqldelight.db.SqlDriver
import com.rohitneel.dailynews.db.DailyNewsDatabase
import com.rohitneel.dailynews.db.DatabaseDriverFactory
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val databaseModule = module {
    single<SqlDriver> { DatabaseDriverFactory(androidContext()).createDriver() }
    single<DailyNewsDatabase> { DailyNewsDatabase(get()) }
}