package com.turis.gestiondetiempo.nav.navBar

import androidx.compose.runtime.Composable
import com.turis.gestiondetiempo.nav.routes.CalendarMode
import com.turis.gestiondetiempo.nav.routes.LoggedRoutes

data class TopBarConfig(
    val titleText: String? = null,
    val showBack: Boolean = false,
    val showUserHeader: Boolean = true,
    val showHome: Boolean = false,
    val showConfig: Boolean = true,
    val showCalendar: Boolean = true,
)

fun topBarFor(
    route: LoggedRoutes,
    userName: String = "User1259"
): TopBarConfig = when (route) {
    is LoggedRoutes.Menu -> TopBarConfig(
        titleText = null, showUserHeader = true,
        showBack = false, showHome = false, showConfig = true, showCalendar = true
    )
    is LoggedRoutes.TaskList -> TopBarConfig(
        titleText = null, showUserHeader = true,
        showBack = false, showHome = false, showConfig = true, showCalendar = true
    )
    is LoggedRoutes.TaskDetail -> TopBarConfig(
        titleText = route.taskTitle ?: "Detalle de tarea", showUserHeader = false,
        showBack = true, showHome = true, showConfig = false, showCalendar = false
    )
    is LoggedRoutes.Timer -> TopBarConfig(
        titleText = "Temporizador", showUserHeader = false,
        showBack = true, showHome = true, showConfig = true, showCalendar = false
    )
    is LoggedRoutes.Calendar -> {
        val title = when (route.mode) {
            CalendarMode.MONTH -> "Calendario"
            CalendarMode.WEEK  -> "Semana actual"
            CalendarMode.DAY   -> route.isoDate ?: "Hoy"
        }
        TopBarConfig(
            titleText = title,
            showUserHeader = false,
            showBack = true,
            showHome = true,
            showConfig = false,
            showCalendar = false
        )
    }
    is LoggedRoutes.Profile -> TopBarConfig(
        titleText = "Datos de Usuario", showUserHeader = false,
        showBack = true, showHome = false, showConfig = true, showCalendar = false
    )
    is LoggedRoutes.Settings -> TopBarConfig(
        titleText = "Configuración", showUserHeader = false,
        showBack = true, showHome = true, showConfig = false, showCalendar = false
    )
}