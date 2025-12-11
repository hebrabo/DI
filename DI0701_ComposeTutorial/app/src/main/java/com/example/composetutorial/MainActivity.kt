package com.example.composetutorial

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.composetutorial.ui.theme.ComposeTutorialTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // setContent es el punto de entrada de Compose.
        // Sustituye al antiguo setContentView(R.layout.activity_main) de XML.
        setContent {
            // Aplicamos el tema de la app (colores, tipografías, formas) a todo lo que esté dentro.
            ComposeTutorialTheme {
                // Llamamos a nuestra función Composable principal pasando datos de prueba.
                Conversation(SampleData.conversationSample)
            }
        }
    }
}

// Data class simple para representar la información de un mensaje.
// En una app real, esto vendría de una base de datos o API.
data class Message(val author: String, val body: String)

/**
 * MessageCard es un componente de UI reutilizable.
 * La anotación @Composable le dice al compilador que esta función
 * convierte datos en interfaz gráfica.
 */
@Composable
fun MessageCard(msg: Message) {
    // Row: Organiza los elementos horizontalmente (Imagen a la izquierda, Texto a la derecha).
    // Modifier: Es clave en Compose. Sirve para configurar el layout (padding, tamaño, clics, etc).
    Row(modifier = Modifier.padding(all = 8.dp)) {

        // --- SECCIÓN DE LA IMAGEN DE PERFIL ---
        Image(
            painter = painterResource(R.drawable.profile_picture),
            contentDescription = null, 
            modifier = Modifier
                .size(40.dp) // Tamaño fijo
                .clip(CircleShape) // Recorta la imagen en círculo
                .border(1.5.dp, MaterialTheme.colorScheme.primary, CircleShape) // Añade borde usando color del tema
        )

        // Spacer: Un espacio vacío horizontal para separar la imagen del texto.
        Spacer(modifier = Modifier.width(8.dp))

        // --- SECCIÓN DE LÓGICA DE ESTADO (STATE) ---
        // Aquí ocurre la magia de la interactividad.
        // `isExpanded` es una variable que guarda si el mensaje está expandido o no.
        // `remember`: Le dice a Compose "guarda este valor en memoria". Si no usamos remember,
        // cada vez que la pantalla se redibuja, la variable se resetearía a false.
        // `mutableStateOf`: Crea una variable observable. Si cambia, la UI se redibuja automáticamente (Recomposición).
        var isExpanded by remember { mutableStateOf(false) }

        // --- SECCIÓN DE ANIMACIÓN DE COLOR ---
        // Esta variable calculará gradualmente el color entre primary y surface
        // dependiendo del valor de isExpanded.
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        )

        // --- SECCIÓN DE TEXTOS (COLUMNA) ---
        // Column: Organiza los textos verticalmente (Autor arriba, Cuerpo abajo).
        // .clickable: Hacemos que toda la columna responda al clic cambiando el estado.
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = msg.author,
                color = MaterialTheme.colorScheme.secondary, // Usa colores del tema para modo oscuro/claro automático
                style = MaterialTheme.typography.titleSmall // Usa tipografía predefinida
            )

            Spacer(modifier = Modifier.height(4.dp)) // Espacio vertical entre autor y mensaje

            // Surface: Un contenedor que maneja formas, sombras, bordes y color de fondo.
            Surface(
                shape = MaterialTheme.shapes.medium, // Bordes redondeados
                shadowElevation = 1.dp,
                color = surfaceColor, // Aquí aplicamos el color animado que definimos arriba
                // .animateContentSize(): Esta es una animación automática.
                // Si el tamaño del contenido cambia (al expandir el texto), suaviza la transición.
                modifier = Modifier.animateContentSize().padding(1.dp)
            ) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    // Lógica visual: Si está expandido, muestra todo (Int.MAX_VALUE).
                    // Si no, muestra solo 1 línea.
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

/**
 * Conversation renderiza la lista completa.
 */
@Composable
fun Conversation(messages: List<Message>) {
    // LazyColumn es el equivalente moderno y sencillo al RecyclerView.
    // "Lazy" significa que solo renderiza los elementos que son visibles en la pantalla,
    // lo que lo hace muy eficiente para listas largas.
    LazyColumn {
        // items: Itera sobre la lista de mensajes.
        items(messages) { message ->
            // Por cada item en la lista, dibuja una MessageCard
            MessageCard(message)
        }
    }
}

// --- PREVIEWS (VISTA PREVIA) ---
// Estas funciones no se ejecutan en la app final, solo sirven para que
// ver el diseño en Android Studio (Split o Design view) sin tener que usar el emulador.

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES, // Fuerza modo oscuro para probar los colores
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PreviewMessageCard() {
    ComposeTutorialTheme {
        Surface {
            // Probamos una sola tarjeta con datos falsos
            MessageCard(
                msg = Message("Lexi", "Hey, take a look at Jetpack Compose, it's great!")
            )
        }
    }
}

@Preview
@Composable
fun PreviewConversation() {
    ComposeTutorialTheme {
        // Probamos la lista completa
        Conversation(SampleData.conversationSample)
    }
}



