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

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController
    private lateinit var appBarConfiguration: AppBarConfiguration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout)

        // NavController
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

        // AppBarConfiguration con DrawerLayout y destinos top-level
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, // tu primer fragmento
                R.id.nav_gallery // otro fragmento
                // agrega aquí todos los destinos top-level de tu nav_graph
            ),
            drawerLayout
        )

        // Configurar Toolbar con NavController
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)

        // NavigationView (menú lateral)
        val navView: NavigationView = findViewById(R.id.nav_view)
        NavigationUI.setupWithNavController(navView, navController)
    }

    // Permite que el botón "hamburguesa" abra/cierre el Drawer
    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }
}

