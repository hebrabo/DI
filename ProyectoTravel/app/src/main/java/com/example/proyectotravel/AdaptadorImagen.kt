package com.example.proyectotravel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView

// Adaptador personalizado para mostrar una lista de objetos Imagen en un RecyclerView
class AdaptadorImagen(var items: ArrayList<Imagen>) : RecyclerView.Adapter<AdaptadorImagen.TarjViewHolder>() {

    // Función lambda que se ejecutará al hacer clic sobre un elemento (miniatura)
    lateinit var onClick: (View) -> Unit

    // Inicializa la lista de elementos (aunque aquí no sería necesario, se mantiene por claridad)
    init {
        this.items = items
    }

    // ViewHolder: representa la vista de un único elemento (una tarjeta o miniatura)
    class TarjViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        // ImageView que mostrará la foto del objeto Imagen
        var imag : ImageView

        init {
            // Asociamos la vista del layout (item_imagen.xml) al ImageView mediante su id
            imag = itemView.findViewById(R.id.imagen_referencia)
        }

        // Metodo que "vincula" los datos del objeto Imagen con los elementos visuales del ViewHolder
        fun bindTarjeta(t: Imagen, onClick: (View) -> Unit) = with(itemView) {
            // Carga la imagen (recurso drawable) en el ImageView
            imag.setImageResource(t.foto)
            // Asigna el evento onClick para este elemento
            setOnClickListener{ onClick(itemView) }
        }
    }

    // Infla (crea) la vista de un nuevo elemento a partir del layout XML (item_imagen.xml)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TarjViewHolder {
        val itemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_imagen, viewGroup, false)
        return TarjViewHolder(itemView)
    }

    // Asocia los datos del elemento actual (posición "pos") con su vista (ViewHolder)
    override fun onBindViewHolder(viewHolder: TarjViewHolder, pos: Int) {
        val item = items.get(pos)
        viewHolder.bindTarjeta(item,onClick)
    }

    // Devuelve el número total de elementos de la lista
    override fun getItemCount(): Int {
        return items.size
    }
}