package com.rohitneel.dailynews.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.rohitneel.dailynews.Platform

@Composable
fun AboutScreen(
    onBackButtonClick: () -> Unit
) {
    Column {
        Toolbar(onBackButtonClick)
        ContentView()
    }
}

@Composable
fun Toolbar(onBackButtonClick: () -> Unit) {
    TopAppBar(
        title = { Text(text = "About Device") },
        navigationIcon = {
            IconButton(onClick = onBackButtonClick) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Back"
                )
            }
        },
        backgroundColor = Color.White
    )
}

@Composable
fun ContentView() {
    val items = makeItems()
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(items) {
            RowView(title = it.first, description = it.second)
        }
    }
}

private fun makeItems(): List<Pair<String, String>> {
    val platform = Platform()
    platform.logSystemInfo()

    return listOf(
        Pair("osName", platform.osName),
        Pair("osVersion", platform.osVersion),
        Pair("deviceModel", platform.deviceModel),
        Pair("density", platform.density.toString())
    )
}

@Composable
fun RowView(title: String, description: String) {
    Column(modifier = Modifier.padding(8.dp)) {
        Text(
            text = title,
            style = MaterialTheme.typography.body1,
            color = Color.Gray
        )
        Text(
            text = description,
            style = MaterialTheme.typography.body2
        )
    }
    Divider()
}