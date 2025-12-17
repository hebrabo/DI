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
import androidx.compose.material3.FabPosition

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadoraFinalApp()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculadoraFinalApp() {
    // --- 1. ESTADOS GLOBALES (Elevados) ---
    // Datos de la calculadora
    var numero1 by rememberSaveable { mutableStateOf("") }
    var numero2 by rememberSaveable { mutableStateOf("") }
    var resultado by rememberSaveable { mutableStateOf("0.0") }

    // Estados de Aspecto (Configuración visual)
    // Opción seleccionada: 0 = Ambas, 1 = Solo Top, 2 = Solo Bottom
    var opcionBarras by rememberSaveable { mutableIntStateOf(0) }
    // Color de fondo del Scaffold
    var esFondoOscuro by rememberSaveable { mutableStateOf(false) }
    val colorFondo = if (esFondoOscuro) Color.LightGray else Color.White

    // Navegación
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    var pantallaActual by rememberSaveable { mutableStateOf("Presentación") }

    // --- 2. ESTRUCTURA (Drawer + Scaffold) ---
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Menú App", modifier = Modifier.padding(16.dp), fontSize = 24.sp, fontWeight = FontWeight.Bold)
                HorizontalDivider()
                // Ítems de navegación
                NavigationDrawerItem(label = { Text("Presentación") }, selected = pantallaActual == "Presentación", onClick = { pantallaActual = "Presentación"; scope.launch { drawerState.close() } })
                NavigationDrawerItem(label = { Text("Calculadora") }, selected = pantallaActual == "Calculadora", onClick = { pantallaActual = "Calculadora"; scope.launch { drawerState.close() } })
                NavigationDrawerItem(label = { Text("Resultado") }, selected = pantallaActual == "Resultado", onClick = { pantallaActual = "Resultado"; scope.launch { drawerState.close() } })
                NavigationDrawerItem(label = { Text("Aspecto") }, selected = pantallaActual == "Aspecto", onClick = { pantallaActual = "Aspecto"; scope.launch { drawerState.close() } })
            }
        }
    ) {
        Scaffold(
            containerColor = colorFondo, // Aplicamos el cambio de color aquí
            snackbarHost = { SnackbarHost(hostState = snackbarHostState) },

            // Lógica para mostrar/ocultar TopBar según lo elegido en "Aspecto"
            topBar = {
                if (opcionBarras == 0 || opcionBarras == 1) {
                    TopAppBar(
                        title = { Text(pantallaActual) },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary, titleContentColor = Color.White, navigationIconContentColor = Color.White, actionIconContentColor = Color.White),
                        navigationIcon = {
                            IconButton(onClick = { scope.launch { drawerState.open() } }) {
                                Icon(Icons.Default.Menu, contentDescription = "Menú")
                            }
                        },
                        actions = {
                            IconButton(onClick = { scope.launch { snackbarHostState.showSnackbar("Estás en $pantallaActual") } }) {
                                Icon(Icons.Default.Info, contentDescription = "Info")
                            }
                        }
                    )
                }
            },

            // Lógica para mostrar/ocultar BottomBar
            bottomBar = {
                if (opcionBarras == 0 || opcionBarras == 2) {
                    BottomAppBar(containerColor = MaterialTheme.colorScheme.primaryContainer) {
                        Text("Calculadora y otras cosas", modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Center)
                    }
                }
            },

            // FAB solo en calculadora
            floatingActionButton = {
                if (pantallaActual == "Calculadora") {
                    FloatingActionButton(onClick = { /* Acción extra */ }) { Icon(Icons.Default.Add, "Sumar") }
                }
            },
            floatingActionButtonPosition = FabPosition.Center

        ) { paddingValues ->

            // --- 3. GESTIÓN DE PANTALLAS ---
            Box(modifier = Modifier.padding(paddingValues).fillMaxSize().padding(16.dp)) {
                when (pantallaActual) {
                    "Calculadora" -> PantallaCalculadora(
                        n1 = numero1,
                        n2 = numero2,
                        res = resultado,
                        onN1Change = { numero1 = it },
                        onN2Change = { numero2 = it },
                        onResultadoChange = { resultado = it }
                    )

                    "Resultado" -> PantallaResultado(resultado)

                    "Aspecto" -> PantallaAspecto(
                        opcionSeleccionada = opcionBarras,
                        onOpcionChange = { opcionBarras = it },
                        esFondoOscuro = esFondoOscuro,
                        onFondoChange = { esFondoOscuro = !esFondoOscuro }
                    )

                    "Presentación" -> PantallaPresentacion()
                }
            }
        }
    }
}

// --- 4. COMPONENTES DE PANTALLA (MODULARIDAD) ---

@Composable
fun PantallaCalculadora(
    n1: String, n2: String, res: String,
    onN1Change: (String) -> Unit,
    onN2Change: (String) -> Unit,
    onResultadoChange: (String) -> Unit
) {
    // FUNCIÓN AUXILIAR: Redondea bonito
    fun formatear(numero: Double): String {
        // Si el número es entero (ej: 8.0), lo convertimos a Int para quitar el .0
        return if (numero % 1.0 == 0.0) {
            numero.toInt().toString()
        } else {
            // Si tiene decimales, mostramos solo 2
            "%.2f".format(numero)
        }
    }

    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Calculadora", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(16.dp))

        // Inputs
        OutlinedTextField(
            value = n1, onValueChange = onN1Change,
            label = { Text("Número 1") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = n2, onValueChange = onN2Change,
            label = { Text("Número 2") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        // FILA 1
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Button(
                onClick = {
                    val r = (n1.toDoubleOrNull() ?: 0.0) + (n2.toDoubleOrNull() ?: 0.0)
                    onResultadoChange(formatear(r)) // Usamos la función formatear
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

        // FILA 2
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
    // Paso 9: Pantalla que solo muestra el último resultado
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

        // Radio Button 1
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = opcionSeleccionada == 0, onClick = { onOpcionChange(0) })
            Text("Ver Ambas (Top & Bottom)")
        }
        // Radio Button 2
        Row(verticalAlignment = Alignment.CenterVertically) {
            RadioButton(selected = opcionSeleccionada == 1, onClick = { onOpcionChange(1) })
            Text("Solo Top Bar")
        }
        // Radio Button 3
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
                // 1. Color del botón (Caja)
                containerColor = if (esFondoOscuro) Color.White else Color.Gray,

                // 2. Color del texto/contenido
                contentColor = if (esFondoOscuro) Color.Black else Color.White
            )
        ) {
            Text(if (esFondoOscuro) "Cambiar a Blanco" else "Cambiar a Gris")
        }
    }
}

@Composable
fun PantallaPresentacion() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tarjeta decorativa
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
            modifier = Modifier.fillMaxWidth().padding(16.dp)
        ) {
            Column(
                modifier = Modifier
                    .padding(32.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Icono decorativo (puedes cambiarlo por otro)
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Logo",
                    modifier = Modifier.size(80.dp),
                    tint = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(24.dp))

                // Título Grande
                Text(
                    text = "Práctica DI0703",
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Subtítulo
                Text(
                    text = "Desarrollo de Interfaces",
                    fontSize = 18.sp,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.primary
                )

                Spacer(modifier = Modifier.height(24.dp))
                HorizontalDivider(thickness = 1.dp, color = Color.Gray)
                Spacer(modifier = Modifier.height(16.dp))

                // Descripción o Nombre del alumno
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