package com.example.di0704_appaprendizajepalabras.data.model

data class Palabra(
    val id: Int,
    val termino: String,
    val definicion: String,
    val idioma: String,       // 4º lugar
    val imagenUrl: String?    // 5º lugar (Opcional)
)