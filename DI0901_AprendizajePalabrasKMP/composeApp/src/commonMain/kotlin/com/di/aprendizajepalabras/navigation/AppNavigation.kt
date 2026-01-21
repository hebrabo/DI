package com.di.aprendizajepalabras.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

import com.di.aprendizajepalabras.viewmodel.PalabraViewModel
import com.di.aprendizajepalabras.ui.screens.LoginScreen
import com.di.aprendizajepalabras.ui.screens.RegisterScreen
import com.di.aprendizajepalabras.ui.screens.PantallaPrincipal
import com.di.aprendizajepalabras.ui.screens.SettingsScreen
import com.di.aprendizajepalabras.ui.screens.DiccionarioScreen
import com.di.aprendizajepalabras.ui.screens.JuegoScreen

@Composable
fun AppNavigation(
    palabraViewModel: PalabraViewModel,
    isDarkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "login"
    ) {
        composable("login") {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onNavigateToRegister = { navController.navigate("registro") }
            )
        }

        composable("registro") {
            RegisterScreen(
                onRegisterSuccess = {
                    navController.navigate("home") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                onBackToLogin = { navController.popBackStack() }
            )
        }

        composable("home") {
            PantallaPrincipal(
                viewModel = palabraViewModel,
                onNavigateToSettings = { navController.navigate("ajustes") },
                onNavigateToDictionary = { navController.navigate("diccionario") },
                onNavigateToGame = { navController.navigate("juego") }
            )
        }

        composable("diccionario") {
            DiccionarioScreen(
                viewModel = palabraViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("ajustes") {
            SettingsScreen(
                viewModel = palabraViewModel,
                isDarkMode = isDarkMode,
                onDarkModeChange = onDarkModeChange,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable("juego") {
            JuegoScreen(
                viewModel = palabraViewModel,
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}