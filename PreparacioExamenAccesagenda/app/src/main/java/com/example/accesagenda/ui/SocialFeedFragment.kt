package com.example.accesagenda.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.accesagenda.R
import com.example.accesagenda.adapter.SocialFeedAdapter // Importar el Adaptador
import com.example.accesagenda.databinding.FragmentSocialFeedBinding
import com.example.accesagenda.model.AgendaItem // Importar el Modelo

/**
 * Fragmento que muestra el feed social de actividades o tareas pendientes.
 * Utiliza un RecyclerView con tarjetas complejas que incluyen imágenes y opciones de acción.
 */
class SocialFeedFragment : Fragment(R.layout.fragment_social_feed) {

    // 1. View Binding: Variable backing para el binding y propiedad segura.
    private var _binding: FragmentSocialFeedBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa el View Binding.
        _binding = FragmentSocialFeedBinding.bind(view)

        // Nota: La línea del título (tvScreenTitle) fue eliminada en el último ajuste del layout.

        // --- LÓGICA DE CARGA DE DATOS Y CONFIGURACIÓN DEL RECYCLERVIEW ---

        // 2. Datos Dummy: Creación de la lista de tareas pendientes (AgendaItem).
        val tasks = listOf(
            // Cada ítem incluye el título, la hora, el icono de estado (corazón/check) y la imagen principal.
            AgendaItem("Cita con el Veterinario", "Hoy - 11:00 AM", R.drawable.ic_corazon_vacio, R.drawable.dentista),
            AgendaItem("Entrega de Proyecto", "Mañana - 09:00 AM", R.drawable.ic_por_cumplir, R.drawable.reunion),
            AgendaItem("Revisar Cuentas Bancarias", "25 de Nov", R.drawable.ic_por_cumplir, R.drawable.banco)
        )

        // 3. Crear el Adaptador: Instanciamos el SocialFeedAdapter y definimos el listener de clic.
        val adapter = SocialFeedAdapter(tasks) { clickedItem ->
            // Implementación del callback de clic: Muestra un Toast con el título de la tarea.
            // Aquí se debería integrar la navegación hacia el ActivityDetailFragment.
            Toast.makeText(context, "Abriendo detalle de: ${clickedItem.title}", Toast.LENGTH_SHORT).show()
        }

        // 4. Configurar el RecyclerView:
        // Establece el LayoutManager (vertical por defecto).
        binding.recyclerViewSocialFeed.layoutManager = LinearLayoutManager(context)

        // Asigna el adaptador con los datos y el listener de clic.
        binding.recyclerViewSocialFeed.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 5. Limpieza: Se anula la referencia del binding para evitar fugas de memoria.
        _binding = null
    }
}