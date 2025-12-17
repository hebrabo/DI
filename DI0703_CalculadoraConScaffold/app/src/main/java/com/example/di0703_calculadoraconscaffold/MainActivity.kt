package com.example.di0703_calculadoraconscaffold

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import androidx.compose.material3.FabPosition // Importante para la posición del FAB

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadoraFinalApp()
        }
    }
}

// La anotación @OptIn es necesaria porque algunos componentes de Material3 (como TopAppBar)
// aún se consideran experimentales en la API y podrían cambiar en el futuro.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculadoraFinalApp() {
    // ========================================================================
    // 1. ELEVACIÓN DE ESTADO (STATE HOISTING)
    // ========================================================================
    // Definimos las variables aquí (en el padre) para poder compartirlas entre hijos.
    // Usamos 'rememberSaveable' en lugar de 'remember' para que los datos NO se pierdan
    // al girar la pantalla (cambios de configuración).

    // Datos de la lógica de negocio
    var numero1 by rememberSaveable { mutableStateOf("") }
    var numero2 by rememberSaveable { mutableStateOf("") }
    var resultado by rememberSaveable { mutableStateOf("0.0") }

    // Estados de UI (Configuración visual)
    var opcionBarras by rememberSaveable { mutableIntStateOf(0) } // 0: Ambas, 1: Top, 2: Bottom
    var esFondoOscuro by rememberSaveable { mutableStateOf(false) }

    // Lógica derivada: El color depende del booleano 'esFondoOscuro'
    val colorFondo = if (esFondoOscuro) Color.LightGray else Color.White

    // Estados de navegación y control
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    // 'scope' es necesario para lanzar acciones asíncronas como abrir el Drawer o mostrar Snackbar
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    // Esta variable actúa como nuestro "Router" o gestor de navegación simple
    var pantallaActual by rememberSaveable { mutableStateOf("Presentación") }

    // ========================================================================
    // 2. ESTRUCTURA: DRAWER -> SCAFFOLD -> CONTENIDO
    // ========================================================================
    // El NavigationDrawer debe envolver al Scaffold para que el menú se deslice SOBRE toda la app.
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Menú App", modifier = Modifier.padding(16.dp), fontSize = 24.sp, fontWeight = FontWeight.Bold)
                HorizontalDivider()

                // Ítems de navegación: Al hacer clic, cambiamos la variable 'pantallaActual'
                // y cerramos el menú suavemente usando una corrutina (scope.launch).
                NavigationDrawerItem(
                    label = { Text("Presentación") },
                    selected = pantallaActual == "Presentación",
                    onClick = { pantallaActual = "Presentación"; scope.launch { drawerState.close() } }
                )
                NavigationDrawerItem(
                    label = { Text("Calculadora") },
                    selected = pantallaActual == "Calculadora",
                    onClick = { pantallaActual = "Calculadora"; scope.launch { drawerState.close() } }
                )
                NavigationDrawerItem(
                    label = { Text("Resultado") },
                    selected = pantallaActual == "Resultado",
                    onClick = { pantallaActual = "Resultado"; scope.launch { drawerState.close() } }
                )
                NavigationDrawerItem(
                    label = { Text("Aspecto") },
                    selected = pantallaActual == "Aspecto",
                    onClick = { pantallaActual = "Aspecto"; scope.launch { drawerState.close() } }
                )
            }
        }
    ) {
        // El SCAFFOLD proporciona la estructura estándar de Material Design (TopBar, BottomBar, FAB)
        Scaffold(
            containerColor = colorFondo, // El color cambia dinámicamente según el estado
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) }, // Zona para mensajes emergentes

            // --- TOP BAR ---
            // Renderizado condicional: Solo se dibuja si la opción elegida lo permite
            topBar = {
                if (opcionBarras == 0 || opcionBarras == 1) {
                    TopAppBar(
                        title = { Text(pantallaActual) }, // El título cambia según la pantalla
                        colors = TopAppBarDefaults.topAppBarColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            titleContentColor = Color.White,
                            navigationIconContentColor = Color.White,
                            actionIconContentColor = Color.White
                        ),
                        // Botón Hamburguesa para abrir el menú lateral
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menú")
                            }
                        },
                        // Icono de información a la derecha
                        actions = {
                            IconButton(onClick = { scope.launch { snackbarHostState.showSnackbar("Estás en $pantallaActual") } }) {
                                Icon(Icons.Default.Info, contentDescription = "Info")
                            }
                        }
                    )
                }
            },

            // --- BOTTOM BAR ---
            bottomBar = {
                if (opcionBarras == 0 || opcionBarras == 2) {
                    BottomAppBar(containerColor = MaterialTheme.colorScheme.primaryContainer) {
                        Text("Calculadora y otras cosas", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                    }
                }
            },

            // --- FLOATING ACTION BUTTON (FAB) ---
            floatingActionButton = {
                // Solo mostramos el botón flotante si estamos en la pantalla Calculadora
                if (pantallaActual == "Calculadora") {
                    FloatingActionButton(onClick = { /* Acción futura */ }) { Icon(Icons.Default.Add, "Sumar") }
                }
            },
            // Posicionamiento explícito en el centro de la pantalla
            floatingActionButtonPosition = FabPosition.Center

        ) { paddingValues ->

            // ========================================================================
            // 3. GESTOR DE PANTALLAS (CONTENIDO PRINCIPAL)
            // ========================================================================
            // Usamos un Box con paddingValues para respetar el espacio ocupado por la TopBar y BottomBar
            Box(modifier = Modifier.padding(paddingValues).fillMaxSize().padding(16.dp)) {

                // Switch para decidir qué Composable pintar en el centro de la pantalla
                when (pantallaActual) {
                    "Calculadora" -> PantallaCalculadora(
                        // Pasamos los valores (Lectura)
                        n1 = numero1,
                        n2 = numero2,
                        res = resultado,
                        // Pasamos las funciones para modificar los valores (Escritura / Eventos)
                        // Esto se llama "Unidirectional Data Flow"
                        onN1Change = { numero1 = it },
                        onN2Change = { numero2 = it },
                        onResultadoChange = { resultado = it }
                    )

                    "Resultado" -> PantallaResultado(resultado) // Solo lectura

                    "Aspecto" -> PantallaAspecto(
                        opcionSeleccionada = opcionBarras,
                        onOpcionChange = { opcionBarras = it },
                        esFondoOscuro = esFondoOscuro,
                        onFondoChange = { esFondoOscuro = !esFondoOscuro }
                    )

                    "Presentación" -> PantallaPresentacion() // Pantalla estática
                }
            }
        }
    }
}

