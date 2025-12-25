package com.example.di0704_appaprendizajepalabras

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import com.example.di0704_appaprendizajepalabras.navigation.AppNavigation
import com.example.di0704_appaprendizajepalabras.ui.theme.DI0704_AppAprendizajePalabrasTheme
import com.example.di0704_appaprendizajepalabras.ui.viewmodel.PalabraViewModel

class MainActivity : ComponentActivity() {

    // 1. Inicializamos el ViewModel a nivel de Activity.
    // Esto garantiza que PantallaPrincipal y SettingsScreen compartan los mismos datos.
    private val palabraViewModel: PalabraViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Habilita el diseño de borde a borde (barra de estado transparente)
        enableEdgeToEdge()

        setContent {
            // 2. Estado del Modo Noche.
            // Usamos rememberSaveable para que si el usuario gira el móvil,
            // no se pierda la elección del modo oscuro.
            var isDarkMode by rememberSaveable { mutableStateOf(false) }

            // 3. Aplicamos el Tema dinámico
            DI0704_AppAprendizajePalabrasTheme(darkTheme = isDarkMode) {

                // Surface es el "lienzo" sobre el que se dibuja la app.
                // Al ponerlo aquí, el color de fondo cambiará automáticamente entre claro/oscuro.
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 4. Inyectamos la navegación
                    AppNavigation(
                        palabraViewModel = palabraViewModel,
                        isDarkMode = isDarkMode,
                        onDarkModeChange = { isDarkMode = it }
                    )
                }
            }
        }
    }
}