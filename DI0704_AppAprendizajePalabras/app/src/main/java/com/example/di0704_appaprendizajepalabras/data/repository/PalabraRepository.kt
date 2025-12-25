package com.example.di0704_appaprendizajepalabras.data.repository

import com.example.di0704_appaprendizajepalabras.data.model.Palabra

class PalabraRepository {
    // Lista de palabras de ejemplo
    private val listaDePalabras = listOf(
        Palabra(1, "Xenofobia", "Rechazo u odio hacia los extranjeros.", "https://images.unsplash.com/photo-1599059813005-11265ba4b4ce?q=80&w=400"),
        Palabra(2, "Empatía", "Capacidad de identificarse con alguien y compartir sus sentimientos.", "https://images.unsplash.com/photo-1516627145497-ae6968895b74?q=80&w=400"),
        Palabra(3, "Resiliencia", "Capacidad de adaptación frente a un agente perturbador.", "https://images.unsplash.com/photo-1509099836639-18ba1795216d?q=80&w=400"),
        Palabra(4, "Perro", "Animal mamífero doméstico de la familia de los cánidos.", "https://images.unsplash.com/photo-1517849845537-4d257902454a?q=80&w=400")
    )

    // Función para obtener todas las palabras
    fun obtenerPalabras(): List<Palabra> {
        return listaDePalabras
    }

    // Función para obtener una palabra aleatoria
    fun obtenerPalabraAleatoria(): Palabra {
        return listaDePalabras.random()
    }
}