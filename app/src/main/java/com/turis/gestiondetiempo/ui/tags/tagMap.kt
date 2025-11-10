package com.turis.gestiondetiempo.ui.tags

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.turis.gestiondetiempo.ui.theme.LocalExtendedColors

data class TagColors(val container: Color, val onContainer: Color)

@Composable
fun TagSwatch.resolve(): TagColors {
    val cs = MaterialTheme.colorScheme
    val ext = LocalExtendedColors.current
    return when (this) {
        TagSwatch.PrimaryContainer -> TagColors(cs.primaryContainer, cs.onPrimaryContainer)
        TagSwatch.SecondaryContainer -> TagColors(cs.secondaryContainer, cs.onSecondaryContainer)
        TagSwatch.TertiaryContainer -> TagColors(cs.tertiaryContainer, cs.onTertiaryContainer)
        TagSwatch.Forth -> TagColors(ext.forth, ext.forthContainer)
        TagSwatch.Fifth -> TagColors(ext.fifth, ext.fifthContainer)
    }
}