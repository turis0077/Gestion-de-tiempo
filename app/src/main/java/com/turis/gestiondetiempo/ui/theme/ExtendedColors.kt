package com.turis.gestiondetiempo.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class ExtendedColors(
    val forth: Color,
    val forthContainer: Color,
    val onForthContainer: Color,
    val fifth: Color,
    val fifthContainer: Color,
    val onFifthContainer: Color
)

val LocalExtendedColors = staticCompositionLocalOf {
    ExtendedColors(
        forth = Yellow40,
        forthContainer = Yellow30,
        onForthContainer = Yellow90,
        fifth = Purple40,
        fifthContainer = Purple30,
        onFifthContainer = Purple90
    )
}