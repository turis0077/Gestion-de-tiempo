package com.turis.gestiondetiempo.data.repository

import com.turis.gestiondetiempo.model.CalendarEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.datetime.LocalDate
import java.util.UUID

interface CalendarRepository {
    val events: Flow<List<CalendarEvent>>

    suspend fun add(
        title: String,
        description: String,
        date: LocalDate
    ): CalendarEvent

    suspend fun update(event: CalendarEvent)

    suspend fun toggleDone(id: String)

    suspend fun getById(id: String): CalendarEvent?

    suspend fun delete(id: String)
}

class InMemoryCalendarRepository : CalendarRepository {

    private val _events = MutableStateFlow<List<CalendarEvent>>(emptyList())
    override val events: Flow<List<CalendarEvent>> = _events.asStateFlow()

    override suspend fun add(
        title: String,
        description: String,
        date: LocalDate
    ): CalendarEvent {
        val ev = CalendarEvent(
            id = UUID.randomUUID().toString(),
            title = title.trim(),
            description = description.trim(),
            date = date
        )
        _events.update { it + ev }
        return ev
    }

    override suspend fun update(event: CalendarEvent) {
        _events.update { list ->
            list.map { if (it.id == event.id) event else it }
        }
    }

    override suspend fun toggleDone(id: String) {
        _events.update { list ->
            list.map { ev ->
                if (ev.id == id) ev.copy(isDone = !ev.isDone) else ev
            }
        }
    }

    override suspend fun getById(id: String): CalendarEvent? =
        _events.value.firstOrNull { it.id == id }

    override suspend fun delete(id: String) {
        _events.update { list -> list.filterNot { it.id == id } }
    }
}