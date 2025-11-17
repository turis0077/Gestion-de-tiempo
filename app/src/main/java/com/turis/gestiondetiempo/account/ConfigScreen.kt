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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import com.turis.gestiondetiempo.R
import com.turis.gestiondetiempo.ui.AppViewModel
import com.turis.gestiondetiempo.ui.theme.GestionDeTiempoTheme
import com.turis.gestiondetiempo.ui.theme.LocalExtendedColors
import com.turis.gestiondetiempo.ui.theme.ThemePreferences
import com.turis.gestiondetiempo.ui.theme.ThemeViewModel

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    onLogout: () -> Unit = {},
    themeViewModel: ThemeViewModel? = null,
    appViewModel: AppViewModel? = null
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
            null
        }
    }

    var showLanguageMenu by remember { mutableStateOf(false) }
    var selectedLanguage by remember { mutableStateOf("Español") }

    // Estado para el diálogo de edición de username
    var showEditUsernameDialog by remember { mutableStateOf(false) }
    var tempUsername by remember { mutableStateOf("") }

    // Obtener username y password del AppViewModel
    val username by (appViewModel?.username ?: remember {
        kotlinx.coroutines.flow.MutableStateFlow("User1259")
    }).collectAsState()

    val password by (appViewModel?.password ?: remember {
        kotlinx.coroutines.flow.MutableStateFlow("")
    }).collectAsState()

    // Convertir password a asteriscos
    val maskedPassword = "*".repeat(password.length)

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

    // Diálogo de edición de nombre de usuario
    if (showEditUsernameDialog) {
        AlertDialog(
            onDismissRequest = { showEditUsernameDialog = false },
            title = { Text("Editar nombre de usuario") },
            text = {
                OutlinedTextField(
                    value = tempUsername,
                    onValueChange = { tempUsername = it },
                    placeholder = { Text("Nuevo nombre de usuario") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    if (tempUsername.isNotBlank()) {
                        appViewModel?.updateUsername(tempUsername)
                    }
                    showEditUsernameDialog = false
                }) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditUsernameDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

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
                    Text(
                        text = "Tema",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.weight(1f)
                    )
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
                                        contentDescription = stringResource(R.string.theme_state_desc),
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
                                shape = CircleShape,
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
                                    contentDescription = stringResource(R.string.dynamic_colors_desc)
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
                    Text(
                        text = "Idioma",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.weight(1f)
                    )
                    Box {
                        Row {
                            Button(
                                onClick = { showLanguageMenu = !showLanguageMenu },
                                shape = RoundedCornerShape(topStart = 14.dp, bottomStart = 14.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.surface,
                                    contentColor = MaterialTheme.colorScheme.onSurface
                                )
                            ) {
                                Text(selectedLanguage)
                            }
                            OutlinedButton(
                                onClick = { showLanguageMenu = !showLanguageMenu },
                                shape = RoundedCornerShape(topEnd = 14.dp, bottomEnd = 14.dp),
                                border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant),
                                contentPadding = PaddingValues(horizontal = 10.dp),
                            ) {
                                Icon(
                                    imageVector = Icons.Outlined.ArrowDropDown,
                                    contentDescription = stringResource(R.string.change_language_desc)
                                )
                            }
                        }

                        DropdownMenu(
                            expanded = showLanguageMenu,
                            onDismissRequest = { showLanguageMenu = false }
                        ) {
                            listOf("Español", "English").forEach { language ->
                                DropdownMenuItem(
                                    text = { Text(language) },
                                    onClick = {
                                        selectedLanguage = language
                                        showLanguageMenu = false
                                    }
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
                    Text(
                        text = "Fondo de reloj",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.weight(1f)
                    )
                    OutlinedButton(
                        onClick = { /* subir imagen */ },
                        shape = RoundedCornerShape(14.dp),
                        border = BorderStroke(
                            width = 1.dp,
                            color = MaterialTheme.colorScheme.outline
                        ),
                    ) {
                        Text(stringResource(R.string.upload_image))
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
                    value = username,
                    trailing = { EditBadge() },
                    onEditClick = {
                        tempUsername = username
                        showEditUsernameDialog = true
                    }
                )
            }
            item {
                LabeledFilledField(
                    label = "Contraseña",
                    value = if (password.isEmpty()) stringResource(R.string.sample_password) else maskedPassword,
                    trailing = { EditBadge() }
                )
            }
            item {
                LabeledFilledField(
                    label = "Fecha de nacimiento",
                    value = stringResource(R.string.sample_birth_date),
                    trailing = { EditBadge() }
                )
            }
            item {
                LabeledFilledField(
                    label = "Gmail",
                    value = stringResource(R.string.sample_email),
                    trailing = { EditBadge() }
                )
            }
            item {
                LabeledFilledField(
                    label = "Facebook",
                    value = stringResource(R.string.sample_facebook),
                    trailing = { EditBadge() }
                )
            }

            item { SectionChip("Otros") }

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
                    ) { Text("Cerrar Sesion") }

                    OutlinedButton(
                        onClick = { /* delete */ },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(18.dp),
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.error),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
                    ) { Text("Borrar Cuenta") }
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
    trailing: @Composable (() -> Unit)? = null,
    onEditClick: (() -> Unit)? = null
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
                    if (onEditClick != null) {
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            color = MaterialTheme.colorScheme.surface,
                            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outlineVariant),
                            onClick = onEditClick
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Edit,
                                contentDescription = stringResource(R.string.edit_desc),
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(6.dp),
                            )
                        }
                    } else {
                        trailing()
                    }
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
            contentDescription = stringResource(R.string.edit_desc),
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