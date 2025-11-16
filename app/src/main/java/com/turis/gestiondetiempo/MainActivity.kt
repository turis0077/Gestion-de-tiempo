package com.turis.gestiondetiempo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.turis.gestiondetiempo.nav.navControllers.NavigationController
import com.turis.gestiondetiempo.ui.theme.GestionDeTiempoTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GestionDeTiempoTheme {
                NavigationController()
            }
        }
    }
}
