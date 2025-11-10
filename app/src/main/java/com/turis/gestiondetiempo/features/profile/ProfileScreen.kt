package com.turis.gestiondetiempo.features.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.turis.gestiondetiempo.ui.theme.GestionDeTiempoTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(onBack: () -> Unit = {}) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Datos de Usuario") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.ArrowBack,
                            contentDescription = "Atrás"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        ProfileContent(Modifier.padding(innerPadding))
    }
}

@Composable
private fun ProfileContent(modifier: Modifier = Modifier) {
    val scroll = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(scroll),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(16.dp))

        Box(modifier = Modifier.size(132.dp), contentAlignment = Alignment.BottomEnd) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(132.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE6F4F1)),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier
                        .size(64.dp)
                        .clip(CircleShape)
                        .background(Color(0xFF0F766E))
                )
            }
            // Botón editar foto
            Surface(shape = CircleShape, tonalElevation = 3.dp, shadowElevation = 3.dp) {
                IconButton(onClick = { }) {
                    Icon(Icons.Outlined.Edit, contentDescription = "Editar foto")
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = "Nombre de usuario:",
                style = MaterialTheme.typography.bodyMedium
            )
        }
        Spacer(Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            OutlinedTextField(
                value = "User1259",
                onValueChange = {},
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 56.dp),
                singleLine = true,
                readOnly = true
            )
            Spacer(Modifier.width(8.dp))
            Surface(shape = RoundedCornerShape(12.dp), tonalElevation = 2.dp) {
                IconButton(onClick = { }) {
                    Icon(Icons.Outlined.Edit, contentDescription = "Editar nombre")
                }
            }
        }

        Spacer(Modifier.height(24.dp))

        SectionTitle("Logros:")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            repeat(3) {
                Surface(
                    shape = CircleShape,
                    tonalElevation = 1.dp,
                    modifier = Modifier.size(52.dp)
                ) {}
            }
        }

        Spacer(Modifier.height(16.dp))

        SectionTitle("Amigos:")
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            repeat(4) {
                Surface(shape = CircleShape, tonalElevation = 1.dp, modifier = Modifier.size(44.dp)) {}
            }
        }

        Spacer(Modifier.height(24.dp))
    }
}

@Composable
private fun SectionTitle(text: String) {
    Text(text = text, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.fillMaxWidth())
    Spacer(Modifier.height(8.dp))
}

@Preview(showBackground = true)
@Composable
private fun ProfilePreview() {
    GestionDeTiempoTheme { ProfileScreen() }
}
