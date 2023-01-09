package com.fl0w3r.graphmaps

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.fl0w3r.graphmaps.navigation.GraphNavHost
import com.fl0w3r.graphmaps.ui.theme.GraphMapsTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GraphMapsApp()
        }
    }
}


@Composable
fun GraphMapsApp() {
    val navController = rememberNavController()

    GraphMapsTheme {
        Scaffold { innerPadding ->
            GraphNavHost(navController = navController, modifier = Modifier.padding(innerPadding))
        }
    }
}