package com.example.di0704_appaprendizajepalabras.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.di0704_appaprendizajepalabras.data.model.Palabra
import com.example.di0704_appaprendizajepalabras.ui.viewmodel.PalabraViewModel

/**
 * PANTALLA DE PROGRESO (Diccionario + Estadísticas)
 * Esta pantalla utiliza una LazyColumn para manejar diferentes tipos de contenido
 * de forma eficiente (encabezados, tarjetas de estadísticas y lista de palabras).
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiccionarioScreen(viewModel: PalabraViewModel, onBackClick: () -> Unit) {
    // Obtenemos la lista de palabras del ViewModel (se actualiza automáticamente)
    val palabras = viewModel.palabrasAprendidas

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Mi Progreso") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        // LazyColumn es como un RecyclerView: solo renderiza lo que se ve en pantalla
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp), // Margen interno para que nada toque los bordes
            verticalArrangement = Arrangement.spacedBy(12.dp) // Espaciado uniforme entre elementos
        ) {

            // --- BLOQUE 1: PANEL DE LOGROS (Estadísticas) ---
            // Usamos 'item' (singular) para elementos que no se repiten
            item {
                Text(
                    text = "Logros del Estudiante",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                ElevatedCard(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.elevatedCardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        // Inyectamos los datos directamente desde el ViewModel
                        StatRow(
                            icon = Icons.Default.LocalFireDepartment,
                            label = "Racha de días",
                            value = "${viewModel.rachaDias} 🔥",
                            color = MaterialTheme.colorScheme.error
                        )
                        StatRow(
                            icon = Icons.Default.EmojiEvents,
                            label = "Juegos ganados",
                            value = "${viewModel.juegosSuperados}"
                        )
                        StatRow(
                            icon = Icons.Default.Visibility,
                            label = "Palabras vistas",
                            value = "${viewModel.palabrasVistas}"
                        )
                    }
                }
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider() // Línea divisoria estética
                Spacer(modifier = Modifier.height(16.dp))
            }

            // --- BLOQUE 2: TÍTULO DEL HISTORIAL ---
            item {
                Text(
                    text = "Diccionario Aprendido (${palabras.size})",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }

            // --- BLOQUE 3: LISTA DINÁMICA O ESTADO VACÍO ---
            if (palabras.isEmpty()) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 40.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            Icons.Default.AutoStories,
                            contentDescription = null,
                            modifier = Modifier.size(48.dp),
                            tint = MaterialTheme.colorScheme.outline
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            "Tu diccionario está vacío. ¡Empieza a explorar!",
                            color = MaterialTheme.colorScheme.outline
                        )
                    }
                }
            } else {
                // 'items' (plural) recorre la lista y crea una tarjeta por cada palabra
                items(palabras) { palabra ->
                    TarjetaPalabraAprendida(palabra)
                }
            }
        }
    }
}

/**
 * COMPONENTE REUTILIZABLE: StatRow
 * Dibuja una fila con Icono + Texto + Valor.
 * Al separarlo así, el código de la pantalla principal queda mucho más limpio.
 */
@Composable
fun StatRow(
    icon: ImageVector,
    label: String,
    value: String,
    color: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onSurfaceVariant
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, tint = color, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f) // Ocupa todo el espacio sobrante
        )
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.ExtraBold,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

/**
 * COMPONENTE REUTILIZABLE: TarjetaPalabraAprendida
 * Define cómo se ve cada palabra en la lista del historial.
 */
@Composable
fun TarjetaPalabraAprendida(palabra: Palabra) {
    OutlinedCard(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Coil carga la imagen desde la URL de forma asíncrona
            AsyncImage(
                model = palabra.imagenUrl,
                contentDescription = null,
                modifier = Modifier
                    .size(70.dp)
                    .clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.Crop, // Recorta la imagen para que rellene el cuadrado
                error = androidx.compose.ui.res.painterResource(android.R.drawable.ic_menu_gallery)
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(
                    text = palabra.termino,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = palabra.definicion,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 2 // Evita que definiciones muy largas rompan el diseño
                )
                Spacer(modifier = Modifier.height(4.dp))
                // Etiqueta del idioma con estilo "chip"
                Surface(
                    color = MaterialTheme.colorScheme.secondaryContainer,
                    shape = MaterialTheme.shapes.extraSmall
                ) {
                    Text(
                        text = palabra.idioma,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }
        }
    }
}