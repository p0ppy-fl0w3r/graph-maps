package com.fl0w3r.graphmaps.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.fl0w3r.graphmaps.ui.screens.update.add.AddScreen
import com.fl0w3r.graphmaps.ui.screens.home.HomeScreen
import com.fl0w3r.graphmaps.ui.screens.update.edit.EditScreen

@Composable
fun GraphNavHost(
    navController: NavHostController, modifier: Modifier = Modifier
) {
    NavHost(navController = navController, startDestination = "home", modifier = modifier) {
        composable("home") {
            HomeScreen(
                onAddClicked = {
                    navController.navigate("add")
                },
                onEditClicked = {
                    navigateToEditUser(navController, it)
                }
            )
        }

        composable("add") {
            AddScreen(onUserAdded = {
                navController.navigate("home")
            })
        }
        composable(route = "edit/{userId}",
            arguments = listOf(
                navArgument(
                    name = "userId"
                ) {
                    type = NavType.StringType
                }
            )
        ) { entry ->
            val userId = entry.arguments?.getString("userId", "")
            userId?.let {
                EditScreen(userId = it, onUserEdited = {
                    navController.navigate("home")
                })
            }
        }
    }
}

private fun navigateToEditUser(navController: NavHostController, userId: String) {
    navController.navigate("edit/$userId")
}