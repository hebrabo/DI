package com.example.di0704_appaprendizajepalabras.data.repository

import com.example.di0704_appaprendizajepalabras.data.model.Palabra

class PalabraRepository {
    private val palabrasEspanol = listOf(
        Palabra(1, "Xenofobia", "Rechazo u odio hacia los extranjeros.", "Español", "https://images.unsplash.com/photo-1599059813005-11265ba4b4ce?q=80&w=400"),
        Palabra(2, "Empatía", "Capacidad de identificarse con alguien.", "Español", "https://images.unsplash.com/photo-1516627145497-ae6968895b74?q=80&w=400")
    )

    private val palabrasIngles = listOf(
        Palabra(101, "Xenophobia", "Dislike of or prejudice against people.", "Inglés", "https://images.unsplash.com/photo-1599059813005-11265ba4b4ce?q=80&w=400"),
        Palabra(102, "Empathy", "The ability to understand others feelings.", "Inglés", "https://images.unsplash.com/photo-1516627145497-ae6968895b74?q=80&w=400")
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