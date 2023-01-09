package com.fl0w3r.graphmaps.ui.theme

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable

@Composable
fun GraphMapsTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = ColorPalette,
        typography = Typography,
        content = content
    )
}