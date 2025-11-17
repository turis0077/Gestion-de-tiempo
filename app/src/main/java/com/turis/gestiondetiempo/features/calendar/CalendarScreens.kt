package com.turis.gestiondetiempo.features.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.turis.gestiondetiempo.model.CalendarEvent
import kotlinx.datetime.*
import kotlin.math.ceil

// Obtener fecha de hoy
private fun today(): LocalDate =
    Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date

private fun monthGridDays(year: Int, month: Month): List<LocalDate?> {
    val first = LocalDate(year, month, 1)
    val startDow = first.dayOfWeek.isoDayNumber % 7
    val length = month.length(isLeapYear(year))
    val cells = MutableList<LocalDate?>(ceil((startDow + length) / 7.0).toInt() * 7) { null }
    (0 until length).forEach { i -> cells[startDow + i] = LocalDate(year, month, i + 1) }
    return cells
}

// verificar año bisiesto
private fun isLeapYear(year: Int): Boolean {
    return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
}

private fun WeekdayHeader(): List<String> = listOf("D","L","M","M","J","V","S")

// Vista mensual
@Composable
fun CalendarMonthView(
    viewModel: CalendarViewModel = androidx.lifecycle.viewmodel.compose.viewModel(),
    onEventClicked: (CalendarEvent) -> Unit = { }
) {
    val state by viewModel.state.collectAsState()
    val ym = state.currentMonth
    val cells = remember(ym) { monthGridDays(ym.year, ym.month) }

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 8.dp)
    ) {
        // Controles de navegación de mes (Anterior y Siguiente)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { viewModel.goPrevMonth() }) {
                Text("←", fontSize = 24.sp)
            }

            Text(
                text = "${ym.month.name.lowercase().replaceFirstChar { it.uppercase() }} ${ym.year}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            IconButton(onClick = { viewModel.goNextMonth() }) {
                Text("→", fontSize = 24.sp)
            }
        }

        CalendarCards()

        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            WeekdayHeader().forEach { day ->
                Box(
                    Modifier.weight(1f),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = day,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        // Grid de días
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 4.dp)
        ) {
            val weeks = cells.chunked(7)
            items(weeks.size) { weekIndex ->
                val week = weeks[weekIndex]
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(100.dp)
                        .padding(vertical = 2.dp)
                ) {
                    week.forEach { date ->
                        val dayEvents = if (date == null) emptyList()
                        else state.events.filter { it.date == date }

                        MonthDayCell(
                            date = date,
                            dayNumber = date?.dayOfMonth ?: 0,
                            events = dayEvents,
                            isToday = date == today(),
                            onDayClick = { d ->
                                if (date != null) {
                                    viewModel.selectDate(d)
                                }
                            },
                            onEventClick = { ev ->
                                // Al hacer click en un evento, mostrar detalles
                                viewModel.openEventDetails(ev)
                                onEventClicked(ev)
                            },
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .padding(horizontal = 2.dp)
                        )
                    }
                }
            }
        }
    }

    val sheetEvent = state.sheetEvent
    if (sheetEvent != null) {
        EventDetails(
            event = sheetEvent,
            onDismiss = { viewModel.closeDetails() }
        )
    }
}

@Composable
fun MonthDayCell(
    date: LocalDate?,
    dayNumber: Int,
    events: List<CalendarEvent>,
    isToday: Boolean,
    onDayClick: (LocalDate) -> Unit,
    onEventClick: (CalendarEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val backgroundColor = when {
        date == null -> MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        isToday -> MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
        else -> MaterialTheme.colorScheme.surface
    }

    Box(
        modifier = modifier
            .border(
                width = 0.5.dp,
                color = MaterialTheme.colorScheme.outlineVariant,
                shape = RoundedCornerShape(4.dp)
            )
            .clip(RoundedCornerShape(4.dp))
            .background(backgroundColor)
            .clickable(enabled = date != null) {
                date?.let { onDayClick(it) }
            }
            .padding(4.dp)
    ) {
        if (date != null) {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Top
            ) {
                // Número del día
                Text(
                    text = dayNumber.toString(),
                    fontSize = 14.sp,
                    fontWeight = if (isToday) FontWeight.Bold else FontWeight.Normal,
                    color = if (isToday) MaterialTheme.colorScheme.primary
                    else MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.padding(bottom = 2.dp)
                )

                // Eventos (máximo 3 visibles)
                events.take(3).forEach { ev ->
                    EventChipCompact(
                        text = ev.title,
                        isDone = ev.isDone,
                        onClick = { onEventClick(ev) }
                    )
                    Spacer(Modifier.height(2.dp))
                }

                // Indicador de más eventos
                if (events.size > 3) {
                    Text(
                        text = "+${events.size - 3}",
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(top = 2.dp)
                    )
                }
            }
        }
    }
}

