package com.example.palette

import android.app.ActivityOptions
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.Slide
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import androidx.core.app.ActivityOptionsCompat
import androidx.palette.graphics.Palette


// Pantalla principal donde se muestran las tarjetas con imágenes.
class MainActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Configuración del Toolbar
        val toolbar = findViewById<Toolbar>(R.id.appbar)
        setSupportActionBar(toolbar)

        // Crear una lista de tarjetas con imágenes
        val items = ArrayList<Tarjeta>()
        items.add(Tarjeta(R.drawable.image1))
        items.add(Tarjeta(R.drawable.image2))
        items.add(Tarjeta(R.drawable.image3))
        items.add(Tarjeta(R.drawable.image4))
        items.add(Tarjeta(R.drawable.image5))
        items.add(Tarjeta(R.drawable.image6))
        items.add(Tarjeta(R.drawable.image7))
        items.add(Tarjeta(R.drawable.image8))

        // Configuración del RecyclerView
        val recView = findViewById<RecyclerView>(R.id.recview)
        recView.setHasFixedSize(true) // Mejora el rendimiento si los items no cambian de tamaño

        val adaptador = CardsAdapter(items) // Crear adaptador con las tarjetas
        recView.adapter = adaptador
        recView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false) // Layout vertical

        // Manejar clicks en cada tarjeta
        adaptador.onClick = { view: View ->
            val position = recView.getChildAdapterPosition(view) // Obtener posición de la tarjeta clickeada
            if (position != RecyclerView.NO_POSITION) {
                val tarjeta = items[position]

                // Cambiar color del toolbar usando Palette a partir de la imagen
                val bitmap = BitmapFactory.decodeResource(resources, tarjeta.imagen)
                Palette.from(bitmap).generate { palette ->
                    val vibrant: Palette.Swatch? = palette?.vibrantSwatch // Color vibrante principal
                    if (vibrant != null) {
                        toolbar.setBackgroundColor(vibrant.rgb) // Fondo del Toolbar
                        toolbar.setTitleTextColor(vibrant.titleTextColor) // Color del texto del Toolbar
                    }
                }

                // Abrir nueva pantalla (DetalleActivity) pasando la imagen seleccionada
                val intent = Intent(this, DetalleActivity::class.java)
                intent.putExtra("imagen", tarjeta.imagen)

                // Elemento compartido: imagen dentro del item clickeado
                val imageView = view.findViewById<View>(R.id.imgCard)

                val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                    this,
                    imageView,
                    "sharedImage"
                )
                window.exitTransition = Slide(Gravity.END)
                startActivity(intent, options.toBundle())
            }
        }
    }
}