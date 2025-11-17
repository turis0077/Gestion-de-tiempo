package com.turis.gestiondetiempo.auth

import android.app.Application
import android.net.Uri
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.turis.gestiondetiempo.data.local.AppDatabase
import com.turis.gestiondetiempo.data.local.UserEntity
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class SignInViewModel(application: Application) : AndroidViewModel(application) {

    private val database = AppDatabase.getDatabase(application)
    private val userDao = database.userDao()

    private val _state = MutableStateFlow(value = SignInState())
    val state = _state.asStateFlow()

    // Para almacenar la URI de la foto seleccionada
    private var selectedPhotoUri: Uri? = null

    fun setPhotoUri(uri: Uri?) {
        selectedPhotoUri = uri
        _state.update { it.copy(foto = it.foto.copy(value = uri?.toString() ?: "")) }
    }

    fun getPhotoUri(): Uri? = selectedPhotoUri

    fun onEvent(event: SignInEvent) {
        when (event) {
            is SignInEvent.UsernameChanged -> {
                _state.update { it.copy(username = it.username.copy(value = event.value)) }
                validateUsername(event.value)
            }
            is SignInEvent.PasswordChanged -> {
                _state.update { it.copy(password = it.password.copy(value = event.value)) }
                validatePassword(event.value)
            }
            is SignInEvent.ConfirmChanged  -> {
                _state.update { it.copy(confirm = it.confirm.copy(value = event.value)) }
                validateConfirm(event.value)
            }
            is SignInEvent.BirthChanged    ->
                _state.update { it.copy(fechaCumple = it.fechaCumple.copy(value = event.value)) }
            is SignInEvent.EmailChanged    ->
                _state.update { it.copy(email = it.email.copy(value = event.value)) }
            is SignInEvent.PhotoChanged    ->
                _state.update { it.copy(foto = it.foto.copy(value = event.value)) }

            SignInEvent.Submit -> submitRegistration()
            SignInEvent.Reset  -> _state.value = SignInState()
            SignInEvent.SimUserExists      -> markUserExists()
            SignInEvent.SimWeakPassword    -> markWeakPassword()
            SignInEvent.SimConfirmMismatch -> markConfirmMismatch()
            SignInEvent.SimAllValid        -> markAllValid()
        }
    }

    private fun validateUsername(username: String) {
        viewModelScope.launch {
            if (username.isBlank()) {
                _state.update {
                    it.copy(username = it.username.copy(status = FieldStatus.Neutral, errorText = null))
                }
                return@launch
            }

            if (username.length < 3) {
                _state.update {
                    it.copy(username = it.username.copy(
                        status = FieldStatus.Error,
                        errorText = "El nombre debe tener al menos 3 caracteres"
                    ))
                }
                return@launch
            }

            val exists = userDao.userExists(username) > 0
            if (exists) {
                _state.update {
                    it.copy(username = it.username.copy(
                        status = FieldStatus.Error,
                        errorText = "Nombre de usuario ya existente"
                    ))
                }
            } else {
                _state.update {
                    it.copy(username = it.username.copy(status = FieldStatus.Success, errorText = null))
                }
            }
        }
    }

    private fun validatePassword(password: String) {
        if (password.isBlank()) {
            _state.update {
                it.copy(password = it.password.copy(status = FieldStatus.Neutral, errorText = null))
            }
            return
        }

        if (password.length < 6) {
            _state.update {
                it.copy(password = it.password.copy(
                    status = FieldStatus.Error,
                    errorText = "La contraseña debe tener al menos 6 caracteres"
                ))
            }
        } else {
            _state.update {
                it.copy(password = it.password.copy(
                    status = FieldStatus.Success,
                    errorText = "Contraseña segura"
                ))
            }
            // Revalidar confirmación si existe
            if (_state.value.confirm.value.isNotBlank()) {
                validateConfirm(_state.value.confirm.value)
            }
        }
    }

    private fun validateConfirm(confirm: String) {
        if (confirm.isBlank()) {
            _state.update {
                it.copy(confirm = it.confirm.copy(status = FieldStatus.Neutral, errorText = null))
            }
            return
        }

        if (confirm != _state.value.password.value) {
            _state.update {
                it.copy(confirm = it.confirm.copy(
                    status = FieldStatus.Error,
                    errorText = "Las contraseñas no coinciden"
                ))
            }
        } else {
            _state.update {
                it.copy(confirm = it.confirm.copy(status = FieldStatus.Success, errorText = null))
            }
        }
    }

    private fun submitRegistration() {
        val currentState = _state.value

        // Validar que todos los campos requeridos estén llenos
        if (currentState.username.value.isBlank() ||
            currentState.password.value.isBlank() ||
            currentState.confirm.value.isBlank()) {
            _state.update {
                it.copy(
                    isError = true,
                    isLoading = false,
                    isSuccess = false
                )
            }
            return
        }

        // Validar que no haya errores
        if (currentState.username.status == FieldStatus.Error ||
            currentState.password.status == FieldStatus.Error ||
            currentState.confirm.status == FieldStatus.Error) {
            _state.update { it.copy(isError = true) }
            return
        }

        _state.update { it.copy(isLoading = true, isSuccess = false, isError = false, submitEnabled = false) }

        viewModelScope.launch {
            try {
                val newUser = UserEntity(
                    username = currentState.username.value,
                    password = currentState.password.value,
                    profilePhotoUri = selectedPhotoUri?.toString()
                )

                userDao.insertUser(newUser)
                delay(500)

                _state.update {
                    it.copy(
                        isLoading = false,
                        isSuccess = true,
                        submitEnabled = true
                    )
                }
            } catch (e: Exception) {
                _state.update {
                    it.copy(
                        isLoading = false,
                        isError = true,
                        submitEnabled = true
                    )
                }
            }
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