// Chip de evento compacto para el calendario
@Composable
fun EventChipCompact(
    text: String,
    isDone: Boolean,
    onClick: () -> Unit
) {
    val containerColor = if (isDone) {
        MaterialTheme.colorScheme.surfaceVariant
    } else {
        MaterialTheme.colorScheme.primaryContainer
    }

    val contentColor = if (isDone) {
        MaterialTheme.colorScheme.onSurfaceVariant
    } else {
        MaterialTheme.colorScheme.onPrimaryContainer
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(4.dp))
            .background(containerColor)
            .clickable { onClick() }
            .padding(horizontal = 4.dp, vertical = 2.dp)
    ) {
        Text(
            text = text,
            fontSize = 10.sp,
            color = contentColor,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}
@Composable
fun EventDetails(
    event: CalendarEvent,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cerrar")
            }
        },
        title = { Text(event.title) },
        text = {
            Column {
                Text(event.description.ifBlank { "Sin descripción" })
                Spacer(Modifier.height(8.dp))
                Text("Fecha: ${event.date}")

                event.time?.let { time ->
                    Text("Hora: ${String.format("%02d", time.hour)}:${String.format("%02d", time.minute)}")
                }

                Text("Estado: ${if (event.isDone) "Terminado" else "Pendiente"}")
            }
        }
    )
}

