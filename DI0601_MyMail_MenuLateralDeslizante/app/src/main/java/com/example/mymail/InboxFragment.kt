package com.example.mymail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

// Fragment que representa la bandeja de entrada (Inbox)
class InboxFragment : Fragment() {

    // Método llamado para crear la interfaz del fragment
    override fun onCreateView(
        inflater: LayoutInflater,       // Permite "inflar" un layout XML
        container: ViewGroup?,          // Contenedor padre donde se añadirá el fragment
        savedInstanceState: Bundle?     // Estado previamente guardado (si lo hay)
    ): View? {
        // Inflamos el layout asociado a este fragment y lo devolvemos como View
        // Este layout se define en res/layout/fragment_inbox.xml
        return inflater.inflate(R.layout.fragment_inbox, container, false)
    }
}

