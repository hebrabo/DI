package com.example.playjuegosv2

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.content.res.ResourcesCompat
import com.example.playjuegosv2.ui.theme.PlayJuegosV2Theme


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Cargar la toolbar
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        // Fuente personalizada para el título
        val titulo = findViewById<TextView>(R.id.titulo)
        titulo.typeface = ResourcesCompat.getFont(this, R.font.courgette_regular)


        // Botón Nuevo Jugador
        val jugador = findViewById<Button>(R.id.button2) as Button

        jugador.setOnClickListener { lanzarNewPlayer() }
    }

    // Activar (inflar) el menú. Acciones al pulsar ítems del menú
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            (R.id.action_search) -> {return true}
            (R.id.action_add) -> {return true}
            else -> {return super.onOptionsItemSelected(item)} }
    }

    // Lanzar actividad Nuevo Jugador
    fun lanzarNewPlayer() {
            val i = Intent(this, NewPlayer::class.java)
            startActivity(i)
        }
    }

