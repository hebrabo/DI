package com.example.palette

import android.graphics.BitmapFactory
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette

class DetalleActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalle)

        val toolbar = findViewById<Toolbar>(R.id.toolbarDetalle)
        val imageView = findViewById<android.widget.ImageView>(R.id.imgDetalle)
        val tvLightVibrant = findViewById<android.widget.TextView>(R.id.tvLightVibrant)
        val tvMuted = findViewById<android.widget.TextView>(R.id.tvMuted)
        val tvDarkMuted = findViewById<android.widget.TextView>(R.id.tvDarkMuted)
        val tvLightMuted = findViewById<android.widget.TextView>(R.id.tvLightMuted)

        val imagenResId = intent.getIntExtra("imagen", -1)
        if (imagenResId != -1) {
            imageView.setImageResource(imagenResId)

            val bitmap = BitmapFactory.decodeResource(resources, imagenResId)
            Palette.from(bitmap).generate { palette ->
                val vibrant = palette?.vibrantSwatch
                val darkVibrant = palette?.darkVibrantSwatch
                val lightVibrant = palette?.lightVibrantSwatch
                val muted = palette?.mutedSwatch
                val darkMuted = palette?.darkMutedSwatch
                val lightMuted = palette?.lightMutedSwatch

                // Toolbar con Vibrant
                vibrant?.let {
                    toolbar.setBackgroundColor(it.rgb)
                    toolbar.setTitleTextColor(it.titleTextColor)
                }

                // StatusBar con Dark Vibrant
                darkVibrant?.let {
                    window.statusBarColor = it.rgb
                }

                // TextViews con los demás colores
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

