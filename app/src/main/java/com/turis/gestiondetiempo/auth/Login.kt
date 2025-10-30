package com.turis.gestiondetiempo.auth

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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen() {
    val colors = MaterialTheme.colorScheme

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Inicio de sesión") },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = colors.surface
                )
            )
        },
        containerColor = colors.background
    ) { inner ->
        Column(
            modifier = Modifier
                .padding(inner)
                .fillMaxSize()
                .padding(horizontal = 24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(24.dp))

            // Logo circular
            Box(
                modifier = Modifier
                    .size(140.dp)
                    .background(colors.surfaceVariant, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    "CRO\nVI",
                    textAlign = TextAlign.Center,
                    lineHeight = 36.sp,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Black,
                    color = colors.onSurfaceVariant
                )
            }

            Spacer(Modifier.height(24.dp))

            // Usuario
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Nombre de usuario") },
                singleLine = true,
                readOnly = true,
                shape = RoundedCornerShape(12.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = colors.outline,
                    unfocusedBorderColor = colors.outline.copy(alpha = 0.6f),
                    focusedContainerColor = colors.surface,
                    unfocusedContainerColor = colors.surface,
                    disabledPlaceholderColor = colors.onSurface.copy(alpha = 0.5f)
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = 56.dp)
            )

            Spacer(Modifier.height(12.dp))

            // Contraseña
            OutlinedTextField(
                value = "",
                onValueChange = {},
                placeholder = { Text("Contraseña") },
                singleLine = true,
                readOnly = true,
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
                onClick = {},
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
    LoginScreen()
}