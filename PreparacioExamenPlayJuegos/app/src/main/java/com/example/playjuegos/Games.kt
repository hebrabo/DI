package com.example.playjuegos

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import android.widget.CheckBox
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Games : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.games)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val fab = findViewById<FloatingActionButton>(R.id.floatingActionButton)

        fab.setOnClickListener {
            val checkIds =  arrayOf(R.id.checkboxAngryBirds, R.id.checkboxDragonFly, R.id.checkboxHillClimbing, R.id.checkboxRadiantDefense, R.id.checkboxPocketSoccer, R.id.checkboxNinjaJump)
            val seleccionados = mutableListOf<String>()

            for (id in checkIds) {
                val check = findViewById<CheckBox>(id)
                if (check.isChecked) {
                    seleccionados.add(check.text.toString())
                }
            }

            val mensaje = when (seleccionados.size) {
                0 -> "No has pulsado ninguna opción"
                1 -> "Has elegido ${seleccionados[0]}"
                2 -> "Has elegido ${seleccionados[0]} y ${seleccionados[1]}"
                else -> {
                    val todosMenosUltimo = seleccionados.dropLast(1).joinToString(", ")
                    val ultimo = seleccionados.last()
                    "Has elegido $todosMenosUltimo y $ultimo"
                }
            }

            Toast.makeText(
                this,
                mensaje,
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        when (id) {
            R.id.action_buscar -> {
                val intent = Intent(this, ChipGroup::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_home -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.action_deseo -> {
                val intent = Intent(this, Deseo::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}