package com.example.di0703_calculadoraconscaffold

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadoraScaffoldApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculadoraScaffoldApp() {
    // Estados de la calculadora
    var numero1 by remember { mutableStateOf("") }
    var numero2 by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("0.0") }

    // --- CONFIGURACIÓN PARA EL SNACKBAR (Paso 4) ---
    // 1. Estado para controlar el Snackbar
    val snackbarHostState = remember { SnackbarHostState() }
    // 2. Scope para lanzar la acción de mostrarlo (es una acción asíncrona)
    val scope = rememberCoroutineScope()

    Scaffold(
        // --- PASO 4 (Host): Le decimos al Scaffold dónde pintar el mensaje ---
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },

        // --- TOP APP BAR (Con acción de Snackbar) ---
        topBar = {
            TopAppBar(
                title = { Text("Calculadora Avanzada") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = Color.White,
                    actionIconContentColor = Color.White
                ),
                actions = {
                    // Acción: Al pulsar el icono de información, sale el Snackbar
                    IconButton(onClick = {
                        scope.launch {
                            snackbarHostState.showSnackbar("Has pulsado Información")
                        }
                    }) {
                        Icon(imageVector = Icons.Default.Info, contentDescription = "Info")
                    }
                }
            )
        },

        // --- PASO 3: BOTTOM APP BAR ---
        bottomBar = {
            BottomAppBar(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
            ) {
                // Texto decorativo en la barra inferior
                Text(
                    text = "Desarrollo de Interfaces",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center
                )
            }
        },

        // --- PASO 5: FLOATING ACTION BUTTON (FAB) ---
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    // Acción del FAB: Calcular (Simulado) o Resetear
                    scope.launch { snackbarHostState.showSnackbar("Acción Principal: Calcular") }
                },
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Calcular", tint = Color.White)
            }
        },
        // Posicionamos el FAB en el CENTRO para que quede sobre la BottomBar
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->

        // CONTENIDO PRINCIPAL
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TextField(
                value = numero1,
                onValueChange = { numero1 = it },
                label = { Text("Número 1") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(10.dp))
            TextField(
                value = numero2,
                onValueChange = { numero2 = it },
                label = { Text("Número 2") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(20.dp))
            Text(text = "Resultado: $resultado", fontSize = 24.sp)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculadoraPreview() {
    CalculadoraScaffoldApp()
}