package com.turis.gestiondetiempo.nav.routes

import kotlinx.serialization.Serializable

sealed interface AuthRoutes {

    @Serializable
    data object Welcome : AuthRoutes

    @Serializable
    data object LogIn : AuthRoutes

    @Serializable
    data object SignIn : AuthRoutes
}