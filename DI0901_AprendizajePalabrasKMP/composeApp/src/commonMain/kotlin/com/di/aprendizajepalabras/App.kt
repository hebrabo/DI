package com.di.aprendizajepalabras

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.di.aprendizajepalabras.navigation.AppNavigation
import com.di.aprendizajepalabras.viewmodel.PalabraViewModel

@Composable
@Preview
fun App() {
    // 1. Este estado controla si el modo oscuro está activo
    var darkTheme by remember { mutableStateOf(false) }

    // 2. Definimos las paletas de colores (puedes personalizarlas luego)
    val colores = if (darkTheme) {
        darkColorScheme() // Paleta oscura automática de Material 3
    } else {
        lightColorScheme() // Paleta clara automática
    }

    // 3. Pasamos la paleta elegida al tema
    MaterialTheme(colorScheme = colores) {
        val miViewModel: PalabraViewModel = viewModel { PalabraViewModel() }

        AppNavigation(
            palabraViewModel = miViewModel,
            isDarkMode = darkTheme,
            onDarkModeChange = { darkTheme = it } // Esta función actualiza el estado
        )
    }
}