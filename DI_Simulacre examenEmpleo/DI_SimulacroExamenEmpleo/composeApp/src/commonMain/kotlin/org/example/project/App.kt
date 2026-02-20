package org.example.project

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import di_simulacroexamenempleo.composeapp.generated.resources.Res
import di_simulacroexamenempleo.composeapp.generated.resources.logoe
import org.example.project.theme.AppTheme
import org.example.project.ui.PantallaAyuda
import org.example.project.ui.PantallaBienvenida
import org.example.project.ui.PantallaInscripcion
import org.example.project.ui.PantallaSolicitudes
import org.example.project.viewmodel.InscripcionViewModel
import org.jetbrains.compose.resources.painterResource

/**
 * FUNCIÓN PRINCIPAL DE LA APLICACIÓN: Gestiona la estructura global y la navegación.
 * Punto de entrada donde se orquestan el ViewModel, el Tema y el Scaffold.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App() {
    /**
     * INYECCIÓN DE DEPENDENCIAS (ViewModel):
     * Instanciamos el ViewModel aquí con 'remember' para que sobreviva a las recomposiciones.
     * Al pasarlo como parámetro a las pantallas, garantizamos que todas compartan la misma
     * lista de datos (el "vector" de inscritos).
     */
    val viewModel = remember { InscripcionViewModel() }

    // Envolvemos la app en el tema personalizado definido en org.example.project.theme
    AppTheme {
        /**
         * ESTADO DE NAVEGACIÓN:
         * 'pantallaActual' determina qué contenido se renderiza en el cuerpo de la app.
         * Se inicializa en "Empleo" (Bienvenida).
         */
        var pantallaActual by remember { mutableStateOf("Empleo") }

        /**
         * SCAFFOLD (Punto 1): Proporciona la estructura básica (Layout) de Material Design.
         * Gestiona automáticamente el espacio para la barra superior (topBar) y el contenido principal.
         */
        Scaffold(
            topBar = {
                /**
                 * BARRA SUPERIOR (Punto 1):
                 * Usamos 'MediumTopAppBar' para cumplir con el requisito de visibilidad del logo
                 * y el título sin que el diseño se vea saturado.
                 */
                MediumTopAppBar(
                    title = {
                        Text(
                            text = "Portal Empleo",
                            style = MaterialTheme.typography.headlineSmall,
                            maxLines = 1
                        )
                    },
                    navigationIcon = {
                        // LOGO (Punto 1.2): Integración de recursos mediante la clase generada Res.
                        Image(
                            painter = painterResource(Res.drawable.logoe),
                            contentDescription = "Logo",
                            modifier = Modifier
                                .padding(start = 12.dp)
                                .size(40.dp)
                        )
                    },
                    actions = {
                        /**
                         * BOTONES DE ACCIÓN (Navegación):
                         * Cambian el valor de 'pantallaActual'.
                         * Incluyen lógica de color condicional para resaltar la página activa.
                         */
                        IconButton(onClick = { pantallaActual = "Empleo" }) {
                            Icon(
                                Icons.Default.Home,
                                contentDescription = "Inicio",
                                // Tinte dinámico: secundario si está activo, blanco (onPrimary) si no.
                                tint = if (pantallaActual == "Empleo") MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onPrimary
                            )
                        }

                        // Automatización de botones mediante una lista para mantener el código limpio.
                        listOf("Inscripción", "Solicitudes", "Ayuda").forEach { nombre ->
                            TextButton(onClick = { pantallaActual = nombre }) {
                                Text(
                                    text = nombre,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = if (pantallaActual == nombre) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onPrimary
                                )
                            }
                        }
                    },
                    // Configuración de colores oficiales  (Blue4 como primary).
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        titleContentColor = MaterialTheme.colorScheme.onPrimary,
                        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        ) { paddingValues ->
            /**
             * CONTENEDOR DE CONTENIDO:
             * 'Box' recibe los 'paddingValues' del Scaffold para evitar que el contenido
             * quede oculto debajo de la TopAppBar.
             */
            Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
                // LÓGICA DE NAVEGACIÓN (When): Carga la pantalla correspondiente según el estado.
                when (pantallaActual) {
                    "Empleo" -> PantallaBienvenida()
                    "Inscripción" -> PantallaInscripcion(viewModel) // Pasamos el ViewModel para guardar datos.
                    "Solicitudes" -> PantallaSolicitudes(viewModel) // Pasamos el ViewModel para leer y filtrar datos.
                    "Ayuda" -> PantallaAyuda()
                }
            }
        }
    }
}