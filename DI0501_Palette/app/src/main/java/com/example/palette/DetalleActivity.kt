package com.example.palette

import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette

// Muestra la imagen seleccionada y sus colores extraídos con Palette
class DetalleActivity : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        // Referencias a elementos de la interfaz
        val toolbar = findViewById<Toolbar>(R.id.toolbarDetalle)
        setSupportActionBar(toolbar)
        supportActionBar?.title = "Palette"
        val imageView = findViewById<android.widget.ImageView>(R.id.imgDetalle)
        val tvLightVibrant = findViewById<android.widget.TextView>(R.id.tvLightVibrant)
        val tvMuted = findViewById<android.widget.TextView>(R.id.tvMuted)
        val tvDarkMuted = findViewById<android.widget.TextView>(R.id.tvDarkMuted)
        val tvLightMuted = findViewById<android.widget.TextView>(R.id.tvLightMuted)

        // Obtener imagen enviada desde MainActivity
        val imagenResId = intent.getIntExtra("imagen", -1)
        if (imagenResId != -1) {
            imageView.setImageResource(imagenResId)

            // Convertir recurso a Bitmap para generar Palette
            val bitmap = BitmapFactory.decodeResource(resources, imagenResId)
            Palette.from(bitmap).generate { palette ->
                // Extraer distintos colores de la imagen
                val vibrant = palette?.vibrantSwatch
                val darkVibrant = palette?.darkVibrantSwatch
                val lightVibrant = palette?.lightVibrantSwatch
                val muted = palette?.mutedSwatch
                val darkMuted = palette?.darkMutedSwatch
                val lightMuted = palette?.lightMutedSwatch

                // Configurar Toolbar con color Vibrant
                vibrant?.let {
                    toolbar.setBackgroundColor(it.rgb)
                    toolbar.setTitleTextColor(it.titleTextColor)
                }

                // Configurar StatusBar con color Dark Vibrant
                darkVibrant?.let {
                    window.statusBarColor = it.rgb
                }

                // Configurar TextViews mostrando el color y su hexadecimal
                lightVibrant?.let {
                    tvLightVibrant.setBackgroundColor(it.rgb)
                    tvLightVibrant.setTextColor(it.titleTextColor)
                    tvLightVibrant.text = "Light Vibrant: #" + Integer.toHexString(it.rgb).uppercase()
                }

                muted?.let {
                    tvMuted.setBackgroundColor(it.rgb)
                    tvMuted.setTextColor(it.titleTextColor)
                    tvMuted.text = "Muted: #" + Integer.toHexString(it.rgb).uppercase()
                }

                darkMuted?.let {
                    tvDarkMuted.setBackgroundColor(it.rgb)
                    tvDarkMuted.setTextColor(it.titleTextColor)
                    tvDarkMuted.text = "Dark Muted: #" + Integer.toHexString(it.rgb).uppercase()
                }

                lightMuted?.let {
                    tvLightMuted.setBackgroundColor(it.rgb)
                    tvLightMuted.setTextColor(it.titleTextColor)
                    tvLightMuted.text = "Light Muted: #" + Integer.toHexString(it.rgb).uppercase()
                }
            }
        }
    }
}

