package com.example.di0603_tablayout

import android.view.View
import androidx.viewpager2.widget.ViewPager2

class CubePageTransformer : ViewPager2.PageTransformer {
    override fun transformPage(view: View, position: Float) {
        // Establecemos una distancia de cámara para que el efecto 3D se vea realista
        view.cameraDistance = 20000f

        when {
            position < -1 -> { // Página muy a la izquierda
                view.alpha = 0f
            }
            position <= 0 -> { // Página saliendo hacia la izquierda ([-1, 0])
                view.alpha = 1f
                view.pivotX = view.width.toFloat() // Pivote en el borde derecho
                view.rotationY = 90f * position
            }
            position <= 1 -> { // Página entrando desde la derecha ((0, 1])
                view.alpha = 1f
                view.pivotX = 0f // Pivote en el borde izquierdo
                view.rotationY = 90f * position
            }
            else -> { // Página muy a la derecha
                view.alpha = 0f
            }
        }
    }
}