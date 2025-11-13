package com.example.playjuegosv2

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.widget.CheckBox

class Games : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.games)

        // Cargar la toolbar
        val toolbar = findViewById(R.id.toolbar_games) as Toolbar
        setSupportActionBar(toolbar)

        

        // Referencia al FAB
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        // Referencias a los CheckBox
        val cbAngry = findViewById<CheckBox>(R.id.cb_angry_birds)
        val cbDragon = findViewById<CheckBox>(R.id.cb_dragon_fly)
        val cbHill = findViewById<CheckBox>(R.id.cb_hill_climbing)
        val cbRadiant = findViewById<CheckBox>(R.id.cb_radiant_defense)
        val cbPocket = findViewById<CheckBox>(R.id.cb_pocket_soccer)
        val cbNinja = findViewById<CheckBox>(R.id.cb_ninja_jump)

        fab.setOnClickListener {
            val seleccionados = mutableListOf<String>()
            // Crear una lista con los juegos seleccionados
            if (cbAngry.isChecked) seleccionados.add("Angry Birds")
            if (cbDragon.isChecked) seleccionados.add("Dragon Fly")
            if (cbHill.isChecked) seleccionados.add("Hill Climbing Racing")
            if (cbRadiant.isChecked) seleccionados.add("Radiant Defense")
            if (cbPocket.isChecked) seleccionados.add("Pocket Soccer")
            if (cbNinja.isChecked) seleccionados.add("Ninja Jump")

            // Mostrar Toast según si hay selección o no
            if (seleccionados.isEmpty()) {
                Toast.makeText(this, "No has marcado ningún juego", Toast.LENGTH_SHORT).show()
            } else {
                val mensaje = "Has elegido: ${seleccionados.joinToString(", ")}"
                Toast.makeText(this, mensaje, Toast.LENGTH_LONG).show() }
        }

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
}
