package com.example.proyectocoffeeshops

// Data class: se usa para representar datos de manera simple y concisa
// En este caso, representa la información de un Coffee Shop
data class CoffeeShop(
    val nombre: String,      // Nombre del café
    val rating: Float,       // Calificación del café (por ejemplo, 4.5 estrellas)
    val ubicacion: String,   // Dirección o ubicación del café
    val imagenResId: Int     // ID del recurso de imagen (por ejemplo, R.drawable.images)
)
