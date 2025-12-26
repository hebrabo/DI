package com.example.di0704_appaprendizajepalabras.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.di0704_appaprendizajepalabras.ui.viewmodel.PalabraViewModel

/**
 * 1. COMPOSABLE DE CONEXIÓN (BRIDGE):
 * Esta función conecta el ViewModel con la interfaz.
 * Separamos la lógica (ViewModel) de la visualización (Content) para que
 * el código sea más fácil de probar y mantener.
 */
@Composable
fun SettingsScreen(
    viewModel: PalabraViewModel,
    isDarkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
    onBackClick: () -> Unit
) {
    // Solo pasamos a la UI lo que realmente necesita configurar.
    // Las estadísticas (racha, aciertos) ya no se pasan aquí porque se movieron a "Mi Progreso".
    SettingsContent(
        isDarkMode = isDarkMode,
        onDarkModeChange = onDarkModeChange,
        onBackClick = onBackClick,
        idiomaActual = viewModel.idiomaActual,
        onIdiomaChange = { viewModel.cambiarIdioma(it) },
        onDeleteProgress = { viewModel.borrarProgreso() }
    )
}

/**
 * 2. COMPOSABLE DE INTERFAZ (UI):
 * Contiene el diseño puro. No sabe qué es un ViewModel, solo recibe datos y
 * emite eventos cuando el usuario pulsa botones.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsContent(
    isDarkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
    onBackClick: () -> Unit,
    idiomaActual: String,
    onIdiomaChange: (String) -> Unit,
    onDeleteProgress: () -> Unit
) {
    // ESTADOS LOCALES:
    // 'showDialog' controla el cartel de confirmación.
    // 'expanded' controla si el menú de idiomas está abierto.
    var showDialog by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    val idiomas = listOf("Español", "Inglés")

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configuración") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(24.dp)
        ) {
            // --- SECCIÓN: APARIENCIA ---
            Text(
                text = "Apariencia",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Modo Noche", fontSize = 18.sp)
                // Switch: Componente visual para estados On/Off
                Switch(checked = isDarkMode, onCheckedChange = onDarkModeChange)
            }

            // Divisor visual para separar secciones
            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            // --- SECCIÓN: APRENDIZAJE ---
            Text(
                text = "Aprendizaje",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Contenedor para el Menú Desplegable (Dropdown)
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = idiomaActual,
                    onValueChange = {},
                    readOnly = true, // El usuario no escribe, solo elige de la lista
                    label = { Text("Idioma a practicar") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },

                    // menuAnchor: Indica a Compose dónde debe "pegarse" el menú flotante.
                    // Usamos 'PrimaryNotEditable' porque el campo es de solo lectura.
                    modifier = Modifier
                        .menuAnchor(type = MenuAnchorType.PrimaryNotEditable, enabled = true)
                        .fillMaxWidth(),

                    leadingIcon = { Icon(Icons.Default.Language, contentDescription = null) }
                )

                // El menú que se despliega con las opciones
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    idiomas.forEach { idioma ->
                        DropdownMenuItem(
                            text = { Text(idioma) },
                            onClick = {
                                onIdiomaChange(idioma) // Notifica el cambio al ViewModel
                                expanded = false
                            }
                        )
                    }
                }
            }

            /**
             * ESPACIADO FIJO:
             * Usamos un alto fijo (64.dp) para que la "Zona de peligro" no se vaya
             * al final de la pantalla ahora que hay menos elementos.
             */
            Spacer(modifier = Modifier.height(64.dp))

            // --- SECCIÓN: PELIGRO (Acciones irreversibles) ---
            Text(
                text = "Zona de peligro",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.error
            )
            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = { showDialog = true }, // Abrimos el diálogo de confirmación
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.error
                ),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Delete, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Restablecer todo el progreso")
            }
        }

        /**
         * DIÁLOGO DE CONFIRMACIÓN (AlertDialog):
         * Solo aparece si el usuario pulsa el botón de borrar.
         * Es una barrera de seguridad fundamental en UX.
         */
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false }, // Se cierra si pulsas fuera
                icon = { Icon(Icons.Default.Warning, contentDescription = null) },
                title = { Text("¿Estás completamente seguro?") },
                text = {
                    Text("Se borrarán tus estadísticas, racha y palabras aprendidas. Esta acción no se puede deshacer.")
                },
                confirmButton = {
                    // Acción de confirmar borrado
                    TextButton(onClick = {
                        onDeleteProgress() // Llama a la lógica de reset del ViewModel
                        showDialog = false
                    }) {
                        Text("Borrar todo", color = MaterialTheme.colorScheme.error)
                    }
                },
                dismissButton = {
                    // Acción de cancelar
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}