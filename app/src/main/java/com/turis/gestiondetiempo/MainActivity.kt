package com.turis.gestiondetiempo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import com.turis.gestiondetiempo.nav.navControllers.LoggedNav
import com.turis.gestiondetiempo.ui.theme.GestionDeTiempoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GestionDeTiempoTheme {
                LoggedNav()
            }
        }
    }
}
