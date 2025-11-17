package com.turis.gestiondetiempo.ui.language

import android.content.Context
import android.content.res.Configuration
import java.util.Locale

object LanguageHelper {

    fun setLocale(context: Context, languageCode: String): Context {
        val locale = Locale.forLanguageTag(languageCode)
        Locale.setDefault(locale)

        val configuration = Configuration(context.resources.configuration)
        configuration.setLocale(locale)

        return context.createConfigurationContext(configuration)
    }

    fun getLocale(languageCode: String): Locale {
        return Locale.forLanguageTag(languageCode)
    }
}