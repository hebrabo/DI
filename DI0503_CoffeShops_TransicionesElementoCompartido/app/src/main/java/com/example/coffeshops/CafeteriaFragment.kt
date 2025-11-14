package com.example.coffeshops

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.transition.Slide
import androidx.transition.TransitionInflater

class CafeteriaFragment : Fragment() {

    // Variable donde guardaremos el nombre recibido desde el fragment anterior
    private var nombreCafeteria: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Recuperamos los argumentos enviados al navegar (el nombre de la cafetería)
        arguments?.let {
            nombreCafeteria = it.getString(ARG_NOMBRE_CAFETERIA)
        }

        /**
         * ================================
         * TRANSICIÓN COMPARTIDA (SHARED)
         * ================================
         * Esta transición se aplica sobre un elemento común entre fragments,
         * en nuestro caso el TextView que muestra el nombre de la cafetería.
         * Debe tener el mismo transitionName en ambos layouts.
         *
         * android.R.transition.move → hace una animación suave
         * moviendo el elemento desde su posición original hasta la nueva.
         */
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(android.R.transition.move)

        /**
         * ===============================
         * TRANSICIONES DEL RESTO DEL UI
         * ===============================
         * Definimos cómo entra el fragment al mostrarse:
         *
         * Slide(Gravity.START):
         *   - La vista entra deslizándose desde la izquierda.
         *
         * duration = 300 → duración de la animación en milisegundos
         */
        enterTransition = Slide(Gravity.START).apply { duration = 300 }
        returnTransition = Slide(Gravity.START).apply { duration = 300 }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        // Inflamos el layout asociado al fragment
        val view = inflater.inflate(R.layout.fragment_cafeteria, container, false)

        /**
         * ========================
         * TÍTULO DE LA CAFETERÍA
         * ========================
         * Aquí colocamos en el TextView el nombre recibido desde el fragment anterior.
         */
        val tvNombre = view.findViewById<TextView>(R.id.tvNombreCafeteria)
        tvNombre.text = nombreCafeteria ?: "Cafetería desconocida"

        /**
         * ================================
         * ANIMACIÓN DE LOS COMENTARIOS
         * ================================
         * Para que las cards no aparezcan instantáneamente mientras
         * se ejecuta la animación principal, las ocultamos temporalmente
         * y las mostramos justo después del primer frame del layout.
         *
         * Esto permite sincronizar su aparición con el slide principal.
         */
        val comentariosLayout = view.findViewById<LinearLayout>(R.id.layoutComentarios)
        comentariosLayout?.visibility = View.INVISIBLE
        comentariosLayout?.post {
            comentariosLayout.visibility = View.VISIBLE
        }

        return view
    }

    companion object {

        // Constante para la clave del argumento
        private const val ARG_NOMBRE_CAFETERIA = "nombre_cafeteria"

        /**
         * Metodo de creación del fragment con argumentos.
         * Permite instanciarlo pasando directamente el nombre.
         */
        fun newInstance(nombre: String): CafeteriaFragment {
            val fragment = CafeteriaFragment()
            val args = Bundle()
            args.putString(ARG_NOMBRE_CAFETERIA, nombre)
            fragment.arguments = args
            return fragment
        }
    }
}
