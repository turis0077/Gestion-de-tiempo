package com.turis.gestiondetiempo.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalContext
import com.turis.gestiondetiempo.ui.theme.Cyan40

private val DarkColorScheme = darkColorScheme(
    primary = Cyan50,
    primaryContainer = Cyan90,
    onPrimaryContainer = Cyan40,
    secondary = Green50,
    secondaryContainer = Green90,
    onSecondaryContainer = Green40,
    tertiary = Blue50,
    tertiaryContainer = Blue90,
    onTertiaryContainer = Blue40,
    surface = SurfaceDark,
    onSurface = onSurfaceDark,
    surfaceVariant = SurfaceVarDark,
    onSurfaceVariant = onSurfaceVarDark,
    outline = outlineDark

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
    surface = SurfaceLight,
    onSurface = onSurfaceLigth,
    surfaceVariant = SurfaceVarLight,
    onSurfaceVariant = onSurfaceVarLight,
    outline = outlineLight
)


// Colores adicionales
private val ExtendedColorsLight = ExtendedColors(
    forth = Yellow40,
    forthContainer = Yellow30,
    onForthContainer = Yellow90,
    fifth = Purple40,
    fifthContainer = Purple30,
    onFifthContainer = Purple90
)


private val ExtendedColorsDark = ExtendedColors(
    forth = Yellow40,
    forthContainer = Yellow30,
    onForthContainer = Yellow90,
    fifth = Purple40,
    fifthContainer = Purple30,
    onFifthContainer = Purple90
)

@Composable
fun GestionDeTiempoTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
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

    val extended = if (darkTheme) ExtendedColorsDark else ExtendedColorsLight

    CompositionLocalProvider(LocalExtendedColors provides extended) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            content = content
        )
    }
}
