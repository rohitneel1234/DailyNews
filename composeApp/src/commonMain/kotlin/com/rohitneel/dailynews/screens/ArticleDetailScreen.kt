package com.rohitneel.dailynews.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil3.compose.AsyncImagePainter
import coil3.compose.rememberAsyncImagePainter
import com.rohitneel.dailynews.articles.application.Article
import com.rohitneel.dailynews.articles.presentation.ArticleViewModel
import com.rohitneel.dailynews.components.PulseAnimation
import com.rohitneel.dailynews.helper.koinInject
import com.rohitneel.dailynews.theme.mediumPadding
import com.rohitneel.dailynews.theme.xLargePadding
import dailynews.composeapp.generated.resources.Res
import dailynews.composeapp.generated.resources.ic_browse
import dailynews.composeapp.generated.resources.logo
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun ArticleDetailScreen(
    viewModel: ArticleViewModel = koinInject(),
    currentArticle: Article,
    navController: NavController
) {
    val url = LocalUriHandler.current
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.ArrowBack,
                            contentDescription = null,
                        )
                    }
                },
                title = {
                    Text(
                        text = "News Detail",
                    )
                },
                actions = {
                    IconButton(onClick = {

                    }) {
                        Icon(
                            imageVector = Icons.Filled.Share,
                            contentDescription = null,
                        )
                    }
                    IconButton(onClick = {
                    }) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_browse),
                            contentDescription = null,
                        )
                    }
                },
                backgroundColor = Color.White
            )
        },
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 16.dp)
                .padding(innerPadding),
            contentPadding = PaddingValues(horizontal = xLargePadding),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(mediumPadding)
        ) {
            item {
                Box(
                    modifier = Modifier.fillMaxWidth().clip(RoundedCornerShape(10)),
                    contentAlignment = Alignment.Center
                ) {
                    var imageLoadResult by remember {
                        mutableStateOf<Result<Painter>?>(null)
                    }
                    val painter = rememberAsyncImagePainter(
                        model = currentArticle.imageUrl,
                        onSuccess = {
                            imageLoadResult =
                                if (it.painter.intrinsicSize.width > 1 && it.painter.intrinsicSize.height > 1) {
                                    Result.success(it.painter)
                                } else {
                                    Result.failure(Exception("Invalid image size"))
                                }
                        },
                        onError = {
                            it.result.throwable.printStackTrace()
                            imageLoadResult = Result.failure(it.result.throwable)
                        }
                    )

                    val painterState by painter.state.collectAsState()
                    val transition by animateFloatAsState(
                        targetValue = if (painterState is AsyncImagePainter.State.Success) {
                            1f
                        } else {
                            0f
                        },
                        animationSpec = tween(durationMillis = 800)
                    )

                    when (val result = imageLoadResult) {
                        null -> {
                            PulseAnimation(
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        else -> {
                            Image(
                                painter = if (result.isSuccess) painter else {
                                    painterResource(Res.drawable.logo)
                                },
                                contentDescription = null,
                                contentScale = ContentScale.Fit,
                                modifier = Modifier
                                    .wrapContentSize()
                                    .graphicsLayer {
                                        if (result.isSuccess) {
                                            rotationX = (1f - transition) * 30f
                                            val scale = 0.8f + (0.2f * transition)
                                            scaleX = scale
                                            scaleY = scale
                                        }
                                    }
                            )
                        }
                    }
                }
            }

            item {
                Text(
                    text = currentArticle.content
                )
            }

        }
    }

}