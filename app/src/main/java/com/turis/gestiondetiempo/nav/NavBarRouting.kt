package com.turis.gestiondetiempo.nav

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.CalendarToday
import androidx.compose.material.icons.rounded.Home
import androidx.compose.ui.graphics.vector.ImageVector

enum class NavBarRouting(
    val label: String,
    val icon: ImageVector,
    val showLabel: Boolean
) {
    PROFILE(label = "UserName", icon = Icons.Outlined.Person, showLabel = true),
    CONFIG(label = "Configuración", icon = Icons.Outlined.Settings, showLabel = false),
    CALENDAR(label = "Calendario", icon = Icons.Rounded.CalendarToday, showLabel = false),
    HOME(label = "Inicio", icon = Icons.Rounded.Home, showLabel = false)
}