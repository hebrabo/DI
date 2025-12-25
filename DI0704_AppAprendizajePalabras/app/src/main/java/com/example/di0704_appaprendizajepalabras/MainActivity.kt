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
import androidx.compose.ui.Modifier
import com.example.di0704_appaprendizajepalabras.navigation.AppNavigation
import com.example.di0704_appaprendizajepalabras.ui.theme.DI0704_AppAprendizajePalabrasTheme
import com.example.di0704_appaprendizajepalabras.ui.viewmodel.PalabraViewModel

class MainActivity : ComponentActivity() {

    // 1. Inicializamos el ViewModel.
    // Al hacerlo aquí, los datos sobrevivirán a rotaciones de pantalla.
    private val palabraViewModel: PalabraViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Habilita que la app use toda la pantalla (opcional pero recomendado)
        enableEdgeToEdge()

        setContent {
            // 2. Estado del Modo Noche (Personalización)
            // Lo declaramos aquí porque es el nivel más alto: si cambia aquí, cambia en toda la app.
            var isDarkMode by remember { mutableStateOf(false) }

            // 3. Aplicamos el Tema de la aplicación
            // El parámetro 'darkTheme' decide si usar la paleta de colores clara u oscura.
            DI0704_AppAprendizajePalabrasTheme(darkTheme = isDarkMode) {

                // Un contenedor Surface que usa el color de fondo del tema
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // 4. Lanzamos el sistema de Navegación
                    // Le pasamos el ViewModel y las funciones para controlar el modo noche.
                    AppNavigation(
                        palabraViewModel = palabraViewModel,
                        isDarkMode = isDarkMode,
                        onDarkModeChange = { nuevoValor -> isDarkMode = nuevoValor }
                    )
                }
            }
        }
    }
}