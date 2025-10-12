package com.turis.gestiondetiempo.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.turis.gestiondetiempo.ui.theme.Cyan40

private val DarkColorScheme = darkColorScheme(
    primary = Cyan40,
    primaryContainer = Cyan90,
    onPrimaryContainer = Cyan30
)

private val LightColorScheme = lightColorScheme(
    primary = Cyan40,
    primaryContainer = Cyan90,
    onPrimaryContainer = Cyan30,
    secondary = Green40,
    secondaryContainer = Green90,
    onSecondaryContainer = Green30,
    tertiary = Blue40,
    tertiaryContainer = Blue90,
    onTertiaryContainer = Blue30,

)

@Composable
fun GestionDeTiempoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}