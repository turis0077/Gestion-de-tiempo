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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.turis.gestiondetiempo.ui.theme.GestionDeTiempoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearCuentaRoute(
    onBack: () -> Unit,
    viewModel: SignInViewModel = viewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    CrearCuentaScreen(
        state = state,
        onEvent = viewModel::onEvent,
        onBack = onBack
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CrearCuentaScreen(
    state: SignInState,
    onEvent: (SignInEvent) -> Unit,
    onBack: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Creación de cuenta") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
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
            InputField(
                field = state.username,
                onValueChange = { onEvent(SignInEvent.UsernameChanged(value = it)) }
            )
            InputField(
                field = state.password,
                onValueChange = { onEvent(SignInEvent.PasswordChanged(value = it)) }
            )
            InputField(
                field = state.confirm,
                onValueChange = { onEvent(SignInEvent.ConfirmChanged(value = it)) }
            )
            InputField(
                field = state.fechaCumple,
                onValueChange = { onEvent(SignInEvent.BirthChanged(value = it)) }
            )
            InputField(
                field = state.email,
                onValueChange = { onEvent(SignInEvent.EmailChanged(value = it)) }
            )
            InputField(
                field = state.foto,
                onValueChange = { onEvent(SignInEvent.PhotoChanged(value = it)) }
            )

            Spacer(Modifier.height(24.dp))
            Button(
                onClick = { onEvent(SignInEvent.Submit) },
                enabled = state.submitEnabled,
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
fun InputField(field: Field, onValueChange: (String) -> Unit) {
    Column(Modifier.padding(vertical = 8.dp)) {
        Text(text = field.placeholder)
        val visualTransformation =
            if (field.isPassword) PasswordVisualTransformation() else VisualTransformation.None //Ocultar caracteres si son de contraseña
        TextField(
            value = field.value,
            onValueChange = onValueChange,
            placeholder = { Text(field.placeholder) },
            visualTransformation = visualTransformation,
            isError = field.status == FieldStatus.Error,
            supportingText = if (field.status == FieldStatus.Error) {
                { Text(field.errorText ?: "") }
            } else null,
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(8.dp)
                )
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CrearCuentaPreview() {
    GestionDeTiempoTheme {
        CrearCuentaScreen(
            state = SignInState(),
            onEvent = {},
            onBack = {}
        )
    }
}