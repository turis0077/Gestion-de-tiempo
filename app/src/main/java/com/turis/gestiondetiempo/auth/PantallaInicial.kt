package com.turis.gestiondetiempo.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun pantallaInicial() {
    //Columna principal
    Column(
        modifier = Modifier
            .padding(25.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        //Logo de la app
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(
                    color = Color.Red,
                    shape = CircleShape
                )
        )
        //Texto de bienvenida
        Text(
            text = "En Crovi te organizarás como nunca antes",
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            color = Color.Black,
            textAlign = androidx.compose.ui.text.style.TextAlign.Center
        )
        //Botón de crear cuenta
        BotonLogin(
            texto = "Crear Cuenta",
            onClick = { /* Crear cuenta */ }
        )
        //Botón para iniciar sesión
        BotonLogin(
            texto = "Iniciar Sesión",
            onClick = { /* Iniciar sesión */ }
        )
    }
}

@Composable
fun BotonLogin(
    texto: String,
    onClick: () -> Unit
){
    //Botón de crear cuenta
    Button(
        onClick = onClick,
        modifier = Modifier.padding(top = 20.dp)
    )
    {
        Row(
            modifier = Modifier.padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        )
        {
            Text(
                text = texto,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                color = Color.White
            )
            Icon(
                Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = null,
                tint = Color.White
            )
        }
    }
}



@Preview(showBackground = true)
@Composable fun PreviewPantallaInicial() {
    pantallaInicial()
}