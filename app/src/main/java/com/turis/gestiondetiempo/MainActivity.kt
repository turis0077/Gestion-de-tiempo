package com.turis.gestiondetiempo

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import com.turis.gestiondetiempo.nav.navControllers.NavigationController
import com.turis.gestiondetiempo.ui.theme.GestionDeTiempoTheme
import com.turis.gestiondetiempo.ui.language.LanguageViewModel
import com.turis.gestiondetiempo.ui.theme.ThemePreferences
import com.turis.gestiondetiempo.ui.theme.ThemeViewModel
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainContent()
        }
    }
}

@SuppressLint("LocalContextConfigurationRead")
@Composable
fun MainContent() {
    val languageViewModel: LanguageViewModel = viewModel()
    val currentLanguage by languageViewModel.currentLanguage.collectAsState()
    val context = LocalContext.current

    val localizedContext = remember(currentLanguage) {
        val locale = Locale.forLanguageTag(currentLanguage.code)
        Locale.setDefault(locale)
        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)
        context.createConfigurationContext(configuration)
    }

    CompositionLocalProvider(LocalContext provides localizedContext) {
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
