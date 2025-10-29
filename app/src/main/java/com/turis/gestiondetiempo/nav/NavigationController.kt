package com.turis.gestiondetiempo.nav

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.turis.gestiondetiempo.nav.AuthRoutes.Start
import com.turis.gestiondetiempo.nav.AuthRoutes.LogIn
import com.turis.gestiondetiempo.nav.AuthRoutes.SignIn
import com.turis.gestiondetiempo.nav.LoggedRoutes.Menu
import com.turis.gestiondetiempo.nav.LoggedRoutes.Profile
import com.turis.gestiondetiempo.nav.LoggedRoutes.TaskList
import com.turis.gestiondetiempo.nav.LoggedRoutes.TaskDetail
import com.turis.gestiondetiempo.nav.LoggedRoutes.Timer
import com.turis.gestiondetiempo.nav.LoggedRoutes.Calendar

@Composable
fun NavigationController() {
    val nav = rememberNavController()

    NavHost(
        navController = nav,
        startDestination = Start
    ) {
        composable<Start> {

        }

        composable<LogIn> {

        }

        composable<SignIn> {

        }

        composable<Menu> {

        }

        composable<TaskList> {

        }

        composable<TaskDetail> {

        }

        composable<Timer> {

        }

        composable<Calendar> {

        }

        composable<Profile> {

        }
    }
}