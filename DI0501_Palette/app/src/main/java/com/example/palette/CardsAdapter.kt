package com.example.palette

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import kotlin.Unit as Unit

// Adaptador para el RecyclerView que muestra las tarjetas con imágenes
class CardsAdapter(var items: ArrayList<Tarjeta>) : RecyclerView.Adapter<CardsAdapter.TarjViewHolder>() {

    // Lambda que se ejecuta cuando se hace click en un item
    lateinit var onClick: (View) -> Unit

    init {
        this.items = items
    }

    // ViewHolder representa cada tarjeta individual en el RecyclerView
    class TarjViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imagen: ImageView

        init {
            imagen = itemView.findViewById(R.id.imgCard) // ImageView de la tarjeta
        }

        // Asignar datos de la tarjeta y el click listener
        fun bindTarjeta(t: Tarjeta, onClick: (View) -> Unit) = with(itemView) {
            imagen.setImageResource(t.imagen) // Mostrar imagen
            setOnClickListener{ onClick(itemView) } // Detectar click
        }

    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): TarjViewHolder {
        // Inflar el layout de cada tarjeta
        val itemView = LayoutInflater.from(viewGroup.context).inflate(R.layout.item_cards, viewGroup, false)
        return TarjViewHolder(itemView)
    }

    override fun onBindViewHolder(viewHolder: TarjViewHolder, pos: Int) {
        val item = items.get(pos)
        viewHolder.bindTarjeta(item,onClick) // Asociar datos y click
    }

    override fun getItemCount(): Int {
        return items.size // Número total de tarjetas
    }
}