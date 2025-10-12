package com.turis.gestiondetiempo

import android.nfc.Tag
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.turis.gestiondetiempo.ui.theme.GestionDeTiempoTheme

class LoggedMenu : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            GestionDeTiempoTheme {
                MainLoggedMenu()
            }
        }
    }
}

@Composable
fun MainLoggedMenu(
    modifier: Modifier = Modifier,
) {
    var text by rememberSaveable { mutableStateOf("")}
    var tagExpanded by remember { mutableStateOf(false) }
    var selectedTag by rememberSaveable { mutableStateOf<Tag?>(null) }

    var showDatePicker by remember { mutableStateOf(false) }
    var selectedDateMillis by rememberSaveable { mutableStateOf<Long?>(null) }

    Column(modifier = modifier.fillMaxSize()) {
        Column() {
            Text(
                text = "¡Hora de Trabajar!",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.displayLarge,
                modifier = Modifier.padding(25.dp)
            )

            ElevatedCard(
                shape = MaterialTheme.shapes.extraLarge,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)

            ) {
                Row() {
                    TextField(
                        label = { Text("Describe tu primera tarea") },
                        value = text,
                        onValueChange = { text = it }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MainLoggedMenuPreview() {
    GestionDeTiempoTheme {
        MainLoggedMenu()
    }
}