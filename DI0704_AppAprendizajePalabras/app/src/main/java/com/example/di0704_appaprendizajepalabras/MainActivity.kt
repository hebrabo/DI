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

/**
 * MAIN ACTIVITY:
 * Es el punto de inicio de la aplicación en el sistema Android.
 * En las apps modernas de Compose, solo suele haber UNA Activity que actúa
 * como contenedor para todas las pantallas.
 */
class MainActivity : ComponentActivity() {

    /**
     * INICIALIZACIÓN DEL VIEWMODEL:
     * Al usar 'by viewModels()', Android crea el ViewModel la primera vez
     * y lo mantiene vivo mientras la Activity exista.
     * Al crearlo aquí arriba, garantizamos que todas las pantallas (Home, Juego,
     * Ajustes) compartan EXACTAMENTE los mismos datos.
     */
    private val palabraViewModel: PalabraViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Habilita el diseño moderno de borde a borde.
        // Esto permite que el contenido de la app se dibuje por debajo
        // de la barra de estado (la de la batería/reloj).
        enableEdgeToEdge()

        // setContent es el puente entre el código Android clásico y Jetpack Compose.
        setContent {

            /**
             * ESTADO DEL MODO NOCHE:
             * 'rememberSaveable' es un grado más potente que 'remember'.
             * No solo recuerda el valor al redibujar la pantalla, sino que lo
             * guarda incluso si el usuario gira el móvil o el sistema mata
             * la app para ahorrar memoria.
             */
            var isDarkMode by rememberSaveable { mutableStateOf(false) }

            /**
             * EL TEMA DE LA APP:
             * Envolvemos toda la aplicación en el tema que se generó al crear el proyecto.
             * Al pasarle 'darkTheme = isDarkMode', todas las pantallas cambiarán
             * sus colores automáticamente al pulsar el Switch de Ajustes.
             */
            DI0704_AppAprendizajePalabrasTheme(darkTheme = isDarkMode) {

                /**
                 * SURFACE (Lienzo):
                 * Es el componente base que proporciona el color de fondo
                 * adecuado según el tema (Blanco en modo claro, casi Negro en modo oscuro).
                 */
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    /**
                     * NAVEGACIÓN:
                     * Finalmente, cargamos el sistema de rutas y le pasamos
                     * los "mandos" (ViewModel y Modo Oscuro).
                     */
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