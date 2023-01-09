package com.fl0w3r.graphmaps.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.fl0w3r.graphmaps.R

@Composable
fun Location(latitude: String, longitude: String, modifier: Modifier = Modifier) {

    AsyncImage(
        model = ImageRequest.Builder(LocalContext.current).data(
            "https://www.mapquestapi.com/staticmap/v5/map?key=tgRq8pPaSmG5SEIJipAv3fgoQZGgobGr&center=$latitude,$longitude&size=600,400@2x"
        ).crossfade(true)
            .placeholder(R.drawable.placeholder)
            .build(), contentDescription = null,
        modifier = modifier
            .fillMaxWidth()
            .height(200.dp),
        contentScale = ContentScale.Crop

    )

}