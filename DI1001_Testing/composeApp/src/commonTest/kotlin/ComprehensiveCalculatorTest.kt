import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.*
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class ComprehensiveCalculatorTest {

    private val testDispatcher = StandardTestDispatcher()
    private val testScope = TestScope(testDispatcher)

    private val calculator = ComprehensiveCalculator()

    @BeforeTest
    fun setUp() {
        // Configuramos el despachador para controlar el tiempo en corrutinas
        Dispatchers.setMain(testDispatcher)
    }

    @AfterTest
    fun tearDown() {
        // Limpiamos el hilo principal tras finalizar
        Dispatchers.resetMain()
    }

    // ==========================================================
    // SECCIÓN 1: OPERACIONES BÁSICAS Y ESTADO
    // ==========================================================

    @Test
    fun `add integers two plus two returns four`() {
        assertEquals(4, calculator.addIntegers(2, 2), "Sum should be 4")
    }

    @Test
    fun `add floats two point five plus three point five returns six`() {
        assertEquals(6.0f, calculator.addFloats(2.5f, 3.5f), 0.0001f, "Sum should be 6.0")
    }

    @Test
    fun `increment state once increments by one`() {
        calculator.incrementState()
        assertEquals(1, calculator.getState(), "State should be incremented by 1")
    }

    // ==========================================================
    // SECCIÓN 2: NUEVOS TESTS (DIVISIÓN Y LÍMITES)
    // ==========================================================

    /**
     * TEST DE FUNCIONALIDAD BÁSICA (Ejercicio 2)
     * Verifica la lógica principal siguiendo el patrón AAA.
     */
    @Test
    fun `divide integers ten by two returns five`() {
        // Arrange: Preparar datos
        val a = 10; val b = 2; val expected = 5
        // Act: Ejecutar acción
        val actual = calculator.divideIntegers(a, b)
        // Assert: Verificar resultado
        assertEquals(expected, actual, "10 / 2 should be 5")
    }

    /**
     * TEST DE ROBUSTEZ (Ejercicio 2)
     * Verifica que el código gestiona errores (división por cero)
     * lanzando la excepción esperada.
     */
    @Test
    fun `divide by zero throws illegal argument exception`() {
        assertFailsWith<IllegalArgumentException> {
            calculator.divideIntegers(10, 0)
        }
    }

    /**
     * TEST DE LÍMITES (Edge Case - Números grandes)
     * Comprueba que no hay desbordamientos (overflow) con números cercanos al límite de Int.
     */
    @Test
    fun `division with large numbers returns correct quotient`() {
        assertEquals(1000000000, calculator.divideIntegers(2000000000, 2))
    }

    /**
     * TEST DE LÍMITES (Edge Case - Truncamiento)
     * Probar cómo se comporta la división entera cuando hay decimales.
     * En Kotlin, el tipo Int siempre trunca hacia cero.
     */
    @Test
    fun `division resulting in decimals truncates correctly`() {
        val actual = calculator.divideIntegers(5, 2)
        assertEquals(2, actual, "La división entera debe truncar decimales")
    }

    // ==========================================================
    // SECCIÓN 3: TESTS ASÍNCRONOS (Corrutinas y Flow)
    // ==========================================================

    @Test
    fun `fetch result async returns expected result`() = runBlocking {
        assertEquals(42, calculator.fetchResultAsync(), "Result should be 42")
    }

    @Test
    fun `fetch result flow returns expected flow`() = runBlocking {
        val expected = listOf(1, 2, 3)
        val actual = calculator.fetchResultFlow().toList()
        assertContentEquals(expected, actual, "Flow should emit 1, 2, 3")
    }

    @Test
    fun `update state flow updates state flow value`() = testScope.runTest {
        val newValue = 5
        val deferredResult = async { calculator.updateStateFlow(newValue) }
        advanceTimeBy(500L) // Simulamos el paso del tiempo
        deferredResult.await()
        assertEquals(newValue, calculator.stateFlow.value)
    }

    @Test
    fun `update state flow with advanceTimeBy updates state flow value`() = runTest {
        advanceTimeBy(500L)
        calculator.updateStateFlow(5)
        assertEquals(5, calculator.stateFlow.value)
    }
}