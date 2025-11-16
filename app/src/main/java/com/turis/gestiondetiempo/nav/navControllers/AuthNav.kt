package com.turis.gestiondetiempo.nav.navControllers

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.turis.gestiondetiempo.auth.CrearCuentaScreen
import com.turis.gestiondetiempo.auth.LoginScreen
import com.turis.gestiondetiempo.auth.PantallaInicial
import com.turis.gestiondetiempo.auth.SignInState
import com.turis.gestiondetiempo.nav.routes.AuthRoutes

@Composable
fun AuthNav(onLoggedIn: () -> Unit) {
    val nav = rememberNavController()

    NavHost(
        navController = nav,
        startDestination = AuthRoutes.Welcome
    ) {
        composable<AuthRoutes.Welcome> {
            PantallaInicial(
                onCrearCuenta = {
                    nav.navigate(AuthRoutes.SignIn)
                },
                onIniciarSesion = {
                    nav.navigate(AuthRoutes.LogIn)
                }
            )
        }

        composable<AuthRoutes.LogIn> {
            LoginScreen(
                onBack = { nav.popBackStack() },
                onLogin = onLoggedIn
            )
        }

        composable<AuthRoutes.SignIn> {
            CrearCuentaScreen(
                state = SignInState(),
                onEvent = { event -> },
                onBack = { nav.popBackStack() }
            )
        }
    }
}