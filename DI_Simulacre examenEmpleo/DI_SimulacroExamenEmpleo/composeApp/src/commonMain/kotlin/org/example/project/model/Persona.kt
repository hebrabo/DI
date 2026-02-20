package org.example.project.model

/**
 * CLASE DE DATOS: Representa la entidad principal.
 * Se utiliza una 'data class' porque su función principal es almacenar estado.
 */

data class Persona(
    val nombre: String,
    val dni: String,
    val edad: String,
    val sexo: String,
    val direccion: String,
    val email: String,
    val telefono: String,
    val habilidades: String
)