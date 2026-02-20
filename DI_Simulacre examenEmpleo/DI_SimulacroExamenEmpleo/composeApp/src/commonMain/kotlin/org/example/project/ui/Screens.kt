package org.example.project.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import di_simulacroexamenempleo.composeapp.generated.resources.Res
import di_simulacroexamenempleo.composeapp.generated.resources.logoe
import kotlinx.coroutines.launch
import org.example.project.model.Persona
import org.example.project.viewmodel.InscripcionViewModel
import org.jetbrains.compose.resources.painterResource

/**
 * PANTALLA DE BIENVENIDA (Punto 1)
 */
@Composable
fun PantallaBienvenida() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(Res.drawable.logoe),
            contentDescription = "Logo Empleo Grande",
            modifier = Modifier
                .size(120.dp)
                .padding(bottom = 32.dp)
        )

        Text(
            text = "Bienvenido a",
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f)
        )

        Text(
            text = "Portal Empleo",
            style = MaterialTheme.typography.displaySmall,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = "Tu futuro profesional empieza aquí",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            textAlign = TextAlign.Center
        )
    }
}

/**
 * PANTALLA DE INSCRIPCIÓN (Punto 2): Formulario de recogida de datos.
 * Implementa validación en tiempo real y feedback mediante Snackbar.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaInscripcion(viewModel: InscripcionViewModel) {
    // ESTADOS LOCALES: Controlan el contenido de los campos de texto.
    var nombre by remember { mutableStateOf("") }
    var dni by remember { mutableStateOf("") }
    var edad by remember { mutableStateOf("18") }
    var sexo by remember { mutableStateOf("Masculino") }
    var direccion by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var habilidades by remember { mutableStateOf("") }

    // Estados para controlar la apertura/cierre de menús desplegables.
    var expandirEdad by remember { mutableStateOf(false) }
    var expandirSexo by remember { mutableStateOf(false) }

    // Componentes para notificaciones flotantes.
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope() // Necesario para lanzar el snackbar desde una corrutina.

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
                .verticalScroll(rememberScrollState()), // Permite scroll si el formulario es largo.
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // CAMPO NOMBRE: Valida letras y espacios mediante el ViewModel.
            OutlinedTextField(
                value = nombre,
                onValueChange = { if (viewModel.esNombreValido(it)) nombre = it },
                label = { Text("Nombre y Apellidos") },
                isError = nombre.isNotEmpty() && !viewModel.esNombreValido(nombre),
                modifier = Modifier.fillMaxWidth()
            )

            // CAMPO DNI: Muestra error visual si no cumple el Regex (8 números + letra).
            OutlinedTextField(
                value = dni,
                onValueChange = { dni = it },
                label = { Text("DNI (8 números + letra)") },
                isError = dni.isNotEmpty() && !viewModel.esDniValido(dni),
                modifier = Modifier.fillMaxWidth()
            )

            // DESPLEGABLE EDAD (Punto 2): Rango 18-67 con scroll interno.
            ExposedDropdownMenuBox(
                expanded = expandirEdad,
                onExpandedChange = { expandirEdad = !expandirEdad }
            ) {
                OutlinedTextField(
                    value = edad,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Edad") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirEdad) },
                    modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true).fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandirEdad,
                    onDismissRequest = { expandirEdad = false },
                    modifier = Modifier.heightIn(max = 280.dp) // Limita altura para habilitar scroll.
                ) {
                    (18..67).forEach { n ->
                        DropdownMenuItem(
                            text = { Text(n.toString()) },
                            onClick = { edad = n.toString(); expandirEdad = false }
                        )
                    }
                }
            }

            // DESPLEGABLE SEXO
            ExposedDropdownMenuBox(
                expanded = expandirSexo,
                onExpandedChange = { expandirSexo = !expandirSexo }
            ) {
                OutlinedTextField(
                    value = sexo,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Sexo") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expandirSexo) },
                    modifier = Modifier.menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable, true).fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expandirSexo,
                    onDismissRequest = { expandirSexo = false }
                ) {
                    listOf("Masculino", "Femenino", "Otro", "No desea compartirlo").forEach { s ->
                        DropdownMenuItem(
                            text = { Text(s) },
                            onClick = { sexo = s; expandirSexo = false }
                        )
                    }
                }
            }

            OutlinedTextField(value = direccion, onValueChange = { direccion = it }, label = { Text("Dirección") }, modifier = Modifier.fillMaxWidth())

            // CAMPO EMAIL: Usa teclado optimizado para correos.
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") },
                isError = email.isNotEmpty() && !viewModel.esEmailValido(email),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                modifier = Modifier.fillMaxWidth()
            )

            // CAMPO TELÉFONO: Filtra para que el usuario solo pueda introducir dígitos.
            OutlinedTextField(
                value = telefono,
                onValueChange = { if (it.all { c -> c.isDigit() }) telefono = it },
                label = { Text("Teléfono") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                modifier = Modifier.fillMaxWidth()
            )

            // CAMPO HABILIDADES: Área de texto más grande (Punto 2.5).
            OutlinedTextField(
                value = habilidades,
                onValueChange = { habilidades = it },
                label = { Text("Habilidades") },
                modifier = Modifier.fillMaxWidth().height(100.dp)
            )

            // BOTONES DE ACCIÓN: Guardado con feedback visual.
            Row(
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                Button(onClick = {
                    nombre = ""; dni = ""; direccion = ""; email = ""; telefono = ""; habilidades = ""
                }) {
                    Text("Borrar")
                }

                Button(onClick = {
                    val nuevaPersona = Persona(nombre, dni, edad, sexo, direccion, email, telefono, habilidades)
                    if (viewModel.guardarPersona(nuevaPersona)) {
                        // Notificamos éxito y limpiamos campos.
                        scope.launch { snackbarHostState.showSnackbar("Inscripción guardada") }
                        nombre = ""; dni = ""; direccion = ""; email = ""; telefono = ""; habilidades = ""
                    } else {
                        // Notificamos error si las validaciones del ViewModel fallan.
                        scope.launch { snackbarHostState.showSnackbar("Error: Revisa los campos") }
                    }
                }) {
                    Text("Guardar")
                }
            }
        }

        // Host que renderiza los mensajes del Snackbar en la parte inferior.
        SnackbarHost(
            hostState = snackbarHostState,
            modifier = Modifier.align(Alignment.BottomCenter)
        )
    }
}

/**
 * PANTALLA DE SOLICITUDES (Punto 3 y 4): Lista de inscritos con filtro y detalles.
 */
