//Componentes reutilizables para pantallas de calendario

package com.turis.gestiondetiempo.features

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
@OptIn(ExperimentalMaterial3Api::class)
//TopBar para calendario
@Composable
fun CalendarTopBar(
    title: String = "Calendario"
)
{
    TopAppBar(
        title = {
            Text(
                text = title,
                style = MaterialTheme.typography.titleLarge
            )
            },
        navigationIcon = {
            IconButton(
                onClick = {}
            )
            {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Volver"
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant
        )
    )
}

//Cartas indicadoras de día, semana y mes
@Composable
fun CalendarCards(
    labels: List<String> = listOf("lunes", "semana 3", "Julio")
) {
    Row(
        modifier = Modifier
        .fillMaxWidth()
        .padding(top = 8.dp, bottom = 12.dp)
        .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        labels.forEach { label ->
            ElevatedCard(
                elevation = CardDefaults.elevatedCardElevation(defaultElevation = 2.dp),
                colors = CardDefaults.elevatedCardColors(
                    containerColor = MaterialTheme.colorScheme.surface
                )
            ) {
                Text(
                    text = label,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

//Marcador de evento
@Composable
fun EventChip(
    text: String,
    container: androidx.compose.ui.graphics.Color,
    content: androidx.compose.ui.graphics.Color
) {
    Box(
        modifier = Modifier
        .clip(
            shape = MaterialTheme.shapes.small
        )
        .background(color = container)
        .padding(horizontal = 8.dp, vertical = 6.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text,
            color = content,
            fontSize = 12.sp)
    }
}

//GridCell para la cuadrícula
@Composable
fun GridCell(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit = {}
) {
    Box(
        modifier = modifier
        .border(1.dp,
            color = MaterialTheme.colorScheme.outlineVariant)
        .padding(4.dp),
        contentAlignment = Alignment.Center,
        content = content
    )
}

//Bottom bar
@Composable
fun CalendarBottomBar() {
    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        NavigationBarItem(
            selected = true,
            onClick = {},
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = "Volver al home"
                )
                   },
            label = null
        )
        Spacer(Modifier.weight(1f))
    }
}
