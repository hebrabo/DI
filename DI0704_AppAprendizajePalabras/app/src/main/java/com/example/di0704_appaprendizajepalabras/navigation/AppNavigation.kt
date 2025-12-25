package com.example.di0704_appaprendizajepalabras.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.di0704_appaprendizajepalabras.ui.screens.*
import com.example.di0704_appaprendizajepalabras.ui.viewmodel.PalabraViewModel

// Definimos los nombres de las pantallas
sealed class Rutas(val ruta: String) {
    object Login : Rutas("login")
    object Registro : Rutas("registro")
    object Home : Rutas("home")
    object Ajustes : Rutas("ajustes")
}

@Composable
fun AppNavigation(
    palabraViewModel: PalabraViewModel,
    isDarkMode: Boolean,           // <--- Nuevo: recibimos el estado
    onDarkModeChange: (Boolean) -> Unit // <--- Nuevo: recibimos la función para cambiarlo
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Rutas.Login.ruta
    ) {
        // ... (Tus bloques de Login y Registro se quedan igual)
        composable(Rutas.Login.ruta) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Rutas.Home.ruta) {
                        popUpTo(Rutas.Login.ruta) { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate(Rutas.Registro.ruta) }
            )
        }

        composable(Rutas.Registro.ruta) {
            RegisterScreen(
                onRegisterSuccess = { navController.navigate(Rutas.Home.ruta) },
                onBackToLogin = { navController.popBackStack() }
            )
        }

        // --- MODIFICACIÓN EN HOME ---
        composable(Rutas.Home.ruta) {
            PantallaPrincipal(
                viewModel = palabraViewModel,
                onNavigateToSettings = { navController.navigate(Rutas.Ajustes.ruta) } // Para que el menú sepa ir a ajustes
            )
        }

        // --- NUEVO BLOQUE DE AJUSTES ---
        composable(Rutas.Ajustes.ruta) {
            SettingsScreen(
                isDarkMode = isDarkMode,
                onDarkModeChange = onDarkModeChange,
                onBackClick = { navController.popBackStack() } // Esto hace que el botón de atrás funcione
            )
        }
    }
}