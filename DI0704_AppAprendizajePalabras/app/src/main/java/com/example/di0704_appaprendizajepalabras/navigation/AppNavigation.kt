package com.example.di0704_appaprendizajepalabras.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.di0704_appaprendizajepalabras.ui.screens.*
import com.example.di0704_appaprendizajepalabras.ui.viewmodel.PalabraViewModel

// 1. Definición de rutas centralizada
sealed class Rutas(val ruta: String) {
    object Login : Rutas("login")
    object Registro : Rutas("registro")
    object Home : Rutas("home")
    object Ajustes : Rutas("ajustes")
}

@Composable
fun AppNavigation(
    palabraViewModel: PalabraViewModel, // ViewModel compartido
    isDarkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Rutas.Login.ruta
    ) {
        // --- PANTALLA DE LOGIN ---
        composable(Rutas.Login.ruta) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Rutas.Home.ruta) {
                        // Limpiamos el historial para que no pueda volver al login con el botón atrás
                        popUpTo(Rutas.Login.ruta) { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate(Rutas.Registro.ruta) }
            )
        }

        // --- PANTALLA DE REGISTRO ---
        composable(Rutas.Registro.ruta) {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate(Rutas.Home.ruta) {
                        popUpTo(Rutas.Login.ruta) { inclusive = true }
                    }
                },
                onBackToLogin = { navController.popBackStack() }
            )
        }

        // --- PANTALLA PRINCIPAL (Home) ---
        composable(Rutas.Home.ruta) {
            PantallaPrincipal(
                viewModel = palabraViewModel, // Pasamos el ViewModel compartido
                onNavigateToSettings = { navController.navigate(Rutas.Ajustes.ruta) }
            )
        }

        // --- PANTALLA DE AJUSTES ---
        composable(Rutas.Ajustes.ruta) {
            SettingsScreen(
                viewModel = palabraViewModel, // Pasamos el MISMO ViewModel para que el idioma coincida
                isDarkMode = isDarkMode,
                onDarkModeChange = onDarkModeChange,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}