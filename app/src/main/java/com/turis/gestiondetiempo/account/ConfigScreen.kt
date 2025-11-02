package com.turis.gestiondetiempo.account

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material.icons.outlined.ArrowDropDown
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.turis.gestiondetiempo.ui.theme.GestionDeTiempoTheme

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier
) {
    var dark by remember { mutableStateOf(false) }
    var language by remember { mutableStateOf("Español") }

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
                        color = MaterialTheme.colorScheme.surface,
                        tonalElevation = 1.dp
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Switch(
                                checked = dark,
                                onCheckedChange = { dark = it },
                                thumbContent = {
                                    Icon(
                                        imageVector = Icons.Outlined.Star,
                                        contentDescription = null
                                    )
                                }
                            )
                            FilledTonalIconButton(
                                onClick = {  },
                                modifier = Modifier,
                                shape = RoundedCornerShape(10.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Settings,
                                    contentDescription = "Android"
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
                        onClick = { /* logout */ },
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