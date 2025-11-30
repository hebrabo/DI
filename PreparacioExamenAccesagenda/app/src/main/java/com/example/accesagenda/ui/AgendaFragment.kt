package com.example.accesagenda.ui

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.accesagenda.R
import com.example.accesagenda.adapter.AgendaAdapter
import com.example.accesagenda.databinding.FragmentAgendaBinding
import com.example.accesagenda.model.AgendaItem

/**
 * Fragmento principal que muestra la lista completa de actividades del usuario
 * en formato vertical utilizando un RecyclerView.
 */
class AgendaFragment : Fragment(R.layout.fragment_agenda) {

    // 1. View Binding: Variable backing para el binding y propiedad segura.
    private var _binding: FragmentAgendaBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa el View Binding.
        _binding = FragmentAgendaBinding.bind(view)

        // Establece el título de la pantalla.
        binding.tvTitle.text = "Gestión del día"

        // 2. Datos Dummy: Creación de la lista de actividades (AgendaItem).
        val tasks = listOf(
            // Item completo: Título, Hora, Icono de Estado, Imagen Principal.
            AgendaItem("Cita con el Veterinario", "11:00 AM", R.drawable.ic_corazon_vacio, R.drawable.dentista),

            // Ítems con datos de ejemplo (los drawables deben existir en res/drawable).
            AgendaItem("Entrega Proyecto", "12:30 PM", R.drawable.ic_por_cumplir, R.drawable.veterinario),
            AgendaItem("Llamada comercial cliente", "15:00 PM", R.drawable.ic_corazon_vacio, R.drawable.reunion),
            AgendaItem("Ir al gimnasio", "19:00 PM", R.drawable.ic_por_cumplir, R.drawable.cafeteria)
        )

        // 3. Configuración del RecyclerView:
        // Establece el LayoutManager como vertical (el predeterminado para LinearLayoutManager).
        binding.rvAgenda.layoutManager = LinearLayoutManager(context)

        // Crea una instancia del AgendaAdapter y la asigna al RecyclerView con los datos de 'tasks'.
        binding.rvAgenda.adapter = AgendaAdapter(tasks)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 4. Limpieza: Se establece el binding a null para evitar fugas de memoria
        // cuando la vista del fragmento es destruida.
        _binding = null
    }
}