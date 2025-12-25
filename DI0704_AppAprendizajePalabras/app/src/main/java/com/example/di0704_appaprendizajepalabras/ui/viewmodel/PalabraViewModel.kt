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
import java.time.LocalDate
import java.time.temporal.ChronoUnit

/**
 * Clase para representar un reto del mini juego (Extra 3)
 */
data class QuizState(
    val palabra: Palabra,
    val opciones: List<String>,
    val respuestaCorrecta: String
)

class PalabraViewModel : ViewModel() {
    private val repository = PalabraRepository()
    private val idsMostradas = mutableSetOf<Int>()

    // 1. Estado del idioma
    var idiomaActual by mutableStateOf("Español")
        private set

    // 2. Estado de la palabra actual
    private val _palabraActual = mutableStateOf(obtenerNuevaPalabraSegura("Español"))
    val palabraActual: State<Palabra> = _palabraActual

    // --- EXTRA 2: ESTADÍSTICAS BÁSICAS ---
    var palabrasVistas by mutableIntStateOf(0)
        private set

    private val _palabrasAprendidas = mutableStateListOf<Palabra>()
    val palabrasAprendidas: List<Palabra> = _palabrasAprendidas

    // --- EXTRA 3: ESTADO DEL MINI JUEGO ---
    private val _quizActual = mutableStateOf<QuizState?>(null)
    val quizActual: State<QuizState?> = _quizActual

    // --- EXTRA 4: ESTADÍSTICAS AVANZADAS ---
    var juegosSuperados by mutableIntStateOf(0)
        private set

    var rachaDias by mutableIntStateOf(1)
        private set

    var totalSesiones by mutableIntStateOf(1) // Empezamos en 1 al abrir la app
        private set

    // Simulamos una fecha de última conexión (en una app real esto vendría de una base de datos)
    private var fechaUltimaConexion: LocalDate = LocalDate.now().minusDays(1)

    init {
        // Al iniciar el ViewModel, calculamos la racha
        actualizarRacha()
    }

    private fun actualizarRacha() {
        val hoy = LocalDate.now()
        // Calculamos la diferencia de días entre hoy y la última vez que se conectó
        val diferencia = ChronoUnit.DAYS.between(fechaUltimaConexion, hoy)

        if (diferencia == 1L) {
            // Se conectó ayer, la racha aumenta
            rachaDias++
        } else if (diferencia > 1L) {
            // Pasó más de un día, la racha se rompe y vuelve a 1
            rachaDias = 1
        }
        // Si diferencia es 0, significa que ya se conectó hoy, no tocamos la racha
    }

    fun cambiarIdioma(nuevoIdioma: String) {
        if (idiomaActual != nuevoIdioma) {
            idiomaActual = nuevoIdioma
            siguientePalabra()
        }
    }

    fun siguientePalabra() {
        val palabraAnterior = _palabraActual.value
        if (palabraAnterior.id != 0 && !_palabrasAprendidas.any { it.id == palabraAnterior.id }) {
            _palabrasAprendidas.add(palabraAnterior)
        }
        palabrasVistas++
        _palabraActual.value = obtenerNuevaPalabraSegura(idiomaActual)
    }

    /**
     * Genera un nuevo reto para el mini juego (Extra 3)
     */
    fun generarNuevoReto() {
        val todasLasPalabras = repository.obtenerPalabrasPorIdioma(idiomaActual)

        if (todasLasPalabras.size >= 3) {
            val palabraCorrecta = todasLasPalabras.random()
            val distractores = todasLasPalabras
                .filter { it.id != palabraCorrecta.id }
                .shuffled()
                .take(2)
                .map { it.definicion }

            val opciones = (distractores + palabraCorrecta.definicion).shuffled()

            _quizActual.value = QuizState(
                palabra = palabraCorrecta,
                opciones = opciones,
                respuestaCorrecta = palabraCorrecta.definicion
            )
        }
    }

    /**
     * Registra un acierto en el juego (Extra 4: Juegos Superados)
     */
    fun registrarAcierto() {
        juegosSuperados++
    }

    fun borrarProgreso() {
        idiomaActual = "Español"
        palabrasVistas = 0
        juegosSuperados = 0
        rachaDias = 1
        totalSesiones = 1
        _palabrasAprendidas.clear()
        idsMostradas.clear()
        _palabraActual.value = obtenerNuevaPalabraSegura("Español")
        _quizActual.value = null
    }

    private fun obtenerNuevaPalabraSegura(idioma: String): Palabra {
        val todasLasPalabras = repository.obtenerPalabrasPorIdioma(idioma)
        if (todasLasPalabras.isEmpty()) {
            return Palabra(0, "Error", "No hay palabras", idioma, null)
        }

        val palabrasNoVistas = todasLasPalabras.filter { it.id !in idsMostradas }

        return if (palabrasNoVistas.isNotEmpty()) {
            val nuevaPalabra = palabrasNoVistas.random()
            idsMostradas.add(nuevaPalabra.id)
            nuevaPalabra
        } else {
            val idsIdiomaActual = todasLasPalabras.map { it.id }.toSet()
            idsMostradas.removeAll(idsIdiomaActual)
            val reinicioPalabra = todasLasPalabras.random()
            idsMostradas.add(reinicioPalabra.id)
            reinicioPalabra
        }
    }
}