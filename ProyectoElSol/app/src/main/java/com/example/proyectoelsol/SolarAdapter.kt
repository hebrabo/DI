package com.example.proyectoelsol

import android.app.AlertDialog
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView

/**
 * Adaptador personalizado para mostrar una lista de objetos 'Solar' en un RecyclerView.
 * Cada elemento muestra una imagen, un nombre y un menú de opciones (3 puntitos).
 */
class SolarAdapter(
    private val items: ArrayList<Solar>
) : RecyclerView.Adapter<SolarAdapter.SolarViewHolder>() {

    /**
     * Clase interna que representa cada "tarjeta" o vista individual de un elemento Solar.
     * Aquí se referencian los elementos visuales del layout item_solar.xml
     */
    class SolarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imagen: ImageView = itemView.findViewById(R.id.imagenSolar)
        val nombre: TextView = itemView.findViewById(R.id.nombreSolar)
        val btnMenu: ImageView = itemView.findViewById(R.id.btnMenu)
    }

    /**
     * Metodo que crea y devuelve un ViewHolder.
     * Se llama cuando RecyclerView necesita una nueva vista para un elemento.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SolarViewHolder {
        // Inflamos el layout XML que define cómo se ve cada tarjeta
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_solar, parent, false)
        return SolarViewHolder(view)
    }

    /**
     * Enlaza los datos del objeto 'Solar' con los elementos visuales del ViewHolder.
     * Es decir, coloca el nombre y la imagen correctos en cada tarjeta.
     */
    override fun onBindViewHolder(holder: SolarViewHolder, position: Int) {
        val item = items[position] // Obtenemos el objeto Solar en la posición actual

        // Asignamos la imagen y el texto
        holder.imagen.setImageResource(item.foto)
        holder.nombre.text = item.nombre

        /**
         * Listener para el botón de los 3 puntitos.
         * Al pulsarlo, se muestra un PopupMenu con las opciones:
         * Renombrar, Duplicar, Eliminar y Mover.
         */
        holder.btnMenu.setOnClickListener { view ->
            val popup = PopupMenu(view.context, view) // Creamos el menú emergente
            popup.menuInflater.inflate(R.menu.menu_item_solar, popup.menu) // Cargamos las opciones del menú

            // Definimos las acciones de cada opción del menú
            popup.setOnMenuItemClickListener { menuItem ->
                when(menuItem.itemId) {

                    // Opción 1: Renombrar el elemento
                    R.id.opcion_renombrar -> {
                        val editText = EditText(view.context)
                        editText.inputType = InputType.TYPE_CLASS_TEXT
                        editText.setText(item.nombre)

                        // Mostramos un cuadro de diálogo para cambiar el nombre
                        AlertDialog.Builder(view.context)
                            .setTitle("Renombrar")
                            .setView(editText)
                            .setPositiveButton("OK") { _, _ ->

                                // Guardamos el nuevo nombre y actualizamos la vista
                                item.nombre = editText.text.toString()
                                notifyItemChanged(position)
                            }
                            .setNegativeButton("Cancelar", null)
                            .show()
                        true
                    }

                    // Opción 2: Duplicar el elemento
                    R.id.opcion_duplicar -> {
                        val nuevoItem = item.copy() // Copiamos el objeto actual
                        items.add(position + 1, nuevoItem) // Lo añadimos justo debajo
                        notifyItemInserted(position + 1) // Notificamos al adaptador
                        true
                    }

                    // Opción 3: Eliminar el elemento
                    R.id.opcion_eliminar -> {
                        items.removeAt(position) // Eliminamos el elemento de la lista
                        notifyItemRemoved(position) // Notificamos el cambio al RecyclerView
                        true
                    }

                    // Opción 4: Mover el elemento una posición arriba
                    R.id.opcion_mover -> {
                        if (position > 0) { // Solo si no es el primero
                            val temp = items[position]
                            items[position] = items[position - 1]
                            items[position - 1] = temp
                            notifyItemMoved(position, position - 1)
                        }
                        true
                    }
                    else -> false
                }
            }
            popup.show()
        }
    }

    override fun getItemCount(): Int = items.size
}
