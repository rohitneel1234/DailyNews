package com.rohitneel.dailynews.articles

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toLocalDateTime
import kotlin.math.abs

class ArticleUseCase(private val repository: ArticleRepository) {

    suspend fun fetchArticles(forceFetch: Boolean): List<Article> {
        val articleRaw = repository.getArticles(forceFetch)
        return mapToArticle(articleRaw)
    }

    private fun mapToArticle(articleRaw: List<ArticleRaw>): List<Article> = articleRaw.map { raw ->
         Article(
            title = raw.title,
            desc = raw.description?: "Click to find out more",
            date = getDaysAgoString(raw.date),
            imageUrl = raw.imageUrl ?: "https://image.cnbcfm.com/api/v1/image/107326078-1698758530118-gettyimages-1765623456-wall26362_igj6ehhp.jpg"
        )
    }

    private fun getDaysAgoString(date:String): String {
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val days = today.daysUntil(
            Instant.parse(date).toLocalDateTime(TimeZone.currentSystemDefault()).date
        )
        val result = when {
            abs(days) > 1 -> "${abs(days)} day ago"
            abs(days) == 1 -> "Yesterday"
            else -> "Today"
        }
        return result
    }
}