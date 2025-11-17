package com.turis.gestiondetiempo.model

import androidx.compose.ui.graphics.Color
import kotlinx.serialization.Serializable
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Locale

enum class TaskTag(val label: String, val tint: Color) {
    Estudios("Estudios", Color(0xFFB8E6C9)),
    Personal("Personal", Color(0xFFD7DFF7)),
    Hobbies("Hobbies", Color(0xFFE9D7F7)),
    Ninguno("Sin categoría", Color(0xFFE6E6E6))
}

@Serializable
data class ChipInfo(
    val text: String,
    val muted: Boolean = false
)

@Serializable
data class SubItem(
    val title: String,
    val done: Boolean = false
)

data class Task(
    val id: String,
    val title: String,
    val date: LocalDate,
    val tag: TaskTag = TaskTag.Ninguno,
    val chips: List<ChipInfo> = emptyList(),
    val subItems: List<SubItem> = emptyList(),
    val completed: Boolean = false,
    val description: String = "",
    val timeMinutes: Int = 0,  // Minutos
    val timeSeconds: Int = 0   // Segundos
)

data class TaskSection(
    val header: String,
    val items: List<Task>
)


fun sampleTaskListEmpty() = listOf<TaskSection>()

fun sampleTaskListOne(): List<TaskSection> = emptyList()

fun sampleTaskListFull(): List<TaskSection> = listOf(
    TaskSection(
        header = "Hoy - 17 de julio",
        items = listOf(
            Task(
                id = "0001",
                title = "Planificación de la semana",
                date = LocalDate.now(),
                tag = TaskTag.Ninguno,
                chips = listOf(ChipInfo("Info"), ChipInfo("5:00"))
            ),
            Task(
                id = "0002",
                title = "Tarea de cálculo",
                date = LocalDate.now(),
                tag = TaskTag.Estudios,
                chips = listOf(ChipInfo("Info"), ChipInfo("10:00")),
                subItems = listOf(
                    SubItem("Ejercicio 1"),
                    SubItem("Ejercicio 2", done = true),
                    SubItem("Ejercicio 3"),
                    SubItem("Ejercicio 4", done = true),
                    SubItem("Ejercicio 5"),
                    SubItem("Ejercicio 6"),
                ),
                description = "Ejercicios de la sección 5 del libro, pág 800"
            )
        )
    ),
    TaskSection(
        header = "Mañana - 18 de julio",
        items = listOf(
            Task(
                id = "0003",
                title = "Lluvia de ideas de proyecto",
                date = LocalDate.now().plusDays(1),
                tag = TaskTag.Personal,
                chips = listOf(ChipInfo("Info"), ChipInfo("13:00"))
            ),
            Task(
                id = "0004",
                title = "Física: Repasar contenidos",
                date = LocalDate.now().plusDays(1),
                tag = TaskTag.Estudios,
                chips = listOf(ChipInfo("Info"), ChipInfo("15:00"))
            ),
            Task(
                id = "0005",
                title = "Leer capítulo 3 de Hábitos Atómicos",
                date = LocalDate.now().plusDays(1),
                tag = TaskTag.Hobbies,
                chips = listOf(ChipInfo("Info"), ChipInfo("16:00"))
            )
        )
    ),
    TaskSection(
        header = "Pasado mañana - 19 de julio",
        items = listOf(
            Task(
                id = "0006",
                title = "Finalizar modelo 3D",
                date = LocalDate.now().plusDays(2),
                tag = TaskTag.Estudios,
                chips = listOf(ChipInfo("Info"), ChipInfo("19:00"))
            )
        )
    )
)

fun sampleTaskDetail(): Task = Task(
    id = "0002",
    title = "Tarea de cálculo",
    date = LocalDate.now(),
    tag = TaskTag.Estudios,
    chips = listOf(ChipInfo("Fecha límite: 17/07/2025", muted = true)),
    subItems = listOf(
        SubItem("Ejercicio 1"),
        SubItem("Ejercicio 2", done = true),
        SubItem("Ejercicio 3"),
        SubItem("Ejercicio 4", done = true),
        SubItem("Ejercicio 5"),
        SubItem("Ejercicio 6"),
    ),
    description = "Ejercicios de la sección 5 del libro, pág 800"
)

// Función para organizar tareas por fechas relativas
fun organizarTareasPorFecha(tareas: List<Task>, today: LocalDate = LocalDate.now()): List<TaskSection> {
    // Filtrar tareas no completadas
    val tareasActivas = tareas.filter { !it.completed }

    if (tareasActivas.isEmpty()) {
        return emptyList()
    }

    // Agrupar tareas por fecha
    val tareasVencidas = mutableListOf<Task>()
    val tareasPorDia = mutableMapOf<LocalDate, MutableList<Task>>()

    tareasActivas.forEach { tarea ->
        when {
            tarea.date.isBefore(today) -> tareasVencidas.add(tarea)
            else -> {
                tareasPorDia.getOrPut(tarea.date) { mutableListOf() }.add(tarea)
            }
        }
    }

    val secciones = mutableListOf<TaskSection>()

    // Agregar sección de tareas vencidas
    if (tareasVencidas.isNotEmpty()) {
        secciones.add(TaskSection(
            header = "Tareas vencidas",
            items = tareasVencidas
        ))
    }

    // Agregar secciones de tareas futuras
    tareasPorDia.keys.sorted().forEach { fecha ->
        val header = obtenerEncabezadoFecha(fecha, today)
        secciones.add(TaskSection(
            header = header,
            items = tareasPorDia[fecha] ?: emptyList()
        ))
    }

    return secciones
}

// Función para obtener el encabezado de fecha relativa
fun obtenerEncabezadoFecha(fecha: LocalDate, today: LocalDate): String {
    val diasDiferencia = ChronoUnit.DAYS.between(today, fecha)
    val formatter = DateTimeFormatter.ofPattern("d 'de' MMMM", Locale("es", "ES"))
    val fechaFormateada = fecha.format(formatter)

    return when (diasDiferencia.toInt()) {
        0 -> "Hoy - $fechaFormateada"
        1 -> "Mañana - $fechaFormateada"
        2 -> "Pasado mañana - $fechaFormateada"
        else -> fechaFormateada
    }
}
