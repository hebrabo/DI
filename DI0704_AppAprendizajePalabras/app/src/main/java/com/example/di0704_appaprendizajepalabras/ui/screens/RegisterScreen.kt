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
fun RegisterScreen(onRegisterSuccess: () -> Unit, onBackToLogin: () -> Unit) {
    // Estados para los campos
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Crear Cuenta", fontSize = 32.sp)

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre Completo") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { onRegisterSuccess() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarse")
        }

        TextButton(onClick = { onBackToLogin() }) {
            Text("¿Ya tienes cuenta? Inicia sesión")
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    // Pasamos lambdas vacías porque el preview no necesita ejecutar lógica de navegación
    RegisterScreen(
        onRegisterSuccess = { /* No hace nada en preview */ },
        onBackToLogin = { /* No hace nada en preview */ }
    )
}