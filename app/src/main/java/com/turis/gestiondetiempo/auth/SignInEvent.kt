package com.turis.gestiondetiempo.auth

sealed interface SignInEvent {
    data class UsernameChanged(val value: String): SignInEvent
    data class PasswordChanged(val value: String): SignInEvent
    data class ConfirmChanged(val value: String): SignInEvent
    data class BirthChanged(val value: String): SignInEvent
    data class EmailChanged(val value: String): SignInEvent
    data class PhotoChanged(val value: String): SignInEvent

    data object Submit: SignInEvent
    data object Reset: SignInEvent

    data object SimUserExists: SignInEvent
    data object SimWeakPassword: SignInEvent
    data object SimConfirmMismatch: SignInEvent
    data object SimAllValid: SignInEvent
}
