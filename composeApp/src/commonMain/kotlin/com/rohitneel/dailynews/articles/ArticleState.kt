package com.rohitneel.dailynews.articles

data class ArticleState(
    val article: List<Article> = listOf(),
    val loading: Boolean = false,
    val error: String? = null
)