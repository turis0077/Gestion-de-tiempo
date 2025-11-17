package com.turis.gestiondetiempo.account

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bedtime
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.turis.gestiondetiempo.ui.theme.GestionDeTiempoTheme
import com.turis.gestiondetiempo.ui.theme.LocalExtendedColors
import com.turis.gestiondetiempo.ui.theme.ThemePreferences
import com.turis.gestiondetiempo.ui.theme.ThemeViewModel

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onLogout: () -> Unit = {},
    themeViewModel: ThemeViewModel? = null
) {
    val context = LocalContext.current

    // Usar viewModel solo si themeViewModel es null y tenemos un Application context válido
    val viewModel = themeViewModel ?: run {
        val appContext = context.applicationContext
        if (appContext is android.app.Application) {
            viewModel<ThemeViewModel>(
                factory = ViewModelProvider.AndroidViewModelFactory.getInstance(appContext)
            )
        } else {
            // Para preview, crear un ViewModel temporal (esto no se usará en producción)
            null
        }
    }

    var language by remember { mutableStateOf("Español") }

    val themeState by (viewModel?.themeState ?: remember {
        kotlinx.coroutines.flow.MutableStateFlow(
            com.turis.gestiondetiempo.ui.theme.ThemeState()
        )
    }).collectAsState()
    val systemInDarkTheme = isSystemInDarkTheme()

    val isDarkTheme = when (themeState.themeMode) {
        ThemePreferences.ThemeMode.LIGHT -> false
        ThemePreferences.ThemeMode.DARK -> true
        ThemePreferences.ThemeMode.SYSTEM -> systemInDarkTheme
    }

    val switchChecked = themeState.themeMode == ThemePreferences.ThemeMode.DARK

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            item { SectionChip("Personalización") }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("Tema", style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                    Surface(
                        shape = RoundedCornerShape(22.dp),
                        color = MaterialTheme.colorScheme.surface
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                                .background(MaterialTheme.colorScheme.surface),
                        ) {
                            Switch(
                                checked = switchChecked,
                                onCheckedChange = { isChecked ->
                                    viewModel?.setThemeMode(
                                        if (isChecked) ThemePreferences.ThemeMode.DARK
                                        else ThemePreferences.ThemeMode.LIGHT
                                    )
                                },
                                thumbContent = {
                                    Icon(
                                        imageVector = if (isDarkTheme) Icons.Filled.Bedtime else Icons.Filled.LightMode,
                                        contentDescription = "estado del tema claro/oscuro",
                                        modifier = Modifier.padding(2.dp)
                                    )
                                },
                                colors = SwitchDefaults.colors(
                                    checkedThumbColor = MaterialTheme.colorScheme.tertiary,
                                    checkedTrackColor = MaterialTheme.colorScheme.tertiary,
                                    checkedBorderColor = MaterialTheme.colorScheme.tertiary,
                                    checkedIconColor = MaterialTheme.colorScheme.tertiaryContainer,

                                    uncheckedThumbColor = Color(0x9E6DEFE6),
                                    uncheckedTrackColor = Color(0xFF259FE0),
                                    uncheckedBorderColor = Color(0x9E6DEFE6),
                                    uncheckedIconColor = LocalExtendedColors.current.forth
                                )
                            )
                            FilledTonalIconButton(
                                onClick = { viewModel?.toggleDynamicColor() },
                                modifier = Modifier,
                                shape = RoundedCornerShape(10.dp),
                                colors = if (themeState.dynamicColor) {
                                    IconButtonDefaults.filledTonalIconButtonColors(
                                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                                        contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                                    )
                                } else {
                                    IconButtonDefaults.filledTonalIconButtonColors()
                                }
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.Palette,
                                    contentDescription = "Colores dinámicos"
                                )
                            }
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Idioma", style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                    Row {
                        Button(
                            onClick = {  },
                            shape = RoundedCornerShape(topStart = 14.dp, bottomStart = 14.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                                contentColor = MaterialTheme.colorScheme.onSurface
                            )
                        ) {
                            Text(language)
                        }
                        OutlinedButton(
                            onClick = {  },
                            shape = RoundedCornerShape( topEnd = 14.dp, bottomEnd = 14.dp ),
                            border = BorderStroke( width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant ),
                            contentPadding = PaddingValues(horizontal = 10.dp),
                        ) {
                            Icon(imageVector = Icons.Outlined.ArrowDropDown, contentDescription = "Cambiar idioma")
                        }
                    }
                }
            }

            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Fondo de reloj", style = MaterialTheme.typography.titleMedium, modifier = Modifier.weight(1f))
                    OutlinedButton(
                        onClick = { /* subir imagen */ },
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outline
                        ),
                    ) {
                        Text("Subir imagen")
                        Spacer(Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.Outlined.Add,
                            contentDescription = null
                        )
                    }
                }
            }

            item { SectionChip("Datos de usuario") }

            item {
                LabeledFilledField(
                    label = "Nombre de usuario",
                    value = "Nombre",
                    trailing = { EditBadge() }
                )
            }
            item {
                LabeledFilledField(
                    label = "Contraseña",
                    value = "*************",
                    trailing = { EditBadge() }
                )
            }
            item {
                LabeledFilledField(
                    label = "Fecha de nacimiento",
                    value = "09/05/2004",
                    trailing = { EditBadge() }
                )
            }
            item {
                LabeledFilledField(
                    label = "Gmail",
                    value = "GoogleUser125@gmail.com",
                    trailing = { EditBadge() }
                )
            }
            item {
                LabeledFilledField(
                    label = "Facebook",
                    value = "FbUser129",
                    trailing = { EditBadge() }
                )
            }

            item { SectionChip("Otro") }

            item {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(
                        onClick = onLogout,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(18.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp)
                    ) { Text("Cerrar sesión") }

                    OutlinedButton(
                        onClick = { /* delete */ },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(18.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.error),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                    ) { Text("Eliminar cuenta") }
                }
            }

            item { Spacer(Modifier.height(24.dp)) }
        }
    }
}

@Composable
private fun SectionChip(title: String) {
    Surface(
        color = MaterialTheme.colorScheme.outline,
        contentColor = MaterialTheme.colorScheme.onPrimary,
        shape = RoundedCornerShape(22.dp),
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
            style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Medium),
            color = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}

@Composable
private fun LabeledFilledField(
    label: String,
    value: String,
    trailing: @Composable (() -> Unit)? = null
) {
    Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
        Text(
            label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Surface(
            shape = RoundedCornerShape(14.dp),
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 0.dp,
            shadowElevation = 0.dp,
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant),
        ) {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 14.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    value,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.weight(1f)
                )
                if (trailing != null) {
                    Spacer(Modifier.width(8.dp))
                    trailing()
                }
            }
        }
    }
}

@Composable
private fun EditBadge() {
    Surface(
        shape = RoundedCornerShape(10.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke( width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant),
    ) {
        Icon(
            imageVector = Icons.Outlined.Edit,
            contentDescription = "Editar",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(6.dp),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ConfigScreenPreview() {
    GestionDeTiempoTheme {
        SettingsScreen()
    }
}