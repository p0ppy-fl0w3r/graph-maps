package com.fl0w3r.graphmaps.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.fl0w3r.graphmaps.ui.screens.add.AddScreen
import com.fl0w3r.graphmaps.ui.screens.home.HomeScreen

@Composable
fun GraphNavHost(
    navController: NavHostController, modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = "home", modifier = modifier) {
        composable("home") {
            HomeScreen(
                onAddClicked = {
                    navController.navigate("add")
                }
            )
        }

        composable("add") {
            AddScreen()
        }
    }
}