// Vista semanal
@Composable
fun CalendarWeekView(
    viewModel: CalendarViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val state by viewModel.state.collectAsState()
    val selectedDate = state.selectedDate

    // Obtener los 7 días de la semana actual
    val startOfWeek = selectedDate.minus(DatePeriod(days = selectedDate.dayOfWeek.isoDayNumber % 7))
    val weekDays = (0..6).map { startOfWeek.plus(DatePeriod(days = it)) }

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
            .padding(horizontal = 8.dp)
    ) {
        // Controles de navegación de semana
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                viewModel.selectDate(selectedDate.minus(DatePeriod(days = 7)))
            }) {
                Text("←", fontSize = 24.sp)
            }

            Text(
                text = "Semana del ${weekDays.first().dayOfMonth} al ${weekDays.last().dayOfMonth}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )

            IconButton(onClick = {
                viewModel.selectDate(selectedDate.plus(DatePeriod(days = 7)))
            }) {
                Text("→", fontSize = 24.sp)
            }
        }

        // Headers de días
        Row(Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
            weekDays.forEach { date ->
                Column(
                    Modifier.weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = date.dayOfWeek.name.take(3),
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                    Text(
                        text = date.dayOfMonth.toString(),
                        fontSize = 14.sp,
                        fontWeight = if (date == today()) FontWeight.Bold else FontWeight.Normal,
                        color = if (date == today()) MaterialTheme.colorScheme.primary
                        else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        HorizontalDivider()

        // Grid de eventos por día
        Row(
            Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(top = 8.dp)
        ) {
            weekDays.forEach { date ->
                val dayEvents = state.events.filter { it.date == date }

                LazyColumn(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .padding(horizontal = 2.dp),
                    verticalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    items(dayEvents.size) { index ->
                        val event = dayEvents[index]
                        EventChipCompact(
                            text = event.title,
                            isDone = event.isDone,
                            onClick = { viewModel.openEventDetails(event) }
                        )
                    }

                    // Espacio al final
                    if (dayEvents.isEmpty()) {
                        item {
                            Text(
                                text = "Sin eventos",
                                fontSize = 11.sp,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier.padding(8.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    // Detalles del evento
    val sheetEvent = state.sheetEvent
    if (sheetEvent != null) {
        EventDetails(
            event = sheetEvent,
            onDismiss = { viewModel.closeDetails() }
        )
    }
}

// Vista diaria
@Composable
fun CalendarDayView(
    viewModel: CalendarViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
) {
    val state by viewModel.state.collectAsState()
    val selectedDate = state.selectedDate
    val dayEvents = state.events.filter { it.date == selectedDate }

    // Agrupar eventos por hora
    val eventsByHour = remember(dayEvents) {
        dayEvents.groupBy { event ->
            event.time?.hour ?: 8
        }
    }

    Column(
        Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.surface)
    ) {
        // Controles de navegación de día
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = {
                viewModel.selectDate(selectedDate.minus(DatePeriod(days = 1)))
            }) {
                Text("←", fontSize = 24.sp)
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = selectedDate.dayOfWeek.name.lowercase().replaceFirstChar { it.uppercase() },
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "${selectedDate.dayOfMonth} de ${selectedDate.month.name.lowercase().replaceFirstChar { it.uppercase() }}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            IconButton(onClick = {
                viewModel.selectDate(selectedDate.plus(DatePeriod(days = 1)))
            }) {
                Text("→", fontSize = 24.sp)
            }
        }

        HorizontalDivider()

        // Vista de horas del día
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 12.dp)
        ) {
            items(24) { hour ->
                Column {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = String.format("%02d:00", hour),
                            modifier = Modifier.width(56.dp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            fontSize = 14.sp,
                            fontWeight = if (eventsByHour.containsKey(hour)) FontWeight.Bold else FontWeight.Normal
                        )
                        HorizontalDivider(
                            Modifier
                                .weight(1f)
                                .height(1.dp),
                            color = MaterialTheme.colorScheme.outlineVariant
                        )
                    }

                    // Mostrar eventos que corresponden a esta hora
                    eventsByHour[hour]?.let { eventsAtThisHour ->
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 56.dp, end = 8.dp),
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            eventsAtThisHour.forEach { event ->
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(RoundedCornerShape(8.dp))
                                        .background(
                                            if (event.isDone) MaterialTheme.colorScheme.surfaceVariant
                                            else MaterialTheme.colorScheme.primaryContainer
                                        )
                                        .clickable { viewModel.openEventDetails(event) }
                                        .padding(12.dp)
                                ) {
                                    Column {
                                        Row(
                                            modifier = Modifier.fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween,
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            Text(
                                                text = event.title,
                                                fontWeight = FontWeight.Bold,
                                                color = if (event.isDone)
                                                    MaterialTheme.colorScheme.onSurfaceVariant
                                                else
                                                    MaterialTheme.colorScheme.onPrimaryContainer,
                                                modifier = Modifier.weight(1f)
                                            )
                                            event.time?.let { time ->
                                                Text(
                                                    text = "${String.format("%02d", time.hour)}:${String.format("%02d", time.minute)}",
                                                    fontSize = 11.sp,
                                                    color = if (event.isDone)
                                                        MaterialTheme.colorScheme.onSurfaceVariant
                                                    else
                                                        MaterialTheme.colorScheme.onPrimaryContainer
                                                )
                                            }
                                        }
                                        if (event.description.isNotBlank()) {
                                            Spacer(Modifier.height(4.dp))
                                            Text(
                                                text = event.description,
                                                fontSize = 12.sp,
                                                color = if (event.isDone)
                                                    MaterialTheme.colorScheme.onSurfaceVariant
                                                else
                                                    MaterialTheme.colorScheme.onPrimaryContainer
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Mensaje si no hay eventos
            if (dayEvents.isEmpty()) {
                item {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "No hay eventos para este día",
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Mes")
@Composable fun PreviewMonth() { CalendarMonthView() }

@Preview(showBackground = true, name = "Semana")
@Composable fun PreviewWeek() { CalendarWeekView() }

@Preview(showBackground = true, name = "Día")
@Composable fun PreviewDay() { CalendarDayView() }