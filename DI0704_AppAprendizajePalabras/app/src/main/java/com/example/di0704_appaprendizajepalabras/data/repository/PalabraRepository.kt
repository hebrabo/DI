package com.example.di0704_appaprendizajepalabras.data.repository

import com.example.di0704_appaprendizajepalabras.data.model.Palabra

class PalabraRepository {
    private val palabrasEspanol = listOf(
        Palabra(1, "Xenofobia", "Rechazo u odio hacia los extranjeros.", "Español", "https://images.unsplash.com/photo-1599059813005-11265ba4b4ce?q=80&w=400"),
        Palabra(2, "Empatía", "Capacidad de identificarse con alguien y compartir sus sentimientos.", "Español", "https://images.unsplash.com/photo-1516627145497-ae6968895b74?q=80&w=400"),
        Palabra(3, "Resiliencia", "Capacidad de adaptación frente a un agente perturbador.", "Español", "https://images.unsplash.com/photo-1509099836639-18ba1795216d?q=80&w=400"),
        Palabra(4, "Perro", "Animal mamífero doméstico de la familia de los cánidos.", "Español", "https://images.unsplash.com/photo-1517849845537-4d257902454a?q=80&w=400"),
        Palabra(5, "Efímero", "Aquello que dura por un periodo muy corto de tiempo.", "Español", "https://images.unsplash.com/photo-1490730141103-6cac27aaab94?q=80&w=400"),
        Palabra(6, "Serendipia", "Hallazgo afortunado e inesperado que se produce cuando se busca otra cosa.", "Español", "https://images.unsplash.com/photo-1534067783941-51c9c23ecefd?q=80&w=400"),
        // CAMBIADA: Imagen de un diente de león (pedir un deseo)
        Palabra(7, "Ojalá", "Expresión de un fuerte deseo de que algo suceda.", "Español", "https://images.unsplash.com/photo-1464822759023-fed622ff2c3b?q=80&w=400"),
        // CAMBIADA: Imagen de un micrófono antiguo
        Palabra(8, "Elocuencia", "Facultad de hablar o escribir de modo eficaz para deleitar o conmover.", "Español", "https://images.unsplash.com/photo-1478737270239-2f02b77fc618?q=80&w=400")
    )

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

    fun obtenerPalabrasPorIdioma(idioma: String): List<Palabra> {
        // Comparamos de forma muy segura para que no falle por tildes o espacios
        val idiomaLimpio = idioma.trim()

        return if (idiomaLimpio.contains("Ingl", ignoreCase = true)) {
            palabrasIngles
        } else {
            palabrasEspanol
        }
    }
}