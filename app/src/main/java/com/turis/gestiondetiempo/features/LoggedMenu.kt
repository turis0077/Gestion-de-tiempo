package com.turis.gestiondetiempo.features

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.turis.gestiondetiempo.ui.tags.TagsViewModel
import com.turis.gestiondetiempo.ui.tags.resolve
import com.turis.gestiondetiempo.ui.theme.GestionDeTiempoTheme
import com.turis.gestiondetiempo.R

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
    modifier: Modifier = Modifier.background(MaterialTheme.colorScheme.surface),
    tagsViewModel: TagsViewModel = viewModel(),

) {
    var text by rememberSaveable { mutableStateOf("")}

    var tagExpanded by remember { mutableStateOf(false) }

    val tagColors = tagsViewModel.selectedTag?.color?.resolve()
    val tagContainerColor = tagColors?.container ?: MaterialTheme.colorScheme.surfaceVariant
    val tagContentColor   = tagColors?.onContainer ?: MaterialTheme.colorScheme.onSurfaceVariant

    val context = androidx.compose.ui.platform.LocalContext.current
    val calendar = java.util.Calendar.getInstance()
    var selectedDateText by rememberSaveable { mutableStateOf<String?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .clip(RoundedCornerShape(50))
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Person,
                                contentDescription = "Usuario",
                                tint = MaterialTheme.colorScheme.onPrimaryContainer,
                                modifier = Modifier.size(28.dp)
                            )
                        }
                        Text(
                            text = "  Hola, User1259",
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.titleMedium.copy(
                                fontWeight = FontWeight.Medium
                            )
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* TODO: Configuración */ }) {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = "Configuración",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                    IconButton(onClick = { /* TODO: Calendario */ }) {
                        Icon(
                            imageVector = Icons.Outlined.DateRange,
                            contentDescription = "Calendario",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(vertical = 15.dp, horizontal = 30.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(top = 80.dp, bottom = 15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "¡Hora de Trabajar!",
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.displayLarge,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(25.dp)
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

                                        TagsDropdownMenu(
                                            expanded = tagExpanded,
                                            onDismiss = { tagExpanded = false },
                                            tags = tagsViewModel.tags,
                                            onSelect = { tagsViewModel.select(it) },
                                            onEdit = { tagsViewModel.upsert(it) },
                                            onCreateNew = { tagsViewModel.createNewTag("Nueva") },
                                            modifier = Modifier.width(260.dp)
                                        )
                                    }

                                    FilledIconButton(
                                        onClick = {
                                            val year = calendar.get(java.util.Calendar.YEAR)
                                            val month = calendar.get(java.util.Calendar.MONTH)
                                            val day = calendar.get(java.util.Calendar.DAY_OF_MONTH)

                                            android.app.DatePickerDialog(
                                                context,
                                                { _, yy, mm, dd ->
                                                    selectedDateText = "%02d/%02d/%04d".format(dd, mm + 1, yy)
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

                selectedDateText?.let { dateText ->
                    Row(
                        modifier = Modifier
                            .padding(top = 12.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .border(
                                width = 1.dp,
                                color = MaterialTheme.colorScheme.outline,
                                shape = RoundedCornerShape(12.dp)
                            )
                            .background(MaterialTheme.colorScheme.surface)
                            .padding(horizontal = 12.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Fecha límite: $dateText",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
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

    }
}

@Preview(showBackground = true)
@Composable
fun MainLoggedMenuPreview() {
    GestionDeTiempoTheme {
        MainLoggedMenu()
    }
}