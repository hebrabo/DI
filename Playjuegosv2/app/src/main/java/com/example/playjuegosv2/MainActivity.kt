package com.example.playjuegosv2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.playjuegosv2.ui.theme.PlayJuegosV2Theme


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Botón Nuevo Jugador
        val jugador = findViewById<Button>(R.id.button2) as Button

        jugador.setOnClickListener { lanzarNewPlayer() }
    }
        fun lanzarNewPlayer() {
            val i = Intent(this, NewPlayer::class.java)
            startActivity(i)
        }
    }

