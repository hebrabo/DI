package com.example.di0602_menuscontextuales

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

/**
 * Adapter para mostrar imágenes en un RecyclerView tipo galería.
 * Cada imagen puede recibir dos tipos de interacción:
 * 1. Clic corto → muestra un menú contextual flotante (PopupMenu)
 * 2. Clic largo → activa el ActionMode
 */
class GalleryAdapter(private val items: List<Int>) :
    RecyclerView.Adapter<GalleryAdapter.ViewHolder>() {

    // Callbacks que se asignarán desde el Fragment
    // Se invocarán al hacer clic corto o largo sobre una imagen
    lateinit var onLongClick: (View) -> Unit  // Para ActionMode
    lateinit var onClick: (View) -> Unit     // Para menú flotante

    /**
     * ViewHolder que representa cada ítem (imagen) del RecyclerView
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.image_item)

        /**
         * Método bind para asignar la imagen y los listeners
         *
         * @param imageRes Recurso de la imagen
         * @param onClick Callback al hacer clic corto
         * @param onLongClick Callback al hacer clic largo
         */
        fun bind(
            imageRes: Int,
            onClick: (View) -> Unit,
            onLongClick: (View) -> Unit
        ) {
            // Asignamos la imagen al ImageView
            imageView.setImageResource(imageRes)

            // Listener para clic corto → menú flotante
            imageView.setOnClickListener { onClick(it) }

            // Listener para clic largo → ActionMode
            imageView.setOnLongClickListener {
                onLongClick(it)
                true // Indica que el evento se ha consumido
            }
        }
    }

    /**
     * Inflamos el layout de cada ítem y creamos un ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_image, parent, false)
        return ViewHolder(view)
    }

    /**
     * Número de ítems que contiene la galería
     */
    override fun getItemCount() = items.size

    /**
     * Asociamos cada ViewHolder con los datos y los callbacks
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position], onClick, onLongClick)
    }
}
