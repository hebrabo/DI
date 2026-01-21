package com.di.aprendizajepalabras.data.repository

import com.di.aprendizajepalabras.data.model.Palabra


/**
 * REPOSITORY PATTERN:
 * El Repositorio es el “origen único de datos”. Su trabajo es gestionar los datos
 * (ya vengan de internet, de una base de datos o, como aquí, de una lista fija).
 * El resto de la app no sabe de dónde vienen los datos, solo se los pide a esta clase.
 */
class PalabraRepository {

    // --- BASE DE DATOS LOCAL (En memoria) ---
    // Usamos 'private' para que nadie desde fuera pueda modificar estas listas directamente.
    // Solo el repositorio tiene permiso para leerlas.

    // Listado de palabras en Español
    private val palabrasEspanol = listOf(
        // Cada objeto 'Palabra' tiene: ID, Término, Definición, Idioma e URL de imagen
        Palabra(
            1,
            "Xenofobia",
            "Rechazo u odio hacia los extranjeros.",
            "Español",
            "https://images.unsplash.com/photo-1599059813005-11265ba4b4ce?q=80&w=400"
        ),
        Palabra(2, "Empatía", "Capacidad de identificarse con alguien y compartir sus sentimientos.", "Español", "https://images.unsplash.com/photo-1516627145497-ae6968895b74?q=80&w=400"),
        Palabra(3, "Resiliencia", "Capacidad de adaptación frente a un agente perturbador.", "Español", "https://images.unsplash.com/photo-1509099836639-18ba1795216d?q=80&w=400"),
        Palabra(4, "Perro", "Animal mamífero doméstico de la familia de los cánidos.", "Español", "https://images.unsplash.com/photo-1517849845537-4d257902454a?q=80&w=400"),
        Palabra(5, "Efímero", "Aquello que dura por un periodo muy corto de tiempo.", "Español", "https://images.unsplash.com/photo-1490730141103-6cac27aaab94?q=80&w=400"),
        Palabra(6, "Serendipia", "Hallazgo afortunado e inesperado que se produce cuando se busca otra cosa.", "Español", "https://images.unsplash.com/photo-1534067783941-51c9c23ecefd?q=80&w=400"),
        Palabra(7, "Ojalá", "Expresión de un fuerte deseo de que algo suceda.", "Español", "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?q=80&w=400"),
        Palabra(8, "Elocuencia", "Facultad de hablar o escribir de modo eficaz para deleitar o conmover.", "Español", "https://images.unsplash.com/photo-1478737270239-2f02b77fc618?q=80&w=400")
    )

    // Listado de palabras en Inglés
    private val palabrasIngles = listOf(
        Palabra(101, "Xenophobia", "Dislike of or prejudice against people from other countries.", "Inglés", "https://images.unsplash.com/photo-1599059813005-11265ba4b4ce?q=80&w=400"),
        Palabra(102, "Empathy", "The ability to understand and share the feelings of another.", "Inglés", "https://images.unsplash.com/photo-1516627145497-ae6968895b74?q=80&w=400"),
        Palabra(103, "Resilience", "The capacity to recover quickly from difficulties.", "Inglés", "https://images.unsplash.com/photo-1509099836639-18ba1795216d?q=80&w=400"),
        Palabra(104, "Dog", "A domesticated carnivorous mammal that typically has a long snout.", "Inglés", "https://images.unsplash.com/photo-1517849845537-4d257902454a?q=80&w=400"),
        Palabra(105, "Ephemeral", "Lasting for a very short time.", "Inglés", "https://images.unsplash.com/photo-1490730141103-6cac27aaab94?q=80&w=400"),
        Palabra(106, "Serendipity", "The occurrence of events by chance in a happy or beneficial way.", "Inglés", "https://images.unsplash.com/photo-1534067783941-51c9c23ecefd?q=80&w=400"),
        Palabra(107, "Wanderlust", "A strong desire to travel and explore the world.", "Inglés", "https://images.unsplash.com/photo-1469854523086-cc02fe5d8800?q=80&w=400"),
        Palabra(108, "Eloquence", "Fluent or persuasive speaking or writing.", "Inglés", "https://images.unsplash.com/photo-1478737270239-2f02b77fc618?q=80&w=400")
    )

    /**
     * FUNCIÓN DE FILTRADO:
     * Este es el único punto de entrada para que el ViewModel pida datos.
     * @param idioma El string que indica qué lista queremos ("Español" o "Inglés").
     * @return Una lista de objetos Palabra filtrada.
     */
    fun obtenerPalabrasPorIdioma(idioma: String): List<Palabra> {

        // 1. Limpieza preventiva: Quitamos espacios vacíos al principio y final
        val idiomaLimpio = idioma.trim()

        // 2. Lógica de selección:
        // Usamos 'contains' e 'ignoreCase = true' para que si recibimos "ingles", "INGLÉS" o "Ingles"
        // el programa sea capaz de entenderlo y no de error.
        return if (idiomaLimpio.contains("Ingl", ignoreCase = true)) {
            palabrasIngles
        } else {
            // Por defecto, si no es inglés, devolvemos la lista en español
            palabrasEspanol
        }
    }
}