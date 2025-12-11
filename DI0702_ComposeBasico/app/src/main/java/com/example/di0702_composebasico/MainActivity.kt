package com.example.di0702_composebasico

import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

// =================================================================================
// CLASE PRINCIPAL (MainActivity)
// El punto de entrada donde configuramos la estructura global de la App.
// =================================================================================
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge() // Permite dibujar detrás de las barras de estado del móvil
        setContent {
            // 🧠 [CONTROLADOR]: El "chófer" que maneja la navegación.
            // Usamos 'rememberNavController' para que sobreviva si giramos la pantalla.
            val navController = rememberNavController()

            // 🧠 [OBSERVADOR DE RUTA]:
            // Para que la barra superior cambie DINÁMICAMENTE, necesitamos "observar"
            // en qué pantalla estamos. 'AsState' significa que si la ruta cambia,
            // la UI se redibujará automáticamente con los nuevos datos.
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            // 🧠 [SCAFFOLD]: El "Andamio" o esqueleto de Material Design.
            // Organiza automáticamente la TopBar, BottomBar y el Contenido.
            Scaffold(
                // --- BARRA SUPERIOR DINÁMICA ---
                topBar = {
                    BarraSuperiorDinamica(
                        currentRoute = currentRoute,
                        // 💡 [STATE HOISTING]: Pasamos FUNCIONES (lambdas) en lugar del navController.
                        // Esto hace que el componente 'BarraSuperiorDinamica' sea independiente y reutilizable.
                        navigateBack = { navController.popBackStack() },
                        navigateToSettings = { navController.navigate("pantalla2") },
                        navigateToMusic = { navController.navigate("pantalla3") }
                    )
                },

                // --- BARRA INFERIOR DE NAVEGACIÓN ---
                bottomBar = {
                    NavigationBar {
                        // Botón 1: Inicio
                        NavigationBarItem(
                            icon = { Icon(Icons.Filled.Home, "Inicio") },
                            label = { Text("Lista") },
                            selected = currentRoute == "pantalla1", // Se ilumina si estamos aquí
                            onClick = {
                                navController.navigate("pantalla1") {
                                    // 💡 Limpiamos la pila de pantallas para que al dar "Atrás"
                                    // no volvamos a la misma pantalla infinitamente.
                                    popUpTo("pantalla1") { inclusive = true }
                                }
                            }
                        )
                        // Botón 2: Horizontal
                        NavigationBarItem(
                            icon = { Icon(Icons.Filled.Person, "Perfil") },
                            label = { Text("Fila") },
                            selected = currentRoute == "pantalla2",
                            onClick = { navController.navigate("pantalla2") }
                        )
                        // Botón 3: Música
                        NavigationBarItem(
                            icon = { Icon(Icons.Filled.PlayArrow, "Música") },
                            label = { Text("Música") },
                            selected = currentRoute == "pantalla3",
                            onClick = { navController.navigate("pantalla3") }
                        )
                    }
                }
            ) { innerPadding ->
                //  [INNER PADDING]: Es CRUCIAL pasar este padding al NavHost.
                // Si no lo hacemos, el contenido se dibujará DETRÁS de las barras superior/inferior.
                NavHost(
                    navController = navController,
                    startDestination = "pantalla1",
                    modifier = Modifier.padding(innerPadding)
                ) {
                    // Definición de las "paradas" (Rutas) de nuestra app
                    composable("pantalla1") {
                        ListaNombres(
                            personas = listaPersonas,
                            onSiguienteClick = { navController.navigate("pantalla2") }
                        )
                    }
                    composable("pantalla2") {
                        SegundaPantalla(
                            personas = listaPersonas,
                            onVolverClick = { navController.popBackStack() } // Vuelve a la anterior
                        )
                    }
                    composable("pantalla3") {
                        PantallaReproductor()
                    }
                }
            }
        }
    }
}

// =================================================================================
// PANTALLA 1: LISTA VERTICAL (LazyColumn)
// =================================================================================
@Composable
fun ListaNombres(personas: List<Persona>, onSiguienteClick: () -> Unit) {
    // [LAZY COLUMN]: Equivalente moderno al RecyclerView.
    // Solo carga en memoria los elementos que el usuario ve en ese momento.
    // Imprescindible para listas largas.
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 'item': Para elementos únicos (cabeceras, imágenes sueltas)
        item {
            Image(
                painter = painterResource(id = R.drawable.pixnio),
                contentDescription = "Imagen de prueba"
            )
        }

        // 'items': Para recorrer una lista de datos automáticamente
        items(personas) { persona ->
            Text(
                text = persona.nombre,
                fontSize = 20.sp
            )
        }

        // Footer con botón
        item {
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = { onSiguienteClick() }, // Ejecutamos la acción que nos pasó el padre
                modifier = Modifier.padding(16.dp)
            ) {
                Text(text = "Siguiente pantalla")
            }
        }
    }
}

