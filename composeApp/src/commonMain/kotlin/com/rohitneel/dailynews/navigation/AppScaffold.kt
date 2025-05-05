package com.rohitneel.dailynews.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.rohitneel.dailynews.articles.application.Article
import com.rohitneel.dailynews.screens.AboutScreen
import com.rohitneel.dailynews.screens.ArticleDetailScreen
import com.rohitneel.dailynews.screens.ArticleScreen
import com.rohitneel.dailynews.screens.Screens
import kotlinx.serialization.json.Json

@Composable
fun AppScaffold() {
    val navController = rememberNavController()

    Scaffold {
        AppNavHost(
            navController = navController,
            modifier = Modifier.fillMaxSize()
                .padding(it)
        )
    }
}

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Screens.ARTICLES.route,
        modifier = modifier
    ) {
        composable(Screens.ARTICLES.route) {
            ArticleScreen(
                onAboutButtonClick = { navController.navigate(Screens.ABOUT.route) },
                navController = navController
            )
        }
        composable(Screens.ARTICLE_DETAIL.route) {
            navController.previousBackStackEntry?.savedStateHandle?.get<String>("article")?.let { article ->
                val currentArticle: Article = Json.decodeFromString(article)
                ArticleDetailScreen(currentArticle =  currentArticle, navController = navController)
            }
        }
        composable(Screens.ABOUT.route) {
            AboutScreen(
                onBackButtonClick = { navController.popBackStack() }
            )
        }
    }
}
