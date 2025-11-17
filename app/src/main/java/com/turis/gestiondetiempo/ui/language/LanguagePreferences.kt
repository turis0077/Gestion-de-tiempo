package com.turis.gestiondetiempo.ui.language

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.languageDataStore: DataStore<Preferences> by preferencesDataStore(name = "language_preferences")

class LanguagePreferences(private val context: Context) {

    companion object {
        private val LANGUAGE_KEY = stringPreferencesKey("selected_language")
    }

    enum class Language(val code: String, val displayName: String) {
        SPANISH("es", "Español"),
        ENGLISH("en", "English")
    }

    val languageFlow: Flow<Language> = context.languageDataStore.data.map { preferences ->
        when (preferences[LANGUAGE_KEY]) {
            Language.ENGLISH.code -> Language.ENGLISH
            else -> Language.SPANISH // Default to Spanish
        }
    }

    suspend fun setLanguage(language: Language) {
        context.languageDataStore.edit { preferences ->
            preferences[LANGUAGE_KEY] = language.code
        }
    }
}