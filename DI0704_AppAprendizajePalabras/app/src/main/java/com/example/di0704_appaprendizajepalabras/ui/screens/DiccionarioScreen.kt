package com.example.di0704_appaprendizajepalabras.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Book
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.di0704_appaprendizajepalabras.data.model.Palabra
import com.example.di0704_appaprendizajepalabras.ui.viewmodel.PalabraViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiccionarioScreen(viewModel: PalabraViewModel, onBackClick: () -> Unit) {
    val palabras = viewModel.palabrasAprendidas

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Palabras Aprendidas") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                    }
                }
            )
        }
    ) { paddingValues ->
        if (palabras.isEmpty()) {
            // Estado vacío
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(Icons.Default.Book, contentDescription = null, modifier = Modifier.size(64.dp), tint = MaterialTheme.colorScheme.outline)
                Spacer(modifier = Modifier.height(16.dp))
                Text("Aún no has aprendido palabras.", color = MaterialTheme.colorScheme.outline)
            }
        } else {
            // Lista de palabras
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(palabras) { palabra ->
                    TarjetaPalabraAprendida(palabra)
                }
            }
        }
    }
}

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
            // Miniatura de la imagen
            AsyncImage(
                model = palabra.imagenUrl,
                contentDescription = null,
                modifier = Modifier.size(60.dp).clip(MaterialTheme.shapes.small),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column {
                Text(text = palabra.termino, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                Text(text = palabra.definicion, style = MaterialTheme.typography.bodySmall, maxLines = 2)
                Text(
                    text = "Idioma: ${palabra.idioma}",
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}