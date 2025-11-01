package com.example.proyectoelsol

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Actividad principal de la aplicación "El Sol".
 * Muestra un listado de fenómenos solares en un RecyclerView y,
 * a través de la Toolbar, permite acceder a una comparativa de planetas.
 */
class MainActivity : AppCompatActivity() {

    // Declaramos variables de clase
    private lateinit var recyclerSolar: RecyclerView
    private lateinit var solarAdapter: SolarAdapter
    private lateinit var listaSolar: ArrayList<Solar>

    /**
     * Metodo principal que se ejecuta al iniciar la actividad.
     * Aquí se configura la interfaz, la Toolbar y el RecyclerView.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main) // Enlazamos el layout principal

        // Configuración de la Toolbar principal
        val toolbar: Toolbar = findViewById(R.id.toolbarMain)
        setSupportActionBar(toolbar) // Convertimos la Toolbar en la barra de acciones

        // Configuración del RecyclerView (cuadrícula de tarjetas)
        recyclerSolar = findViewById(R.id.recyclerSolar)
        recyclerSolar.layoutManager = GridLayoutManager(this, 2)

        // Creamos una lista de elementos Solar (nombre + imagen)
        listaSolar = arrayListOf(
            Solar("Corona solar", R.drawable.corona_solar),
            Solar("Erupción solar", R.drawable.erupcionsolar),
            Solar("Espículas", R.drawable.espiculas),
            Solar("Filamentos", R.drawable.filamentos),
            Solar("Magnetosfera", R.drawable.magnetosfera),
            Solar("Mancha solar", R.drawable.manchasolar)
        )

        // Asignamos el adaptador personalizado
        solarAdapter = SolarAdapter(listaSolar)
        recyclerSolar.adapter = solarAdapter
    }

    /**
     * Infla el menú de la Toolbar (los tres puntitos de la app principal).
     * El menú está definido en res/menu/menu_main.xml.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    /**
     * Gestiona las acciones del menú principal de la Toolbar.
     * En este caso, solo una opción: “Comparar planetas”.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.opcion_comparar -> {
                mostrarComparativaPlanetas() // Abrir el diálogo de comparación
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Muestra un cuadro de diálogo (AlertDialog) con un layout personalizado
     * para comparar datos entre dos planetas: diámetro, distancia al Sol y densidad.
     */
    private fun mostrarComparativaPlanetas() {

        // Inflamos el layout del diálogo (res/layout/layout_comparar_planetas.xml)
        val view = layoutInflater.inflate(R.layout.layout_comparar_planetas, null)

        // Referencias a los AutoCompleteTextView (para seleccionar planetas)
        val auto1: AutoCompleteTextView = view.findViewById(R.id.autoPlaneta1)
        val auto2: AutoCompleteTextView = view.findViewById(R.id.autoPlaneta2)

        // Referencias a los TextView de la tabla comparativa
        val colPlaneta1: TextView = view.findViewById(R.id.colPlaneta1)
        val colPlaneta2: TextView = view.findViewById(R.id.colPlaneta2)
        val diametro1: TextView = view.findViewById(R.id.diametro1)
        val diametro2: TextView = view.findViewById(R.id.diametro2)
        val distancia1: TextView = view.findViewById(R.id.distancia1)
        val distancia2: TextView = view.findViewById(R.id.distancia2)
        val densidad1: TextView = view.findViewById(R.id.densidad1)
        val densidad2: TextView = view.findViewById(R.id.densidad2)

        // Adaptador de los nombres de planetas (para el menú desplegable)
        val nombresPlanetas = listaPlanetas.map { it.nombre }
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, nombresPlanetas)
        auto1.setAdapter(adapter)
        auto2.setAdapter(adapter)

        // Creamos el diálogo
        val dialog = AlertDialog.Builder(this)
            .setTitle("Comparar planetas")
            .setView(view)
            .setPositiveButton("Cerrar", null) // Solo botón de cierre
            .create()

        /// Hacemos que el dropdown se muestre al tocar el campo
        dialog.setOnShowListener {
            auto1.threshold = 1
            auto2.threshold = 1

            // Mostrar lista al tocar el primer campo
            auto1.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    v.post { auto1.showDropDown() }
                }
                false
            }


            // Mostrar lista al tocar el segundo campo
            auto2.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_DOWN) {
                    v.post { auto2.showDropDown() }
                }
                false
            }
        }

        dialog.show() // Mostramos el diálogo al usuario


        /**
         * Función interna que actualiza la tabla comparativa
         * según los planetas seleccionados en los AutoCompleteTextView.
         */
        fun actualizarTabla() {
            val p1 = listaPlanetas.find { it.nombre == auto1.text.toString() }
            val p2 = listaPlanetas.find { it.nombre == auto2.text.toString() }

            if (p1 != null && p2 != null) {
                // Asignamos los datos a las columnas correspondientes
                colPlaneta1.text = p1.nombre
                colPlaneta2.text = p2.nombre
                diametro1.text = p1.diametro.toString()
                diametro2.text = p2.diametro.toString()
                distancia1.text = p1.distanciaSol.toString()
                distancia2.text = p2.distanciaSol.toString()
                densidad1.text = p1.densidad.toString()
                densidad2.text = p2.densidad.toString()
            }
        }

        // Detectar selección de planetas y actualizar la tabla
        auto1.setOnItemClickListener { _, _, _, _ -> actualizarTabla() }
        auto2.setOnItemClickListener { _, _, _, _ -> actualizarTabla() }
    }
}

