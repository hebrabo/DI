package com.example.di0704_appaprendizajepalabras.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    isDarkMode: Boolean,
    onDarkModeChange: (Boolean) -> Unit,
    onBackClick: () -> Unit // Añadimos esto para poder volver al Home
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Configuración") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Volver")
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
            Text(text = "Personalización", fontSize = 24.sp, style = MaterialTheme.typography.titleMedium)

            Spacer(modifier = Modifier.height(32.dp))

            // Fila para el interruptor de Modo Noche
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Modo Noche", fontSize = 18.sp)
                Switch(
                    checked = isDarkMode,
                    onCheckedChange = { onDarkModeChange(it) }
                )
            }

            Text(
                text = "Activa esta opción para descansar la vista en entornos oscuros.",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SettingsScreenPreview() {
    SettingsScreen(isDarkMode = true, onDarkModeChange = {}, onBackClick = {})
}