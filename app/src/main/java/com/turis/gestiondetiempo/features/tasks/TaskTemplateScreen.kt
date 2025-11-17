package com.turis.gestiondetiempo.features.tasks

import android.app.DatePickerDialog
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.turis.gestiondetiempo.R
import com.turis.gestiondetiempo.features.tags.TagsDropdownMenu
import com.turis.gestiondetiempo.model.*
import com.turis.gestiondetiempo.ui.AppViewModel
import com.turis.gestiondetiempo.ui.tags.TagsViewModel
import com.turis.gestiondetiempo.ui.tags.resolve
import com.turis.gestiondetiempo.ui.theme.GestionDeTiempoTheme
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun TaskTemplateScreen(
    task: Task,
    onAddSubItem: () -> Unit = {},
    onBack: () -> Unit = {},
    onProfileClick: () -> Unit = {},
    onTimerClick: ((Int) -> Unit)? = null,
    tagsViewModel: TagsViewModel = viewModel(),
    appViewModel: AppViewModel = viewModel()
) {
    var tagExpanded by remember { mutableStateOf(false) }
    var subItems by rememberSaveable { mutableStateOf(task.subItems) }
    var newSubItemText by remember { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf(task.description) }
    var selectedTag by rememberSaveable { mutableStateOf(task.tag) }
    var selectedDate by rememberSaveable { mutableStateOf(task.date) }

    // Estados para edición y eliminación de subtareas
    var showEditDialog by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }
    var showOptionsDialog by remember { mutableStateOf(false) }
    var selectedSubItem by remember { mutableStateOf<SubItem?>(null) }
    var editedSubItemText by remember { mutableStateOf("") }

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    // Formatear la fecha para mostrarla
    val dateFormatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val dateText = selectedDate.format(dateFormatter)

    // Actualizar la tarea en el ViewModel cuando cambien las subtareas, descripción, tag o fecha
    LaunchedEffect(subItems, description, selectedTag, selectedDate) {
        appViewModel.updateTask(task.copy(
            subItems = subItems,
            description = description,
            tag = selectedTag,
            date = selectedDate
        ))
    }

    // Diálogo de opciones (Editar/Eliminar)
    if (showOptionsDialog && selectedSubItem != null) {
        AlertDialog(
            onDismissRequest = { showOptionsDialog = false },
            title = { Text("¿Qué deseas hacer?") },
            text = {
                Column {
                    TextButton(
                        onClick = {
                            editedSubItemText = selectedSubItem?.title ?: ""
                            showOptionsDialog = false
                            showEditDialog = true
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Editar texto", modifier = Modifier.fillMaxWidth())
                    }
                    TextButton(
                        onClick = {
                            showOptionsDialog = false
                            showDeleteDialog = true
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Eliminar subtarea", modifier = Modifier.fillMaxWidth())
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { showOptionsDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Diálogo de edición
    if (showEditDialog && selectedSubItem != null) {
        AlertDialog(
            onDismissRequest = { showEditDialog = false },
            title = { Text("Editar subtarea") },
            text = {
                OutlinedTextField(
                    value = editedSubItemText,
                    onValueChange = { editedSubItemText = it },
                    placeholder = { Text("Nuevo texto") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    if (editedSubItemText.isNotBlank()) {
                        subItems = subItems.map {
                            if (it == selectedSubItem) it.copy(title = editedSubItemText.trim()) else it
                        }
                    }
                    showEditDialog = false
                    selectedSubItem = null
                }) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showEditDialog = false
                    selectedSubItem = null
                }) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Diálogo de eliminación
    if (showDeleteDialog && selectedSubItem != null) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("¿Eliminar subtarea?") },
            text = { Text("¿Estás seguro de que deseas eliminar esta subtarea?") },
            confirmButton = {
                TextButton(onClick = {
                    subItems = subItems.filter { it != selectedSubItem }
                    showDeleteDialog = false
                    selectedSubItem = null
                }) {
                    Text("Eliminar")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showDeleteDialog = false
                    selectedSubItem = null
                }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val totalSeconds = task.timeMinutes * 60 + task.timeSeconds
                    onTimerClick?.invoke(totalSeconds)
                },
                containerColor = MaterialTheme.colorScheme.secondaryContainer
            ) {
                Icon(Icons.Outlined.AccessTime, contentDescription = "Reloj")
            }
        }
    ) { inner ->
        LazyColumn(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Botones de fecha y etiqueta
            item {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    FilledTonalButton(
                        onClick = {
                            val year = calendar.get(Calendar.YEAR)
                            val month = calendar.get(Calendar.MONTH)
                            val day = calendar.get(Calendar.DAY_OF_MONTH)

                            DatePickerDialog(
                                context,
                                { _, yy, mm, dd ->
                                    selectedDate = java.time.LocalDate.of(yy, mm + 1, dd)
                                },
                                year, month, day
                            ).show()
                        },
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        ),
                        shape = RoundedCornerShape(10.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                        modifier = Modifier.wrapContentWidth()
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.DateRange,
                            contentDescription = "Fecha límite",
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(6.dp))
                        Text(
                            text = "Fecha límite: $dateText",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }

                    Box {
                        FilledTonalButton(
                            onClick = { tagExpanded = true },
                            colors = ButtonDefaults.filledTonalButtonColors(
                                containerColor = selectedTag.tint,
                                contentColor = MaterialTheme.colorScheme.onSurface
                            ),
                            shape = RoundedCornerShape(10.dp),
                            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                            modifier = Modifier.wrapContentWidth()
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.outline_bookmark_24),
                                contentDescription = "Etiqueta",
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(Modifier.width(6.dp))
                            Text(
                                text = selectedTag.label,
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onSurface
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
                }
            }

            // TextField de descripción (editable)
            item {
                OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = false,
                    label = null,
                    placeholder = { Text("Descripción") },
                    supportingText = null
                )
            }

            // Lista de subitems con estados
            items(subItems) { si ->
                SubItemRow(
                    item = si,
                    onToggle = {
                        subItems = subItems.map {
                            if (it.title == si.title) it.copy(done = !it.done) else it
                        }
                    },
                    onLongPress = {
                        selectedSubItem = si
                        showOptionsDialog = true
                    }
                )
            }

            // Campo "+ Agregar tarea" (funcional)
            item {
                OutlinedTextField(
                    value = newSubItemText,
                    onValueChange = { newSubItemText = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("+ Agregar tarea") },
                    singleLine = true,
                    trailingIcon = {
                        if (newSubItemText.isNotBlank()) {
                            IconButton(onClick = {
                                subItems = subItems + SubItem(newSubItemText.trim())
                                newSubItemText = ""
                            }) {
                                Icon(Icons.Outlined.Add, "Agregar")
                            }
                        }
                    }
                )
            }

            item { Spacer(Modifier.height(96.dp)) }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun SubItemRow(
    item: SubItem,
    onToggle: () -> Unit = {},
    onLongPress: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = { /* No hacemos nada en click simple */ },
                onLongClick = onLongPress
            )
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = item.done, onCheckedChange = { onToggle() })
        Spacer(Modifier.width(8.dp))
        Text(item.title, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskTemplatePreview() {
    GestionDeTiempoTheme { TaskTemplateScreen(task = sampleTaskDetail()) }
}