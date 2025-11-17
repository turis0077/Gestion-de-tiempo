package com.turis.gestiondetiempo.auth

data class LoginState(
    val username: Field = Field(placeholder = "Nombre de usuario"),
    val password: Field = Field(isPassword = true, placeholder = "Contraseña"),

    val errorMessage: String? = null,
    val isLoading: Boolean = false,
    val submitEnabled: Boolean = true
)