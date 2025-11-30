package com.example.accesagenda.model

import androidx.annotation.DrawableRes

/**
 * Clase de datos que representa una categoría de actividad en la pantalla de inicio (HomeFragment).
 * Esta estructura es usada por el CategoryAdapter para poblar el RecyclerView horizontal.
 */
data class CategoryItem(
    // Nombre visible de la categoría (ej. "Gimnasio", "Compra", "Pago de Servicios").
    val name: String,

    // Recurso (ID de Drawable) para la imagen o icono circular que representa la categoría.
    // Es opcional (Int?) para permitir categorías sin un recurso visual específico.
    @DrawableRes val imageResId: Int? = null
)