package com.turis.gestiondetiempo.auth

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class LoginViewModel : ViewModel() {

    private val _state = MutableStateFlow(value = LoginState())
    val state = _state.asStateFlow()

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.UsernameChanged -> {
                _state.update {
                    it.copy(
                        username = it.username.copy(value = event.value),
                        errorMessage = null // Limpiar error al escribir
                    )
                }
            }
            is LoginEvent.PasswordChanged -> {
                _state.update {
                    it.copy(
                        password = it.password.copy(value = event.value),
                        errorMessage = null // Limpiar error al escribir
                    )
                }
            }
            LoginEvent.Submit -> validateAndSubmit()
        }
    }

    private fun validateAndSubmit() {
        val currentState = _state.value

        // Validar que ambos campos estén llenos
        if (currentState.username.value.isEmpty() || currentState.password.value.isEmpty()) {
            _state.update {
                it.copy(
                    errorMessage = "Por favor, ingrese nombre de usuario y contraseña"
                )
            }
            return
        }

        // Si todo está bien, limpiar el error y permitir el login
        _state.update {
            it.copy(
                errorMessage = null,
                isLoading = false
            )
        }
    }

    fun getUsernameValue(): String = _state.value.username.value
    fun getPasswordValue(): String = _state.value.password.value
    fun hasError(): Boolean = _state.value.errorMessage != null
}