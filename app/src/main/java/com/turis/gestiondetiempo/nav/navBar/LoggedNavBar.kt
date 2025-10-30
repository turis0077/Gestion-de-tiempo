package com.turis.gestiondetiempo.nav.navBar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.ui.Modifier
import com.turis.gestiondetiempo.nav.navBar.NavBarRouting
import com.turis.gestiondetiempo.nav.routes.LoggedRoutes

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

@Composable
fun MainScaffold(
    title: String,
    showBack: Boolean,
    showHome: Boolean,
    showProfile: Boolean,
    showConfig: Boolean,
    showCalendar: Boolean,
    showTitle: Boolean,
    content: @Composable () -> Unit
) {
    Scaffold(
        topBar = {

        }
    ) { inner -> Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(inner)
        ) {
            content()
        }
    }
}