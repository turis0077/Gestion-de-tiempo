package com.turis.gestiondetiempo.auth

sealed interface LoginEvent {
    data class UsernameChanged(val value: String): LoginEvent
    data class PasswordChanged(val value: String): LoginEvent
    data object Submit: LoginEvent
}