@Composable
fun PantallaSolicitudes(viewModel: InscripcionViewModel) {
    var personaDetalle by remember { mutableStateOf<Persona?>(null) }
    var textoFiltro by remember { mutableStateOf("") }
    var filtroAplicado by remember { mutableStateOf("") }

    // FILTRADO (Punto 4): Se recalcula la lista según el nombre introducido.
    val listaFiltrada = if (filtroAplicado.isEmpty()) viewModel.listaInscritos
    else viewModel.listaInscritos.filter { it.nombre.contains(filtroAplicado, ignoreCase = true) }

    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        OutlinedTextField(
            value = textoFiltro,
            onValueChange = { textoFiltro = it },
            label = { Text("Filtrar por nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        Row(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
            Button(onClick = { filtroAplicado = textoFiltro }) { Text("Aplicar Filtro") }
            Button(onClick = { filtroAplicado = ""; textoFiltro = "" }) { Text("Eliminar Filtro") }
        }

        // LISTADO (Punto 3): LazyColumn es eficiente para listas de tamaño variable.
        LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            items(listaFiltrada) { persona ->
                Card(modifier = Modifier.fillMaxWidth().clickable { personaDetalle = persona }) {
                    Row(
                        modifier = Modifier.padding(16.dp).fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(persona.nombre)
                        // BOTÓN ELIMINAR: Llama a la lógica del ViewModel.
                        IconButton(onClick = { viewModel.eliminarPersona(persona) }) {
                            Icon(Icons.Default.Delete, contentDescription = "Borrar", tint = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            }
        }

        // DIÁLOGO DE DETALLES (Punto 3): Se muestra cuando personaDetalle no es null.
        personaDetalle?.let { persona ->
            AlertDialog(
                onDismissRequest = { personaDetalle = null },
                confirmButton = { TextButton(onClick = { personaDetalle = null }) { Text("Cerrar") } },
                title = { Text("Detalles del Candidato") },
                text = {
                    Column {
                        Text("Nombre: ${persona.nombre}")
                        Text("DNI: ${persona.dni}")
                        Text("Edad: ${persona.edad}")
                        Text("Sexo: ${persona.sexo}")
                        Text("Dirección: ${persona.direccion}")
                        Text("Email: ${persona.email}")
                        Text("Teléfono: ${persona.telefono}")
                        Text("Habilidades: ${persona.habilidades}")
                    }
                }
            )
        }
    }
}

/**
 * PANTALLA DE AYUDA: Contiene texto informativo y enlace externo.
 */
@Composable
fun PantallaAyuda() {
    val uriHandler = LocalUriHandler.current
    val interactionSource = remember { MutableInteractionSource() }
    val estaPulsado by interactionSource.collectIsPressedAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Ayuda y Consejos",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        Text(
            text = """
                Esta aplicación busca facilitarte la búsqueda de trabajo mediante la presentación de un minicurriculo.
                
                Buscar trabajo supone asumir un proceso activo, estructurado y exigente que requiere planificación, disciplina y estrategia, más que una simple búsqueda pasiva.
                
                No se trata solo de enviar currículos, sino de gestionar una "carrera profesional" personal, entendiendo que es un trabajo en sí mismo.
                
                Buscar trabajo implica dedicar tiempo diario, establecer metas, mantener un horario y desarrollar hábitos como investigar empresas, redactar currículos personalizados y preparar entrevistas.
                
                El proceso incluye múltiples pasos: desde autoevaluarse (qué te gusta, qué habilidades tienes), localizar ofertas (a través de portales como Indeed, LinkedIn, o redes personales), enviar candidaturas, prepararse para entrevistas y mantener una red activa.
            """.trimIndent(),
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = Modifier.height(24.dp))

        // BOTÓN EXTERNO: Abre el navegador con la URL del SEPE.
        Button(
            onClick = { uriHandler.openUri("https://www.sepe.es/") },
            interactionSource = interactionSource,
            colors = ButtonDefaults.buttonColors(
                // Efecto visual: cambia de color al pulsar (isPressed).
                containerColor = if (estaPulsado) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.secondary
            )
        ) {
            Text("Visitar web del SEPE")
        }
    }
}