package com.turis.gestiondetiempo.model

import kotlinx.datetime.LocalTime
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class CalendarEvent(
    val id: String,
    val title: String,
    val description: String,
    val date: LocalDate,
    val time: LocalTime? = null,
    val isDone: Boolean = false
)