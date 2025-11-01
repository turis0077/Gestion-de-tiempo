package com.turis.gestiondetiempo.nav.navControllers

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.turis.gestiondetiempo.features.calendar.CalendarDayView
import com.turis.gestiondetiempo.features.calendar.CalendarMonthView
import com.turis.gestiondetiempo.features.calendar.CalendarWeekView
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

        }
    }
}