// ========================================================================
// 4. COMPONENTES MODULARES (PANTALLAS)
// ========================================================================

@Composable
fun PantallaCalculadora(
    n1: String, n2: String, res: String,
    onN1Change: (String) -> Unit, // Callback para avisar al padre que el texto cambió
    onN2Change: (String) -> Unit,
    onResultadoChange: (String) -> Unit
) {
    // Función auxiliar local para lógica de presentación (formateo de decimales)
    fun formatear(numero: Double): String {
        return if (numero % 1.0 == 0.0) {
            numero.toInt().toString() // Si es entero (8.0), devuelve "8"
        } else {
            "%.2f".format(numero) // Si es decimal, recorta a 2 decimales
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Calculadora", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        // Campo de texto 1
        OutlinedTextField(
            value = n1,
            onValueChange = onN1Change, // Ejecuta la lambda que recibimos del padre
            label = { Text("Número 1") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Campo de texto 2
        OutlinedTextField(
            value = n2, onValueChange = onN2Change,
            label = { Text("Número 2") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Fila de botones 1 (Sumar/Restar)
        // Usamos weight(1f) para que ambos botones ocupen exactamente el 50% del ancho
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = {
                    val r = (n1.toDoubleOrNull() ?: 0.0) + (n2.toDoubleOrNull() ?: 0.0)
                    onResultadoChange(formatear(r))
                },
                modifier = Modifier.weight(1f)
            ) { Text("SUMAR") }

            Button(
                onClick = {
                    val r = (n1.toDoubleOrNull() ?: 0.0) - (n2.toDoubleOrNull() ?: 0.0)
                    onResultadoChange(formatear(r))
                },
                modifier = Modifier.weight(1f)
            ) { Text("RESTAR") }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Fila de botones 2 (Multi/Div)
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = {
                    val r = (n1.toDoubleOrNull() ?: 0.0) * (n2.toDoubleOrNull() ?: 0.0)
                    onResultadoChange(formatear(r))
                },
                modifier = Modifier.weight(1f)
            ) { Text("MULTI") }

            Button(
                onClick = {
                    val v1 = n1.toDoubleOrNull() ?: 0.0
                    val v2 = n2.toDoubleOrNull() ?: 0.0
                    if (v2 != 0.0) {
                        onResultadoChange(formatear(v1 / v2))
                    } else {
                        onResultadoChange("Error /0")
                    }
                },
                modifier = Modifier.weight(1f)
            ) { Text("DIVIDIR") }
        }

        Spacer(modifier = Modifier.height(24.dp))
        Text("Resultado Actual: $res", fontSize = 20.sp, color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
fun PantallaResultado(resultado: String) {
    // Esta pantalla es "Stateless" (sin estado propio), solo muestra lo que recibe
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Último Resultado", fontSize = 22.sp, color = Color.Gray)
        Spacer(modifier = Modifier.height(16.dp))
        Card(
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
        ) {
            Text(
                text = resultado,
                fontSize = 48.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(32.dp),
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun PantallaAspecto(
    opcionSeleccionada: Int,
    onOpcionChange: (Int) -> Unit,
    esFondoOscuro: Boolean,
    onFondoChange: () -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text("Configuración de Aspecto", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(24.dp))

        Text("Visibilidad de Barras:", fontWeight = FontWeight.Bold)

        // Radio Buttons: Al hacer clic, invocan el callback 'onOpcionChange' que actualiza el estado en el padre
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = opcionSeleccionada == 0, onClick = { onOpcionChange(0) })
            Text("Ver Ambas (Top & Bottom)")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = opcionSeleccionada == 1, onClick = { onOpcionChange(1) })
            Text("Solo Top Bar")
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = opcionSeleccionada == 2, onClick = { onOpcionChange(2) })
            Text("Solo Bottom Bar")
        }

        Spacer(modifier = Modifier.height(32.dp))
        HorizontalDivider(thickness = 1.dp, color = Color.Gray)
        Spacer(modifier = Modifier.height(16.dp))

        Text("Color de Fondo del Scaffold:", fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onFondoChange,
            colors = ButtonDefaults.buttonColors(
                // Cambiamos el color del botón según el estado
                containerColor = if (esFondoOscuro) Color.White else Color.Gray,
                // IMPORTANTE: Cambiamos el color del texto para asegurar contraste (Accesibilidad)
                contentColor = if (esFondoOscuro) Color.Black else Color.White
            )
        ) {
            Text(if (esFondoOscuro) "Cambiar a Blanco" else "Cambiar a Gris")
        }
    }
}

@Composable
fun PantallaPresentacion() {
    // Pantalla estática de bienvenida con diseño centrado
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(32.dp).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Logo",
                    modifier = Modifier.size(80.dp),
                    tint = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(24.dp))

                Text(
                    text = "Práctica DI0703",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Desarrollo de Interfaces",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider(thickness = 1.dp, color = Color.Gray)
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Ejercicio de Calculadora Modular\ncon Jetpack Compose",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                    color = Color.Gray
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CalculadoraPreview() {
    CalculadoraFinalApp()
}