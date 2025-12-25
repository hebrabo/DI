package com.example.di0704_appaprendizajepalabras.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.di0704_appaprendizajepalabras.ui.viewmodel.PalabraViewModel

@Composable
fun SettingsScreen(
    viewModel: PalabraViewModel,
    isDarkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
    onBackClick: () -> Unit
) {
    // Conectamos la UI con el estado real del ViewModel
    SettingsContent(
        isDarkMode = isDarkMode,
        onDarkModeChange = onDarkModeChange,
        onBackClick = onBackClick,
        idiomaActual = viewModel.idiomaActual, // Leemos del VM
        onIdiomaChange = { nuevoIdioma -> viewModel.cambiarIdioma(nuevoIdioma) }, // Escribimos al VM
        onDeleteProgress = { viewModel.borrarProgreso() }
    )
}

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
    var showDialog by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    // Solo los idiomas que manejamos en el Repositorio
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
            // SECCIÓN: APARIENCIA
            Text(text = "Apariencia", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Modo Noche", fontSize = 18.sp)
                Switch(checked = isDarkMode, onCheckedChange = onDarkModeChange)
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            // SECCIÓN: APRENDIZAJE
            Text(text = "Aprendizaje", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = "Idioma a practicar", style = MaterialTheme.typography.bodyMedium)
            Spacer(modifier = Modifier.height(8.dp))

            // Menú Desplegable conectado al ViewModel
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = idiomaActual,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Language, contentDescription = null) }
                )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    idiomas.forEach { idioma ->
                        DropdownMenuItem(
                            text = { Text(idioma) },
                            onClick = {
                                onIdiomaChange(idioma) // Notifica al ViewModel
                                expanded = false
                            }
                        )
                    }
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            // SECCIÓN: PELIGRO
            Text(text = "Datos", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Delete, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Borrar todo el progreso")
            }
        }

        // Diálogo de confirmación
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                icon = { Icon(Icons.Default.Warning, contentDescription = null) },
                title = { Text("¿Estás seguro?") },
                text = { Text("Se borrarán todas las estadísticas y el idioma volverá a ser Español.") },
                confirmButton = {
                    TextButton(onClick = {
                        onDeleteProgress()
                        showDialog = false
                    }) { Text("Borrar", color = MaterialTheme.colorScheme.error) }
                },
                dismissButton = {
                    TextButton(onClick = { showDialog = false }) { Text("Cancelar") }
                }
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingsScreenPreview() {
    SettingsContent(
        isDarkMode = false,
        onDarkModeChange = {},
        onBackClick = {},
        idiomaActual = "Español",
        onIdiomaChange = {},
        onDeleteProgress = {}
    )
}