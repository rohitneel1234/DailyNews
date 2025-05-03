package com.rohitneel.dailynews.di

import com.rohitneel.dailynews.articles.di.articleModule

val sharedModule = listOf(
    articleModule,
    networkModule
)