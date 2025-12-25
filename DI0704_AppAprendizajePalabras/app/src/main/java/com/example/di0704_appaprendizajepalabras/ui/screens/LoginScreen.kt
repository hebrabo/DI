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

@Composable
fun LoginScreen(onLoginSuccess: () -> Unit, onNavigateToRegister: () -> Unit) {
    // Variables para guardar lo que el usuario escribe
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Bienvenido", fontSize = 32.sp)

        Spacer(modifier = Modifier.height(32.dp))

        // Campo de Email
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
            visualTransformation = PasswordVisualTransformation(), // Para ocultar los puntos
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // Botón de Entrar
        Button(
            onClick = { onLoginSuccess() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Iniciar Sesión")
        }

        // Enlace a Registro
        TextButton(onClick = { onNavigateToRegister() }) {
            Text("¿No tienes cuenta? Regístrate")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun LoginScreenPreview() {
    // Le pasamos lambdas vacías {} porque el preview no necesita navegar
    LoginScreen(
        onLoginSuccess = { /* No hace nada en preview */ },
        onNavigateToRegister = { /* No hace nada en preview */ }
    )
}