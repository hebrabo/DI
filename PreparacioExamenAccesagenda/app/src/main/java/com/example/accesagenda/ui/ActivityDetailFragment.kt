package com.example.accesagenda.ui

import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import com.example.accesagenda.R
import com.example.accesagenda.databinding.FragmentActivityDetailBinding

/**
 * Fragmento que muestra el detalle de una actividad individual.
 * Asume que el layout (fragment_activity_detail.xml) incluye contenedores
 * para el filtro (dropdownFilter) y el ordenamiento (dropdownSort).
 */
class ActivityDetailFragment : Fragment(R.layout.fragment_activity_detail) {

    // Variable para manejar el View Binding de forma segura (para evitar memory leaks).
    private var _binding: FragmentActivityDetailBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // 1. Vinculación (Binding): Inicializa el View Binding para acceder a las vistas.
        _binding = FragmentActivityDetailBinding.bind(view)

        // ----------------------------------------------------
        // LÓGICA PARA LOS DESPLEGABLES (PopupMenu)
        // ----------------------------------------------------

        // 2. Configuración del Desplegable para FILTRAR
        binding.dropdownFilter.setOnClickListener { anchorView ->
            // Muestra el menú pop-up anclado a la vista de filtro, usando el recurso menu_filter.
            showPopupMenu(anchorView, R.menu.menu_filter) { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_filter_all -> {
                        // Lógica: Cargar/Mostrar todas las actividades.
                        true
                    }
                    R.id.action_filter_today -> {
                        // Lógica: Cargar/Mostrar solo las actividades de hoy.
                        true
                    }
                    R.id.action_filter_week -> {
                        // Lógica: Cargar/Mostrar las actividades de esta semana.
                        true
                    }
                    else -> false
                }
            }
        }

        // 3. Configuración del Desplegable para ORDENAR
        binding.dropdownSort.setOnClickListener { anchorView ->
            // Muestra el menú pop-up anclado a la vista de ordenamiento, usando el recurso menu_sort.
            showPopupMenu(anchorView, R.menu.menu_sort) { menuItem ->
                when (menuItem.itemId) {
                    R.id.action_sort_date -> {
                        // Lógica: Ordenar la lista por fecha.
                        true
                    }
                    R.id.action_sort_location -> {
                        // Lógica: Ordenar la lista por ubicación.
                        true
                    }
                    R.id.action_sort_status -> {
                        // Lógica: Ordenar la lista por estado (Pendiente, Completado, etc.).
                        true
                    }
                    else -> false
                }
            }
        }
    }

    /**
     * Función de utilidad para crear y mostrar un PopupMenu genérico.
     *
     * @param anchorView La vista que servirá como ancla para el menú (donde aparecerá).
     * @param menuResId El ID del recurso de menú (ej. R.menu.menu_filter).
     * @param listener El listener que maneja el clic en un ítem del menú.
     */
    private fun showPopupMenu(
        anchorView: View,
        menuResId: Int,
        listener: PopupMenu.OnMenuItemClickListener
    ) {
        val popup = PopupMenu(requireContext(), anchorView)
        // Inflamos el menú desde el XML.
        popup.menuInflater.inflate(menuResId, popup.menu)
        // Asignamos el listener para manejar las selecciones.
        popup.setOnMenuItemClickListener(listener)
        // Mostramos el menú.
        popup.show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Limpiamos la referencia al binding para evitar fugas de memoria.
        _binding = null
    }
}