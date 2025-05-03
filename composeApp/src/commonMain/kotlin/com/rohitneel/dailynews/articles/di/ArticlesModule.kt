package com.rohitneel.dailynews.articles.di

import com.rohitneel.dailynews.articles.data.ArticleDataSource
import com.rohitneel.dailynews.articles.data.ArticleRepository
import com.rohitneel.dailynews.articles.data.ArticleService
import com.rohitneel.dailynews.articles.application.ArticleUseCase
import com.rohitneel.dailynews.articles.presentation.ArticleViewModel
import org.koin.dsl.module


val articleModule = module {
    single<ArticleService> { ArticleService(get()) }
    single<ArticleUseCase> { ArticleUseCase(get()) }
    single<ArticleViewModel> { ArticleViewModel(get()) }
    single<ArticleDataSource> { ArticleDataSource(get()) }
    single<ArticleRepository> { ArticleRepository(get(), get()) }
}