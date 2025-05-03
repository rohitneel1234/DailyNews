package com.rohitneel.dailynews.articles.di

import com.rohitneel.dailynews.articles.ArticleDataSource
import com.rohitneel.dailynews.articles.ArticleRepository
import com.rohitneel.dailynews.articles.ArticleService
import com.rohitneel.dailynews.articles.ArticleUseCase
import com.rohitneel.dailynews.articles.ArticleViewModel
import org.koin.dsl.module


val articleModule = module {
    single<ArticleService> { ArticleService(get()) }
    single<ArticleUseCase> { ArticleUseCase(get()) }
    single<ArticleViewModel> { ArticleViewModel(get()) }
    single<ArticleDataSource> { ArticleDataSource(get()) }
    single<ArticleRepository> { ArticleRepository(get(), get()) }
}