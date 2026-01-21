package com.di.aprendizajepalabras

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "DI0901_AprendizajePalabrasKMP",
    ) {
        App()
    }
}