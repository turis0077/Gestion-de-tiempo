package com.turis.gestiondetiempo.ui.theme

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class ThemeState(
    val themeMode: ThemePreferences.ThemeMode = ThemePreferences.ThemeMode.SYSTEM,
    val dynamicColor: Boolean = false
)

class ThemeViewModel(application: Application) : AndroidViewModel(application) {

    private val themePreferences = ThemePreferences(application)

    private val _themeState = MutableStateFlow(ThemeState())
    val themeState: StateFlow<ThemeState> = _themeState.asStateFlow()

    init {
        // Cargar preferencias guardadas
        viewModelScope.launch {
            themePreferences.themeModeFlow.collect { mode ->
                _themeState.update { it.copy(themeMode = mode) }
            }
        }

        viewModelScope.launch {
            themePreferences.dynamicColorFlow.collect { enabled ->
                _themeState.update { it.copy(dynamicColor = enabled) }
            }
        }
    }

    fun setThemeMode(mode: ThemePreferences.ThemeMode) {
        viewModelScope.launch {
            themePreferences.setThemeMode(mode)
        }
    }

    fun toggleDynamicColor() {
        viewModelScope.launch {
            val newValue = !_themeState.value.dynamicColor
            themePreferences.setDynamicColor(newValue)
        }
    }
}