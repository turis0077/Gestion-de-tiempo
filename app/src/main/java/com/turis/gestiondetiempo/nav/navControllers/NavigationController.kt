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
        // Flujo de autenticación (AuthNav)
        composable<MainRoutes.Start> {
            AuthNav(
                onLoggedIn = {
                    root.navigate(MainRoutes.Logged) {
                        // Elimina Start del backstack para que no se pueda volver atrás
                        popUpTo(MainRoutes.Start) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }

        // Flujo logeado (LoggedNav)
        composable<MainRoutes.Logged> {
            LoggedNav(
                onLogout = {
                    root.navigate(MainRoutes.Start) {
                        // Elimina Logged del backstack para que no se pueda volver atrás
                        popUpTo(MainRoutes.Logged) { inclusive = true }
                        launchSingleTop = true
                    }
                }
            )
        }
    }
}