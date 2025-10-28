package com.example.playjuegosv2

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


class NewPlayer : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.newplayer)

        // Cargar la toolbar
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)



        // Obtener referencias a los EditText
        val etName = findViewById(R.id.editTextName) as EditText
        val etPhoneticName = findViewById(R.id.editTextPhoneticName) as EditText
        val etNickname = findViewById(R.id.editTextNickname) as EditText
        val etEmail = findViewById(R.id.editTextEmailAddress) as EditText

        // Obtener textos introducidos
        val nameText = etName.text
        val phoneticText = etPhoneticName.text
        val nicknameText = etNickname.text
        val emailText = etEmail.text

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
