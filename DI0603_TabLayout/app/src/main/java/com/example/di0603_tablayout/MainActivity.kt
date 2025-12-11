package com.example.di0603_tablayout

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tabLayout: TabLayout = findViewById(R.id.tabs)

        // Cargar Fragment 1 al inicio
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.content_fragment, Fragment1())
                .commit()
            // Título inicial
            supportActionBar?.title = "Tab 1"
        }

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                val fragment = when (tab.position) {
                    0 -> Fragment1()
                    1 -> Fragment2() // Aquí se cargarán las tarjetas
                    2 -> Fragment3()
                    else -> Fragment1()
                }

                supportFragmentManager.beginTransaction()
                    .replace(R.id.content_fragment, fragment)
                    .commit()

                // Cambiar el título de la barra superior
                supportActionBar?.title = tab.text
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }
}