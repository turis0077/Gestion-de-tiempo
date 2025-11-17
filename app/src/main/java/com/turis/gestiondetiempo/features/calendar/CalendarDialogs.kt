package com.turis.gestiondetiempo.features.calendar

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.input.TextFieldValue
import com.turis.gestiondetiempo.model.CalendarEvent
import kotlinx.datetime.LocalDate

@Composable
fun EditEventDialog(
    initialDate: LocalDate,
    initial: CalendarEvent? = null,
    onDismiss: () -> Unit,
    onConfirm: (title: String, description: String, date: LocalDate) -> Unit
) {
    var title by remember(initial) { mutableStateOf(TextFieldValue(initial?.title.orEmpty())) }
    var description by remember(initial) { mutableStateOf(TextFieldValue(initial?.description.orEmpty())) }
    val date = initial?.date ?: initialDate

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                enabled = title.text.isNotBlank(),
                onClick = { onConfirm(title.text.trim(), description.text.trim(), date) }
            ) { Text("Guardar") }
        },
        dismissButton = { TextButton(onClick = onDismiss) { Text("Cancelar") } },
        title = { Text(if (initial == null) "Nuevo evento" else "Editar evento") },
        text = {
            Column {
                OutlinedTextField(value = title, onValueChange = { title = it }, label = { Text("Título") })
                OutlinedTextField(value = description, onValueChange = { description = it }, label = { Text("Descripción") })
            }
        }
    )
}