// =================================================================================
// PANTALLA 2: LISTA HORIZONTAL (LazyRow)
// =================================================================================
@Composable
fun SegundaPantalla(personas: List<Persona>, onVolverClick: () -> Unit) {
    // 🧠 [LAZY ROW]: Igual que LazyColumn, pero permite scroll horizontal.
    LazyRow(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Blue),
        verticalAlignment = Alignment.CenterVertically, // Centrado vertical
        horizontalArrangement = Arrangement.spacedBy(16.dp) // Espacio automático entre elementos
    ) {
        item {
            Image(
                painter = painterResource(id = R.drawable.pixnio),
                contentDescription = "Imagen",
                modifier = Modifier
                    .size(100.dp)
                    .padding(start = 16.dp)
            )
        }

        items(personas) { persona ->
            Text(
                text = persona.nombre,
                color = Color.White,
                fontSize = 20.sp
            )
        }

        item {
            ElevatedButton(
                onClick = { onVolverClick() },
                modifier = Modifier.padding(end = 16.dp)
            ) {
                Text(text = "Volver")
            }
        }
    }
}

// =================================================================================
// PANTALLA 3: REPRODUCTOR DE MÚSICA (Gestión de Recursos)
// =================================================================================
@Composable
fun PantallaReproductor() {
    // 1. Obtenemos el contexto actual (necesario porque MediaPlayer es una API antigua de Android)
    val context = LocalContext.current

    // 2. 🧠 [REMEMBER]: Concepto clave de Compose.
    // Si no usamos 'remember', cada vez que la pantalla se redibuje (recomposición),
    // se crearía un NUEVO MediaPlayer y la canción empezaría de cero en bucle.
    val mediaPlayer = remember { MediaPlayer.create(context, R.raw.musica) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Green),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Reproductor de Música", fontSize = 24.sp)
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Button(onClick = { mediaPlayer.start() }) { Text("Play") }
            Spacer(modifier = Modifier.width(16.dp))
            Button(onClick = { mediaPlayer.pause() }) { Text("Pause") }
        }
    }

    // 3. 💡 [DISPOSABLE EFFECT]: Gestión del ciclo de vida.
    // Este bloque sirve para limpiar la "basura" cuando el usuario abandona esta pantalla.
    // Si no hacemos esto, la música seguiría sonando aunque cambiemos de pantalla o cerremos la app.
    DisposableEffect(Unit) {
        onDispose {
            mediaPlayer.stop()
            mediaPlayer.release() // Libera la memoria del teléfono
        }
    }
}

// =================================================================================
// COMPONENTE: BARRA SUPERIOR DINÁMICA
// Extraída fuera para mantener el código limpio y organizado
// =================================================================================
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BarraSuperiorDinamica(
    currentRoute: String?,
    navigateBack: () -> Unit,
    navigateToSettings: () -> Unit,
    navigateToMusic: () -> Unit
) {
    // Lógica pura: Determinamos el título según la ruta
    val titulo = when (currentRoute) {
        "pantalla1" -> "Lista de Nombres"
        "pantalla2" -> "Vista Horizontal"
        "pantalla3" -> "Reproductor"
        else -> "Mi App"
    }

    // Usamos CenterAlignedTopAppBar para un estilo más moderno (título centrado)
    CenterAlignedTopAppBar(
        title = { Text(titulo) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            titleContentColor = MaterialTheme.colorScheme.primary
        ),
        // BOTÓN IZQUIERDO (Navigation Icon)
        navigationIcon = {
            // Solo mostramos la flecha "Atrás" si NO estamos en el inicio
            if (currentRoute != "pantalla1") {
                IconButton(onClick = navigateBack) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver atrás"
                    )
                }
            }
        },
        // BOTONES DERECHOS (Actions) - Cambian según la pantalla
        actions = {
            when (currentRoute) {
                "pantalla1" -> {
                    IconButton(onClick = navigateToSettings) {
                        Icon(Icons.AutoMirrored.Filled.ArrowForward, contentDescription = "Ir a horizontal")
                    }
                }
                "pantalla2" -> {
                    IconButton(onClick = navigateToMusic) {
                        Icon(Icons.Filled.PlayArrow, contentDescription = "Ir a Música")
                    }
                }
                "pantalla3" -> {
                    IconButton(onClick = { /* Acción ejemplo */ }) {
                        Icon(Icons.Filled.Share, contentDescription = "Compartir")
                    }
                }
            }
        }
    )
}

// =================================================================================
// PREVIEWS: Para ver el diseño en Android Studio sin ejecutar el emulador
// =================================================================================
@Preview(showBackground = true)
@Composable
fun PreviewListaNombres() {
    // Pasamos una función vacía {} porque en la preview no navegamos
    ListaNombres(personas = listaPersonas, onSiguienteClick = {})
}
