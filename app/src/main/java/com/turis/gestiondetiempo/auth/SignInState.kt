package com.turis.gestiondetiempo.auth

enum class FieldStatus { Neutral, Success, Error }

data class Field(
    val value: String = "",
    val status: FieldStatus = FieldStatus.Neutral,
    val errorText: String? = null,
    val isPassword: Boolean = false,
    val placeholder: String = ""
)

data class SignInState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val isError: Boolean = false,

    val username: Field = Field(placeholder = "Nombre de usuario"),
    val password: Field = Field(isPassword = true, placeholder = "Contraseña"),
    val confirm: Field = Field(isPassword = true, placeholder = "Confirmar contraseña"),
    val fechaCumple: Field = Field(placeholder = "DD/MM/AAAA"),
    val email: Field = Field(placeholder = "userexample@gmail.com"),
    val foto: Field = Field(placeholder = "subir archivo png o jpg"),

    val submitEnabled: Boolean = true
)
