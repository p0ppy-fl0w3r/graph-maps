package com.fl0w3r.graphmaps.ui.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun HomeScreen(onAddClicked: () -> Unit, modifier: Modifier = Modifier) {
    HomeBody(modifier = modifier, onAddClicked = { onAddClicked() })
}

@Composable
fun HomeBody(onAddClicked: () -> Unit, modifier: Modifier = Modifier) {
    Column(modifier = modifier) {

        Row() {
            IconButton(onClick = { onAddClicked() }) {
                Icon(imageVector = Icons.Default.Map, contentDescription = "Add new user.")
            }
        }
        Text(text = "Home Screen")
    }
}