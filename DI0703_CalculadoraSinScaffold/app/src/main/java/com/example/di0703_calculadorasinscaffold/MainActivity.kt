package com.example.di0703_calculadorasinscaffold

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.di0703_calculadorasinscaffold.ui.theme.DI0703_AppCalculadoraTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CalculadoraSinScaffold()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalculadoraSinScaffold() {
    // Definición de estados (igual que antes)
    var numero1 by remember { mutableStateOf("") }
    var numero2 by remember { mutableStateOf("") }
    var resultado by remember { mutableStateOf("0.0") }

    // COLUMNA PRINCIPAL: Contenedor de toda la pantalla
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        // ---------------------------------------------------------
        // 1. LA BARRA SUPERIOR (Simulada o usando el componente)
        // ---------------------------------------------------------
        // Al estar dentro de una Column, esto se dibuja primero (arriba)
        TopAppBar(
            title = { Text("Calculadora_Basica", color = Color.White) },
            colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF008577))
        )

        // ---------------------------------------------------------
        // 2. EL CONTENIDO DE LA CALCULADORA
        // ---------------------------------------------------------
        Column(
            modifier = Modifier
                .padding(16.dp) // Margen para que no pegue a los bordes
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            // Input 1
            TextField(
                value = numero1,
                onValueChange = { numero1 = it },
                label = { Text("Número 1") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Input 2
            TextField(
                value = numero2,
                onValueChange = { numero2 = it },
                label = { Text("Número 2") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Botones Fila 1
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                // Reutilizamos la lógica de botones
                BotonSimple("SUMAR") { resultado = operacion(numero1, numero2, "+") }
                BotonSimple("RESTAR") { resultado = operacion(numero1, numero2, "-") }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botones Fila 2
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                BotonSimple("MULTI") { resultado = operacion(numero1, numero2, "*") }
                BotonSimple("DIVISION") { resultado = operacion(numero1, numero2, "/") }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Resultado
            Text(
                text = "Resultado: $resultado",
                fontSize = 28.sp,
                color = Color.Gray
            )
        }
    }
}

// Botón auxiliar simplificado para este ejemplo
@Composable
fun BotonSimple(texto: String, accion: () -> Unit) {
    Button(
        onClick = accion,
        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
        shape = MaterialTheme.shapes.small
    ) {
        Text(text = texto, color = Color.Black)
    }
}

// Lógica auxiliar simplificada
fun operacion(n1: String, n2: String, tipo: String): String {
    val v1 = n1.toDoubleOrNull() ?: 0.0
    val v2 = n2.toDoubleOrNull() ?: 0.0
    return when (tipo) {
        "+" -> (v1 + v2).toString()
        "-" -> (v1 - v2).toString()
        "*" -> (v1 * v2).toString()
        "/" -> if (v2 != 0.0) (v1 / v2).toString() else "0.0"
        else -> "0.0"
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    DI0703_AppCalculadoraTheme {
        CalculadoraSinScaffold()
    }
}