package com.turis.gestiondetiempo.auth


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.turis.gestiondetiempo.R
import com.turis.gestiondetiempo.ui.AppViewModel
import com.turis.gestiondetiempo.ui.theme.GestionDeTiempoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(
    onBack: () -> Unit = {},
    onLogin: () -> Unit = {},
    loginViewModel: LoginViewModel = viewModel(),
    appViewModel: AppViewModel? = null
) {
    val colors = MaterialTheme.colorScheme
    val loginState by loginViewModel.state.collectAsState()

    // Detectar login exitoso y navegar
    if (loginViewModel.isLoginSuccessful() && !loginState.isLoading) {
        appViewModel?.updateUsername(loginViewModel.getLoggedInUsername())
        appViewModel?.setCurrentUser(loginViewModel.getLoggedInUsername())
        onLogin()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Inicio de sesión") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "regresar",
                            tint = colors.primary
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colors.primaryContainer.copy(alpha = 0.5f),
                    titleContentColor = colors.primary
                )
            )
        },
        containerColor = colors.surface
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
                .padding(horizontal = 24.dp)
                .background(colors.surface),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .size(140.dp)
                    .background(colors.surfaceVariant, CircleShape)
                    .shadow(
                        shape = CircleShape,
                        elevation = 6.dp,
                        spotColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.4f)
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Image(
                    painter = painterResource(R.drawable.app_icon),
                    contentDescription = "Logo",
                    modifier = Modifier
                        .size(800.dp)
                        .clip(CircleShape)
                )
            }

            Spacer(Modifier.height(24.dp))

            // Mensaje de error
            loginState.errorMessage?.let { errorMsg ->
                Surface(
                    color = colors.errorContainer,
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = errorMsg,
                        color = colors.onErrorContainer,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.padding(12.dp),
                        textAlign = TextAlign.Center
                    )
                }
                Spacer(Modifier.height(16.dp))
            }

            // Usuario
            OutlinedTextField(
                value = loginState.username.value,
                onValueChange = { loginViewModel.onEvent(LoginEvent.UsernameChanged(it)) },
                placeholder = { Text(loginState.username.placeholder) },
                singleLine = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colors.outline,
                    unfocusedBorderColor = colors.outline.copy(alpha = 0.6f),
                    focusedContainerColor = colors.surface,
                    unfocusedContainerColor = colors.surface
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 56.dp)
            )

            Spacer(Modifier.height(12.dp))

            // Contraseña
            OutlinedTextField(
                value = loginState.password.value,
                onValueChange = { loginViewModel.onEvent(LoginEvent.PasswordChanged(it)) },
                placeholder = { Text(loginState.password.placeholder) },
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colors.outline,
                    unfocusedBorderColor = colors.outline.copy(alpha = 0.6f),
                    focusedContainerColor = colors.surface,
                    unfocusedContainerColor = colors.surface
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 56.dp)
            )

            Spacer(Modifier.height(16.dp))

            // Botón principal
            Button(
                onClick = {
                    loginViewModel.onEvent(LoginEvent.Submit)
                    // Solo navegar si no hay error
                    if (!loginViewModel.hasError()) {
                        // Guardar username y password en AppViewModel
                        appViewModel?.updateUsername(loginViewModel.getUsernameValue())
                        appViewModel?.updatePassword(loginViewModel.getPasswordValue())
                        onLogin()
                    }
                },
                shape = RoundedCornerShape(14.dp),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colors.primaryContainer,
                    contentColor = colors.onPrimaryContainer
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp)
            ) {
                Text("Iniciar sesión", fontWeight = FontWeight.Bold, fontSize = 16.sp)
            }

            Spacer(Modifier.height(18.dp))

            // Botones secundarios
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {
                SocialChip("f")
                SocialChip("G")
                SocialChip("▶")
            }

            Spacer(Modifier.height(24.dp))
        }
    }
}

@Composable
private fun SocialChip(label: String) {
    val colors = MaterialTheme.colorScheme
    Surface(
        shape = RoundedCornerShape(10.dp),
        tonalElevation = 2.dp,
        shadowElevation = 6.dp,
        color = colors.surface,
        modifier = Modifier.size(width = 52.dp, height = 46.dp)
    ) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(label, style = MaterialTheme.typography.titleMedium, color = colors.onSurface)
        }
    }
}

@Preview(showBackground = true)
@Composable fun PreviewLoginScreen() {
    GestionDeTiempoTheme {
        LoginScreen()
    }
}