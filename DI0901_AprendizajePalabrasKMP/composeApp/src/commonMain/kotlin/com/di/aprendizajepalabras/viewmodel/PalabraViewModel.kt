package com.di.aprendizajepalabras.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.di.aprendizajepalabras.data.model.Palabra
import com.di.aprendizajepalabras.data.repository.PalabraRepository
import java.util.Calendar
import java.util.concurrent.TimeUnit

/**
 * ESTADO DEL QUIZ:
 * Clase auxiliar para agrupar los datos necesarios para un reto del mini juego.
 */
data class QuizState(
    val palabra: Palabra,          // La palabra que el usuario debe adivinar
    val opciones: List<String>,    // Lista de 3 definiciones mezcladas
    val respuestaCorrecta: String  // La definición correcta para comparar
)

/**
 * VIEWMODEL:
 * Su función es separar la lógica de los datos de la interfaz visual (UI).
 * Si la app se gira o cambia de tamaño, el ViewModel sobrevive y no pierde los datos.
 */
class PalabraViewModel : ViewModel() {
    private val repository = PalabraRepository()

    // --- LÓGICA DE "SACO DE PALABRAS" (Anti-repetición) ---
    // Usamos Sets (conjuntos) porque no permiten IDs duplicados.
    // Tenemos sacos independientes para que el juego no interfiera con la pantalla principal.
    private val idsMostradas = mutableSetOf<Int>()      // Saco para la pantalla Home
    private val idsQuizMostrados = mutableSetOf<Int>()  // Saco para el Mini Juego

    // --- ESTADOS REACTIVOS ---
    // 'mutableStateOf' hace que cuando el valor cambie, la UI se entere y se redibuje sola.

    // 1. Idioma actual
    var idiomaActual by mutableStateOf("Español")
        private set // Solo el ViewModel puede cambiar el idioma

    // 2. Palabra que se muestra en la Home
    private val _palabraActual = mutableStateOf(obtenerNuevaPalabraSegura("Español"))
    val palabraActual: State<Palabra> = _palabraActual

    // 3. Estadísticas básicas (Extra 2)
    var palabrasVistas by mutableIntStateOf(0)
        private set

    // 'mutableStateListOf' es una lista especial que avisa a la UI cuando añadimos elementos
    private val _palabrasAprendidas = mutableStateListOf<Palabra>()
    val palabrasAprendidas: List<Palabra> = _palabrasAprendidas

    // 4. Estado actual del Mini Juego (Extra 3)
    private val _quizActual = mutableStateOf<QuizState?>(null)
    val quizActual: State<QuizState?> = _quizActual

    // 5. Estadísticas avanzadas (Extra 4)
    var juegosSuperados by mutableIntStateOf(0)
        private set

    var rachaDias by mutableIntStateOf(1)
        private set

    var totalSesiones by mutableIntStateOf(1)
        private set

    // --- CONTROL DE TIEMPO (API 24) ---
    // Guardamos la fecha simulada de la "última vez" que se abrió la app
    private var fechaUltimaConexionMs: Long = System.currentTimeMillis() - TimeUnit.DAYS.toMillis(1)

    init {
        // Al crearse el ViewModel, calculamos si el usuario mantiene su racha
        actualizarRacha()
    }

    /**
     * LÓGICA DE RACHA:
     * Compara la fecha actual con la última conexión para ver si ha pasado 1 día,
     * más de uno (se rompe la racha) o es el mismo día.
     */
    private fun actualizarRacha() {
        val hoy = Calendar.getInstance()
        val ultima = Calendar.getInstance().apply { timeInMillis = fechaUltimaConexionMs }

        // Normalizamos las fechas a medianoche para que la hora exacta no importe
        resetearHora(hoy)
        resetearHora(ultima)

        val diferenciaMs = hoy.timeInMillis - ultima.timeInMillis
        val diasDiferencia = TimeUnit.MILLISECONDS.toDays(diferenciaMs)

        if (diasDiferencia == 1L) {
            rachaDias++ // Se conectó ayer: ¡Racha aumentada!
        } else if (diasDiferencia > 1L) {
            rachaDias = 1 // Pasó más de un día: Racha perdida, volvemos a 1
        }
        // Actualizamos la fecha a "ahora"
        fechaUltimaConexionMs = System.currentTimeMillis()
    }

