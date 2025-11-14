package com.example.coffeshops

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * MainFragment:
 * -------------
 * Este fragment representa la pantalla principal de la aplicación.
 * Muestra una lista de cafeterías en un RecyclerView.
 * Al pulsar sobre un elemento, se navega a CafeteriaFragment con:
 *   - Datos enviados por argumento
 *   - Animación con elemento compartido (shared element transition)
 */
class MainFragment : Fragment() {

    // RecyclerView donde se muestra la lista
    private lateinit var recyclerView: RecyclerView

    // Adaptador de la lista
    private lateinit var adapter: CoffeAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        /**
         * 1️) INFLAMOS EL LAYOUT DEL FRAGMENT
         * -----------------------------------
         * fragment_main.xml contiene:
         *   - Un RecyclerView con id "main"
         */
        val view = inflater.inflate(R.layout.fragment_main, container, false)

        // Referencia al RecyclerView
        recyclerView = view.findViewById(R.id.main)

        /**
         * 2) CONFIGURAMOS EL RECYCLER VIEW
         * ---------------------------------
         * LinearLayoutManager muestra los elementos en columna.
         */
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        /**
         * 3️) CONFIGURAMOS EL ADAPTADOR
         * ------------------------------
         * - cargarDatos() devuelve la lista fija de cafeterías
         * - onItemClick se ejecuta cuando el usuario pulsa una card
         */
        adapter = CoffeAdapter(cargarDatos().toMutableList()) { cafeteria, tvNombre ->

            /**
             * 4️) ANIMACIÓN DEL ELEMENTO COMPARTIDO
             * -------------------------------------
             * FragmentNavigatorExtras conecta la transición del TextView (título)
             * entre MainFragment → CafeteriaFragment.
             *
             * "tvNombre to nombre_cafeteria" significa:
             *    View del título en esta pantalla ➜ View con el mismo transitionName en destino
             */
            val extras = FragmentNavigatorExtras(tvNombre to "nombre_cafeteria")

            /**
             * 5️) NAVEGACIÓN AL SIGUIENTE FRAGMENT
             * -----------------------------------
             * - navigate() abre CafeteriaFragment
             * - bundleOf pasa el nombre de la cafetería
             * - extras activa la animación compartida
             */
            findNavController().navigate(
                R.id.action_mainFragment_to_cafeteriaFragment,
                bundleOf("nombre_cafeteria" to cafeteria.titulo),
                null,
                extras
            )
        }

        // Asociamos el adaptador al RecyclerView
        recyclerView.adapter = adapter

        return view
    }

    /**
     * 6️) FUNCIÓN QUE GENERA LA LISTA DE CAFETERÍAS
     * ---------------------------------------------
     * Devuelve un ArrayList con datos estáticos para el RecyclerView.
     */
    private fun cargarDatos(): ArrayList<CartaDatos> = arrayListOf(
        CartaDatos(R.drawable.images,  "Antico Caffè Greco",      "St. Italy, Rome"),
        CartaDatos(R.drawable.images2, "Coffee Room",              "St. Germany, Berlin"),
        CartaDatos(R.drawable.images3, "Coffee Ibiza",             "St. Colon, Madrid"),
        CartaDatos(R.drawable.images4, "Pudding Coffee Shop",      "St. Diagonal, Barcelona"),
        CartaDatos(R.drawable.images5, "L'Express",                 "St. Picadilly Circus, London"),
        CartaDatos(R.drawable.images6, "Coffee Corner",            "St. Àngel Guimerà, Valencia")
    )
}
