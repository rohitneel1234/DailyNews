package com.rohitneel.dailynews.articles

class ArticleRepository(
    private val dataSource: ArticleDataSource,
    private val service: ArticleService
) {

    suspend fun getArticles(forceFetch: Boolean): List<ArticleRaw> {
        if (forceFetch) {
            dataSource.clearArticles()
            return fetchArticles()
        }

        val articles = dataSource.getAllArticles()
        println("Got ${articles.size} articles from database")

        if (articles.isEmpty()) {
            return fetchArticles()
        }
        return articles
    }

    private suspend fun fetchArticles(): List<ArticleRaw> {
        val fetchedArticles = service.fetchArticles()
        dataSource.insertArticles(fetchedArticles)
        return fetchedArticles
    }
}