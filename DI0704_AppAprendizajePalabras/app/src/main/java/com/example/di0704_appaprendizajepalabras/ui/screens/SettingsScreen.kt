package com.example.di0704_appaprendizajepalabras.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
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
    SettingsContent(
        isDarkMode = isDarkMode,
        onDarkModeChange = onDarkModeChange,
        onBackClick = onBackClick,
        idiomaActual = viewModel.idiomaActual,
        onIdiomaChange = { viewModel.cambiarIdioma(it) },
        onDeleteProgress = { viewModel.borrarProgreso() },
        // Pasamos los nuevos datos del Extra 4
        racha = viewModel.rachaDias,
        juegosGanados = viewModel.juegosSuperados,
        palabrasVistas = viewModel.palabrasVistas,
        sesiones = viewModel.totalSesiones
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
    onDeleteProgress: () -> Unit,
    racha: Int,
    juegosGanados: Int,
    palabrasVistas: Int,
    sesiones: Int
) {
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

            // --- SECCIÓN: APRENDIZAJE ---
            Text(text = "Aprendizaje", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(16.dp))
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = idiomaActual,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Idioma a practicar") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Language, contentDescription = null) }
                )
                ExposedDropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    idiomas.forEach { idioma ->
                        DropdownMenuItem(
                            text = { Text(idioma) },
                            onClick = { onIdiomaChange(idioma); expanded = false }
                        )
                    }
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))

            // --- EXTRA 4: DASHBOARD DE ESTADÍSTICAS ---
            Text(text = "Tu Progreso", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.primary)
            Spacer(modifier = Modifier.height(12.dp))

            ElevatedCard(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f))
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    StatRow(icon = Icons.Default.LocalFireDepartment, label = "Racha actual", value = "$racha días", color = MaterialTheme.colorScheme.error)
                    StatRow(icon = Icons.Default.EmojiEvents, label = "Juegos ganados", value = "$juegosGanados")
                    StatRow(icon = Icons.Default.Visibility, label = "Palabras vistas", value = "$palabrasVistas")
                    StatRow(icon = Icons.Default.History, label = "Sesiones totales", value = "$sesiones")
                }
            }

            Spacer(modifier = Modifier.weight(1f)) // Empuja lo siguiente al final

            // --- SECCIÓN: PELIGRO ---
            Text(text = "Zona de peligro", style = MaterialTheme.typography.titleSmall, color = MaterialTheme.colorScheme.error)
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = { showDialog = true },
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.Delete, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Restablecer progreso")
            }
        }

        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                icon = { Icon(Icons.Default.Warning, contentDescription = null) },
                title = { Text("¿Reiniciar todo?") },
                text = { Text("Perderás tu racha de $racha días y tus $juegosGanados juegos ganados.") },
                confirmButton = {
                    TextButton(onClick = { onDeleteProgress(); showDialog = false }) {
                        Text("Confirmar", color = MaterialTheme.colorScheme.error)
                    }
                },
                dismissButton = { TextButton(onClick = { showDialog = false }) { Text("Cancelar") } }
            )
        }
    }
}

@Composable
fun StatRow(icon: ImageVector, label: String, value: String, color: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onSurfaceVariant) {
    Row(
        modifier = Modifier.fillMaxWidth().padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(text = label, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
        Text(text = value, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Bold)
    }
}