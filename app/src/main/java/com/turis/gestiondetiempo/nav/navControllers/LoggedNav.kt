package com.turis.gestiondetiempo.nav.navControllers

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.turis.gestiondetiempo.account.SettingsScreen
import com.turis.gestiondetiempo.features.menu.MainLoggedMenu
import com.turis.gestiondetiempo.nav.navBar.LoggedNavBar
import com.turis.gestiondetiempo.nav.navBar.topBarFor
import com.turis.gestiondetiempo.nav.routes.LoggedRoutes

@Composable
private fun LoggedNav(onLoggedIn: () -> Unit) {
    val nav = rememberNavController()
    val backStack by nav.currentBackStackEntryFlow.collectAsState(initial = nav.currentBackStackEntry)

     val currentRoute: LoggedRoutes = backStack?.let { entry ->
         entry.destination.route?.let { route ->
             when {
                 route.contains("Menu") -> LoggedRoutes.Menu
                 route.contains("TaskList") -> LoggedRoutes.TaskList
                 route.contains("TaskDetail") -> runCatching { entry.toRoute<LoggedRoutes.TaskDetail>() }.getOrNull() ?: LoggedRoutes.Menu
                 route.contains("Timer") -> runCatching { entry.toRoute<LoggedRoutes.Timer>() }.getOrNull() ?: LoggedRoutes.Menu
                 route.contains("Calendar") -> runCatching { entry.toRoute<LoggedRoutes.Calendar>() }.getOrNull() ?: LoggedRoutes.Menu
                 route.contains("Profile") -> LoggedRoutes.Profile
                 route.contains("Settings") -> LoggedRoutes.Settings
                 else -> LoggedRoutes.Menu
             }
         }
    } ?: LoggedRoutes.Menu

    val config = remember(currentRoute) { topBarFor(currentRoute, userName = "User1259") }

    val goHome: () -> Unit = {
        nav.navigate(LoggedRoutes.Menu) {
            popUpTo(LoggedRoutes.Menu) { inclusive = true }
            launchSingleTop = true
        }
    }
    val goProfile: () -> Unit = { nav.navigate(LoggedRoutes.Profile) }
    val goConfig: () -> Unit = { nav.navigate(LoggedRoutes.Settings) }
    val goCalendar: () -> Unit = { nav.navigate(LoggedRoutes.Calendar()) }
    val goBack: () -> Unit = { nav.popBackStack() }

        LoggedNavBar(
            config = config,
            onBack = if (config.showBack) goBack else null,
            onHome = if (config.showHome) goHome else null,
            onProfile = if (config.showUserHeader) goProfile else null,
            onConfig = if (config.showConfig) goConfig else null,
            onCalendar = if (config.showCalendar) goCalendar else null
        ) {
            NavHost(
                navController = nav,
                startDestination = LoggedRoutes.Menu
            ) {
                composable<LoggedRoutes.Menu> {
                    MainLoggedMenu(
                        modifier = Modifier.fillMaxSize()
                    )
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

                composable<LoggedRoutes.Settings> {
                    SettingsScreen(
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }