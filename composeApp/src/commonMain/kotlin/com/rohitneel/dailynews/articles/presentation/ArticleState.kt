package com.rohitneel.dailynews.articles.presentation

import com.rohitneel.dailynews.articles.application.Article

data class ArticleState(
    val article: List<Article> = listOf(),
    val loading: Boolean = false,
    val error: String? = null
)