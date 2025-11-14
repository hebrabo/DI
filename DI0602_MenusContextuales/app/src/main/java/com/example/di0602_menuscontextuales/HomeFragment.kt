package com.example.di0602_menuscontextuales

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Fragment que representa la pantalla "Home" del proyecto.
 * Este fragmento no tiene funcionalidad adicional por ahora.
 * Solo infla su layout correspondiente.
 */
class HomeFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflamos el layout fragment_home.xml y lo devolvemos
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
}
