package com.example.accesagenda.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.accesagenda.R
import com.example.accesagenda.model.AgendaItem

/**
 * Adaptador para mostrar la lista de ítems de la agenda (AgendaItem)
 * en un RecyclerView, utilizando el layout item_agenda_row.
 * Este adaptador no maneja clics en los ítems.
 */
class AgendaAdapter(private val items: List<AgendaItem>) :
    RecyclerView.Adapter<AgendaAdapter.ViewHolder>() {

    /**
     * ViewHolder: Contiene las referencias a las vistas (TextViews, ImageView)
     * de un solo ítem de la lista (item_agenda_row.xml).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Campos de texto para el título y la hora/fecha de la actividad.
        val title: TextView = view.findViewById(R.id.tvTitle)
        val time: TextView = view.findViewById(R.id.tvTime)

        // ImageView para la imagen principal del ítem (el círculo grande con la imagen).
        val circleImage: ImageView = view.findViewById(R.id.ivCircleIcon)

        // ImageView para el icono de estado (el corazón o check, el icono pequeño).
        val icon: ImageView = view.findViewById(R.id.ivIcon)
    }

    /**
     * Crea y devuelve un nuevo ViewHolder.
     * Se llama cuando el RecyclerView necesita crear una nueva fila visual.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Inflamos el layout de la fila (item_agenda_row.xml).
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_agenda_row, parent, false)
        return ViewHolder(view)
    }

    /**
     * Vincula los datos de la posición 'position' a las vistas del ViewHolder.
     * Se llama para actualizar el contenido de una fila existente o nueva.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]

        // 1. Asignar los textos
        holder.title.text = item.title
        holder.time.text = item.time

        // 2. Manejo de la Imagen Principal (circleImage)
        // item.imageResId es el cuarto argumento del modelo.
        val imageId = item.imageResId ?: 0

        if (imageId != 0) {
            // Si hay un recurso de imagen válido, lo asignamos y aseguramos visibilidad.
            holder.circleImage.setImageResource(imageId)
            holder.circleImage.visibility = View.VISIBLE
        } else {
            // Si imageId es nulo o 0, usamos un drawable de fallback (fondo de launcher).
            holder.circleImage.setImageResource(R.drawable.ic_launcher_background)
            holder.circleImage.visibility = View.VISIBLE
        }

        // 3. Manejo del Icono de Estado (icon)
        // item.iconRes es el tercer argumento del modelo (el corazón o check).
        val iconId = item.iconRes ?: 0

        if (iconId != 0) {
            // Si hay un recurso de icono válido, lo asignamos.
            holder.icon.setImageResource(iconId)
            holder.icon.visibility = View.VISIBLE
        } else {
            // Si no hay icono, ocultamos el ImageView para ahorrar espacio.
            holder.icon.visibility = View.GONE
        }
    }

    /**
     * Devuelve el número total de ítems en la lista de datos.
     */
    override fun getItemCount() = items.size
}