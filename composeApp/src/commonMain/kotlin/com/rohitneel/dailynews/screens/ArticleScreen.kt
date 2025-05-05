package com.rohitneel.dailynews.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil3.compose.AsyncImage
import com.rohitneel.dailynews.articles.application.Article
import com.rohitneel.dailynews.articles.presentation.ArticleViewModel
import com.rohitneel.dailynews.components.ShimmerEffectMain
import com.rohitneel.dailynews.helper.koinInject
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Composable
fun ArticleScreen(
    onAboutButtonClick:() -> Unit,
    viewModel: ArticleViewModel = koinInject(),
    navController: NavController
) {
    val articleState = viewModel.articleState.collectAsState()

    Column {
        AppBar(onAboutButtonClick)
        if (articleState.value.loading)
            ShimmerEffectMain()
        if (articleState.value.error!=null)
            ErrorMessage(articleState.value.error!!)
        if (articleState.value.article.isNotEmpty())
            ArticleListView(viewModel, navController)
    }
}

@Composable
fun AppBar(onAboutButtonClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = "Articles") },
        actions = {
            IconButton(onClick = onAboutButtonClick) {
                Icon(
                    imageVector = Icons.Outlined.Info,
                    contentDescription = "About"
                )
            }
        },
        backgroundColor = Color.White
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArticleListView(viewModel: ArticleViewModel, navController: NavController) {
    val pullRefreshState = rememberPullRefreshState(
        refreshing = viewModel.articleState.value.loading,
        onRefresh = { viewModel.getArticle(true) }
    )
    Box(modifier = Modifier.pullRefresh(pullRefreshState)) {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(viewModel.articleState.value.article) { article ->
                ArticleItemView(article = article, onClick = {
                    val articleStr = Json.encodeToString(article)
                    navController.currentBackStackEntry?.savedStateHandle?.apply {
                        set("article", articleStr)
                    }
                    navController.navigate(Screens.ARTICLE_DETAIL.route)
                })
            }
        }
        PullRefreshIndicator(
            refreshing = viewModel.articleState.value.loading,
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
fun ErrorMessage(message: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = message,
            style = TextStyle(fontSize = 28.sp, textAlign = TextAlign.Center)
        )
    }
}

@Composable
fun ArticleItemView(article: Article, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
            .clickable { onClick() }
    ) {
        AsyncImage(
            model = article.imageUrl,
            contentDescription = null,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = article.title,
            style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 22.sp)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = article.desc,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = article.date,
            style = TextStyle(color = Color.Gray),
            modifier = Modifier.align(Alignment.End)
        )
        Spacer(modifier = Modifier.height(4.dp))
    }
}