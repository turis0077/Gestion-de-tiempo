package com.turis.gestiondetiempo.ui.language

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LanguageViewModel(application: Application) : AndroidViewModel(application) {

    private val languagePreferences = LanguagePreferences(application)

    private val _currentLanguage = MutableStateFlow(LanguagePreferences.Language.SPANISH)
    val currentLanguage: StateFlow<LanguagePreferences.Language> = _currentLanguage.asStateFlow()

    init {
        // Cargar idioma guardado
        viewModelScope.launch {
            languagePreferences.languageFlow.collect { language ->
                _currentLanguage.value = language
            }
        }
    }

    fun setLanguage(language: LanguagePreferences.Language) {
        viewModelScope.launch {
            languagePreferences.setLanguage(language)
        }
    }
}