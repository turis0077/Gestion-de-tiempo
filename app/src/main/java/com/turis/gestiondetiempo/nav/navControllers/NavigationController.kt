package com.turis.gestiondetiempo.nav.navControllers

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.turis.gestiondetiempo.nav.routes.MainRoutes

@Composable
fun NavigationController() {
    val root = rememberNavController()

    NavHost(
        navController = root,
        startDestination = MainRoutes.Start
    ) {
        composable<MainRoutes.Start> {
            AuthNav(
                onLoggedIn = {
                    root.navigate(MainRoutes.Logged) {
                        popUpTo(MainRoutes.Start) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        composable<MainRoutes.Logged> {
            LoggedNav(
                onLogout = {
                    root.navigate(MainRoutes.Start) {
                        popUpTo(MainRoutes.Logged) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}