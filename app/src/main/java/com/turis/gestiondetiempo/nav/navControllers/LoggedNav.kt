package com.turis.gestiondetiempo.nav.navControllers

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.turis.gestiondetiempo.nav.routes.LoggedRoutes

@Composable
private fun LoggedNav(onLoggedIn: () -> Unit) {
    val nav = rememberNavController()

    NavHost(
        navController = nav,
        startDestination = LoggedRoutes.Menu
    ) {
        composable<LoggedRoutes.Menu> {

        }

        composable<LoggedRoutes.TaskList> {

        }

        composable<LoggedRoutes.TaskDetail> {

        }

        composable<LoggedRoutes.Timer> {

        }

        composable<LoggedRoutes.Calendar> {

        }

        composable<LoggedRoutes.Profile> {

        }
    }
}