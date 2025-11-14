package com.example.di0602_menuscontextuales

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GalleryFragment : Fragment() {

    private val images = listOf(
        R.drawable.image1, R.drawable.image2, R.drawable.image3,
        R.drawable.image4, R.drawable.image5, R.drawable.image6,
        R.drawable.image7, R.drawable.image8, R.drawable.image9
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gallery, container, false)
        val recyclerView: RecyclerView = view.findViewById(R.id.recycler_view)

        // GridLayoutManager con 2 columnas → imágenes más grandes
        recyclerView.layoutManager = GridLayoutManager(context, 2)

        // Opcional: quitar márgenes entre ítems (fotos pegadas)
        recyclerView.addItemDecoration(object : RecyclerView.ItemDecoration() {
            override fun getItemOffsets(
                outRect: android.graphics.Rect,
                view: View,
                parent: RecyclerView,
                state: RecyclerView.State
            ) {
                outRect.set(0, 0, 0, 0) // sin espacio entre imágenes
            }
        })

        recyclerView.adapter = GalleryAdapter(images)
        return view
    }
}
