package com.turis.gestiondetiempo.model

import androidx.compose.ui.graphics.Color
import java.time.LocalDate

enum class TaskTag(val label: String, val tint: Color) {
    Estudios("Estudios", Color(0xFFB8E6C9)),
    Personal("Personal", Color(0xFFD7DFF7)),
    Hobbies("Hobbies", Color(0xFFE9D7F7)),
    Ninguno("Sin categoría", Color(0xFFE6E6E6))
}

data class ChipInfo(
    val text: String,
    val muted: Boolean = false
)

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
)

data class TaskSection(
    val header: String,
    val items: List<Task>
)


fun sampleTaskListEmpty() = listOf<TaskSection>()

fun sampleTaskListOne(): List<TaskSection> = listOf(
    TaskSection(
        header = "Hoy - 17 de julio",
        items = listOf(
            Task(
                id = "1",
                title = "Tarea de cálculo",
                date = LocalDate.now(),
                tag = TaskTag.Ninguno,
                chips = listOf(ChipInfo("Info", true), ChipInfo("00:20", true))
            )
        )
    )
)

fun sampleTaskListFull(): List<TaskSection> = listOf(
    TaskSection(
        header = "Hoy - 17 de julio",
        items = listOf(
            Task(
                id = "2",
                title = "Planificación de la semana",
                date = LocalDate.now(),
                tag = TaskTag.Ninguno,
                chips = listOf(ChipInfo("Info"), ChipInfo("5:00"))
            ),
            Task(
                id = "3",
                title = "Tarea de cálculo",
                date = LocalDate.now(),
                tag = TaskTag.Estudios,
                chips = listOf(ChipInfo("Info"), ChipInfo("10:00"))
            )
        )
    ),
    TaskSection(
        header = "Mañana - 18 de julio",
        items = listOf(
            Task(
                id = "4",
                title = "Lluvia de ideas de proyecto",
                date = LocalDate.now().plusDays(1),
                tag = TaskTag.Personal,
                chips = listOf(ChipInfo("Info"), ChipInfo("13:00"))
            ),
            Task(
                id = "5",
                title = "Física: Repasar contenidos",
                date = LocalDate.now().plusDays(1),
                tag = TaskTag.Estudios,
                chips = listOf(ChipInfo("Info"), ChipInfo("15:00"))
            ),
            Task(
                id = "6",
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
                id = "7",
                title = "Finalizar modelo 3D",
                date = LocalDate.now().plusDays(2),
                tag = TaskTag.Estudios,
                chips = listOf(ChipInfo("Info"), ChipInfo("19:00"))
            )
        )
    )
)

fun sampleTaskDetail(): Task = Task(
    id = "T-Calc",
    title = "Tarea de cálculo",
    date = LocalDate.of(2025, 7, 17),
    tag = TaskTag.Estudios,
    chips = listOf(ChipInfo("Fecha límite: 17/07/2025", muted = true)),
    subItems = listOf(
        SubItem("Ejercicio 1"),
        SubItem("Ejercicio 2"),
        SubItem("Ejercicio 3"),
        SubItem("Ejercicio 4"),
        SubItem("Ejercicio 5"),
        SubItem("Ejercicio 6"),
    )
)
