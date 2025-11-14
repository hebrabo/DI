package com.example.coffeshops

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

/**
 * Adaptador del RecyclerView que muestra la lista de cafeterías.
 *
 * @param tabernae Lista de objetos CartaDatos que representan cada cafetería.
 * @param onItemClick Lambda que se ejecuta cuando se hace clic en una card.
 *        Se pasa el objeto seleccionado y la vista usada para la transición compartida.
 */
class CoffeAdapter(
    private val tabernae: MutableList<CartaDatos>,
    private val onItemClick: (CartaDatos, View) -> Unit
) : RecyclerView.Adapter<CoffeAdapter.CoffeViewHolder>() {

    /**
     * ViewHolder: representa cada una de las tarjetas (cards) de la lista.
     * Aquí referenciamos todos los elementos visuales que vamos a rellenar.
     */
    class CoffeViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val imagen: ImageView = view.findViewById(R.id.imagenCafeteria)
        val titulo: TextView = view.findViewById(R.id.titulo)
        val subTitulo: TextView = view.findViewById(R.id.subtitulo)
        val score: TextView = view.findViewById(R.id.score)
        val ratingBar: RatingBar = view.findViewById(R.id.ratingBar)
        val button: Button = view.findViewById(R.id.button)
    }

    /**
     * Crea un nuevo ViewHolder inflando el layout de cada card.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoffeViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_item, parent, false)
        return CoffeViewHolder(view)
    }

    /**
     * Devuelve el número de elementos en la lista.
     */
    override fun getItemCount(): Int = tabernae.size

    /**
     * Vincula los datos de cada cafetería con las views del ViewHolder.
     * Aquí es donde rellenamos la card con imagen, título, subtítulo, rating...
     */
    override fun onBindViewHolder(holder: CoffeViewHolder, position: Int) {
        val item = tabernae[position]

        // Imagen y textos de la cafetería
        holder.imagen.setImageResource(item.imagen)
        holder.titulo.text = item.titulo
        holder.subTitulo.text = item.subtitulo

        // Mostrar puntuación numérica y gráfica
        holder.score.text = item.puntuacion.toString()
        holder.ratingBar.rating = item.puntuacion

        /**
         * IMPORTANTE:
         * Para evitar que el RecyclerView reutilice ratings anteriores,
         * primero eliminamos cualquier listener existente.
         */
        holder.ratingBar.setOnRatingBarChangeListener(null)

        /**
         * Listener para actualizar en tiempo real la puntuación
         * y reflejarla en el objeto de datos.
         */
        holder.ratingBar.setOnRatingBarChangeListener { _, rating, _ ->
            holder.score.text = String.format("%.1f", rating)
            item.puntuacion = rating
        }

        /**
         * =============================
         * TRANSICIÓN COMPARTIDA
         * =============================
         * Le damos un nombre común al título, que será animado al
         * navegar hacia el fragment de detalle (CafeteriaFragment).
         */
        holder.titulo.transitionName = "nombre_cafeteria"

        /**
         * Evento de clic en toda la card.
         * Se envían:
         * - El objeto "item" (cafetería seleccionada)
         * - El TextView del título → usado para la animación shared element
         */
        holder.itemView.setOnClickListener {
            onItemClick(item, holder.titulo)
        }
    }
}
