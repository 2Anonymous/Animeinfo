package com.example.animeinfo.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.animeinfo.presentation.detail.DetailScreen
import com.example.animeinfo.presentation.home.HomeScreen
import com.example.animeinfo.presentation.splash.SplashScreen

sealed class Screen(val route: String) {
    data object Splash : Screen("splash")
    data object Home : Screen("home")
    data object Detail : Screen("detail/{animeId}") {
        fun createRoute(animeId: Int) = "detail/$animeId"
    }
}

@Composable
fun NavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Splash.route
    ) {
        composable(route = Screen.Splash.route) {
            SplashScreen(
                onSplashFinished = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Splash.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = Screen.Home.route) {
            HomeScreen(
                onAnimeClick = { animeId ->
                    navController.navigate(Screen.Detail.createRoute(animeId))
                }
            )
        }

        composable(
            route = Screen.Detail.route,
            arguments = listOf(
                navArgument("animeId") {
                    type = NavType.IntType
                }
            )
        ) {
            DetailScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}

