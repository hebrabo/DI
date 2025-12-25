package com.example.di0704_appaprendizajepalabras.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.di0704_appaprendizajepalabras.data.model.Palabra
import com.example.di0704_appaprendizajepalabras.ui.viewmodel.PalabraViewModel
import kotlinx.coroutines.launch
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaPrincipal(
    viewModel: PalabraViewModel,
    onNavigateToSettings: () -> Unit // Parámetro necesario para navegar
) {
    // 1. Estados necesarios para el Menú Lateral
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope() // Necesario para animar el menú (abrir/cerrar)
    val palabra = viewModel.palabraActual.value

    // 2. Estructura del Menú Lateral (Drawer)
    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text("Menú de Aprendizaje", modifier = Modifier.padding(20.dp), style = MaterialTheme.typography.titleLarge)
                HorizontalDivider()

                // Opción: Volver a la palabra (solo cierra el menú)
                NavigationDrawerItem(
                    label = { Text("Palabra del Día") },
                    selected = true,
                    onClick = { scope.launch { drawerState.close() } }
                )

                // Opción: Ir a Ajustes (Cierra el menú y navega)
                NavigationDrawerItem(
                    label = { Text("Configuración") },
                    selected = false,
                    onClick = {
                        scope.launch { drawerState.close() }
                        onNavigateToSettings()
                    }
                )
            }
        }
    ) {
        // 3. Chasis de la pantalla (TopBar + Contenido)
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
            Box(modifier = Modifier.padding(paddingValues)) {
                // Llamamos a la función de dibujo que creamos en el Paso 8
                ContenidoPalabra(
                    palabra = palabra,
                    onNextClick = { viewModel.siguientePalabra() }
                )
            }
        }
    }
}

@Composable
fun ContenidoPalabra(palabra: Palabra, onNextClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "📘 Palabra del Día", fontSize = 24.sp)

        Spacer(modifier = Modifier.height(20.dp))

        // MOSTRAR IMAGEN SI EXISTE
        palabra.imagenUrl?.let { url ->
            AsyncImage(
                model = url,
                contentDescription = "Imagen de ${palabra.termino}",
                modifier = Modifier
                    .size(200.dp) // Tamaño de la foto
                    .padding(8.dp)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Text(text = palabra.termino, fontSize = 32.sp, fontWeight = FontWeight.Bold)

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = palabra.definicion,
            fontSize = 18.sp,
            modifier = Modifier.padding(horizontal = 20.dp)
        )

        Spacer(modifier = Modifier.height(40.dp))

        Button(onClick = onNextClick) {
            Text("Practicar nuevas palabras")
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun PantallaPrincipalPreview() {
    // Creamos una palabra de prueba para verla en el diseño
    val palabraPrueba = Palabra(1, "Xenofobia", "Rechazo u odio hacia los extranjeros.")

    ContenidoPalabra(palabra = palabraPrueba, onNextClick = {})
}