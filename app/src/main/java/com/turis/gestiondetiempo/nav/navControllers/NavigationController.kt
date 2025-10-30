package com.turis.gestiondetiempo.nav.navControllers

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.turis.gestiondetiempo.nav.routes.MainRoutes

@Composable
fun NavigationController() {
    val root = rememberNavController()

    NavHost(
        navController = root,
        startDestination = MainRoutes.Start
    ) {

    }
}