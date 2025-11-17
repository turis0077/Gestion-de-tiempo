package com.turis.gestiondetiempo.features.tasks

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.CalendarMonth
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.turis.gestiondetiempo.R
import com.turis.gestiondetiempo.features.tags.TagsDropdownMenu
import com.turis.gestiondetiempo.model.*
import com.turis.gestiondetiempo.ui.AppViewModel
import com.turis.gestiondetiempo.ui.components.TimeChip
import com.turis.gestiondetiempo.ui.tags.TagsViewModel
import com.turis.gestiondetiempo.ui.tags.resolve
import com.turis.gestiondetiempo.ui.theme.GestionDeTiempoTheme

@Composable
fun TaskListScreen(
    uiState: List<TaskSection>,
    onAdd: () -> Unit = {},
    onTaskClick: (String, String) -> Unit = { _, _ -> },
    onProfileClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onCalendarClick: () -> Unit = {},
    onTaskToggle: (String, Boolean) -> Unit = { _, _ -> },
    tagsViewModel: TagsViewModel = viewModel(),
    appViewModel: AppViewModel = viewModel()
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAdd,
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
            ) { Icon(Icons.Outlined.Add, contentDescription = "Agregar") }
        }
    ) { inner ->
        LazyColumn(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .background(MaterialTheme.colorScheme.surface),
            contentPadding = PaddingValues(bottom = 96.dp)
        ) {
            // Título
            item {
                Spacer(Modifier.height(16.dp))
                Text(
                    "Tus tareas",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Black,
                    lineHeight = 40.sp
                )
                Spacer(Modifier.height(16.dp))
            }

            // Mensaje si no hay tareas
            if (uiState.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 48.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            "No tienes ninguna tarea pendiente, ¡Felicidades!",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Secciones
            uiState.forEachIndexed { sIdx, section ->
                item {
                    Text(
                        section.header,
                        style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.SemiBold)
                    )
                    Spacer(Modifier.height(8.dp))
                }

                itemsIndexed(section.items, key = { _, it -> it.id }) { index, task ->
                    var tagExpanded by remember { mutableStateOf(false) }
                    var showDeleteDialog by remember { mutableStateOf(false) }
                    var selectedTag by remember(task.tag) { mutableStateOf(task.tag) }

                    val tagContainerColor = selectedTag.tint
                    val tagContentColor = MaterialTheme.colorScheme.onSurface

                    // Diálogo de confirmación
                    if (showDeleteDialog) {
                        AlertDialog(
                            onDismissRequest = { showDeleteDialog = false },
                            title = { Text("¿La tarea ya está finalizada?") },
                            confirmButton = {
                                TextButton(onClick = {
                                    onTaskToggle(task.id, true)
                                    showDeleteDialog = false
                                }) {
                                    Text("Sí")
                                }
                            },
                            dismissButton = {
                                TextButton(onClick = { showDeleteDialog = false }) {
                                    Text("No")
                                }
                            }
                        )
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        // Fila principal: izquierda (título) | derecha (columna de chips)
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // Izquierda
                            Row(
                                modifier = Modifier.weight(1f),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Checkbox(
                                    checked = task.completed,
                                    onCheckedChange = {
                                        if (it) {
                                            showDeleteDialog = true
                                        }
                                    }
                                )
                                Spacer(Modifier.width(4.dp))
                                Text(
                                    task.title,
                                    style = MaterialTheme.typography.bodyLarge,
                                    softWrap = true
                                )
                            }

                            // Derecha: columna de categoría + fila inferior
                            Column(
                                horizontalAlignment = Alignment.End,
                                modifier = Modifier.widthIn(min = 120.dp)
                            ) {
                                Box {
                                    FilledTonalButton(
                                        onClick = { tagExpanded = true },
                                        colors = ButtonDefaults.filledTonalButtonColors(
                                            containerColor = tagContainerColor,
                                            contentColor = tagContentColor
                                        ),
                                        shape = RoundedCornerShape(10.dp),
                                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.outline_bookmark_24),
                                            contentDescription = "Etiqueta",
                                            tint = tagContentColor,
                                            modifier = Modifier.size(18.dp)
                                        )
                                        Spacer(Modifier.width(6.dp))
                                        Text(
                                            text = selectedTag.label,
                                            style = MaterialTheme.typography.labelMedium
                                        )
                                    }

                                    // Menú simple de selección de tags
                                    DropdownMenu(
                                        expanded = tagExpanded,
                                        onDismissRequest = { tagExpanded = false },
                                        modifier = Modifier.width(200.dp)
                                    ) {
                                        TaskTag.values().forEach { tag ->
                                            DropdownMenuItem(
                                                text = { Text(tag.label) },
                                                onClick = {
                                                    selectedTag = tag
                                                    tagExpanded = false
                                                    // Actualizar en el ViewModel
                                                    appViewModel.updateTask(task.copy(tag = tag))
                                                },
                                                leadingIcon = {
                                                    Box(
                                                        modifier = Modifier
                                                            .size(24.dp)
                                                            .clip(RoundedCornerShape(4.dp))
                                                            .background(tag.tint)
                                                    )
                                                }
                                            )
                                        }
                                    }
                                }
                                Spacer(Modifier.height(6.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    ProgressChip(task = task)
                                    InfoIconChip(onClick = { onTaskClick(task.id, task.title) })
                                    TimeChip(task.chips.lastOrNull()?.text ?: "10:00")
                                }
                            }
                        }

                        // Divider
                        val esUltimoDeSeccion = index == section.items.lastIndex
                        val esUltimaSeccion = sIdx == uiState.lastIndex
                        if (!(esUltimoDeSeccion && esUltimaSeccion)) {
                            Spacer(Modifier.height(12.dp))
                            Divider()
                        }
                    }
                }

                if (sIdx != uiState.lastIndex) {
                    item { Spacer(Modifier.height(16.dp)) }
                }
            }
        }
    }
}

// Círculo de progreso real basado en subtareas
@Composable
fun ProgressChip(task: Task) {
    val totalSubItems = task.subItems.size
    val completedSubItems = task.subItems.count { it.done }
    val progress = if (totalSubItems > 0) completedSubItems.toFloat() / totalSubItems else 0f

    Surface(
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp
    ) {
        Box(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 6.dp),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator(
                progress = { progress },
                modifier = Modifier.size(20.dp),
                strokeWidth = 2.dp,
                trackColor = MaterialTheme.colorScheme.surfaceVariant,
            )
        }
    }
}

@Composable
fun InfoIconChip(onClick: () -> Unit = {}) {
    Surface(
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp,
        onClick = onClick
    ) {
        Icon(
            imageVector = Icons.Outlined.Info,
            contentDescription = "Información",
            tint = MaterialTheme.colorScheme.outline,
            modifier = Modifier
                .padding(horizontal = 10.dp, vertical = 6.dp)
                .size(18.dp)
        )
    }
}


@Preview(showBackground = true, name = "Vacío")
@Composable
private fun PreviewEmpty() {
    GestionDeTiempoTheme { TaskListScreen(uiState = sampleTaskListEmpty()) }
}

@Preview(showBackground = true, name = "Una tarea")
@Composable
private fun PreviewOne() {
    GestionDeTiempoTheme { TaskListScreen(uiState = sampleTaskListOne()) }
}

@Preview(showBackground = true, name = "Lleno")
@Composable
private fun PreviewFull() {
    GestionDeTiempoTheme { TaskListScreen(uiState = sampleTaskListFull()) }
}




