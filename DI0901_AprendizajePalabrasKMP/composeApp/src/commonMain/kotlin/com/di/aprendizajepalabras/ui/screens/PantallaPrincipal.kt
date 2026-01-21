package com.di.aprendizajepalabras.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.di.aprendizajepalabras.data.model.Palabra
import com.di.aprendizajepalabras.viewmodel.PalabraViewModel
import kotlinx.coroutines.launch

/**
 * PANTALLA PRINCIPAL:
 * Versión Multiplataforma (Android y Desktop).
 * Se ha eliminado la lógica de sensores de Android para permitir la compatibilidad.
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
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val palabra = viewModel.palabraActual.value

    /**
     * ESTRUCTURA DE NAVEGACIÓN LATERAL (DRAWER)
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

                NavigationDrawerItem(
                    label = { Text("Inicio") },
                    icon = { Icon(Icons.Default.Home, null) },
                    selected = true,
                    onClick = { scope.launch { drawerState.close() } },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

                NavigationDrawerItem(
                    label = { Text("Mi Progreso") },
                    icon = { Icon(Icons.Default.Assessment, null) },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        onNavigateToDictionary()
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )

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

@Composable
fun ContenidoPalabra(palabra: Palabra, idioma: String, contador: Int, onNextClick: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            SuggestionChip(
                onClick = { },
                label = { Text("Practicando: $idioma") },
                icon = { Icon(Icons.Default.Translate, null, modifier = Modifier.size(16.dp)) }
            )
            Badge(containerColor = MaterialTheme.colorScheme.secondaryContainer) {
                Text("Vistas: $contador", modifier = Modifier.padding(8.dp), fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        ElevatedCard(
            modifier = Modifier.fillMaxWidth(),
            shape = MaterialTheme.shapes.extraLarge
        ) {
            Column(modifier = Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
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

        // Mensaje adaptado para multiplataforma
        Text(
            "Pulsa el botón para descubrir una nueva palabra",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.outline
        )
    }
}