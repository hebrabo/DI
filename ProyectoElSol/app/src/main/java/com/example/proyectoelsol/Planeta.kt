package com.example.proyectoelsol

/**
 * Clase de datos (data class) que representa la información básica de un planeta.
 * Cada planeta tiene un nombre, diámetro relativo, distancia al Sol y densidad.
 */
data class Planeta(
    val nombre: String,       // Nombre del planeta (por ejemplo, "Tierra")
    val diametro: Double,     // Tamaño relativo al de la Tierra (Tierra = 1.0)
    val distanciaSol: Double, // Distancia al Sol en unidades astronómicas (UA)
    val densidad: Int         // Densidad media del planeta en kg/m³ (aprox.)
)

/**
 * Lista inmutable que contiene los datos de todos los planetas del sistema solar.
 * Se usa para poblar los AutoCompleteTextView en el diálogo de comparación.
 */
val listaPlanetas = listOf(
    Planeta("Mercurio", 0.382, 0.387, 5400),   // Planeta más cercano al Sol
    Planeta("Venus", 0.949, 0.723, 5250),      // Segundo planeta, muy caliente
    Planeta("Tierra", 1.0, 1.0, 5520),         // Planeta de referencia (1 UA)
    Planeta("Marte", 0.53, 1.542, 3960),       // Planeta rojo
    Planeta("Júpiter", 11.2, 5.203, 1350),     // Gigante gaseoso más grande
    Planeta("Saturno", 9.41, 9.539, 700),      // Gigante gaseoso con anillos
    Planeta("Urano", 3.38, 19.81, 1200),       // Gigante helado con rotación extrema
    Planeta("Neptuno", 3.81, 30.07, 1500),     // Planeta más lejano del Sol conocido
    Planeta("Plutón", 0.18, 39.44, 500)        // Planeta enano, valores estimados
)

