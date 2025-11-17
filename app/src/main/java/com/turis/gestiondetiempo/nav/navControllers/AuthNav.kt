package com.turis.gestiondetiempo.nav.navControllers

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.turis.gestiondetiempo.auth.CrearCuentaRoute
import com.turis.gestiondetiempo.auth.LoginScreen
import com.turis.gestiondetiempo.auth.PantallaInicial
import com.turis.gestiondetiempo.nav.routes.AuthRoutes
import com.turis.gestiondetiempo.ui.AppViewModel

@Composable
fun AuthNav(
    onLoggedIn: () -> Unit,
    appViewModel: AppViewModel
) {
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
                onLogin = onLoggedIn,
                appViewModel = appViewModel
            )
        }

        composable<AuthRoutes.SignIn> {
            CrearCuentaRoute(
                onBack = { nav.popBackStack() },
                onSuccess = { nav.popBackStack() }
            )
        }
    }
}