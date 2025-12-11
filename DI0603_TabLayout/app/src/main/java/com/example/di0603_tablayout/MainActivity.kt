package com.example.di0603_tablayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Referencias
        val tabLayout = findViewById<TabLayout>(R.id.tabs)
        val viewPager = findViewById<ViewPager2>(R.id.view_pager)

        // 1. Crear y asignar el adaptador al ViewPager2
        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter

        // --- ANIMACIÓN (Ejercicio 5) ---
        // Al estar en el mismo paquete, detectará la clase automáticamente
        viewPager.setPageTransformer(CubePageTransformer())
        // -------------------------------

        // 2. Vincular TabLayout y ViewPager2 con TabLayoutMediator
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "TAB 1"
                1 -> tab.text = "TAB 2"
                2 -> tab.text = "TAB 3"
            }
        }.attach()
    }
}