package com.example.playjuegos

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navView: NavigationView
    private lateinit var toggle: ActionBarDrawerToggle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Toolbar
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        // DrawerLayout
        drawerLayout = findViewById(R.id.drawer_layout)
        navView = findViewById(R.id.nav_view)

        // Toggle del Drawer
        toggle = ActionBarDrawerToggle(
            this, drawerLayout, toolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )

        // Cambiar el color del ícono del hamburger
        toggle.drawerArrowDrawable.color = android.graphics.Color.WHITE
        
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Botones principales
        findViewById<Button>(R.id.button).setOnClickListener {
            startActivity(Intent(this, Games::class.java))
        }
        findViewById<Button>(R.id.button2).setOnClickListener {
            startActivity(Intent(this, NewPlayer::class.java))
        }
        findViewById<Button>(R.id.button3).setOnClickListener {
            startActivity(Intent(this, Preferences::class.java))
        }


        // Aplicar padding solo al contenido, no a Toolbar
        val contentLayout = findViewById<LinearLayout>(R.id.content_layout)
        ViewCompat.setOnApplyWindowInsetsListener(contentLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // NavigationView listener
        navView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_main -> startActivity(Intent(this, MainActivity::class.java))
                R.id.nav_games -> startActivity(Intent(this, Games::class.java))
                R.id.nav_newplayer -> startActivity(Intent(this, NewPlayer::class.java))
                R.id.nav_preferences -> startActivity(Intent(this, Preferences::class.java))
                R.id.nav_chipgroup -> startActivity(Intent(this, ChipGroup::class.java))
                R.id.nav_deseo -> startActivity(Intent(this, Deseo::class.java))
            }
            drawerLayout.closeDrawers()
            true
        }
    }

    override fun onCreateOptionsMenu(menu: android.view.Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: android.view.MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_buscar -> startActivity(Intent(this, ChipGroup::class.java))
            R.id.action_home -> startActivity(Intent(this, MainActivity::class.java))
            R.id.action_deseo -> startActivity(Intent(this, Deseo::class.java))
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }
}
