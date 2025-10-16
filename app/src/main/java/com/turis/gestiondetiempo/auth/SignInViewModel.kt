package com.turis.gestiondetiempo.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel : ViewModel() {

    private val _state = MutableStateFlow(value = SignInState())
    val state = _state.asStateFlow()

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.UsernameChanged ->
                _state.update { it.copy(username = it.username.copy(value = event.value)) }
            is SignInEvent.PasswordChanged ->
                _state.update { it.copy(password = it.password.copy(value = event.value)) }
            is SignInEvent.ConfirmChanged  ->
                _state.update { it.copy(confirm = it.confirm.copy(value = event.value)) }
            is SignInEvent.BirthChanged    ->
                _state.update { it.copy(fechaCumple = it.fechaCumple.copy(value = event.value)) }
            is SignInEvent.EmailChanged    ->
                _state.update { it.copy(email = it.email.copy(value = event.value)) }
            is SignInEvent.PhotoChanged    ->
                _state.update { it.copy(foto = it.foto.copy(value = event.value)) }

            SignInEvent.Submit -> simulateSubmit()
            SignInEvent.Reset  -> _state.value = SignInState()

            //Estados de validación ficticios (para mostrar visualización):
            SignInEvent.SimUserExists      -> markUserExists()
            SignInEvent.SimWeakPassword    -> markWeakPassword()
            SignInEvent.SimConfirmMismatch -> markConfirmMismatch()
            SignInEvent.SimAllValid        -> markAllValid()
        }
    }

    private fun simulateSubmit() {
        _state.update { it.copy(isLoading = true, isSuccess = false, isError = false) }
        viewModelScope.launch {
            delay(1500)

            _state.update { it.copy(isLoading = false, isSuccess = true) }
        }
    }

    private fun markUserExists() {
        _state.update {
            it.copy(
                username = it.username.copy(
                    status = FieldStatus.Error,
                    errorText = "Nombre de usuario ya existente"
                ),
                password = it.password.copy(status = FieldStatus.Success, errorText = "Contraseña segura"),
                confirm  = it.confirm.copy(status = FieldStatus.Neutral, errorText = null),
                isError = false, isSuccess = false, isLoading = false
            )
        }
    }

    private fun markWeakPassword() {
        _state.update {
            it.copy(
                username = it.username.copy(status = FieldStatus.Neutral, errorText = null),
                password = it.password.copy(status = FieldStatus.Error, errorText = "Contraseña débil"),
                confirm  = it.confirm.copy(status = FieldStatus.Neutral, errorText = null),
                isError = false, isSuccess = false, isLoading = false
            )
        }
    }

    private fun markConfirmMismatch() {
        _state.update {
            it.copy(
                password = it.password.copy(status = FieldStatus.Success, errorText = "Contraseña segura"),
                confirm  = it.confirm.copy(status = FieldStatus.Error, errorText = "Contraseña no coincide"),
                isError = false, isSuccess = false, isLoading = false
            )
        }
    }

    private fun markAllValid() {
        _state.update {
            it.copy(
                username = it.username.copy(status = FieldStatus.Neutral, errorText = null),
                password = it.password.copy(status = FieldStatus.Success, errorText = "Contraseña segura"),
                confirm  = it.confirm.copy(status = FieldStatus.Neutral, errorText = null),
                isError = false, isSuccess = false, isLoading = false,
                submitEnabled = true
            )
        }
    }
}
