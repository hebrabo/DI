package com.example.di0602_menuscontextuales

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Fragmento que representa la sección "Slideshow".
 * Se carga cuando se selecciona la opción correspondiente
 * en el menú lateral de la aplicación.
 */
class SlideshowFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflamos el layout asociado a este fragmento
        return inflater.inflate(R.layout.fragment_slideshow, container, false)
    }
}
