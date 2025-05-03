package com.rohitneel.dailynews.di

import com.rohitneel.dailynews.articles.presentation.ArticleViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { ArticleViewModel(get()) }
}