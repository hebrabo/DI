package com.example.coffeshops

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

/**
 * MainActivity:
 * --------------
 * Esta actividad actúa como contenedor principal de la aplicación.
 * Su única función es mostrar un NavHostFragment y gestionar la barra superior (Toolbar).
 *
 * Gracias al Navigation Component, los fragments se cargarán dentro del
 * FragmentContainerView definido en activity_main.xml.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Vincula esta actividad con su layout activity_main.xml
        setContentView(R.layout.activity_main)

        /**
         * 1️⃣ CONFIGURACIÓN DEL TOOLBAR
         * -----------------------------
         * Recuperamos la toolbar desde el layout y la convertimos en la ActionBar
         * oficial de la actividad. A partir de este momento podremos controlar:
         *  - El título de la app
         *  - El botón de "volver" (up button)
         *  - Los menús
         */
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        /**
         * IMPORTANTE:
         * ----------
         * No llamamos a NavigationUI.setupActionBarWithNavController()
         * porque la app usa transiciones compartidas y configuraciones manuales.
         * Si se usa sin prepararlo correctamente, la app puede cerrarse.
         *
         * En este caso, el título se controla desde los fragments.
         */
    }
}
