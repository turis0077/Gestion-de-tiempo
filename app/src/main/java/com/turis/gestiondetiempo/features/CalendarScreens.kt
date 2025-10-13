//Pantallas de calendario

package com.turis.gestiondetiempo.features

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

//Pantalla de vista mensual
@Composable
fun CalendarMonthView() {
    Scaffold(
        topBar = {
            CalendarTopBar(
            title = "Calendario"
        )
                 },
        bottomBar = { CalendarBottomBar() }
    ) { padding ->
        Column(
            Modifier
                .padding(padding)
                .padding(12.dp)
                .fillMaxSize()
        ) {
            CalendarCards()

            Row(
                Modifier.fillMaxWidth()
            )
            {
                listOf("D","L","M","M","J","V","S").forEach {
                    Box(
                        Modifier
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    )
                    {
                        Text(
                            text = it,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
            Spacer(Modifier.height(8.dp))

            repeat(5) {
                Row(Modifier.fillMaxWidth().height(96.dp)) {
                    repeat(7) {
                        GridCell(Modifier.weight(1f).fillMaxHeight()) { }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "Mes")
@Composable fun PreviewMonth() {
    CalendarMonthView()
}