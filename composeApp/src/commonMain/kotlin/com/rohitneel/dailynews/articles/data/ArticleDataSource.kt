package com.rohitneel.dailynews.articles.data

import com.rohitneel.dailynews.db.DailyNewsDatabase

class ArticleDataSource(private val database: DailyNewsDatabase) {

    fun getAllArticles(): List<ArticleRaw> =
        database.dailyNewsDatabaseQueries.selectAllArticles(::mapToArticleRaw).executeAsList()

    fun insertArticles(articles: List<ArticleRaw>) {
        database.dailyNewsDatabaseQueries.transaction {
            articles.forEach { articleRaw ->
                insertArticle(articleRaw)
            }
        }
    }

    fun clearArticles() =
        database.dailyNewsDatabaseQueries.removeAllArticles()

    private fun insertArticle(articlesRaw: ArticleRaw) {
        database.dailyNewsDatabaseQueries.insertArticle(
            title = articlesRaw.title,
            description = articlesRaw.description,
            date = articlesRaw.date,
            imageUrl = articlesRaw.imageUrl,
            content = articlesRaw.content
        )
    }

    private fun mapToArticleRaw(
        title: String,
        description: String?,
        date: String,
        imageUrl: String?,
        content: String?
    ): ArticleRaw = ArticleRaw(
        title = title,
        description = description,
        date = date,
        imageUrl = imageUrl,
        content = content
    )
}