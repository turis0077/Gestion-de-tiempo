package com.turis.gestiondetiempo.nav.routes

import kotlinx.serialization.Serializable

@Serializable
enum class CalendarMode { DAY, WEEK, MONTH }

sealed interface LoggedRoutes {

    @Serializable
    data object Menu : LoggedRoutes

    @Serializable
    data object Profile : LoggedRoutes

    @Serializable
    data object TaskList : LoggedRoutes

    @Serializable
    data class TaskDetail(
        val taskId: String,
        val taskTitle: String? = null
    ) : LoggedRoutes

    @Serializable
    data class Timer(
        val initialSeconds: Int = 0
    ) : LoggedRoutes

    @Serializable
    data class Calendar(
        val mode: CalendarMode = CalendarMode.MONTH,
        val isoDate: String? = null
    ) : LoggedRoutes

    @Serializable
    data object Settings : LoggedRoutes

}