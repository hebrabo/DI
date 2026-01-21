package com.di.aprendizajepalabras.data.model

/**
 * DATA CLASS (Clase de Datos):
 * En Kotlin, una 'data class' es una clase diseñada específicamente para guardar datos.
 * Al usarla, Kotlin genera automáticamente funciones útiles como:
 * - equals() y hashCode(): Para comparar si dos palabras son iguales.
 * - copy(): Para crear una copia de una palabra cambiando solo un campo.
 * - toString(): Para imprimir la palabra de forma legible en la consola.
 */
data class Palabra(
    // 1. Un identificador único (ID) para distinguir palabras,
    // fundamental para nuestra lógica de "no repetir palabras".
    val id: Int,

    // 2. El término o palabra técnica que el usuario va a aprender.
    val termino: String,

    // 3. La explicación detallada del significado de la palabra.
    val definicion: String,

    // 4. El idioma (ej. "Español" o "Inglés") para que el
    // Repositorio sepa a qué lista pertenece.
    val idioma: String,

    // 5. URL de la imagen (Opcional).
    // El símbolo '?' indica que este campo puede ser NULO (null).
    // Esto es útil si alguna palabra del diccionario no tiene imagen asociada.
    val imagenUrl: String?
)