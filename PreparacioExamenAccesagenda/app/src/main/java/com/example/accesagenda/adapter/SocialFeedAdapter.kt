package com.example.accesagenda.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.accesagenda.databinding.ItemTaskSocialFeedBinding
import com.example.accesagenda.model.AgendaItem

/**
 * Adaptador para el RecyclerView del Social Feed.
 * Muestra las actividades como tarjetas completas (con imagen grande, avatar, acciones)
 * y permite manejar el evento de clic en cada tarjeta.
 *
 * @param tasks La lista de objetos AgendaItem (tareas/actividades) a mostrar.
 * @param onItemClicked La función lambda de callback que se ejecuta al hacer clic en una tarjeta.
 */
class SocialFeedAdapter(
    private val tasks: List<AgendaItem>,
    private val onItemClicked: (AgendaItem) -> Unit // Función de callback para el clic
) : RecyclerView.Adapter<SocialFeedAdapter.TaskViewHolder>() {

    /**
     * ViewHolder: Contiene la referencia al View Binding y es responsable de
     * vincular los datos de una tarea a su vista de tarjeta individual.
     */
    inner class TaskViewHolder(private val binding: ItemTaskSocialFeedBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Asigna los datos del AgendaItem a las vistas y configura el listener de clic.
         *
         * @param item El objeto AgendaItem con los datos a mostrar.
         */
        fun bind(item: AgendaItem) {
            // 1. Asignar Título y Hora
            binding.tvTaskTitle.text = item.title
            binding.tvTaskTime.text = item.time

            // 2. Imagen Principal (imageResId)
            // Carga la imagen principal de la tarjeta (el campo imageResId de AgendaItem).
            item.imageResId?.let { resId ->
                binding.ivMainImage.setImageResource(resId)
            }
            // NOTA: La lógica para el avatar (ivAvatar) y la acción 'Realizado/Por cumplir'
            // debe implementarse aquí si el modelo AgendaItem se extiende o si usan datos estáticos.

            // 3. Configurar el Listener de Clic
            // Al hacer clic en la vista raíz (la tarjeta completa), se llama al callback
            // proporcionado en el constructor del Adaptador.
            binding.root.setOnClickListener {
                onItemClicked(item)
            }
        }
    }

    /**
     * Crea y devuelve un nuevo ViewHolder inflando el layout de la tarjeta
     * (item_task_social_feed.xml) usando View Binding.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val binding = ItemTaskSocialFeedBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false // El RecyclerView se encarga de adjuntar la vista al padre.
        )
        return TaskViewHolder(binding)
    }

    /**
     * Vincula los datos del AgendaItem en la posición 'position' al ViewHolder.
     */
    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        // Llama al método bind del ViewHolder con el ítem de la posición actual.
        holder.bind(tasks[position])
    }

    /**
     * Devuelve el número total de ítems en la lista de tareas.
     */
    override fun getItemCount(): Int = tasks.size
}