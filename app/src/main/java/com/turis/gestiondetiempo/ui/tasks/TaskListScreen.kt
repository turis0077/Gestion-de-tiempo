package com.turis.gestiondetiempo.ui.tasks

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.turis.gestiondetiempo.model.*
import com.turis.gestiondetiempo.ui.components.TagPill
import com.turis.gestiondetiempo.ui.components.TimeChip

@Composable
fun TaskListScreen(
    uiState: List<TaskSection>,
    onAdd: () -> Unit = {}
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
                .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(bottom = 96.dp)
        ) {
            // Header
            item {
                Spacer(Modifier.height(16.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE6F4F1))
                    ) {
                        Box(
                            modifier = Modifier
                                .size(22.dp)
                                .align(Alignment.Center)
                                .clip(CircleShape)
                                .background(Color(0xFF0F766E))
                        )
                    }
                    Spacer(Modifier.width(12.dp))
                    Text(
                        "Hola, User1259",
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.weight(1f)
                    )
                    IconButton(onClick = {}) { Icon(Icons.Outlined.Settings, null) }
                    IconButton(onClick = {}) { Icon(Icons.Outlined.CalendarMonth, null) }
                }
                Spacer(Modifier.height(16.dp))
                Text(
                    "Tus tareas",
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Black,
                    lineHeight = 40.sp
                )
                Spacer(Modifier.height(6.dp))
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
                                Checkbox(checked = false, onCheckedChange = null)
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
                                when (task.tag) {
                                    TaskTag.Estudios -> TagPill(TaskTag.Estudios)
                                    TaskTag.Personal -> TagPill(TaskTag.Personal)
                                    TaskTag.Hobbies -> TagPill(TaskTag.Hobbies)
                                    else -> {}
                                }
                                Spacer(Modifier.height(6.dp))
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    ProgressChip()
                                    InfoIconChip()
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

// Círculo de progreso (emoji temporal)
@Composable
fun ProgressChip() {
    Surface(
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp
    ) {
        Text(
            text = "🔵", // Emoji de progreso
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
        )
    }
}

@Composable
fun InfoIconChip() {
    Surface(
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
        color = MaterialTheme.colorScheme.surface,
        tonalElevation = 0.dp
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
    MaterialTheme { TaskListScreen(uiState = sampleTaskListEmpty()) }
}

@Preview(showBackground = true, name = "Una tarea")
@Composable
private fun PreviewOne() {
    MaterialTheme { TaskListScreen(uiState = sampleTaskListOne()) }
}

@Preview(showBackground = true, name = "Lleno")
@Composable
private fun PreviewFull() {
    MaterialTheme { TaskListScreen(uiState = sampleTaskListFull()) }
}
