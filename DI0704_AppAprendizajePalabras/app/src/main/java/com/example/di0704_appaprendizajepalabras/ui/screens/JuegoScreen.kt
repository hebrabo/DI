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
    // Generar el primer reto al entrar
    LaunchedEffect(Unit) { viewModel.generarNuevoReto() }

    val quiz = viewModel.quizActual.value
    var mensajeResultado by remember { mutableStateOf("") }

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
            modifier = Modifier.fillMaxSize().padding(padding).padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            quiz?.let { q ->
                Text("¿Cuál es la definición de:", style = MaterialTheme.typography.titleMedium)
                Text(
                    text = q.palabra.termino,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(30.dp))

                q.opciones.forEach { opcion ->
                    Button(
                        onClick = {
                            mensajeResultado = if (opcion == q.respuestaCorrecta) "¡Correcto! 🎉" else "Casi... era otra ❌"
                        },
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        shape = MaterialTheme.shapes.medium,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer,
                            contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                        )
                    ) {
                        Text(opcion, textAlign = TextAlign.Center)
                    }
                }

                if (mensajeResultado.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(20.dp))
                    Text(mensajeResultado, style = MaterialTheme.typography.headlineSmall)
                    Button(onClick = {
                        mensajeResultado = ""
                        viewModel.generarNuevoReto()
                    }) {
                        Text("Siguiente reto")
                    }
                }
            }
        }
    }
}