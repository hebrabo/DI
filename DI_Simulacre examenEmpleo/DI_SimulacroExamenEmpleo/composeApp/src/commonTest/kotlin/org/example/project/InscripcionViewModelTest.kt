package org.example.project

import org.example.project.viewmodel.InscripcionViewModel
import kotlin.test.Test
import kotlin.test.assertTrue
import kotlin.test.assertFalse

/**
 * PRUEBAS UNITARIAS (Punto 5): Validación de la lógica de negocio.
 * Esta clase se encarga de verificar que las funciones del ViewModel
 * funcionan correctamente sin necesidad de ejecutar la interfaz gráfica.
 */
class InscripcionViewModelTest {

    // Instanciamos el ViewModel que queremos probar.
    // Al estar en commonTest, estas pruebas pueden ejecutarse tanto en Android como en JVM.
    private val viewModel = InscripcionViewModel()

    /**
     * TEST: Validación del nombre.
     * Verifica que el sistema solo acepte caracteres alfabéticos y espacios.
     */
    @Test
    fun testValidacionNombre() {
        // Caso de éxito: Un nombre estándar debe ser válido.
        assertTrue(viewModel.esNombreValido("Juan Alberto"))

        // Caso de error: Si contiene números o símbolos, debe retornar false.
        // Esto previene que se guarden datos corruptos en la lista de solicitudes.
        assertFalse(viewModel.esNombreValido("Juan123"))
    }

    /**
     * TEST: Validación del DNI.
     * Comprueba que se cumpla el patrón de 8 dígitos seguidos de una letra.
     */
    @Test
    fun testValidacionDni() {
        // Caso de éxito: Formato NIF estándar (8 números + letra).
        assertTrue(viewModel.esDniValido("12345678Z"))

        // Caso de error: Longitud insuficiente.
        assertFalse(viewModel.esDniValido("12345Z"))

        // Caso de error: Formato sin letra al final.
        assertFalse(viewModel.esDniValido("123456789"))
    }

    /**
     * TEST: Validación del Email.
     * Asegura que el texto introducido tenga una estructura de correo electrónico válida.
     */
    @Test
    fun testValidacionEmail() {
        // Caso de éxito: Email con estructura usuario@dominio.com.
        assertTrue(viewModel.esEmailValido("profe@examen.com"))

        // Caso de error: Falta el símbolo '@', un requisito esencial del formato.
        assertFalse(viewModel.esEmailValido("profe.com"))
    }
}