package com.turis.gestiondetiempo.features.profile

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.turis.gestiondetiempo.ui.AppViewModel
import com.turis.gestiondetiempo.ui.theme.GestionDeTiempoTheme

@Composable
fun ProfileScreen(
    onBack: () -> Unit = {},
    appViewModel: AppViewModel = viewModel()
) {
    ProfileContent(appViewModel = appViewModel)
}

@Composable
private fun ProfileContent(
    modifier: Modifier = Modifier,
    appViewModel: AppViewModel = viewModel()
) {
    val scroll = rememberScrollState()
    val username by appViewModel.username.collectAsState()
    val profilePhotoUri by appViewModel.profilePhotoUri.collectAsState()

    var showEditUsernameDialog by remember { mutableStateOf(false) }
    var showPhotoDialog by remember { mutableStateOf(false) }
    var tempUsername by remember { mutableStateOf("") }
    var tempPhotoUri by remember { mutableStateOf<Uri?>(null) }

    // Launcher para seleccionar de galería
    val galleryLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        tempPhotoUri = uri
    }

    // Diálogo de edición de nombre de usuario
    if (showEditUsernameDialog) {
        AlertDialog(
            onDismissRequest = { showEditUsernameDialog = false },
            title = { Text("Editar nombre de usuario") },
            text = {
                OutlinedTextField(
                    value = tempUsername,
                    onValueChange = { tempUsername = it },
                    placeholder = { Text("Nuevo username") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
            },
            confirmButton = {
                TextButton(onClick = {
                    if (tempUsername.isNotBlank()) {
                        appViewModel.updateUsername(tempUsername)
                    }
                    showEditUsernameDialog = false
                }) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showEditUsernameDialog = false }) {
                    Text("Cancelar")
                }
            }
        )
    }

    // Diálogo de foto de perfil
    if (showPhotoDialog) {
        AlertDialog(
            onDismissRequest = {
                tempPhotoUri = null
                showPhotoDialog = false
            },
            title = { Text("Cargar una nueva foto de perfil") },
            text = {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    // Previsualización circular
                    Box(
                        modifier = Modifier
                            .size(120.dp)
                            .clip(CircleShape)
                            .background(Color(0xFFE6F4F1)),
                        contentAlignment = Alignment.Center
                    ) {
                        if (tempPhotoUri != null) {
                            Image(
                                painter = rememberAsyncImagePainter(tempPhotoUri),
                                contentDescription = "Preview",
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        } else if (profilePhotoUri != null) {
                            Image(
                                painter = rememberAsyncImagePainter(profilePhotoUri),
                                contentDescription = "Current photo",
                                modifier = Modifier
                                    .size(120.dp)
                                    .clip(CircleShape),
                                contentScale = ContentScale.Crop
                            )
                        } else {
                            Box(
                                modifier = Modifier
                                    .size(60.dp)
                                    .clip(CircleShape)
                                    .background(Color(0xFF0F766E))
                            )
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    // Opciones de carga
                    OutlinedButton(
                        onClick = { galleryLauncher.launch("image/*") },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Seleccionar de galería")
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = {
                    if (tempPhotoUri != null) {
                        appViewModel.updateProfilePhoto(tempPhotoUri)
                    }
                    tempPhotoUri = null
                    showPhotoDialog = false
                }) {
                    Text("Guardar")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    tempPhotoUri = null
                    showPhotoDialog = false
                }) {
                    Text("Cancelar")
                }
            }
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(scroll),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.height(24.dp))

        Box(modifier = Modifier.size(132.dp), contentAlignment = Alignment.BottomEnd) {
            // Avatar
            Box(
                modifier = Modifier
                    .size(132.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFE6F4F1)),
                contentAlignment = Alignment.Center
            ) {
                if (profilePhotoUri != null) {
                    Image(
                        painter = rememberAsyncImagePainter(profilePhotoUri),
                        contentDescription = "Profile photo",
                        modifier = Modifier
                            .size(132.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(Color(0xFF0F766E))
                    )
                }
            }


            // Botón editar foto
            Surface(shape = CircleShape, tonalElevation = 3.dp, shadowElevation = 3.dp) {
                IconButton(onClick = {
                    tempPhotoUri = profilePhotoUri
                    showPhotoDialog = true
                }) {
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
                value = username,
                onValueChange = {},
                modifier = Modifier
                    .weight(1f)
                    .heightIn(min = 56.dp),
                singleLine = true,
                readOnly = true
            )
            Spacer(Modifier.width(8.dp))
            Surface(shape = RoundedCornerShape(12.dp), tonalElevation = 2.dp) {
                IconButton(onClick = {
                    tempUsername = username
                    showEditUsernameDialog = true
                }) {
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