package com.example.di0704_appaprendizajepalabras.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.di0704_appaprendizajepalabras.ui.screens.*
import com.example.di0704_appaprendizajepalabras.ui.viewmodel.PalabraViewModel

/**
 * 1. DEFINICIÓN DE RUTAS CENTRALIZADA:
 * Usamos una 'sealed class' (clase sellada) para evitar errores de escritura.
 * En lugar de escribir "home" manualmente en cada botón, usamos Rutas.Home.ruta.
 * Esto hace que si algún día cambiamos el nombre de una ruta, solo lo hagamos aquí.
 */
sealed class Rutas(val ruta: String) {
    object Login : Rutas("login")
    object Registro : Rutas("registro")
    object Home : Rutas("home")
    object Ajustes : Rutas("ajustes")
    object Diccionario : Rutas("diccionario") // Pantalla de Progreso (Extra 1 y 4)
    object Juego : Rutas("juego")             // Mini Juego (Extra 3)
}

/**
 * 2. COMPOSABLE DE NAVEGACIÓN PRINCIPAL:
 * Aquí configuramos el NavHost, que es el "contenedor" donde irán apareciendo las pantallas.
 */
@Composable
fun AppNavigation(
    palabraViewModel: PalabraViewModel, // Inyectamos el ViewModel para compartir datos entre pantallas
    isDarkMode: Boolean,                // Estado del modo oscuro
    onDarkModeChange: (Boolean) -> Unit // Función para cambiar el modo oscuro
) {
    // El navController es el "conductor" que ejecuta las órdenes de navegación
    val navController = rememberNavController()

    // El NavHost define el mapa de navegación y la pantalla inicial (startDestination)
    NavHost(
        navController = navController,
        startDestination = Rutas.Login.ruta
    ) {

        // --- RUTA: LOGIN ---
        composable(Rutas.Login.ruta) {
            LoginScreen(
                onLoginSuccess = {
                    // Al entrar con éxito, navegamos al Home y BORRAMOS el Login del historial
                    // para que el usuario no pueda volver atrás al login pulsando el botón físico
                    navController.navigate(Rutas.Home.ruta) {
                        popUpTo(Rutas.Login.ruta) { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate(Rutas.Registro.ruta) }
            )
        }

        // --- RUTA: REGISTRO ---
        composable(Rutas.Registro.ruta) {
            RegisterScreen(
                onRegisterSuccess = {
                    // Igual que en Login, tras registrarse vamos al Home y limpiamos el historial
                    navController.navigate(Rutas.Home.ruta) {
                        popUpTo(Rutas.Login.ruta) { inclusive = true }
                    }
                },
                onBackToLogin = { navController.popBackStack() } // Simplemente vuelve atrás
            )
        }

        // --- RUTA: PANTALLA PRINCIPAL (Home) ---
        composable(Rutas.Home.ruta) {
            PantallaPrincipal(
                viewModel = palabraViewModel,
                onNavigateToSettings = { navController.navigate(Rutas.Ajustes.ruta) },
                onNavigateToDictionary = { navController.navigate(Rutas.Diccionario.ruta) },
                onNavigateToGame = { navController.navigate(Rutas.Juego.ruta) }
            )
        }

        // --- RUTA: AJUSTES ---
        composable(Rutas.Ajustes.ruta) {
            SettingsScreen(
                viewModel = palabraViewModel,
                isDarkMode = isDarkMode,
                onDarkModeChange = onDarkModeChange,
                // popBackStack() quita la pantalla actual y muestra la anterior en la pila
                onBackClick = { navController.popBackStack() }
            )
        }

        // --- RUTA: DICCIONARIO / PROGRESO (Extra 1 y 4) ---
        composable(Rutas.Diccionario.ruta) {
            DiccionarioScreen(
                viewModel = palabraViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }

        // --- RUTA: MINI JUEGO (Extra 3) ---
        composable(Rutas.Juego.ruta) {
            JuegoScreen(
                viewModel = palabraViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}