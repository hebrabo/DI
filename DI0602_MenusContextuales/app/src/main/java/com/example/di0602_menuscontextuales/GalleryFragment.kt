package com.example.di0602_menuscontextuales

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.PopupMenu
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Fragment que muestra una galería de imágenes en un RecyclerView.
 * Permite:
 * 1. Pulsación larga sobre una imagen → activa ActionMode (barra de opciones contextual)
 * 2. Clic corto sobre una imagen → muestra un menú flotante (PopupMenu)
 */
class GalleryFragment : Fragment() {

    // Lista de imágenes a mostrar en la galería
    private val images = listOf(
        R.drawable.image1, R.drawable.image2, R.drawable.image3,
        R.drawable.image4, R.drawable.image5, R.drawable.image6,
        R.drawable.image7, R.drawable.image8, R.drawable.image9
    )

    // Callback de ActionMode
    private lateinit var modeCallBack: ActionMode.Callback

    // Referencia al ActionMode activo (si existe)
    private var actionMode: ActionMode? = null

    // Guardamos la vista seleccionada para poder resaltarla
    private var selectedView: View? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)

        // Usamos un GridLayoutManager con 2 columnas para imágenes más grandes
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        // Opcional: quitar márgenes entre ítems → fotos pegadas
        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: android.graphics.Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.set(0, 0, 0, 0) // sin espacios
            }
        })

        // Creamos el adapter y lo asignamos al RecyclerView
        val adapter = GalleryAdapter(images)
        recyclerView.adapter = adapter

        // ---------------------------------------------------
        // CONFIGURACIÓN DEL ACTIONMODE
        // ---------------------------------------------------
        modeCallBack = object : ActionMode.Callback {
            override fun onCreateActionMode(mode: ActionMode, menu: Menu): Boolean {
                // Título de la barra de opciones contextual
                mode.title = "Opciones"
                // Inflamos el menú desde menu_context.xml
                mode.menuInflater.inflate(R.menu.menu_context, menu)
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode, menu: Menu) = false

            override fun onActionItemClicked(mode: ActionMode, item: MenuItem): Boolean {
                // Acciones según la opción seleccionada
                when (item.itemId) {
                    R.id.action_editar -> Log.i("GalleryFragment", "Editar")
                    R.id.action_eliminar -> Log.i("GalleryFragment", "Eliminar")
                    R.id.action_compartir -> Log.i("GalleryFragment", "Compartir")
                    else -> return false
                }
                // Cerramos el ActionMode tras realizar la acción
                mode.finish()
                return true
            }

            override fun onDestroyActionMode(mode: ActionMode) {
                // Limpiamos referencias
                actionMode = null
                // Quitamos el resaltado de la imagen
                selectedView?.setBackgroundColor(Color.TRANSPARENT)
                selectedView = null
            }
        }

        // ---------------------------------------------------
        // CALLBACKS DEL ADAPTER
        // ---------------------------------------------------

        // Pulsación larga → activa ActionMode
        adapter.onLongClick = { view ->
            if (actionMode == null) {
                selectedView = view
                // Resaltamos la imagen seleccionada
                view.setBackgroundColor(Color.LTGRAY)
                // Iniciamos el ActionMode
                actionMode = view.startActionMode(modeCallBack)
            }
        }

        // Clic corto → muestra un menú flotante (PopupMenu)
        adapter.onClick = { view ->
            val popup = PopupMenu(requireContext(), view)
            // Inflamos el mismo menú
            popup.menuInflater.inflate(R.menu.menu_context, popup.menu)
            // Listener de selección de opciones
            popup.setOnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.action_editar -> Log.i("GalleryFragment", "Editar (flotante)")
                    R.id.action_eliminar -> Log.i("GalleryFragment", "Eliminar (flotante)")
                    R.id.action_compartir -> Log.i("GalleryFragment", "Compartir (flotante)")
                }
                true
            }
            // Mostramos el PopupMenu
            popup.show()
        }

        return view
    }
}
