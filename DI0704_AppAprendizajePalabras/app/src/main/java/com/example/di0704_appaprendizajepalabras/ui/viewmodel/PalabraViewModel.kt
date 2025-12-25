package com.example.di0704_appaprendizajepalabras.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue // IMPORTANTE para el "by"
import androidx.lifecycle.ViewModel
import com.example.di0704_appaprendizajepalabras.data.model.Palabra
import com.example.di0704_appaprendizajepalabras.data.repository.PalabraRepository

class PalabraViewModel : ViewModel() {
    private val repository = PalabraRepository()

    // 1. Estado del idioma
    var idiomaActual by mutableStateOf("Español")
        private set

    // 2. Estado de la palabra actual
    private val _palabraActual = mutableStateOf(obtenerNuevaPalabraSegura("Español"))
    val palabraActual: State<Palabra> = _palabraActual

    // --- EXTRAS: ESTADÍSTICAS Y APRENDIZAJE ---

    // 3. Contador de palabras vistas (Corregido: "by" en lugar de "por")
    var palabrasVistas by mutableStateOf(0)
        private set

    // 4. Lista de palabras ya aprendidas
    private val _palabrasAprendidas = mutableStateListOf<Palabra>()
    val palabrasAprendidas: List<Palabra> = _palabrasAprendidas

    fun cambiarIdioma(nuevoIdioma: String) {
        if (idiomaActual != nuevoIdioma) {
            idiomaActual = nuevoIdioma
            siguientePalabra()
        }
    }

    fun siguientePalabra() {
        val palabraAnterior = _palabraActual.value
        // Guardamos en el historial si es una palabra válida y no está repetida
        if (palabraAnterior.id != 0 && !_palabrasAprendidas.any { it.id == palabraAnterior.id }) {
            _palabrasAprendidas.add(palabraAnterior)
        }

        // Incrementamos estadística
        palabrasVistas++

        // Nueva palabra
        _palabraActual.value = obtenerNuevaPalabraSegura(idiomaActual)
    }

    fun borrarProgreso() {
        idiomaActual = "Español"
        palabrasVistas = 0
        _palabrasAprendidas.clear()
        _palabraActual.value = obtenerNuevaPalabraSegura("Español")
    }

    private fun obtenerNuevaPalabraSegura(idioma: String): Palabra {
        val lista = repository.obtenerPalabrasPorIdioma(idioma)
        return if (lista.isNotEmpty()) {
            lista.random()
        } else {
            Palabra(
                id = 0,
                termino = "Sin palabras",
                definicion = "No se encontraron términos en $idioma.",
                idioma = idioma,
                imagenUrl = null
            )
        }
    }
}