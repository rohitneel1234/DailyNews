package com.rohitneel.dailynews.articles.application

import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val title: String,
    val desc: String,
    val date: String,
    val imageUrl: String,
    val content: String
)