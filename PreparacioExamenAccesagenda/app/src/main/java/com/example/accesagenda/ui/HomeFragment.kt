package com.example.accesagenda.ui.home

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.accesagenda.R
import com.example.accesagenda.adapter.CategoryAdapter
import com.example.accesagenda.adapter.HomeAgendaAdapter
import com.example.accesagenda.databinding.FragmentHomeBinding
import com.example.accesagenda.model.AgendaItem
import com.example.accesagenda.model.CategoryItem

/**
 * Fragmento que actúa como la pantalla de inicio de la aplicación.
 * Muestra un banner, una lista horizontal de categorías y una lista horizontal
 * de las actividades del día ("Hoy").
 */
class HomeFragment : Fragment(R.layout.fragment_home) {

    // 1. View Binding: Variable backing para el binding y propiedad segura.
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa el View Binding.
        _binding = FragmentHomeBinding.bind(view)

        // 2. Cargar la imagen del banner estático (asumiendo que ivHomeBanner existe en el layout).
        binding.ivHomeBanner.setImageResource(R.drawable.relojarena)

        // --- SECCIÓN DE CATEGORÍAS (RecyclerView Horizontal) ---

        // 3a. Datos Dummy: Lista de categorías.
        val categories = listOf(
            CategoryItem("Gimnasio", R.drawable.gimnasio),
            CategoryItem("Compra", R.drawable.mercado),
            CategoryItem("Pago de Servicios", R.drawable.banco),
            CategoryItem("Celebración", R.drawable.cena)
        )

        // 3b. Configuración del RecyclerView de Categorías (rvCategories).
        binding.rvCategories.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL, // Configuración horizontal
            false // No invertir la lista
        )
        // Asignación del adaptador de categorías.
        binding.rvCategories.adapter = CategoryAdapter(categories)

        // --- SECCIÓN DE ACTIVIDADES DE HOY (RecyclerView Horizontal) ---

        // 3c. Datos Dummy: Lista de actividades para "Hoy".
        val items = listOf(
            AgendaItem("Cita con el Veterinario", "11:00 AM", R.drawable.ic_corazon_vacio, R.drawable.dentista),
            AgendaItem("Reunión Sala Juntas", "15:00 PM", R.drawable.ic_por_cumplir, R.drawable.reunion),
            AgendaItem("Cena Familiar", "20:00 PM", R.drawable.ic_por_cumplir, R.drawable.cena)
        )

        // 4. Configuración del RecyclerView de Actividades de Hoy (rvToday).
        binding.rvToday.layoutManager = LinearLayoutManager(
            context,
            LinearLayoutManager.HORIZONTAL, // Configuración horizontal
            false
        )

        // Creación del Adaptador con el Listener de Navegación.
        val homeAgendaAdapter = HomeAgendaAdapter(items) { clickedItem ->
            // CLAVE: Lógica de navegación. Cuando se hace clic en una tarjeta de actividad,
            // navegamos al fragmento de detalle de actividad.
            findNavController().navigate(R.id.action_homeFragment_to_activityDetailFragment)
        }

        binding.rvToday.adapter = homeAgendaAdapter

        // --- LÓGICA DE NAVEGACIÓN A TAREAS PENDIENTES ---

        // 5. Configuración del Listener para la tarjeta de Tareas Pendientes.
        binding.cardPendingActivities.setOnClickListener {
            // CLAVE: Navegamos del HomeFragment al SocialFeedFragment (listado de tareas).
            findNavController().navigate(R.id.action_homeFragment_to_socialFeedFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 6. Limpieza: Se establece el binding a null para evitar fugas de memoria.
        _binding = null
    }
}