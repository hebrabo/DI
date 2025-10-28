package com.example.playjuegosv2

import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.floatingactionbutton.FloatingActionButton
import android.widget.TextView

class Chips : AppCompatActivity() {

    private lateinit var fab: FloatingActionButton
    private var fabElevated = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chips)

        val chipGroup = findViewById<ChipGroup>(R.id.chipGroupFiltros)
        val bottomAppBar = findViewById<BottomAppBar>(R.id.bottom_appbar)
        bottomAppBar.replaceMenu(R.menu.menu_main)

        fab = findViewById(R.id.fab)

        //  Mostrar Toast cuando se selecciona un chip
        chipGroup.setOnCheckedStateChangeListener { group, checkedIds ->
            if (checkedIds.isNotEmpty()) {
                val chipId = checkedIds[0]
                val chip = group.findViewById<Chip>(chipId)
                Toast.makeText(this, "Has seleccionado: ${chip.text}", Toast.LENGTH_SHORT).show()
            }
        }

        // Mostrar Toast cuando se selecciona un género
        val generos = listOf(
            R.id.textAccion,
            R.id.textAventura,
            R.id.textDeportes,
            R.id.textDisparos,
            R.id.textEstrategia,
            R.id.textLucha,
            R.id.textMusical,
            R.id.textRol,
            R.id.textSimulacion
        )

        for (id in generos) {
            findViewById<TextView>(id).setOnClickListener { textView ->
                Toast.makeText(this, "Has seleccionado: ${(textView as TextView).text}", Toast.LENGTH_SHORT).show()
            }
        }


        //  Animación del FAB al pulsarlo
        fab.setOnClickListener {
            if (!fabElevated) {
                // Desplazar hacia arriba
                ObjectAnimator.ofFloat(fab, View.TRANSLATION_Y, -100f).apply {
                    duration = 300
                    start()
                }
                fabElevated = true
            } else {
                // Volver a la posición original
                ObjectAnimator.ofFloat(fab, View.TRANSLATION_Y, 0f).apply {
                    duration = 300
                    start()
                }
                fabElevated = false
            }
        }
    }
}