    private fun resetearHora(cal: Calendar) {
        cal.set(Calendar.HOUR_OF_DAY, 0)
        cal.set(Calendar.MINUTE, 0)
        cal.set(Calendar.SECOND, 0)
        cal.set(Calendar.MILLISECOND, 0)
    }

    // --- ACCIONES DEL USUARIO ---

    fun cambiarIdioma(nuevoIdioma: String) {
        if (idiomaActual != nuevoIdioma) {
            idiomaActual = nuevoIdioma
            siguientePalabra() // Refrescamos la palabra de la Home
            generarNuevoReto() // Refrescamos el reto del juego
        }
    }

    /**
     * PASAR A LA SIGUIENTE PALABRA (Home):
     * Guarda la palabra anterior en "aprendidas" y busca una nueva.
     */
    fun siguientePalabra() {
        val palabraAnterior = _palabraActual.value
        // Si no está ya en la lista de aprendidas, la añadimos
        if (palabraAnterior.id != 0 && !_palabrasAprendidas.any { it.id == palabraAnterior.id }) {
            _palabrasAprendidas.add(palabraAnterior)
        }
        palabrasVistas++
        _palabraActual.value = obtenerNuevaPalabraSegura(idiomaActual)
    }

    /**
     * GENERAR NUEVO RETO (Mini Juego):
     * Crea una pregunta con 1 respuesta correcta y 2 fallidas (distractores).
     */
    fun generarNuevoReto() {
        val todasLasPalabras = repository.obtenerPalabrasPorIdioma(idiomaActual)
        if (todasLasPalabras.size < 3) return

        // 1. Filtrar las que NO han salido en el Quiz (Saco independiente)
        var disponiblesQuiz = todasLasPalabras.filter { it.id !in idsQuizMostrados }

        // 2. Si se acaban, vaciamos el saco y empezamos de nuevo
        if (disponiblesQuiz.isEmpty()) {
            idsQuizMostrados.clear()
            disponiblesQuiz = todasLasPalabras
        }

        val palabraCorrecta = disponiblesQuiz.random()
        idsQuizMostrados.add(palabraCorrecta.id)

        // 3. Obtener distractores (definiciones de otras palabras al azar)
        val distractores = todasLasPalabras
            .filter { it.id != palabraCorrecta.id }
            .shuffled()
            .take(2)
            .map { it.definicion }

        // 4. Mezclar la correcta con los distractores
        val opciones = (distractores + palabraCorrecta.definicion).shuffled()

        _quizActual.value = QuizState(
            palabra = palabraCorrecta,
            opciones = opciones,
            respuestaCorrecta = palabraCorrecta.definicion
        )
    }

    fun registrarAcierto() {
        juegosSuperados++
    }

    /**
     * RESET TOTAL:
     * Devuelve la app al estado inicial de fábrica.
     */
    fun borrarProgreso() {
        idiomaActual = "Español"
        palabrasVistas = 0
        juegosSuperados = 0
        rachaDias = 1
        totalSesiones = 1
        _palabrasAprendidas.clear()
        idsMostradas.clear()
        idsQuizMostrados.clear()
        _palabraActual.value = obtenerNuevaPalabraSegura("Español")
        _quizActual.value = null
    }

    /**
     * LÓGICA DE SELECCIÓN SEGURA (Saco Circular):
     * Garantiza que el usuario vea todas las palabras antes de repetir ninguna.
     */
    private fun obtenerNuevaPalabraSegura(idioma: String): Palabra {
        val todasLasPalabras = repository.obtenerPalabrasPorIdioma(idioma)
        if (todasLasPalabras.isEmpty()) {
            return Palabra(0, "Error", "No hay palabras", idioma, null)
        }

        // Buscamos las que no han estado en pantalla (idsMostradas)
        val palabrasNoVistas = todasLasPalabras.filter { it.id !in idsMostradas }

        return if (palabrasNoVistas.isNotEmpty()) {
            val nuevaPalabra = palabrasNoVistas.random()
            idsMostradas.add(nuevaPalabra.id)
            nuevaPalabra
        } else {
            // Si ya vimos todas las del idioma actual, vaciamos el saco de ese idioma
            val idsIdiomaActual = todasLasPalabras.map { it.id }.toSet()
            idsMostradas.removeAll(idsIdiomaActual)
            val reinicioPalabra = todasLasPalabras.random()
            idsMostradas.add(reinicioPalabra.id)
            reinicioPalabra
        }
    }
}