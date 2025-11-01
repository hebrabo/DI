package com.example.proyectocoffeeshops

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Adaptador para RecyclerView que conecta la lista de CoffeeShop con la vista
class CoffeeAdapter(private val lista: List<CoffeeShop>) :
    RecyclerView.Adapter<CoffeeAdapter.CoffeeViewHolder>() {

    // ViewHolder almacena referencias a las vistas de cada item para optimizar el rendimiento
    class CoffeeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgCoffee: ImageView = itemView.findViewById(R.id.imgCoffee)  // Imagen del café
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)     // Nombre del café
        val ratingBar: RatingBar = itemView.findViewById(R.id.ratingBar)  // RatingBar para mostrar estrellas
        val tvRating: TextView = itemView.findViewById(R.id.tvRating)     // Texto que muestra el valor del rating
        val tvUbicacion: TextView = itemView.findViewById(R.id.tvUbicacion) // Dirección del café
    }

    // Se crea la vista de cada item inflando el layout XML correspondiente
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_coffee_shop, parent, false) // Inflamos item_coffee_shop.xml
        return CoffeeViewHolder(view)
    }

    // Se asocian los datos de la lista con las vistas del ViewHolder
    override fun onBindViewHolder(holder: CoffeeViewHolder, position: Int) {
        val item = lista[position] // Obtenemos el CoffeeShop correspondiente a esta posición

        // Llenamos cada vista con los datos del objeto
        holder.imgCoffee.setImageResource(item.imagenResId)
        holder.tvNombre.text = item.nombre
        holder.ratingBar.rating = item.rating
        holder.tvRating.text = item.rating.toString()
        holder.tvUbicacion.text = item.ubicacion

        // Listener para actualizar el TextView cuando el usuario cambie el RatingBar
        holder.ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            holder.tvRating.text = rating.toString() // Actualizamos el texto con la nueva calificación
        }
    }

    // Devuelve la cantidad de items que el RecyclerView debe mostrar
    override fun getItemCount(): Int = lista.size
}
