package com.turis.gestiondetiempo.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.turis.gestiondetiempo.ui.theme.GestionDeTiempoTheme


@Composable
fun PantallaInicial(
    onCrearCuenta: () -> Unit = {},
    onIniciarSesion: () -> Unit = {}
) {
    GestionDeTiempoTheme {
        val colors = MaterialTheme.colorScheme

        Surface(
            modifier = Modifier.fillMaxSize(),
            color = colors.background
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // logo
                Box(
                    modifier = Modifier
                        .size(150.dp)
                        .background(colors.surfaceVariant, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "CRO\nVI",
                        lineHeight = 36.sp,
                        textAlign = TextAlign.Center,
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Black,
                        color = colors.onSurfaceVariant
                    )
                }

                Spacer(Modifier.height(24.dp))

                // Texto de bienvenida
                Text(
                    text = "En Crovi te organizarás como nunca antes",
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    color = colors.onBackground,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )

                Spacer(Modifier.height(28.dp))

                // Botón: Crear cuenta
                BotonLogin(
                    texto = "Crear cuenta",
                    fondo = colors.primaryContainer,
                    contenido = colors.onPrimaryContainer,
                    onClick = onCrearCuenta
                )

                // Botón: Iniciar sesión
                BotonLogin(
                    texto = "Iniciar sesión",
                    fondo = colors.surface,
                    contenido = colors.onSurface,
                    borde = BorderStroke(1.dp, colors.outline),
                    onClick = onIniciarSesion
                )
            }
        }
    }
}

@Composable
fun BotonLogin(
    texto: String,
    fondo: Color,
    contenido: Color,
    borde: BorderStroke? = null,
    onClick: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    Button(
        onClick = onClick,
        shape = RoundedCornerShape(14.dp),
        border = borde,
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = fondo,
            contentColor = contenido,
            disabledContainerColor = colors.surfaceVariant,
            disabledContentColor = colors.onSurfaceVariant
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp)
            .padding(vertical = 8.dp)
    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(texto, fontSize = 18.sp, fontWeight = FontWeight.Bold)
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null
            )
        }
    }
}



@Preview(showBackground = true)
@Composable fun PreviewPantallaInicial() {
    PantallaInicial()
}