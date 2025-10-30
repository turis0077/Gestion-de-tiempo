package com.turis.gestiondetiempo.nav.navControllers

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.turis.gestiondetiempo.nav.routes.AuthRoutes

@Composable
private fun AuthNav(onLoggedIn: () -> Unit) {
    val nav = rememberNavController()

    NavHost(
        navController = nav,
        startDestination = AuthRoutes.Welcome
    ) {
        composable<AuthRoutes.Welcome> {

        }

        composable<AuthRoutes.LogIn> {

        }

        composable<AuthRoutes.SignIn> {

        }
    }
}