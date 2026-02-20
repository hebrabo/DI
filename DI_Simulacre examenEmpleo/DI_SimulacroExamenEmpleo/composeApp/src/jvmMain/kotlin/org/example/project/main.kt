package org.example.project

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import androidx.compose.ui.unit.dp

/**
 * PUNTO DE ENTRADA PARA DESKTOP (JVM):
 * Esta función es el "main" específico para la plataforma de escritorio.
 * Cumple con el Punto 6 del examen: hacer el proyecto ejecutable en Desktop.
 */
fun main() = application {
    /**
     * GESTIÓN DE LA VENTANA:
     * 'rememberWindowState' define las dimensiones iniciales al abrir la app en PC.
     * Usar 800x600 garantiza que la 'MediumTopAppBar' y los formularios
     * se visualicen correctamente sin cortes desde el inicio.
     */
    val windowState = rememberWindowState(width = 800.dp, height = 600.dp)

    /**
     * COMPONENTE WINDOW:
     * Define la ventana nativa del sistema operativo (Windows, macOS o Linux).
     */
    Window(
        // Cierra el proceso de la aplicación al cerrar la ventana.
        onCloseRequest = ::exitApplication,

        // Título que aparecerá en la barra de la ventana del sistema.
        title = "Portal Empleo",

        // Aplicamos el estado de tamaño definido anteriormente.
        state = windowState
    ) {
        /**
         * INTEGRACIÓN MULTIPLATAFORMA:
         * Llamamos a la función App() que reside en 'commonMain'.
         * Esto demuestra la potencia de KMP: el mismo código de UI
         * que corre en Android se ejecuta aquí sobre la JVM de escritorio.
         */
        App()
    }
}