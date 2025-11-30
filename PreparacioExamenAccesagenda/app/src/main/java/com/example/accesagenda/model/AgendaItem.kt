package com.example.accesagenda.model

import androidx.annotation.DrawableRes

/**
 * Clase de datos que representa una única actividad o tarea dentro de la agenda.
 * Se utiliza como el modelo fundamental para poblar los RecyclerViews
 * (tanto en el HomeFragment como en el SocialFeedFragment).
 *
 * NOTA: El uso de 'data class' genera automáticamente métodos como equals(), hashCode(),
 * toString(), y copy(), ideales para modelos de datos en Kotlin.
 */
data class AgendaItem(
    // Título breve de la actividad (ej. "Cita con el Veterinario", "Reunión Sala Juntas").
    val title: String,

    // Hora o descripción temporal (ej. "11:00 AM", "Mañana - 09:00 AM", "25 de Nov").
    val time: String,

    // Recurso (ID de Drawable) para el icono de estado pequeño (ej. Corazón, Reloj de Cumplir).
    // Es opcional (Int?) porque no todos los ítems lo requieren.
    @DrawableRes val iconRes: Int?,

    // Recurso (ID de Drawable) para la imagen principal de la tarjeta (el círculo o la foto grande).
    // Es opcional y tiene un valor por defecto de null.
    @DrawableRes val imageResId: Int? = null
)