package com.example.di0704_appaprendizajepalabras.data.repository

import com.example.di0704_appaprendizajepalabras.data.model.Palabra

class PalabraRepository {
    // Lista de palabras de ejemplo
    private val listaDePalabras = listOf(
        Palabra(1, "Xenofobia", "Rechazo u odio hacia los extranjeros."),
        Palabra(2, "Empatía", "Capacidad de identificarse con alguien y compartir sus sentimientos."),
        Palabra(3, "Resiliencia", "Capacidad de adaptación de un ser vivo frente a un agente perturbador."),
        Palabra(4, "Altruismo", "Tendencia a procurar el bien de las personas de manera desinteresada."),
        Palabra(5, "Efímero", "Aquello que dura por un período muy corto de tiempo.")
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