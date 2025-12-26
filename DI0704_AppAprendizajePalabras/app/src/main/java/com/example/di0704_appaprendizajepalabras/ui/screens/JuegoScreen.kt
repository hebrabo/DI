package com.example.di0704_appaprendizajepalabras.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.di0704_appaprendizajepalabras.ui.viewmodel.PalabraViewModel

/**
 * PANTALLA DEL MINI JUEGO:
 * Aquí el usuario pone a prueba lo aprendido asociando términos con sus definiciones.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JuegoScreen(viewModel: PalabraViewModel, onBackClick: () -> Unit) {

    /**
     * 1. INICIALIZACIÓN:
     * 'LaunchedEffect' con clave 'Unit' se ejecuta una sola vez cuando se entra en la pantalla.
     * Sirve para pedirle al ViewModel que prepare la primera pregunta (el reto).
     */
    LaunchedEffect(Unit) {
        viewModel.generarNuevoReto()
    }

    // --- ESTADOS DE LA PANTALLA ---
    // Obtenemos el reto actual (palabra + 3 opciones) del ViewModel
    val quiz = viewModel.quizActual.value

    // Guardamos el texto de "¡Correcto!" o "Casi..." para mostrarlo abajo
    var mensajeResultado by remember { mutableStateOf("") }

    // Este booleano es clave: evita que el usuario pulse varios botones a la vez
    // o que sume puntos infinitos pulsando muchas veces la respuesta correcta.
    var respondido by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("¿Cuánto sabes?") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // 'let' comprueba que 'quiz' no sea nulo antes de pintar la UI
            quiz?.let { q ->
                Text(
                    text = "¿Cuál es la definición de:",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.outline
                )

                Text(
                    text = q.palabra.termino,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(32.dp))

                /**
                 * 2. GENERACIÓN DINÁMICA DE BOTONES:
                 * Recorremos la lista de 3 opciones (mezcladas previamente en el ViewModel).
                 */
                q.opciones.forEach { opcion ->
                    val esCorrecta = opcion == q.respuestaCorrecta

                    Button(
                        onClick = {
                            if (!respondido) { // Solo permitimos un intento por reto
                                respondido = true
                                if (esCorrecta) {
                                    mensajeResultado = "¡Correcto! 🎉"
                                    viewModel.registrarAcierto() // Sumamos a las estadísticas
                                } else {
                                    mensajeResultado = "Casi... era otra ❌"
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        // Desactivamos el botón si ya se ha respondido
                        enabled = !respondido,
                        shape = MaterialTheme.shapes.large,
                        colors = ButtonDefaults.buttonColors(
                            /**
                             * LÓGICA DE COLORES POST-RESPUESTA:
                             * Si ya respondió y esta era la correcta, se pone en color Primario (azul/verde).
                             * Si no, se queda en el color secundario estándar.
                             */
                            containerColor = if (respondido && esCorrecta)
                                MaterialTheme.colorScheme.primary
                            else
                                MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = if (respondido && esCorrecta)
                                MaterialTheme.colorScheme.onPrimary
                            else
                                MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    ) {
                        Text(
                            text = opcion,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(vertical = 8.dp)
                        )
                    }
                }

                /**
                 * 3. SECCIÓN DE FEEDBACK:
                 * Solo aparece cuando el usuario ya ha hecho clic en una opción.
                 */
                if (respondido) {
                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        text = mensajeResultado,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (mensajeResultado.contains("Correcto"))
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.error
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Botón para resetear estados y pedir un nuevo reto al ViewModel
                    Button(
                        onClick = {
                            mensajeResultado = ""
                            respondido = false
                            viewModel.generarNuevoReto()
                        },
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
                    ) {
                        Text("Siguiente reto")
                    }
                }
            } ?: run {
                /**
                 * 4. ESTADO DE ESPERA:
                 * Mientras el ViewModel prepara las palabras (o si hay un retraso),
                 * mostramos un círculo de carga para no dejar la pantalla vacía.
                 */
                CircularProgressIndicator()
                Text("Preparando el desafío...", modifier = Modifier.padding(top = 16.dp))
            }
        }
    }
}