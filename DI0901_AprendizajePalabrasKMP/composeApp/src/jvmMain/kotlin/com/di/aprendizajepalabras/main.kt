package com.di.aprendizajepalabras

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

/**
 * Punto de entrada para la versión de Escritorio (PC).
 */
fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Aprendizaje ABN - Palabras", // Título de la ventana
    ) {
        App() // Aquí llamamos a tu función App() que está en commonMain
    }
}