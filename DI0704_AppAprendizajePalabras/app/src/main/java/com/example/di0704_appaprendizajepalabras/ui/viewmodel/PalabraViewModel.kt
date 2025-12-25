package com.example.di0704_appaprendizajepalabras.ui.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.di0704_appaprendizajepalabras.data.model.Palabra
import com.example.di0704_appaprendizajepalabras.data.repository.PalabraRepository

class PalabraViewModel : ViewModel() {
    private val repository = PalabraRepository()

    // Estado privado que contiene la palabra actual
    private val _palabraActual = mutableStateOf(repository.obtenerPalabraAleatoria())

    // Estado público que la UI puede leer pero no modificar
    val palabraActual: State<Palabra> = _palabraActual

    // Función para cambiar a una nueva palabra
    fun siguientePalabra() {
        _palabraActual.value = repository.obtenerPalabraAleatoria()
    }
}