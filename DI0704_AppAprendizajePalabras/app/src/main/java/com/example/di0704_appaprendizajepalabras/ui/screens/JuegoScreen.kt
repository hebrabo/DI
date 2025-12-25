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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JuegoScreen(viewModel: PalabraViewModel, onBackClick: () -> Unit) {
    // Generar el primer reto al entrar en la pantalla
    LaunchedEffect(Unit) { viewModel.generarNuevoReto() }

    val quiz = viewModel.quizActual.value
    var mensajeResultado by remember { mutableStateOf("") }
    // Estado para bloquear botones tras responder una vez
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

                // Lista de opciones
                q.opciones.forEach { opcion ->
                    val esCorrecta = opcion == q.respuestaCorrecta

                    Button(
                        onClick = {
                            if (!respondido) { // Solo procesamos si no ha respondido aún
                                respondido = true
                                if (esCorrecta) {
                                    mensajeResultado = "¡Correcto! 🎉"
                                    viewModel.registrarAcierto() // ACTUALIZA EXTRA 4
                                } else {
                                    mensajeResultado = "Casi... era otra ❌"
                                }
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 6.dp),
                        enabled = !respondido, // Desactiva botones tras la respuesta
                        shape = MaterialTheme.shapes.large,
                        colors = ButtonDefaults.buttonColors(
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

                // Sección de feedback y siguiente reto
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
                // Estado de carga si no hay palabras suficientes
                CircularProgressIndicator()
                Text("Preparando el desafío...", modifier = Modifier.padding(top = 16.dp))
            }
        }
    }
}