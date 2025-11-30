package com.example.accesagenda.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.accesagenda.databinding.ItemCategoryRowBinding
import com.example.accesagenda.model.CategoryItem
// R se importa automáticamente si es necesario, pero está bien mantenerlo si se usa.
import com.example.accesagenda.R

/**
 * Adaptador para el RecyclerView que muestra las categorías de la agenda
 * (ej. Gimnasio, Compra). Utiliza el layout item_category_row.xml.
 */
class CategoryAdapter(private val items: List<CategoryItem>) :
    RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    /**
     * ViewHolder: Contiene la referencia al View Binding (ItemCategoryRowBinding)
     * para acceder a las vistas del layout de un solo ítem.
     */
    class CategoryViewHolder(private val binding: ItemCategoryRowBinding) :
        RecyclerView.ViewHolder(binding.root) {

        /**
         * Asigna los datos de un objeto CategoryItem a las vistas correspondientes.
         */
        fun bind(item: CategoryItem) {
            binding.tvCategoryName.text = item.name

            // Asignar el recurso de imagen. Usamos 'let' porque imageResId es nullable (Int?).
            item.imageResId?.let { resId ->
                binding.ivCategoryIcon.setImageResource(resId)
            }
        }
    }

    /**
     * Crea y devuelve un nuevo ViewHolder inflando el layout de la fila
     * usando View Binding.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        // Inflamos el layout item_category_row usando el LayoutInflater y el binding.
        val binding = ItemCategoryRowBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false // 'false' porque el RecyclerView se encargará de adjuntar la vista.
        )
        return CategoryViewHolder(binding)
    }

    /**
     * Vincula los datos del CategoryItem en la posición 'position' al ViewHolder.
     */
    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(items[position])
    }

    /**
     * Devuelve el número total de ítems en la lista de categorías.
     */
    override fun getItemCount() = items.size
}