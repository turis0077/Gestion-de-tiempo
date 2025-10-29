package com.turis.gestiondetiempo.nav

import kotlinx.serialization.Serializable

sealed interface LoggedRoutes {

    @Serializable
    data object Menu : LoggedRoutes

    @Serializable
    data object Profile : LoggedRoutes

    @Serializable
    data object TaskList : LoggedRoutes

    @Serializable
    data class TaskDetail(
        val taskId: Long
    ) : LoggedRoutes

    @Serializable
    data class Timer(
        val initialSeconds: Int = 0
    ) : LoggedRoutes

    @Serializable
    data object Calendar : LoggedRoutes
}