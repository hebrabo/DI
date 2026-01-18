package com.codewithfk.arlearner.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.codewithfk.arlearner.util.Utils
import com.google.ar.core.Config
import com.google.ar.core.Frame
import com.google.ar.core.Plane
import com.google.ar.core.TrackingFailureReason
import io.github.sceneview.ar.ARScene
import io.github.sceneview.ar.arcore.createAnchorOrNull
import io.github.sceneview.ar.arcore.getUpdatedPlanes
import io.github.sceneview.ar.rememberARCameraNode
import io.github.sceneview.model.ModelInstance
import io.github.sceneview.rememberCollisionSystem
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberMaterialLoader
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNodes
import io.github.sceneview.rememberView

/**
 * QuizScreen: Pantalla de evaluación lúdica.
 * El objetivo es que el niño cuente los objetos que aparecen en AR y elija el número correcto.
 */
@Composable
fun QuizScreen(navController: NavController) {
    // Puntuación del jugador. Se mantiene durante la sesión actual.
    val score = remember { mutableStateOf(0) }

    Box(modifier = Modifier.fillMaxSize()) {

        /**
         * Seleccionamos un reto aleatorio de nuestra lista de juegos.
         * 'model.value' contiene el nombre del número y la ruta del archivo 3D.
         */
        val model = remember { mutableStateOf(Utils.randomModel()) }

        // --- INICIALIZACIÓN DE MOTORES GRÁFICOS ---
        val engine = rememberEngine()
        val modelLoader = rememberModelLoader(engine = engine)
        val materialLoader = rememberMaterialLoader(engine = engine)
        val cameraNode = rememberARCameraNode(engine = engine)
        val childNodes = rememberNodes()
        val view = rememberView(engine = engine)
        val collisionSystem = rememberCollisionSystem(view = view)

        // Elementos técnicos para el seguimiento de la cámara y detección de superficies.
        val planeRenderer = remember { mutableStateOf(true) }
        val modelInstance = remember { mutableListOf<ModelInstance>() }
        val trackingFailureReason = remember { mutableStateOf<TrackingFailureReason?>(null) }
        val frame = remember { mutableStateOf<Frame?>(null) }



        // Escena de Realidad Aumentada
        ARScene(
            modifier = Modifier.fillMaxSize(),
            childNodes = childNodes,
            engine = engine,
            view = view,
            modelLoader = modelLoader,
            collisionSystem = collisionSystem,
            planeRenderer = planeRenderer.value,
            cameraNode = cameraNode,
            materialLoader = materialLoader,
            onTrackingFailureChanged = { trackingFailureReason.value = it },
            onSessionUpdated = { _, updatedFrame ->
                frame.value = updatedFrame

                /**
                 * LÓGICA DE COLOCACIÓN AUTOMÁTICA:
                 * A diferencia del modo libre, aquí la app busca el suelo por sí misma.
                 * Si no hay ningún objeto en pantalla (childNodes.isEmpty()), busca el primer
                 * plano horizontal detectado y coloca el reto allí sin que el niño tenga que tocar.
                 */
                if (childNodes.isEmpty()) {
                    updatedFrame.getUpdatedPlanes()
                        .firstOrNull { it.type == Plane.Type.HORIZONTAL_UPWARD_FACING }?.let { plane ->
                            plane.createAnchorOrNull(plane.centerPose)?.let { anchor ->
                                // Creamos el nodo del objeto (ej: las manzanas para contar)
                                childNodes += Utils.createAnchorNode(
                                    engine = engine,
                                    modelLoader = modelLoader,
                                    materialLoader = materialLoader,
                                    modelInstance = modelInstance,
                                    anchor = anchor,
                                    model = model.value.second
                                )
                            }
                        }
                }
            },
            sessionConfiguration = { session, config ->
                // Configuración de realismo: sombras y oclusión con objetos reales.
                config.depthMode = when (session.isDepthModeSupported(Config.DepthMode.AUTOMATIC)) {
                    true -> Config.DepthMode.AUTOMATIC
                    else -> Config.DepthMode.DISABLED
                }
                config.lightEstimationMode = Config.LightEstimationMode.ENVIRONMENTAL_HDR
            }
        )

        /**
         * GENERACIÓN DE RESPUESTAS:
         * Creamos una lista con 3 opciones:
         * 1. Dos números aleatorios de nuestra lista.
         * 2. El número correcto (model.value.first).
         * Usamos '.shuffled()' para que la respuesta correcta no esté siempre en el mismo sitio.
         */
        val listOfAnswers = remember {
            mutableStateOf(
                listOf(
                    Utils.numbers.keys.random(),
                    Utils.numbers.keys.random(),
                    model.value.first
                ).shuffled()
            )
        }

        // --- INTERFAZ DE USUARIO (UI) ---

        // Parte superior: Título y Puntuación actual
        Box(modifier = Modifier.fillMaxWidth().padding(top = 40.dp)) {
            Text(
                text = "Quiz ABN: ¿Qué número ves?",
                modifier = Modifier.align(Alignment.Center),
                fontSize = 24.sp
            )
            Text(
                text = "Puntos: ${score.value}",
                modifier = Modifier.align(Alignment.CenterEnd).padding(end = 16.dp),
                fontSize = 24.sp
            )
        }

        // Parte inferior: Botones con las opciones numéricas
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(bottom = 40.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            listOfAnswers.value.forEach { answerKey ->
                // Reutilizamos el componente NumberItem para los botones de respuesta
                NumberItem(number = answerKey) {
                    // VALIDACIÓN DE RESPUESTA
                    if (model.value.first == answerKey) {
                        // Si acierta: suma punto y genera un nuevo reto aleatorio
                        score.value += 1
                        model.value = Utils.randomModel()

                        // Regeneramos las opciones de respuesta para el nuevo reto
                        listOfAnswers.value = listOf(
                            Utils.numbers.keys.random(),
                            Utils.numbers.keys.random(),
                            model.value.first
                        ).shuffled()

                        /**
                         * REINICIO DE ESCENA:
                         * Borramos los objetos actuales para que el nuevo reto
                         * aparezca en una posición nueva cuando se detecte el suelo.
                         */
                        childNodes.clear()
                        modelInstance.clear()
                        frame.value = null
                    }
                }
            }
        }
    }
}