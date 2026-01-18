package com.codewithfk.arlearner.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.codewithfk.arlearner.ui.navigation.AlphabetScreen
import com.codewithfk.arlearner.ui.navigation.QuizScreen

/**
 * HomeScreen: Representa la pantalla de inicio o menú principal.
 * * @param navController Es el objeto que gestiona la navegación. Es como el "GPS"
 * de la app; le decimos a dónde queremos ir y él nos lleva.
 */
@Composable
fun HomeScreen(navController: NavController) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Título principal de la aplicación.
        // Enfocado al método ABN (Aprendizaje Basado en Números)
        Text(
            text = "AR Learner ABN",
            fontSize = 32.sp,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        /**
         * BOTÓN PARA APRENDER NÚMEROS
         * Al pulsar este botón, el GPS (navController) nos lleva a la lista de números.
         * Usamos 'AlphabetScreen' porque es el nombre técnico de la ruta en nuestro NavRoutes.
         */
        Button(
            onClick = { navController.navigate(AlphabetScreen) },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Aprender Números")
        }

        /**
         * BOTÓN PARA EL MODO QUIZ (JUEGO)
         * Este botón lleva al niño a la actividad de preguntas y respuestas en Realidad Aumentada.
         */
        Button(
            onClick = { navController.navigate(QuizScreen) },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "Jugar al Quiz")
        }
    }
}