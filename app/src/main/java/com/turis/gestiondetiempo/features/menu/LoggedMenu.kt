package com.turis.gestiondetiempo.features.menu

import android.app.DatePickerDialog
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledIconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.turis.gestiondetiempo.ui.AppViewModel
import com.turis.gestiondetiempo.ui.tags.TagsViewModel
import com.turis.gestiondetiempo.ui.tags.resolve
import com.turis.gestiondetiempo.ui.theme.GestionDeTiempoTheme
import com.turis.gestiondetiempo.R
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.TextButton
import com.turis.gestiondetiempo.features.tags.TagsDropdownMenu
import com.turis.gestiondetiempo.features.tasks.TaskListScreen
import com.turis.gestiondetiempo.model.sampleTaskListOne
import com.turis.gestiondetiempo.model.TaskTag
import com.turis.gestiondetiempo.model.organizarTareasPorFecha
import com.turis.gestiondetiempo.nav.navBar.LoggedNavBar
import com.turis.gestiondetiempo.nav.navBar.TopBarConfig
import androidx.compose.ui.input.pointer.pointerInput
import java.time.LocalDate
import java.util.Calendar

class LoggedMenu : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GestionDeTiempoTheme {
                MainLoggedMenu()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainLoggedMenu(
    modifier: Modifier = Modifier
        .background(MaterialTheme.colorScheme.surface)
        .fillMaxSize(),
    onTaskClick: (String, String) -> Unit = { _, _ -> },
    onProfileClick: () -> Unit = {},
    onSettingsClick: () -> Unit = {},
    onCalendarClick: () -> Unit = {},
    tagsViewModel: TagsViewModel = viewModel(),
    appViewModel: AppViewModel = viewModel()
) {
    var text by rememberSaveable { mutableStateOf("")}
    var selectedTag by remember { mutableStateOf(TaskTag.Ninguno) }
    var selectedDate by remember { mutableStateOf<LocalDate?>(null) }
    var showConfirmDialog by remember { mutableStateOf(false) }

    var tagExpanded by remember { mutableStateOf(false) }

    val tagContainerColor = selectedTag.tint
    val tagContentColor = MaterialTheme.colorScheme.onSurface

    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val tasks by appViewModel.tasks.collectAsState()
    val taskSections = organizarTareasPorFecha(tasks)

    var showTaskList by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false
    )

    if (showConfirmDialog && text.isNotBlank()) {
        AlertDialog(
            onDismissRequest = { showConfirmDialog = false },
            title = { Text("¿Quieres agendar esta tarea?") },
            text = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text("Descripción: $text", style = MaterialTheme.typography.bodyLarge)
                    Text("Categoría: ${selectedTag.label}", style = MaterialTheme.typography.bodyMedium)
                    Text(
                        "Fecha: ${selectedDate?.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")) ?: "No especificada"}",
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            },
            confirmButton = {
                Button(onClick = {
                    if (selectedDate != null) {
                        appViewModel.createTask(text, selectedDate!!, selectedTag)
                        text = ""
                        selectedTag = TaskTag.Ninguno
                        selectedDate = null
                    }
                    showConfirmDialog = false
                }) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showConfirmDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(top = 60.dp, bottom = 15.dp)
                .padding(horizontal = 25.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(
                text = "¡Hora de Trabajar!",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayLarge,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            )

            ElevatedCard(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextField(
                        placeholder = { Text("Describe tu primera tarea") },
                        value = text,
                        onValueChange = { text = it },
                        modifier = Modifier
                            .weight(1f),
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = MaterialTheme.colorScheme.surface,
                            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                            focusedIndicatorColor = MaterialTheme.colorScheme.surface,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.surface
                        ),

                        trailingIcon = {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box {
                                    FilledIconButton(
                                        onClick = { tagExpanded = true },
                                        colors = IconButtonDefaults.filledIconButtonColors(
                                            containerColor = tagContainerColor,
                                            contentColor = tagContentColor
                                        ),
                                        shape = RoundedCornerShape(10.dp)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.outline_bookmark_24),
                                            contentDescription = "Etiqueta",
                                            tint = tagContentColor
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

                                FilledIconButton(
                                    onClick = {
                                        val year = calendar.get(Calendar.YEAR)
                                        val month = calendar.get(Calendar.MONTH)
                                        val day = calendar.get(Calendar.DAY_OF_MONTH)

                                        DatePickerDialog(
                                            context,
                                            { _, yy, mm, dd ->
                                                selectedDate = LocalDate.of(yy, mm + 1, dd)
                                            },
                                            year, month, day
                                        ).show()
                                    },
                                    colors = IconButtonDefaults.filledIconButtonColors(
                                        containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                        contentColor = tagContentColor
                                    ),
                                    modifier = Modifier.padding(start = 6.dp),
                                    shape = RoundedCornerShape(10.dp)
                                ) {
                                    Icon(
                                        imageVector = Icons.Outlined.DateRange,
                                        contentDescription = "Fecha limite",
                                        tint = tagContentColor
                                    )
                                }
                            }
                        }
                    )
                }
            }

            if (text.isNotBlank() && selectedDate != null) {
                Button(
                    onClick = { showConfirmDialog = true },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, bottom = 120.dp),
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Text("Crear tarea")
                }
            }
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 80.dp),
            contentAlignment = Alignment.BottomCenter
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { showTaskList = true }
                    .pointerInput(Unit) {
                        detectVerticalDragGestures { change, dragAmount ->
                            if (dragAmount < -50f) {
                                showTaskList = true
                            }
                        }
                    }
            ) {
                Text(
                    text = "desliza para ver tu lista de tareas",
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        fontWeight = FontWeight.Light,
                        shadow = Shadow(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f),
                            offset = Offset(0f, 2f),
                            blurRadius = 3f
                        )
                    ),
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp),
                    textAlign = TextAlign.Center
                )

                Image(
                    painter = painterResource(R.drawable.deslizador),
                    contentDescription = "Deslizador",
                    modifier = Modifier.size(100.dp)
                )
            }
        }
    }

    // ModalBottomSheet para mostrar la lista de tareas
    if (showTaskList) {
        ModalBottomSheet(
            onDismissRequest = { showTaskList = false },
            sheetState = sheetState,
            containerColor = MaterialTheme.colorScheme.surface
        ) {
            TaskListScreen(
                uiState = taskSections,
                onAdd = { showTaskList = false },
                onTaskClick = onTaskClick,
                onProfileClick = onProfileClick,
                onSettingsClick = onSettingsClick,
                onCalendarClick = onCalendarClick,
                onTaskToggle = { taskId, completed ->
                    appViewModel.toggleTaskCompletion(taskId, completed)
                },
                appViewModel = appViewModel
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainLoggedMenuPreview() {
    GestionDeTiempoTheme {
        LoggedNavBar(
            config = TopBarConfig(
                titleText = null,
                showUserHeader = true,
                showBack = false,
                showHome = false,
                showConfig = true,
                showCalendar = true
            ),
            onProfile = {},
            onConfig = {},
            onCalendar = {}
        ) {
            MainLoggedMenu()
        }
    }
}