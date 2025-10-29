package com.turis.gestiondetiempo.nav

import kotlinx.serialization.Serializable

sealed interface AuthRoutes {

    @Serializable
    data object Start : AuthRoutes

    @Serializable
    data object LogIn : AuthRoutes

    @Serializable
    data object SignIn : AuthRoutes
}
