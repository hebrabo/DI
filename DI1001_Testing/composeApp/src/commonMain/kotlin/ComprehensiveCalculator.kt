import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow

class ComprehensiveCalculator {

    private var internalState: Int = 0

    private val _stateFlow: MutableStateFlow<Int> = MutableStateFlow(0)
    val stateFlow: StateFlow<Int> = _stateFlow

    // --- Operaciones Aritméticas ---

    fun addIntegers(a: Int, b: Int): Int {
        return a + b
    }

    fun addFloats(a: Float, b: Float): Float {
        return a + b
    }

    /**
     * Nueva función de división (Ejercicio 2).
     * Aplica 'require' para asegurar la robustez, lanzando excepción si el divisor es 0.
     */
    fun divideIntegers(a: Int, b: Int): Int {
        require(b != 0) { "El divisor no puede ser cero" }
        return a / b
    }

    // --- Gestión de Estado ---

    fun incrementState() {
        internalState += 1
    }

    fun getState(): Int {
        return internalState
    }

    // --- Operaciones Asíncronas (Corrutinas y Flow) ---

    suspend fun fetchResultAsync(): Int {
        delay(1000L)  // Simula operación de larga duración
        return 42
    }

    fun fetchResultFlow(): Flow<Int> = flow {
        emit(1)
        emit(2)
        emit(3)
    }

    suspend fun updateStateFlow(newValue: Int) {
        delay(500L)  // Simula retardo
        _stateFlow.value = newValue
    }
}