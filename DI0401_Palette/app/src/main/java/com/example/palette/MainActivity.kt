package com.example.palette

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.appcompat.widget.Toolbar
import androidx.palette.graphics.Palette



class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar = findViewById<Toolbar>(R.id.appbar)
        setSupportActionBar(toolbar)

        val items = ArrayList<Tarjeta>()
        items.add(Tarjeta(R.drawable.image1))
        items.add(Tarjeta(R.drawable.image2))
        items.add(Tarjeta(R.drawable.image3))
        items.add(Tarjeta(R.drawable.image4))
        items.add(Tarjeta(R.drawable.image5))
        items.add(Tarjeta(R.drawable.image6))
        items.add(Tarjeta(R.drawable.image7))
        items.add(Tarjeta(R.drawable.image8))

        val recView = findViewById<RecyclerView>(R.id.recview)

        recView.setHasFixedSize(true)

        val adaptador = CardsAdapter(items)
        recView.adapter = adaptador
        recView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        // Manejar clicks en cada tarjeta
        adaptador.onClick = { view: View ->
            val position = recView.getChildAdapterPosition(view)
            if (position != RecyclerView.NO_POSITION) {
                val tarjeta = items[position]

                // Cambiar color del toolbar
                val bitmap = BitmapFactory.decodeResource(resources, tarjeta.imagen)
                Palette.from(bitmap).generate { palette ->
                    val vibrant: Palette.Swatch? = palette?.vibrantSwatch
                    if (vibrant != null) {
                        toolbar.setBackgroundColor(vibrant.rgb)
                        toolbar.setTitleTextColor(vibrant.titleTextColor)
                    }
                }

                // Abrir nueva pantalla con la imagen seleccionada
                val intent = Intent(this, DetalleActivity::class.java)
                intent.putExtra("imagen", tarjeta.imagen)
                startActivity(intent)
            }
        }
    }
}