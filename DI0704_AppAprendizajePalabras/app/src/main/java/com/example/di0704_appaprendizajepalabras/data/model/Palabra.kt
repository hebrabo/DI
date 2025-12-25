package com.example.di0704_appaprendizajepalabras.data.model

data class Palabra(
    val id: Int, // Identificador único
    val termino: String, // Será la palabra que aparece arriba (ej: "Xenofobia").
    val definicion: String, // El texto descriptivo.
    // imagenUrl: Lo dejo preparado por si decido hacer el "Extra" de mostrar imágenes como la del perro.
    val imagenUrl: String? = null // El ? significa que puede ser nulo (opcional)
)
