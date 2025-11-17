package com.turis.gestiondetiempo.auth

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.turis.gestiondetiempo.data.local.AppDatabase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val userDao = database.userDao()

    private val _state = MutableStateFlow(value = LoginState())
    val state = _state.asStateFlow()

    private var loginSuccessful = false
    private var loggedInUsername = ""

    fun onEvent(event: LoginEvent) {
        when (event) {
            is LoginEvent.UsernameChanged -> {
                _state.update {
                    it.copy(
                        username = it.username.copy(value = event.value),
                        errorMessage = null
                    )
                }
            }
            is LoginEvent.PasswordChanged -> {
                _state.update {
                    it.copy(
                        password = it.password.copy(value = event.value),
                        errorMessage = null
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

        _state.update { it.copy(isLoading = true, errorMessage = null) }

        viewModelScope.launch {
            try {
                val user = userDao.login(
                    currentState.username.value,
                    currentState.password.value
                )

                if (user != null) {
                    // Login exitoso
                    loginSuccessful = true
                    loggedInUsername = user.username
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = null
                        )
                    }
                } else {
                    // Usuario o contraseña incorrectos
                    loginSuccessful = false
                    _state.update {
                        it.copy(
                            isLoading = false,
                            errorMessage = "Usuario o contraseña incorrectos"
                        )
                    }
                }
            } catch (e: Exception) {
                loginSuccessful = false
                _state.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = "Error al iniciar sesión: ${e.message}"
                    )
                }
            }
        }
    }

    fun getUsernameValue(): String = _state.value.username.value
    fun getPasswordValue(): String = _state.value.password.value
    fun hasError(): Boolean = _state.value.errorMessage != null
    fun isLoginSuccessful(): Boolean = loginSuccessful
    fun getLoggedInUsername(): String = loggedInUsername
}