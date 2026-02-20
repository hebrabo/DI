package org.example.project.viewmodel

import androidx.compose.runtime.mutableStateListOf
import org.example.project.model.Persona

class InscripcionViewModel {
    // El "vector" solicitado en el examen
    private val _listaInscritos = mutableStateListOf<Persona>()
    val listaInscritos: List<Persona> get() = _listaInscritos

    // Validaciones (Comprobación de tipos)
    fun esNombreValido(nombre: String): Boolean = nombre.all { it.isLetter() || it.isWhitespace() }
    fun esDniValido(dni: String): Boolean = dni.matches(Regex("^[0-9]{8}[A-Za-z]$"))
    fun esEmailValido(email: String): Boolean = email.matches(Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$"))

    fun guardarPersona(p: Persona): Boolean {
        if (esNombreValido(p.nombre) && esDniValido(p.dni) && esEmailValido(p.email)) {
            _listaInscritos.add(p)
            return true
        }
        return false
    }

    fun eliminarPersona(p: Persona) {
        _listaInscritos.remove(p)
    }
}