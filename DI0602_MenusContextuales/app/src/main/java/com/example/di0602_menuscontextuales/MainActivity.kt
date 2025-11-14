package com.example.di0602_menuscontextuales

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.google.android.material.navigation.NavigationView

/**
 * MainActivity que sirve como contenedor principal de la aplicación.
 * Maneja el DrawerLayout, Toolbar y la navegación entre fragments.
 */
class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configuración de la Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Referencia al DrawerLayout para poder abrir/cerrar el menú lateral
        drawerLayout = findViewById(R.id.drawer_layout)

        // Obtenemos el NavController a través del NavHostFragment
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // Configuración de AppBar con destinos top-level y DrawerLayout
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,    // Fragmento "Home" es top-level
                R.id.nav_gallery  // Fragmento "Gallery" es top-level
                // Agregar más destinos top-level según sea necesario
            ),
            drawerLayout
        )

        // Conectar Toolbar con NavController para manejar título y botón "hamburguesa"
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

        // Configurar NavigationView (menú lateral) con el NavController
        val navView: NavigationView = findViewById(R.id.nav_view)
        NavigationUI.setupWithNavController(navView, navController)
    }

    /**
     * Permite que el botón "hamburguesa" abra/cierre el Drawer
     * cuando se presiona en la Toolbar
     */
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }
}
