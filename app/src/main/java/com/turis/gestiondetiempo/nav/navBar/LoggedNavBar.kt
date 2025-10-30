package com.turis.gestiondetiempo.nav.navBar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.turis.gestiondetiempo.account.SettingsScreen
import com.turis.gestiondetiempo.features.menu.MainLoggedMenu
import com.turis.gestiondetiempo.nav.navBar.NavBarRouting
import com.turis.gestiondetiempo.nav.routes.LoggedRoutes
import com.turis.gestiondetiempo.ui.theme.GestionDeTiempoTheme

val TopBarNavigation: Saver<NavBarRouting, String> = Saver(
    save = { it.name },
    restore = { NavBarRouting.valueOf(it) }
)

fun NavBarRouting.navRoute(): Any = when (this) {
    NavBarRouting.PROFILE -> LoggedRoutes.Profile
    NavBarRouting.CONFIG -> LoggedRoutes.Menu
    NavBarRouting.CALENDAR -> LoggedRoutes.Calendar
    NavBarRouting.HOME -> LoggedRoutes.TaskList
}

@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun LoggedNavBar(
    config: TopBarConfig,
    userName: String = "User1259",
    onBack: (() -> Unit)? = null,
    onHome: (() -> Unit)? = null,
    onConfig: (() -> Unit)? = null,
    onCalendar: (() -> Unit)? = null,
    modifier: Modifier = Modifier.background(MaterialTheme.colorScheme.surface),
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    if (config.showUserHeader && config.titleText == null) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(40.dp)
                                    .clip(RoundedCornerShape(50))
                                    .background(MaterialTheme.colorScheme.primaryContainer),
                                contentAlignment = Alignment.Center
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Person,
                                    contentDescription = "Usuario",
                                    tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                    modifier = Modifier.size(22.dp)
                                )
                            }
                            Text(
                                text = "  Hola, $userName",
                                color = MaterialTheme.colorScheme.onSurface,
                                style = MaterialTheme.typography.titleMedium
                            )
                        }
                    } else {
                        Text(
                            text = config.titleText ?: "",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.titleLarge
                        )
                    }
                },
                navigationIcon = {
                    if (config.showBack && onBack != null) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                actions = {
                    if (config.showHome && onHome != null) {
                        IconButton(onClick = onHome) {
                            Icon(Icons.Outlined.Home, contentDescription = "Inicio")
                        }
                    }
                    if (config.showConfig && onConfig != null) {
                        IconButton(onClick = onConfig) {
                            Icon(Icons.Outlined.Settings, contentDescription = "Configuración")
                        }
                    }
                    if (config.showCalendar && onCalendar != null) {
                        IconButton(onClick = onCalendar) {
                            Icon(Icons.Outlined.DateRange, contentDescription = "Calendario")
                        }
                    }
                }
            )
        }
    ) { inner ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(inner)
                .padding(vertical = 15.dp, horizontal = 30.dp)
                .background(MaterialTheme.colorScheme.surface),
            verticalArrangement = Arrangement.Top, //SpaceBetween?
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            content()
        }
    }
}


// Pruebas de preview

@Preview(showBackground = true, name = "Settings")
@Composable
fun Preview_Settings_In_Scaffold() {
    GestionDeTiempoTheme {
        LoggedNavBar(
            config = TopBarConfig(
                titleText = "Configuración",
                showUserHeader = false,
                showBack = true,
                showHome = true,

                showConfig = false,
                showCalendar = false
            ),
            onBack = {},
            onHome = {}
        ) {
            SettingsScreen(
                modifier = Modifier,
            )
        }
    }
}
