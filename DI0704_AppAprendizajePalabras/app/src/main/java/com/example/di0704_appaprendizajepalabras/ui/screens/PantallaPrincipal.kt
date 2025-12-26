package com.example.di0704_appaprendizajepalabras.ui.screens

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assessment
import androidx.compose.material.icons.filled.Gamepad
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Translate
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.di0704_appaprendizajepalabras.data.model.Palabra
import com.example.di0704_appaprendizajepalabras.ui.viewmodel.PalabraViewModel
import kotlinx.coroutines.launch
import kotlin.math.abs

/**
 * PANTALLA PRINCIPAL:
 * Es el tablero central de la app. Gestiona el menú lateral, el sensor de agitado
 * y muestra la palabra actual.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipal(
    viewModel: PalabraViewModel,
    onNavigateToSettings: () -> Unit,
    onNavigateToDictionary: () -> Unit,
    onNavigateToGame: () -> Unit
) {
    // ESTADOS DE UI
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed) // Estado del menú (abierto/cerrado)
    val scope = rememberCoroutineScope() // Necesario para lanzar animaciones (como abrir el menú)
    val context = LocalContext.current // Acceso al contexto de Android para usar los sensores
    val palabra = viewModel.palabraActual.value // Palabra que estamos mostrando actualmente

    /**
     * EXTRA 5: SENSOR DE AGITADO (SHAKE)
     * 'DisposableEffect' es perfecto para sensores. Cuando entras en la pantalla,
     * activa el acelerómetro. Cuando sales de la pantalla (onDispose), lo apaga
     * automáticamente para no gastar batería.
     */
    DisposableEffect(Unit) {
        val sensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        val sensorListener = object : SensorEventListener {
            private var lastUpdate: Long = 0
            private var lastX = 0f; private var lastY = 0f; private var lastZ = 0f
            private val SHAKE_THRESHOLD = 800 // Sensibilidad del agitado

            override fun onSensorChanged(event: SensorEvent) {
                val curTime = System.currentTimeMillis()
                // Solo comprobamos el movimiento cada 100 milisegundos para ahorrar procesador
                if ((curTime - lastUpdate) > 100) {
                    val diffTime = curTime - lastUpdate
                    lastUpdate = curTime

                    val x = event.values[0]
                    val y = event.values[1]
                    val z = event.values[2]

                    // Cálculo matemático para detectar un movimiento brusco (agitado)
                    val speed = abs(x + y + z - lastX - lastY - lastZ) / diffTime * 10000

                    if (speed > SHAKE_THRESHOLD) {
                        viewModel.siguientePalabra() // ¡Agitado detectado! Pedimos otra palabra
                    }
                    lastX = x; lastY = y; lastZ = z
                }
            }
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}
        }

        // Registramos el "escuchador" del sensor
        sensorManager.registerListener(sensorListener, accelerometer, SensorManager.SENSOR_DELAY_NORMAL)

        onDispose {
            // MUY IMPORTANTE: Quitamos el listener al salir para evitar fugas de memoria
            sensorManager.unregisterListener(sensorListener)
        }
    }

    /**
     * ESTRUCTURA DE NAVEGACIÓN LATERAL (DRAWER)
     * El 'ModalNavigationDrawer' envuelve a toda la pantalla para permitir el menú deslizante.
     */
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    "Menú de Aprendizaje",
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 10.dp),
                    style = MaterialTheme.typography.titleLarge
                )
                HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

                // BOTÓN: INICIO
                NavigationDrawerItem(
                    label = { Text("Inicio") },
                    icon = { Icon(Icons.Default.Home, null) },
                    selected = true,
                    onClick = { scope.launch { drawerState.close() } },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                // BOTÓN: MI PROGRESO (Extra 1 y 4)
                NavigationDrawerItem(
                    label = { Text("Mi Progreso") },
                    icon = { Icon(Icons.Default.Assessment, null) },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() } // Cerramos el menú antes de irnos
                        onNavigateToDictionary()
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                // BOTÓN: MINI JUEGO (Extra 3)
                NavigationDrawerItem(
                    label = { Text("Mini Juego") },
                    selected = false,
                    icon = { Icon(Icons.Default.Gamepad, null) },
                    onClick = {
                        scope.launch { drawerState.close() }
                        onNavigateToGame()
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                // BOTÓN: CONFIGURACIÓN
                NavigationDrawerItem(
                    label = { Text("Configuración") },
                    icon = { Icon(Icons.Default.Settings, null) },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        onNavigateToSettings()
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        }
    ) {
        // CONTENIDO VISIBLE DE LA PANTALLA
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    title = { Text("Diccionario Diario") },
                    navigationIcon = {
                        IconButton(onClick = { scope.launch { drawerState.open() } }) {
                            Icon(Icons.Default.Menu, contentDescription = "Abrir Menú")
                        }
                    }
                )
            }
        ) { paddingValues ->
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                // Llamamos al componente que dibuja la palabra actual
                ContenidoPalabra(
                    palabra = palabra,
                    idioma = viewModel.idiomaActual,
                    contador = viewModel.palabrasVistas,
                    onNextClick = { viewModel.siguientePalabra() }
                )
            }
        }
    }
}

/**
 * COMPONENTE: ContenidoPalabra
 * Se encarga exclusivamente de la parte visual de la palabra: imagen, término y definición.
 */
@Composable
fun ContenidoPalabra(palabra: Palabra, idioma: String, contador: Int, onNextClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // CHIPS SUPERIORES
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SuggestionChip(
                onClick = { },
                label = { Text("Practicando: $idioma") },
                icon = { Icon(Icons.Default.Translate, null, modifier = Modifier.size(16.dp)) }
            )
            // Muestra cuántas palabras llevamos en la sesión
            Badge(containerColor = MaterialTheme.colorScheme.secondaryContainer) {
                Text("Vistas: $contador", modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // TARJETA DE LA PALABRA
        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
                // Coil carga la imagen de la palabra
                AsyncImage(
                    model = palabra.imagenUrl,
                    contentDescription = null,
                    modifier = Modifier.fillMaxWidth().height(220.dp),
                    contentScale = ContentScale.Fit
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text(palabra.termino, fontSize = 36.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
                Text(palabra.definicion, fontSize = 18.sp, textAlign = TextAlign.Center, modifier = Modifier.padding(horizontal = 8.dp))

                Spacer(modifier = Modifier.height(16.dp))
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(onClick = onNextClick, modifier = Modifier.fillMaxWidth().height(56.dp)) {
            Text("Siguiente palabra")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text("💡 ¡Agita tu dispositivo para cambiar de palabra!", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.outline)
    }
}