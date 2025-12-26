package com.example.di0704_appaprendizajepalabras.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.tooling.preview.Preview

/**
 * PANTALLA DE LOGIN:
 * Esta pantalla demuestra el patrón de "Elevación de Estado" (State Hoisting).
 * No navega por sí misma, sino que avisa a través de funciones (lambdas)
 * cuando algo importante sucede.
 */
@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,      // Callback: "Avisar que el login fue bien"
    onNavigateToRegister: () -> Unit // Callback: "Avisar que el usuario quiere ir a Registro"
) {
    /**
     * GESTIÓN DE ESTADO (State):
     * 'mutableStateOf' crea un valor que Compose "vigila".
     * 'remember' hace que ese valor no se borre cuando la pantalla se redibuja.
     * Si no usáramos esto, al escribir una letra, la pantalla se refrescaría
     * y la variable volvería a estar vacía.
     */
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Column organiza los elementos de forma vertical (uno debajo de otro)
    Column(
        modifier = Modifier
            .fillMaxSize()       // Ocupa toda la pantalla
            .padding(24.dp),    // Margen de seguridad en los bordes
        horizontalAlignment = Alignment.CenterHorizontally, // Centrado horizontal
        verticalArrangement = Arrangement.Center           // Centrado vertical
    ) {
        Text(text = "Bienvenido", fontSize = 32.sp)

        Spacer(modifier = Modifier.height(32.dp))

        /**
         * CAMPO DE EMAIL:
         * 'value' lee el estado actual.
         * 'onValueChange' actualiza el estado cada vez que el usuario pulsa una tecla.
         */
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Campo de Password
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            // Transforma el texto plano en puntos (••••) por seguridad
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botón de Entrar
        Button(
            onClick = {
                // Aquí iría la lógica de validación real (Firebase, API, etc.)
                // Por ahora, simplemente ejecutamos la función de éxito
                onLoginSuccess()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar Sesión")
        }

        // Enlace a Registro: Un botón que parece texto plano
        TextButton(onClick = { onNavigateToRegister() }) {
            Text("¿No tienes cuenta? Regístrate")
        }
    }
}

/**
 * PREVIEW:
 * Herramienta para desarrolladores que permite ver el diseño en tiempo real
 * sin necesidad de lanzar el emulador.
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    // Pasamos lambdas vacías {} para satisfacer los parámetros
    LoginScreen(
        onLoginSuccess = { },
        onNavigateToRegister = { }
    )
}