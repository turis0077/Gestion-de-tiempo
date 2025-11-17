package com.turis.gestiondetiempo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.turis.gestiondetiempo.nav.navControllers.NavigationController
import com.turis.gestiondetiempo.ui.theme.GestionDeTiempoTheme
import com.turis.gestiondetiempo.ui.theme.ThemePreferences
import com.turis.gestiondetiempo.ui.theme.ThemeViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val themeViewModel: ThemeViewModel = viewModel()
            val themeState by themeViewModel.themeState.collectAsState()
            val systemInDarkTheme = isSystemInDarkTheme()

            val darkTheme = when (themeState.themeMode) {
                ThemePreferences.ThemeMode.LIGHT -> false
                ThemePreferences.ThemeMode.DARK -> true
                ThemePreferences.ThemeMode.SYSTEM -> systemInDarkTheme
            }

            GestionDeTiempoTheme(
                darkTheme = darkTheme,
                dynamicColor = themeState.dynamicColor
            ) {
                NavigationController()
            }
        }
    }
}
