package com.rohitneel.dailynews.di

import app.cash.sqldelight.db.SqlDriver
import com.rohitneel.dailynews.db.DailyNewsDatabase
import com.rohitneel.dailynews.db.DatabaseDriverFactory
import org.koin.dsl.module

val databaseModule = module {
    single<SqlDriver> {  DatabaseDriverFactory().createDriver() }
    single<DailyNewsDatabase> { DailyNewsDatabase(get()) }
}