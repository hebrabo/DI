package com.example.di0703_calculadoraconscaffold

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
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
import androidx.compose.runtime.saveable.rememberSaveable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadoraDrawerApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculadoraDrawerApp() {
    // ESTADOS DE LA CALCULADORA
    var numero1 by rememberSaveable { mutableStateOf("") }
    var numero2 by rememberSaveable { mutableStateOf("") }
    var resultado by rememberSaveable { mutableStateOf("0.0") }

    // ESTADOS DE NAVEGACIÓN Y DRAWER
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Variable para saber qué pantalla mostrar
    // Opciones: "Presentación", "Calculadora", "Resultado", "Aspecto"
    var pantallaActual by rememberSaveable { mutableStateOf("Calculadora") }

    // 1. EL DRAWER ENVUELVE AL SCAFFOLD
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Menú Principal", modifier = Modifier.padding(16.dp), fontSize = 20.sp)
                HorizontalDivider()

                // Ítems del menú (Punto 7 del ejercicio)
                NavigationDrawerItem(
                    label = { Text("Presentación") },
                    selected = pantallaActual == "Presentación",
                    onClick = {
                        pantallaActual = "Presentación"
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Calculadora") },
                    selected = pantallaActual == "Calculadora",
                    onClick = {
                        pantallaActual = "Calculadora"
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Resultado") },
                    selected = pantallaActual == "Resultado",
                    onClick = {
                        pantallaActual = "Resultado"
                        scope.launch { drawerState.close() }
                    }
                )
                NavigationDrawerItem(
                    label = { Text("Aspecto") },
                    selected = pantallaActual == "Aspecto",
                    onClick = {
                        pantallaActual = "Aspecto"
                        scope.launch { drawerState.close() }
                    }
                )
            }
        }
    ) {
        // 2. EL SCAFFOLD VA DENTRO DEL DRAWER
        Scaffold(
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
            topBar = {
                TopAppBar(
                    title = { Text(pantallaActual) }, // El título cambia según la pantalla
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = Color.White,
                        navigationIconContentColor = Color.White,
                        actionIconContentColor = Color.White
                    ),
                    // NUEVO: Icono de Hamburguesa para abrir el menú
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(imageVector = Icons.Default.Menu, contentDescription = "Menú")
                        }
                    },
                    actions = {
                        IconButton(onClick = { scope.launch { snackbarHostState.showSnackbar("Info") } }) {
                            Icon(imageVector = Icons.Default.Info, contentDescription = "Info")
                        }
                    }
                )
            },
            bottomBar = {
                BottomAppBar(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                ) {
                    Text("DI0703 - Android Compose", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                }
            },
            floatingActionButton = {
                // Solo mostramos el botón de calcular si estamos en la pantalla Calculadora
                if (pantallaActual == "Calculadora") {
                    FloatingActionButton(
                        onClick = { scope.launch { snackbarHostState.showSnackbar("Calculando...") } },
                        containerColor = MaterialTheme.colorScheme.primary
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "Calcular", tint = Color.White)
                    }
                }
            },
            floatingActionButtonPosition = FabPosition.Center
        ) { paddingValues ->

            // 3. GESTIÓN DE PANTALLAS (CAMBIO DE CONTENIDO)
            Box(modifier = Modifier.padding(paddingValues).fillMaxSize().padding(16.dp)) {
                when (pantallaActual) {
                    "Calculadora" -> {
                        // El código de nuestra calculadora
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            TextField(
                                value = numero1, onValueChange = { numero1 = it },
                                label = { Text("Número 1") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            TextField(
                                value = numero2, onValueChange = { numero2 = it },
                                label = { Text("Número 2") }, keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                modifier = Modifier.fillMaxWidth()
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            Text("Resultado actual: $resultado", fontSize = 20.sp)
                        }
                    }
                    "Presentación" -> Text("Pantalla de Presentación (Pendiente)", fontSize = 24.sp)
                    "Resultado" -> Text("Pantalla de Resultado Global (Pendiente)", fontSize = 24.sp)
                    "Aspecto" -> Text("Configuración de Aspecto (Pendiente)", fontSize = 24.sp)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculadoraPreview() {
    CalculadoraDrawerApp()
}