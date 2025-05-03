package com.rohitneel.dailynews.articles

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ArticleViewModel(private val useCase: ArticleUseCase) : ViewModel() {
    private val scope = viewModelScope

    private val _articleState = MutableStateFlow(ArticleState(loading = true))

    val articleState: StateFlow<ArticleState> get() = _articleState

    init {
        getArticle()
    }

     fun getArticle(forceFetch: Boolean = false) {
        scope.launch {
            _articleState.emit(ArticleState(loading = true, article = _articleState.value.article))
            val fetchedArticles = useCase.fetchArticles(forceFetch)
            _articleState.emit(ArticleState(article = fetchedArticles))
        }
    }
}

