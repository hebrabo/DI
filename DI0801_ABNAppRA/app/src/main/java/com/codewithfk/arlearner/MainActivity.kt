package com.codewithfk.arlearner

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute

/**
 * 1. GESTIÓN DE RUTAS Y PANTALLAS:
 * En Android moderno, a veces las clases de navegación y las funciones de pantalla
 * tienen nombres iguales (ej: ARScreen). Usamos 'as' para crear un alias y
 * evitar que el compilador se confunda.
 */
import com.codewithfk.arlearner.ui.navigation.ARScreen as ARRoute
import com.codewithfk.arlearner.ui.navigation.AlphabetScreen as AlphabetRoute
import com.codewithfk.arlearner.ui.navigation.HomeScreen as HomeRoute
import com.codewithfk.arlearner.ui.navigation.QuizScreen as QuizRoute

import com.codewithfk.arlearner.ui.screens.ARScreen
import com.codewithfk.arlearner.ui.screens.NumberListScreen
import com.codewithfk.arlearner.ui.screens.HomeScreen
import com.codewithfk.arlearner.ui.screens.QuizScreen
import com.codewithfk.arlearner.ui.theme.ARLearnerTheme

/**
 * MainActivity: La puerta de entrada física a la aplicación.
 * Es la actividad que Android lanza cuando el niño toca el icono de la app.
 */
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        setContent {
            ARLearnerTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

                    // Creamos el controlador de navegación (el GPS de la aplicación)
                    val navController = rememberNavController()

                    /**
                     * NAVHOST: El mapa de carreteras.
                     * Aquí definimos qué pantalla se debe mostrar según la ruta activa.
                     * startDestination indica que la app siempre comenzará en el menú principal.
                     */
                    NavHost(
                        navController = navController,
                        startDestination = HomeRoute,
                        modifier = Modifier.padding(innerPadding)
                    ) {

                        // DEFINICIÓN DE LOS DESTINOS:

                        // Pantalla de Inicio: El menú con los botones "Numbers" y "Quiz".
                        composable<HomeRoute> {
                            HomeScreen(navController)
                        }

                        /**
                         * Pantalla de Realidad Aumentada:
                         * Es especial porque recibe un parámetro ('model').
                         * Aquí extraemos ese valor para pasárselo a la cámara AR.
                         */
                        composable<ARRoute> {
                            val args = it.toRoute<ARRoute>()
                            ARScreen(navController, args.model)
                        }

                        // Pantalla de Lista: Muestra los 100 números para las actividades ABN.
                        composable<AlphabetRoute> {
                            NumberListScreen(navController)
                        }

                        // Pantalla de Quiz: El juego de preguntas y respuestas.
                        composable<QuizRoute> {
                            QuizScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}