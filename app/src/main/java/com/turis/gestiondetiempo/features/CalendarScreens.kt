//Pantallas de calendario

package com.turis.gestiondetiempo.features

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.DividerDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

//Pantalla de vista mensual
@Composable
fun CalendarMonthView() {
    Scaffold(
        topBar = {
            CalendarTopBar(
            title = "Calendario"
        )
                 },
        bottomBar = { CalendarBottomBar() }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(12.dp)
                .fillMaxSize()
        ) {
            CalendarCards()

            Row(
                Modifier.fillMaxWidth()
            )
            {
                listOf("D","L","M","M","J","V","S").forEach {
                    Box(
                        Modifier
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    )
                    {
                        Text(
                            text = it,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
            Spacer(Modifier.height(8.dp))

            repeat(5) {
                Row(Modifier.fillMaxWidth().height(96.dp)) {
                    repeat(7) {
                        GridCell(Modifier.weight(1f).fillMaxHeight()) { }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Mes")
@Composable fun PreviewMonth() {
    CalendarMonthView()
}

//Pantalla de vista semanal
@Composable
fun CalendarWeekView() {
    Scaffold(
        topBar = { CalendarTopBar(title = "Calendario") },
        bottomBar = { CalendarBottomBar() }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(12.dp)
                .fillMaxSize()
        ) {
            CalendarCards()

            Row(Modifier.fillMaxWidth()) {
                listOf("D","L","M","M","J","V","S").forEach {
                    Box(
                        Modifier.weight(1f),
                        contentAlignment = Alignment.Center
                    )
                    {
                        Text(
                            text = it,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
            Spacer(Modifier.height(8.dp))

            Row(
                Modifier.fillMaxWidth()
                    .weight(1f)
            )
            {
                repeat(7) { col ->
                    Column(
                        Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .padding(6.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        //Eventos
                        when (col) {
                            1 -> {
                                EventChip(
                                    text = "Hoy",
                                    MaterialTheme.colorScheme.errorContainer,
                                    MaterialTheme.colorScheme.onErrorContainer)
                                EventChip(
                                    text = "#5\n#6\n#7\n#8",
                                    MaterialTheme.colorScheme.primaryContainer,
                                    MaterialTheme.colorScheme.onPrimaryContainer)
                                EventChip(
                                    text = "#9\n#10\n#11\n#12",
                                    MaterialTheme.colorScheme.primaryContainer,
                                    MaterialTheme.colorScheme.onPrimaryContainer)
                            }
                            2 -> EventChip(
                                text = "#1\n#2\n#3\n#4",
                                MaterialTheme.colorScheme.secondaryContainer,
                                MaterialTheme.colorScheme.onSecondaryContainer)
                            3 -> EventChip(
                                text = "#5\n#6\n#7\n#8",
                                MaterialTheme.colorScheme.primaryContainer,
                                MaterialTheme.colorScheme.onPrimaryContainer)
                            6 -> EventChip(
                                text = "#1\n#2\n#3\n#4",
                                MaterialTheme.colorScheme.tertiaryContainer,
                                MaterialTheme.colorScheme.onTertiaryContainer)
                        }
                    }
                }
            }
        }
    }
}


@Preview(showBackground = true, name = "Mes")
@Composable fun PreviewWeek() {
    CalendarWeekView()
}

//Pantalla de vista diaria
@Composable
fun CalendarDayView() {
    Scaffold(
        topBar = { CalendarTopBar("Lunes 24 de diciembre") },
        bottomBar = { CalendarBottomBar() }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(horizontal = 12.dp)
                .fillMaxSize()
        ) {
            CalendarCards(listOf("lunes", "semana 3", "Diciembre"))
            (5..23).forEach { h ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "${h}:00",
                        modifier = Modifier.width(56.dp),
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    HorizontalDivider(
                        Modifier.height(1.dp).weight(1f),
                        DividerDefaults.Thickness,
                        color = MaterialTheme.colorScheme.outlineVariant
                    )
                }

                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    when (h) {
                        7 -> {
                            EventChip(
                                text = "#1\n#2\n#3\n#4",
                                MaterialTheme.colorScheme.primaryContainer,
                                MaterialTheme.colorScheme.onPrimaryContainer)
                            EventChip(
                                text = "#1\n#2\n#3\n#4",
                                MaterialTheme.colorScheme.secondaryContainer,
                                MaterialTheme.colorScheme.onSecondaryContainer)
                            EventChip(
                                text = "#1\n#2\n#3\n#4",
                                MaterialTheme.colorScheme.tertiaryContainer,
                                MaterialTheme.colorScheme.onTertiaryContainer)
                        }
                        11, 12 -> EventChip(
                            text = "#5\n#6\n#7\n#8",
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.onPrimaryContainer)
                        16 -> EventChip(
                            text = "#9\n#10\n#11\n#12",
                            MaterialTheme.colorScheme.primaryContainer,
                            MaterialTheme.colorScheme.onPrimaryContainer)
                        18 -> EventChip(text = "#5\n#6\n#7\n#8",
                            MaterialTheme.colorScheme.tertiaryContainer,
                            MaterialTheme.colorScheme.onTertiaryContainer)
                    }
                }
            }
            Spacer(Modifier.height(16.dp))
        }
    }
}

@Preview(showBackground = true, name = "Día")
@Composable fun PreviewDay() {
    CalendarDayView()
}