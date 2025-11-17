package com.turis.gestiondetiempo.features.calendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.turis.gestiondetiempo.data.repository.CalendarRepository
import com.turis.gestiondetiempo.data.repository.InMemoryCalendarRepository
import com.turis.gestiondetiempo.model.CalendarEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

private fun today(): LocalDate =
    Clock.System.now()
        .toLocalDateTime(TimeZone.currentSystemDefault())
        .date

data class CalendarUiState(
    val currentMonth: LocalDate = today(),
    val selectedDate: LocalDate = today(),
    val events: List<CalendarEvent> = emptyList(),
    val showEditor: Boolean = false,
    val editing: CalendarEvent? = null,
    val sheetEvent: CalendarEvent? = null
)

class CalendarViewModel(
    private val repo: CalendarRepository = InMemoryCalendarRepository()
) : ViewModel() {

    private val _internalState = MutableStateFlow(CalendarUiState())

    val state: StateFlow<CalendarUiState> = combine(
        _internalState,
        repo.events
    ) { ui, events -> ui.copy(events = events) }
        .stateIn(
            viewModelScope,
            SharingStarted.Eagerly,
            CalendarUiState()
        )

    fun goPrevMonth() {
        _internalState.update { s ->
            val d = s.currentMonth
            val prev = if (d.monthNumber == 1) {
                LocalDate(d.year - 1, 12, 1)
            } else {
                LocalDate(d.year, d.monthNumber - 1, 1)
            }
            s.copy(currentMonth = prev)
        }
    }

    fun goNextMonth() {
        _internalState.update { s ->
            val d = s.currentMonth
            val next = if (d.monthNumber == 12) {
                LocalDate(d.year + 1, 1, 1)
            } else {
                LocalDate(d.year, d.monthNumber + 1, 1)
            }
            s.copy(currentMonth = next)
        }
    }

    fun selectDate(date: LocalDate) {
        _internalState.update { it.copy(selectedDate = date) }
    }

    fun openNewEditor(forDate: LocalDate) {
        _internalState.update {
            it.copy(
                showEditor = true,
                editing = null,
                selectedDate = forDate
            )
        }
    }

    fun openEdit(event: CalendarEvent) {
        _internalState.update {
            it.copy(
                showEditor = true,
                editing = event,
                selectedDate = event.date
            )
        }
    }

    fun closeEditor() {
        _internalState.update {
            it.copy(
                showEditor = false,
                editing = null
            )
        }
    }

    fun saveEvent(
        title: String,
        description: String,
        date: LocalDate
    ) {
        viewModelScope.launch {
            val editing = state.value.editing
            if (editing == null) {
                // nuevo evento
                repo.add(title, description, date)
            } else {
                // actualización
                repo.update(
                    editing.copy(
                        title = title,
                        description = description,
                        date = date
                    )
                )
            }
            closeEditor()
        }
    }

    fun openEventDetails(event: CalendarEvent) {
        _internalState.update { it.copy(sheetEvent = event) }
    }

    fun closeDetails() {
        _internalState.update { it.copy(sheetEvent = null) }
    }

    fun updateEvent(event: CalendarEvent) {
        viewModelScope.launch {
            repo.update(event)
        }
    }

    fun toggleDone(id: String) {
        viewModelScope.launch {
            repo.toggleDone(id)
        }
    }

    fun deleteEvent(id: String) {
        viewModelScope.launch {
            repo.delete(id)
            val current = _internalState.value.sheetEvent
            if (current?.id == id) {
                _internalState.update { it.copy(sheetEvent = null) }
            }
        }
    }
}