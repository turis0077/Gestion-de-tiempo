package com.turis.gestiondetiempo.data.repository

import com.turis.gestiondetiempo.data.remote.service.CalendarApiService
import com.turis.gestiondetiempo.model.CalendarEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.LocalDate

class GoogleCalendarRepository(
    private val apiService: CalendarApiService = CalendarApiService()
) : CalendarRepository {

    private val _events = MutableStateFlow<List<CalendarEvent>>(emptyList())
    override val events: Flow<List<CalendarEvent>> = _events.asStateFlow()

    private var accessToken: String? = null

    fun setAccessToken(token: String) {
        accessToken = token
    }


    suspend fun syncEvents() {
        val token = accessToken ?: throw IllegalStateException("Access token no configurado")
        val remoteEvents = apiService.getCalendarEvents(token)
        _events.value = remoteEvents
    }

    override suspend fun add(
        title: String,
        description: String,
        date: LocalDate
    ): CalendarEvent {
        val token = accessToken ?: throw IllegalStateException("Access token no configurado")
        val newEvent = apiService.addEvent(token, title, description, date)
        _events.value = _events.value + newEvent
        return newEvent
    }

    override suspend fun update(event: CalendarEvent) {
        val token = accessToken ?: throw IllegalStateException("Access token no configurado")
        val updatedEvent = apiService.updateEvent(token, event)
        _events.value = _events.value.map {
            if (it.id == updatedEvent.id) updatedEvent else it
        }
    }

    override suspend fun toggleDone(id: String) {
        val event = _events.value.firstOrNull { it.id == id } ?: return
        val toggled = event.copy(isDone = !event.isDone)
        update(toggled)
    }

    override suspend fun getById(id: String): CalendarEvent? {
        return _events.value.firstOrNull { it.id == id }
    }

    override suspend fun delete(id: String) {
        val token = accessToken ?: throw IllegalStateException("Access token no configurado")
        apiService.deleteEvent(token, id)
        _events.value = _events.value.filterNot { it.id == id }
    }
}