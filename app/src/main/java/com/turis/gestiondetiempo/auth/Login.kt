package com.turis.gestiondetiempo.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
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
fun loginScreen() {
    //Columna principal
    Column(
        modifier = Modifier
            .padding(25.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    )
    {
        //Logo de la app (Por el momento círculo)
        Box(
            modifier = Modifier
                .size(200.dp)
                .background(
                    color = Color.Red,
                    shape = CircleShape
            )
        )

    }
}

@Preview(showBackground = true)
@Composable fun PreviewLoginScreen() {
    loginScreen()
}