package com.turis.gestiondetiempo.features.tasks

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview
import com.turis.gestiondetiempo.model.*
import com.turis.gestiondetiempo.ui.components.DateChip
import com.turis.gestiondetiempo.ui.components.TagPill
import com.turis.gestiondetiempo.ui.theme.GestionDeTiempoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TaskTemplateScreen(
    task: Task,
    onAddSubItem: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            // Fila superior con Back y checkbox a la izquierda del título
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.AutoMirrored.Outlined.ArrowBack, contentDescription = "Atrás")
                    }
                },
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            task.title,
                            style = MaterialTheme.typography.titleLarge,
                            fontSize = 22.sp
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = onAddSubItem,
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
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 12.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // Chips: fecha + etiqueta
            item {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DateChip("Fecha límite: ${task.date}")
                    if (task.tag != TaskTag.Ninguno) TagPill(task.tag)
                }
            }

            // TextField de descripción (mismo look del mock)
            item {
                OutlinedTextField(
                    value = "Ejercicios de la sección 5 del libro, pág 800",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    singleLine = false,
                    label = null,
                    placeholder = null,
                    supportingText = null
                )
            }

            // Lista de subitems con estados
            items(task.subItems) { si ->
                SubItemRow(si)
            }

            // Campo “+ Agregar tarea” (solo UI)
            item {
                OutlinedTextField(
                    value = "",
                    onValueChange = {},
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("+ Agregar tarea") },
                    singleLine = true,
                    enabled = false
                )
            }

            item { Spacer(Modifier.height(96.dp)) }
        }
    }
}

@Composable
private fun SubItemRow(item: SubItem) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(checked = item.done, onCheckedChange = null)
        Spacer(Modifier.width(8.dp))
        Text(item.title, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showBackground = true)
@Composable
private fun TaskTemplatePreview() {
    GestionDeTiempoTheme { TaskTemplateScreen(task = sampleTaskDetail()) }
}
