package com.codewithfk.arlearner.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.codewithfk.arlearner.ui.navigation.ARScreen
import kotlin.random.Random

/**
 * NumberListScreen: La "estantería" de juegos.
 * Aquí mostramos los 100 números para que el niño elija uno y comience la actividad ABN.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun NumberListScreen(navController: NavController) {
    // Creamos una lista que va del 1 al 100.
    // Usamos 'map' para convertir cada número en un texto (String) para las etiquetas.
    val listOfNumbers = (1..100).map { it.toString() }

    Column {
        // Cabecera de la pantalla con el título "Numbers"
        Box(modifier = Modifier.height(60.dp).fillMaxWidth()) {
            Text(
                text = "Numbers",
                fontSize = 24.sp,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        /**
         * FlowRow: Es un contenedor inteligente.
         * Coloca los números uno al lado del otro y, cuando llega al borde de la pantalla,
         * salta automáticamente a la siguiente línea. Ideal para cuadrículas dinámicas.
         */
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                // Habilitamos el desplazamiento vertical para que el niño pueda bajar hasta el 100
                .verticalScroll(state = rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.Center
        ) {
            // Recorremos cada número de nuestra lista para crear un botón para cada uno
            listOfNumbers.forEach { number ->
                NumberItem(number = number) {
                    // Al pulsar el número, viajamos a la pantalla AR.
                    // Pasamos el número elegido como parámetro 'model'.
                    navController.navigate(ARScreen(model = number))
                }
            }
        }
    }
}

/**
 * NumberItem: Representa el diseño visual de cada botón numérico.
 * @param number El texto que se mostrará (ej: "42").
 * @param onClick La acción que ocurrirá cuando el niño toque el botón.
 */
@Composable
fun NumberItem(number: String, onClick: () -> Unit) {
    // Generamos un color pastel aleatorio para cada número.
    // Usamos 'remember(number)' para que el color no cambie si la pantalla se refresca.
    val color = remember(number) {
        generateRandomLightColor()
    }

    Box(modifier = Modifier
        .padding(16.dp)
        .size(60.dp)
        .clip(RoundedCornerShape(16.dp))
        .background(color)
        .clickable { onClick() }) {
        Text(
            text = number,
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.Center),
            color = Color.Black
        )
    }
}

/**
 * Función didáctica para crear colores claros (pasteles).
 */
fun generateRandomLightColor(): Color {
    val random = Random(System.currentTimeMillis())
    // Pedimos valores de Rojo, Verde y Azul altos (entre 150 y 255) para asegurar tonos claros.
    val red = random.nextInt(150, 256)
    val green = random.nextInt(200, 256)
    val blue = random.nextInt(200, 256)
    return Color(red, green, blue)
}