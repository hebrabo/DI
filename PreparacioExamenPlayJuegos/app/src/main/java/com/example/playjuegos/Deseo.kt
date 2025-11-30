package com.example.playjuegos

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar

class Deseo : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.deseo)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        val nombreJuego = findViewById<EditText>(R.id.editTextNombre)
        val spinnerPlataformas = findViewById<Spinner>(R.id.spinnerPlataformas)
        val editTextTematica = findViewById<EditText>(R.id.editTextTematica)
        val ratingBarGanas = findViewById<RatingBar>(R.id.ratingBarGanas)
        val botonCancelar = findViewById<Button>(R.id.buttonCancelar)
        val botonEnviar = findViewById<Button>(R.id.buttonEnviar)

        // Spinner con plataformas
        val plataformas = arrayOf("PS4", "PC", "XBOX", "WII", "WIIU", "3DS", "X360")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, plataformas)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerPlataformas.adapter = adapter

        // Cancelar → borrar todos los campos
        botonCancelar.setOnClickListener {
            nombreJuego.text.clear()
            spinnerPlataformas.setSelection(0)
            editTextTematica.text.clear()
            ratingBarGanas.rating = 0f
        }

        // Enviar → mostrar mensaje y borrar campos
        botonEnviar.setOnClickListener {
            Toast.makeText(this, "Petición enviada", Toast.LENGTH_LONG).show()
            nombreJuego.text.clear()
            spinnerPlataformas.setSelection(0)
            editTextTematica.text.clear()
            ratingBarGanas.rating = 0f
        }
    }

    // Menu igual que el resto de Activities
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.action_buscar) {
            val intent = Intent(this, ChipGroup::class.java)
            startActivity(intent)
            return true
        }
        if (id == R.id.action_home) {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}