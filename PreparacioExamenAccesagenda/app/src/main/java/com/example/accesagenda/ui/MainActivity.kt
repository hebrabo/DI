package com.example.accesagenda.ui

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment // Para obtener el controlador de navegación
import androidx.navigation.ui.setupWithNavController // Para vincular la barra de navegación inferior
import com.example.accesagenda.R
import com.example.accesagenda.databinding.ActivityMainBinding // Clase de binding generada

/**
 * La actividad principal y contenedor de la aplicación.
 * Es responsable de configurar el sistema de navegación (Navigation Component)
 * y la barra de navegación inferior (BottomNavigationView).
 */
class MainActivity : AppCompatActivity() {

    // 1. View Binding: Declaración tardía de la instancia de binding.
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 2. Inicializar View Binding: Infla el layout de la actividad.
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // 3. Obtener el NavController:
        // Buscamos el Fragmento que actúa como host de navegación.
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment

        // Obtenemos el controlador que maneja la navegación.
        val navController = navHostFragment.navController

        // 4. Vincular BottomNavigationView:
        // Conecta los ítems del menú de la barra inferior con los destinos del grafo de navegación.
        binding.bottomNavigation.setupWithNavController(navController)

        // 5. Lógica de Visibilidad Condicional: Ocultar la barra inferior en el Login.
        navController.addOnDestinationChangedListener { _, destination, _ ->
            // Escucha cada vez que cambia el destino (Fragmento) activo.
            if (destination.id == R.id.loginFragment) {
                // Si el destino es la pantalla de Login, oculta la barra.
                binding.bottomNavigation.visibility = View.GONE
            } else {
                // Para cualquier otro destino (Home, Agenda, etc.), muestra la barra.
                binding.bottomNavigation.visibility = View.VISIBLE
            }
        }
    }
}