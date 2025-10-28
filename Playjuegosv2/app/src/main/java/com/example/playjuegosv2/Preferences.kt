package com.example.playjuegosv2

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.RatingBar
import android.widget.SeekBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import android.widget.Toast
import com.google.android.material.floatingactionbutton.FloatingActionButton

class Preferences : AppCompatActivity() {

    // Variable para guardar la opción seleccionada
    private var option: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.preferences)

        // Cargar la toolbar
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        // Obtener referencia al RadioGroup
        val rGroup = findViewById<RadioGroup>(R.id.radioGroup)

        rGroup.setOnCheckedChangeListener(object : RadioGroup.OnCheckedChangeListener {
            override fun onCheckedChanged(group: RadioGroup, checkedId: Int) {
                // Obtener el RadioButton seleccionado
                val checkedRadioButton = rGroup.findViewById<RadioButton>(checkedId)
                option = checkedRadioButton.text as String
            }
        })

        // Inicializar ratingbar y SeekBar
        val rb = findViewById<RatingBar>(R.id.ratingBar)
        val sb = findViewById<SeekBar>(R.id.seekBar)

        // Ajustar el rango de la SeekBar al número de estrellas del RatingBar
        sb.max = rb.numStars

        // Cambiar el RatingBar actualiza la SeekBar.
        rb.onRatingBarChangeListener = RatingBar.OnRatingBarChangeListener { ratingBar, rating, fromUser ->
            sb.progress = rating.toInt()
        }

        // Cambiar la SeekBar actualiza el RatingBar.
        sb.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                rb.rating = progress.toFloat() //actualiza el ratingBar
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        })

        // FloatingActionButton
        val fab = findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {
            if (option.isEmpty()) {
                Toast.makeText(this, "No has pulsado ninguna opción", Toast.LENGTH_LONG).show()
            } else {
                val rating = rb.rating.toInt()
                Toast.makeText(this, "Has votado por $option con $rating estrellas", Toast.LENGTH_LONG).show()
            }
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
