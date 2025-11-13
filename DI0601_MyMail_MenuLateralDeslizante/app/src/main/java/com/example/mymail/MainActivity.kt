package com.example.mymail

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import android.widget.Spinner
import androidx.fragment.app.Fragment

// MainActivity hereda de AppCompatActivity porque vamos a usar Toolbar y DrawerLayout
class MainActivity : AppCompatActivity() {

    // Declaramos variables para el Drawer, NavigationView y Toolbar
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Indicamos el layout principal de la actividad
        setContentView(R.layout.activity_main)

        // Inicializamos la Toolbar y la configuramos como ActionBar
        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        // Inicializamos el DrawerLayout y la NavigationView
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        // Creamos un toggle para la Toolbar que abre/cierra el Drawer
        // R.string.open_drawer y R.string.close_drawer son para accesibilidad (TalkBack)
        val toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.open_drawer, R.string.close_drawer
        )
        drawerLayout.addDrawerListener(toggle) // Añadimos el listener al Drawer
        toggle.syncState() // Sincronizamos el estado del toggle con el Drawer

        // ----------------------------
        // Configuración del Spinner de emails en el header
        // ----------------------------
        val header = navView.getHeaderView(0) // Obtenemos la vista del header
        val spinner = header.findViewById<Spinner>(R.id.email_spinner) // Spinner del header
        val emails = listOf("usuario1@mail.com", "usuario2@mail.com") // Lista de emails
        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item, // Layout simple para el item del Spinner
            emails
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter // Asignamos el adapter al Spinner

        // ----------------------------
        // Listener para los items del menú lateral
        // ----------------------------
        navView.setNavigationItemSelectedListener { menuItem ->
            // Dependiendo del item pulsado, cargamos el fragment y el título correspondiente
            when (menuItem.itemId) {
                R.id.nav_inbox -> replaceFragment(InboxFragment(), "Inbox")
                R.id.nav_outbox -> replaceFragment(OutboxFragment(), "Outbox")
                R.id.nav_trash -> replaceFragment(TrashFragment(), "Trash")
                R.id.nav_spam -> replaceFragment(SpamFragment(), "Spam")
            }
            // Cerramos el Drawer después de seleccionar un item
            drawerLayout.closeDrawer(GravityCompat.START)
            true
        }

        // ----------------------------
        // Cargar fragment inicial al iniciar la app
        // ----------------------------
        if (savedInstanceState == null) {
            replaceFragment(InboxFragment(), "Inbox") // Mostramos Inbox al inicio con título
        }
    }

    // Función para reemplazar el fragment mostrado en el contenedor y actualizar el título
    private fun replaceFragment(fragment: Fragment, title: String) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment) // Reemplaza el fragment actual
            .commit() // Aplica los cambios

        toolbar.title = title // Actualiza el título de la Toolbar al fragment seleccionado
    }
}
