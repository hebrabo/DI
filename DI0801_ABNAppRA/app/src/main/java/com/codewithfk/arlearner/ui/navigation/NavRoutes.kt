package com.codewithfk.arlearner.ui.navigation

import kotlinx.serialization.Serializable

/**
 * Este archivo define las "paradas" o destinos de nuestra aplicación.
 * Usamos la librería de Navegación de Jetpack Compose (versión 2.8.0+),
 * que permite que las rutas sean objetos reales en lugar de simples textos (Strings).
 *
 * La anotación @Serializable es obligatoria para que la librería pueda convertir
 * estos objetos en rutas internas de forma segura.
 */

@Serializable
object HomeScreen
// Representa la pantalla de inicio. Al ser un 'object', no necesita parámetros
// porque solo sirve para mostrar el menú principal.

@Serializable
data class ARScreen(val model: String)
// Representa la pantalla de Realidad Aumentada.
// Usamos una 'data class' porque necesitamos pasar información: el 'model'.
// El 'model' será el número (ej: "5") que el niño elija para que la cámara
// sepa que debe cargar 5 manzanas o el objeto correspondiente.

@Serializable
object AlphabetScreen
// Aunque el nombre técnico es AlphabetScreen (por compatibilidad con el código original),
// en nuestra app actúa como la pantalla de "Lista de Números" para el método ABN.
// Es donde el niño elige el reto con el que quiere jugar.

@Serializable
object QuizScreen
// Representa el modo desafío o Quiz. Aquí es donde el niño debe identificar
// qué cantidad de objetos está viendo en Realidad Aumentada.