package com.example.accesagenda.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.accesagenda.R
import com.example.accesagenda.databinding.ItemAgendaCardBinding
import com.example.accesagenda.model.AgendaItem

/**
 * Adaptador para el RecyclerView de la pantalla de inicio (HomeFragment).
 * Muestra los ítems de la agenda en un formato de tarjeta grande (horizontal)
 * y maneja el evento de clic en cada tarjeta para la navegación.
 *
 * @param items La lista de objetos AgendaItem a mostrar.
 * @param onItemClicked La función lambda que se ejecuta cuando el usuario hace clic en una tarjeta.
 */
class HomeAgendaAdapter(
    private val items: List<AgendaItem>,
    private val onItemClicked: (AgendaItem) -> Unit // Función lambda de callback para el clic
) : RecyclerView.Adapter<HomeAgendaAdapter.HomeAgendaViewHolder>() {

    /**
     * ViewHolder: Contiene la referencia al View Binding (ItemAgendaCardBinding)
     * y es responsable de actualizar las vistas de una única tarjeta.
     */
    class HomeAgendaViewHolder(private val binding: ItemAgendaCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Asigna los datos del AgendaItem a las vistas y configura el listener de clic.
         *
         * @param item El objeto AgendaItem con los datos a mostrar.
         * @param onItemClicked La función de callback que se ejecuta al hacer clic.
         */
        fun bind(item: AgendaItem, onItemClicked: (AgendaItem) -> Unit) {

            // 1. Asignar la imagen principal de la tarjeta (ivCardImage).
            // Manejamos el recurso de forma segura (imageResId es Int?).
            item.imageResId?.let { resId ->
                binding.ivCardImage.setImageResource(resId)
            } ?: run {
                // Usar un drawable de respaldo si imageResId es nulo.
                binding.ivCardImage.setImageResource(R.drawable.ic_launcher_background)
            }

            // 2. Asignar el texto de la hora.
            binding.tvCardTime.text = item.time

            // 3. Asignar el icono de estado (corazón/check, ivCardHeart).
            // item.iconRes es el recurso del icono pequeño.
            item.iconRes?.let { resId ->
                binding.ivCardHeart.setImageResource(resId)
                binding.ivCardHeart.visibility = View.VISIBLE
            } ?: run {
                // Ocultar el icono si el recurso no está presente.
                binding.ivCardHeart.visibility = View.GONE
            }

            // 4. Asignar el texto del título.
            binding.tvCardTitle.text = item.title

            // 5. CLAVE: Asignar el Listener de Clic a toda la tarjeta.
            // Cuando se hace clic, llamamos a la función de callback con el ítem actual.
            binding.root.setOnClickListener {
                onItemClicked(item)
            }
        }
    }

    /**
     * Crea y devuelve un nuevo ViewHolder inflando el layout de la tarjeta (item_agenda_card.xml)
     * usando View Binding.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeAgendaViewHolder {
        val binding = ItemAgendaCardBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return HomeAgendaViewHolder(binding)
    }

    /**
     * Vincula los datos del AgendaItem en la posición 'position' al ViewHolder.
     * Es crucial pasar también la función 'onItemClicked' al método bind.
     */
    override fun onBindViewHolder(holder: HomeAgendaViewHolder, position: Int) {
        // Pasamos el ítem de la lista y el callback de clic al ViewHolder.
        holder.bind(items[position], onItemClicked)
    }

    /**
     * Devuelve el número total de ítems en la lista de datos.
     */
    override fun getItemCount() = items.size
}