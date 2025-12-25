package com.example.di0704_appaprendizajepalabras.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example.di0704_appaprendizajepalabras.data.model.Palabra
import com.example.di0704_appaprendizajepalabras.data.repository.PalabraRepository

class PalabraViewModel : ViewModel() {
    private val repository = PalabraRepository()

    // 1. Estado del idioma
    var idiomaActual by mutableStateOf("Español")
        private set

    // --- NUEVO: Registro de IDs mostrados para evitar repeticiones ---
    private val idsMostradas = mutableSetOf<Int>()

    // 2. Estado de la palabra actual
    // Inicializamos con el saco vacío para la primera palabra
    private val _palabraActual = mutableStateOf(obtenerNuevaPalabraSegura("Español"))
    val palabraActual: State<Palabra> = _palabraActual

    // --- EXTRAS: ESTADÍSTICAS Y APRENDIZAJE ---
    var palabrasVistas by mutableIntStateOf(0)
        private set

    private val _palabrasAprendidas = mutableStateListOf<Palabra>()
    val palabrasAprendidas: List<Palabra> = _palabrasAprendidas

    fun cambiarIdioma(nuevoIdioma: String) {
        if (idiomaActual != nuevoIdioma) {
            idiomaActual = nuevoIdioma
            // No reseteamos el saco aquí para que, si vuelve al idioma anterior,
            // recuerde qué palabras ya vio en esa sesión.
            siguientePalabra()
        }
    }

    fun siguientePalabra() {
        val palabraAnterior = _palabraActual.value

        // Guardamos en el historial (Extra)
        if (palabraAnterior.id != 0 && !_palabrasAprendidas.any { it.id == palabraAnterior.id }) {
            _palabrasAprendidas.add(palabraAnterior)
        }

        // Incrementamos estadística
        palabrasVistas++

        // Obtenemos la siguiente palabra con la lógica de filtrado
        _palabraActual.value = obtenerNuevaPalabraSegura(idiomaActual)
    }

    fun borrarProgreso() {
        idiomaActual = "Español"
        palabrasVistas = 0
        _palabrasAprendidas.clear()
        idsMostradas.clear() // Vaciamos el saco de palabras vistas
        _palabraActual.value = obtenerNuevaPalabraSegura("Español")
    }

    /**
     * Lógica Inteligente: Filtra las palabras para no repetir.
     */
    private fun obtenerNuevaPalabraSegura(idioma: String): Palabra {
        val todasLasPalabras = repository.obtenerPalabrasPorIdioma(idioma)

        if (todasLasPalabras.isEmpty()) {
            return Palabra(0, "Error", "No hay palabras en el repositorio.", idioma, null)
        }

        // 1. Buscamos palabras que NO estén en el saco de 'idsMostradas'
        val palabrasNoVistas = todasLasPalabras.filter { it.id !in idsMostradas }

        return if (palabrasNoVistas.isNotEmpty()) {
            // 2. Si quedan palabras por ver, elegimos una al azar
            val nuevaPalabra = palabrasNoVistas.random()
            idsMostradas.add(nuevaPalabra.id) // La metemos en el saco
            nuevaPalabra
        } else {
            // 3. Si ya se han mostrado todas (saco lleno), reiniciamos el ciclo
            // Limpiamos del saco solo las palabras correspondientes a este idioma
            val idsIdiomaActual = todasLasPalabras.map { it.id }.toSet()
            idsMostradas.removeAll(idsIdiomaActual)

            // Elegimos una al azar del total y empezamos de nuevo
            val reinicioPalabra = todasLasPalabras.random()
            idsMostradas.add(reinicioPalabra.id)
            reinicioPalabra
        }
    }
}