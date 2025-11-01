package com.example.proyectotravel

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.ImageSwitcher
import android.widget.ImageView
import android.widget.ViewSwitcher
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/* MainActivity: actividad principal que muestra una galería con miniaturas (RecyclerView)
 * y una imagen grande que cambia dinámicamente mediante un ImageSwitcher.
 */

class MainActivity : AppCompatActivity(), ViewSwitcher.ViewFactory {

    // Lista que almacenará los objetos Imagen con los recursos drawable
    private lateinit var fotos: ArrayList<Imagen>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Inicializamos la lista con las imágenes que se mostrarán en la galería
        fotos = ArrayList()
        fotos.add(Imagen(R.drawable.travel1))
        fotos.add(Imagen(R.drawable.travel2))
        fotos.add(Imagen(R.drawable.travel3))
        fotos.add(Imagen(R.drawable.travel4))
        fotos.add(Imagen(R.drawable.travel5))
        fotos.add(Imagen(R.drawable.travel6))
        fotos.add(Imagen(R.drawable.travel7))
        fotos.add(Imagen(R.drawable.travel8))

        // Configuramos la toolbar (barra superior), si existe en el layout
        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        // Configuración del ImageSwitcher (zona inferior con la imagen grande)
        val imageSwitcher = findViewById(R.id.imageSwitcher) as ImageSwitcher
        imageSwitcher.setFactory(this)
        imageSwitcher.inAnimation = AnimationUtils.loadAnimation(this, android.R.anim.slide_in_left)
        imageSwitcher.outAnimation = AnimationUtils.loadAnimation(this, android.R.anim.slide_out_right)

        // Mostrar una imagen inicial (la primera del array)
        imageSwitcher.setImageResource(fotos[0].foto)

        // Configuración del RecyclerView (lista horizontal de miniaturas)
        val recView = findViewById(R.id.recView) as RecyclerView
        recView.setHasFixedSize(true)
        val adaptador = AdaptadorImagen(fotos)

        /* Definimos el comportamiento al hacer clic sobre una miniatura:
         * se obtiene la posición del elemento clicado y se cambia la imagen grande.
         */
        adaptador.onClick = {
            val t = fotos[recView.getChildAdapterPosition(it)]
            imageSwitcher.setImageResource(t.foto)
        }

        // Asignamos el adaptador y el layout manager al RecyclerView
        recView.adapter = adaptador
        recView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    }

    /* Implementación del metodo de ViewFactory:
     * crea dinámicamente las vistas (ImageView) que el ImageSwitcher usará para mostrar imágenes.
     */
    override fun makeView(): View {
        val imageView = ImageView(this)
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP) // Ajuste visual de la imagen
        imageView.layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        return imageView
    }
}
