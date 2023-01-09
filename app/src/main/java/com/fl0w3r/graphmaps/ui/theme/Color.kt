package com.fl0w3r.graphmaps.ui.theme

import androidx.compose.ui.graphics.Color
import androidx.compose.material.darkColors


val Blue500 = Color(0xFF1E80B9)
val DarkBlue900 = Color(0xFF26282F)

// The app is always in dark mode regardless of the user's system theme.
val ColorPalette = darkColors(
    primary = Blue500,
    surface = DarkBlue900,
    onSurface = Color.White,
    background = DarkBlue900,
    onBackground = Color.White
)