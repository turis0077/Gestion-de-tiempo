package com.turis.gestiondetiempo.nav.navControllers

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.turis.gestiondetiempo.account.SettingsScreen
import com.turis.gestiondetiempo.features.calendar.CalendarDayView
import com.turis.gestiondetiempo.features.calendar.CalendarMonthView
import com.turis.gestiondetiempo.features.calendar.CalendarWeekView
import com.turis.gestiondetiempo.features.menu.MainLoggedMenu
import com.turis.gestiondetiempo.features.profile.ProfileScreen
import com.turis.gestiondetiempo.features.tasks.TaskListScreen
import com.turis.gestiondetiempo.features.tasks.TaskTemplateScreen
import com.turis.gestiondetiempo.model.sampleTaskDetail
import com.turis.gestiondetiempo.model.sampleTaskListOne
import com.turis.gestiondetiempo.nav.navBar.LoggedNavBar
import com.turis.gestiondetiempo.nav.navBar.topBarFor
import com.turis.gestiondetiempo.nav.routes.LoggedRoutes

@Composable
fun LoggedNav(onLoggedIn: () -> Unit = {}) {
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
                        modifier = Modifier.fillMaxSize(),
                        onTaskClick = { taskId, taskTitle ->
                            nav.navigate(LoggedRoutes.TaskDetail(taskId = taskId, taskTitle = taskTitle))
                        },
                        onProfileClick = goProfile,
                        onSettingsClick = goConfig,
                        onCalendarClick = goCalendar
                    )
                }

                composable<LoggedRoutes.TaskList> {
                    TaskListScreen(
                        uiState = sampleTaskListOne(),
                        onAdd = { /* TODO: Implementar navegación a crear tarea */ },
                        onTaskClick = { taskId, taskTitle ->
                            nav.navigate(LoggedRoutes.TaskDetail(taskId = taskId, taskTitle = taskTitle))
                        },
                        onProfileClick = goProfile,
                        onSettingsClick = goConfig,
                        onCalendarClick = goCalendar
                    )
                }

                composable<LoggedRoutes.TaskDetail> {
                    TaskTemplateScreen(
                        task = sampleTaskDetail(),
                        onAddSubItem = { /* TODO: Implementar agregar subtarea */ },
                        onBack = goBack,
                        onProfileClick = goProfile
                    )
                }

                composable<LoggedRoutes.Timer> {

                }

        composable<LoggedRoutes.Calendar> {
            var selectedTabIndex by remember { mutableStateOf(0) }

            Column {
                // Barra de pestañas
                TabRow(
                    selectedTabIndex = selectedTabIndex,
                    modifier = Modifier.padding(bottom = 8.dp),
                ) {
                    Tab(
                        selected = selectedTabIndex == 0,
                        onClick = { selectedTabIndex = 0 }
                    ) {
                        Text("Mes", modifier = Modifier.padding(16.dp))
                    }
                    Tab(
                        selected = selectedTabIndex == 1,
                        onClick = { selectedTabIndex = 1 }
                    ) {
                        Text("Semana", modifier = Modifier.padding(16.dp))
                    }
                    Tab(
                        selected = selectedTabIndex == 2,
                        onClick = { selectedTabIndex = 2 }
                    ) {
                        Text("Día", modifier = Modifier.padding(16.dp))
                    }
                }

                //
                when (selectedTabIndex) {
                    0 -> CalendarMonthView()
                    1 -> CalendarWeekView()
                    2 -> CalendarDayView()
                }
            }
        }

                composable<LoggedRoutes.Profile> {
                    ProfileScreen(
                        onBack = goBack
                    )
                }

                composable<LoggedRoutes.Settings> {
                    SettingsScreen(
                        modifier = Modifier.fillMaxSize(),
                    )
                }
            }
        }
    }