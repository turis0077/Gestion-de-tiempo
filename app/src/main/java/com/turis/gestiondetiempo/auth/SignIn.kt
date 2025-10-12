//Pantallas de Creación de cuenta en sus diferentes estados.

package com.turis.gestiondetiempo.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
@OptIn(ExperimentalMaterial3Api::class)

@Composable
fun CrearCuentaScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Creación de cuenta") },
                navigationIcon = {
                    IconButton(onClick = { /* Volver a pantalla anterior */ }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant
                )

            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            InputField(label = "Nombre de usuario", placeholder = "Nombre de usuario")
            InputField(label = "Contraseña", placeholder = "Contraseña", isPassword = true)
            InputField(label = "Confirma tu contraseña", placeholder = "Contraseña", isPassword = true)
            InputField(label = "Ingresa tu fecha de nacimiento", placeholder = "MM/DD/AAAA")
            InputField(label = "Correo electrónico", placeholder = "userexample@gmail.com")
            InputField(label = "Sube una foto de perfil", placeholder = "subir archivo png o jpg")

            Spacer(Modifier.height(24.dp))
            Button(
                onClick = { /* crear cuenta */ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    contentColor = MaterialTheme.colorScheme.onPrimary
                ),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier.fillMaxWidth(0.9f)
            ) {
                Text("Crear Cuenta")
            }
        }
    }
}

@Composable
fun InputField(label: String, placeholder: String, isPassword: Boolean = false) {
    Column(Modifier.padding(vertical = 8.dp)) {
        Text(label)
        val visualTransformation =
            if (isPassword) PasswordVisualTransformation() else VisualTransformation.None //Ocultar caracteres si son de contraseña
        TextField(
            value = "",
            onValueChange = {},
            placeholder = { Text(placeholder) },
            visualTransformation = visualTransformation,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape =RoundedCornerShape(8.dp))
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CrearCuentaPreview() {
    CrearCuentaScreen()
}

