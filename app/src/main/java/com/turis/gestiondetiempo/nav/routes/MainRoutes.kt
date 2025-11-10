package com.turis.gestiondetiempo.nav.routes

import kotlinx.serialization.Serializable

sealed interface MainRoutes {
    @Serializable
    data object Start: MainRoutes

    @Serializable
    data object Logged: MainRoutes
}