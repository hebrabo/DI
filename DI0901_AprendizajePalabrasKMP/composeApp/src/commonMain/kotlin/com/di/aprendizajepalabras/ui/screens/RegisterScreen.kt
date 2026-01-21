package com.di.aprendizajepalabras.ui.screens


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
 * PANTALLA DE REGISTRO:
 * Al igual que el Login, esta pantalla captura datos del usuario.
 * Utiliza callbacks (funciones lambda) para avisar al Navegador de qué hacer
 * cuando el usuario termina el proceso o decide volver atrás.
 */
@Composable
fun RegisterScreen(
    onRegisterSuccess: () -> Unit, // Callback: "Usuario registrado con éxito"
    onBackToLogin: () -> Unit      // Callback: "Usuario quiere volver al Login"
) {
    /**
     * VARIABLES DE ESTADO:
     * Usamos 'remember' para que los textos no se pierdan al girar la pantalla
     * o al redibujar la UI (recomposición).
     * Cada vez que escribes una letra, la UI se entera gracias al 'mutableStateOf'.
     */
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    // Column organiza los elementos en vertical, uno debajo del otro.
    Column(
        modifier = Modifier
            .fillMaxSize()       // Ocupa todo el alto y ancho disponible
            .padding(24.dp),    // Añade un margen de 24dp en todos los lados
        horizontalAlignment = Alignment.CenterHorizontally, // Centra los hijos horizontalmente
        verticalArrangement = Arrangement.Center           // Centra el contenido en el medio de la pantalla
    ) {
        Text(text = "Crear Cuenta", fontSize = 32.sp)

        // El Spacer crea un "bloque de aire" o espacio vacío entre elementos
        Spacer(modifier = Modifier.height(32.dp))

        // --- CAMPO: NOMBRE COMPLETO ---
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it }, // Actualiza la variable 'nombre' al escribir
            label = { Text("Nombre Completo") },
            modifier = Modifier.fillMaxWidth() // Hace que el campo ocupe todo el ancho
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- CAMPO: EMAIL ---
        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // --- CAMPO: CONTRASEÑA ---
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            // visualTransformation oculta los caracteres de la contraseña (••••)
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))

        // --- BOTÓN: REGISTRARSE ---
        Button(
            onClick = {
                // Aquí iría la lógica para guardar el usuario en una Base de Datos.
                // Por ahora, ejecutamos el aviso de éxito.
                onRegisterSuccess()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Registrarse")
        }

        // --- ENLACE: VOLVER AL LOGIN ---
        TextButton(onClick = { onBackToLogin() }) {
            Text("¿Ya tienes cuenta? Inicia sesión")
        }
    }
}

/**
 * PREVIEW (Vista Previa):
 * Permite a los desarrolladores ver cómo queda el diseño sin tener que
 * compilar la app en un móvil real cada vez que hacen un cambio.
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun RegisterScreenPreview() {
    // Pasamos lambdas vacías porque el diseño visual no necesita lógica real
    RegisterScreen(
        onRegisterSuccess = { },
        onBackToLogin = { }
    )
}