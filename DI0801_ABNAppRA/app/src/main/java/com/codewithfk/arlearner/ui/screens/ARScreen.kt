package com.codewithfk.arlearner.ui.screens

import android.view.MotionEvent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.codewithfk.arlearner.util.Utils
import com.google.ar.core.Config
import com.google.ar.core.Frame
import com.google.ar.core.TrackingFailureReason
import io.github.sceneview.ar.ARScene
import io.github.sceneview.ar.arcore.createAnchorOrNull
import io.github.sceneview.ar.arcore.isValid
import io.github.sceneview.ar.rememberARCameraNode
import io.github.sceneview.model.ModelInstance
import io.github.sceneview.node.Node
import io.github.sceneview.rememberCollisionSystem
import io.github.sceneview.rememberEngine
import io.github.sceneview.rememberMaterialLoader
import io.github.sceneview.rememberModelLoader
import io.github.sceneview.rememberNodes
import io.github.sceneview.rememberOnGestureListener
import io.github.sceneview.rememberView

/**
 * ARScreen: La pantalla donde el niño interactúa con los objetos en el mundo real.
 * @param model Es el número (en formato String) que el niño seleccionó en la lista.
 */
@Composable
fun ARScreen(navController: NavController, model: String) {

    // --- CONFIGURACIÓN DEL MOTOR 3D ---
    // Usamos 'remember' para que estos motores no se reinicien cada vez que la pantalla se redibuja.

    // El 'engine' es el corazón gráfico que procesa la imagen.
    val engine = rememberEngine()
    // Los 'loaders' se encargan de cargar los archivos de los modelos (.glb) y sus texturas.
    val modelLoader = rememberModelLoader(engine = engine)
    val materialLoader = rememberMaterialLoader(engine = engine)

    // La cámara de AR que rastrea dónde está el móvil en el espacio.
    val cameraNode = rememberARCameraNode(engine = engine)
    // Una lista que guarda todos los objetos (nodos) que vamos poniendo en el suelo.
    val childNodes = rememberNodes()

    val view = rememberView(engine = engine)
    // El sistema de colisiones permite que los objetos "se toquen" o detecten toques del dedo.
    val collisionSystem = rememberCollisionSystem(view = view)

    // Estado para saber si debemos dibujar la cuadrícula de puntos en el suelo.
    val planeRenderer = remember { mutableStateOf(true) }

    // Almacena las instancias de los modelos creados.
    val modelInstance = remember { mutableListOf<ModelInstance>() }

    // Guarda el motivo si la Realidad Aumentada falla (ej: falta de luz).
    val trackingFailureReason = remember { mutableStateOf<TrackingFailureReason?>(null) }

    // El 'frame' representa un único instante de tiempo de lo que ve la cámara.
    val frame = remember { mutableStateOf<Frame?>(null) }

    // Componente principal que dibuja la cámara y los objetos AR
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
        onTrackingFailureChanged = {
            trackingFailureReason.value = it
        },
        onSessionUpdated = { _, updatedFrame ->
            // Actualizamos constantemente el 'frame' para saber dónde está el suelo en cada segundo.
            frame.value = updatedFrame
        },
        sessionConfiguration = { session, config ->
            // Activamos el modo de profundidad para que los objetos se oculten detrás de muebles reales.
            config.depthMode = when (session.isDepthModeSupported(Config.DepthMode.AUTOMATIC)) {
                true -> Config.DepthMode.AUTOMATIC
                else -> Config.DepthMode.DISABLED
            }
            // Estimación de luz: Hace que los objetos 3D tengan las mismas sombras que las de la habitación.
            config.lightEstimationMode = Config.LightEstimationMode.ENVIRONMENTAL_HDR
        },
        onGestureListener = rememberOnGestureListener(
            onSingleTapConfirmed = { e: MotionEvent, node: Node? ->
                // Si el usuario toca la pantalla y NO está tocando un objeto ya existente:
                if (node == null) {
                    // Lanzamos un "rayo" desde el dedo hacia el suelo (Hit Test).
                    val hitTestResult = frame.value?.hitTest(e.x, e.y)

                    // Buscamos el primer punto válido donde el rayo choque con una superficie plana.
                    hitTestResult?.firstOrNull {
                        it.isValid(depthPoint = false, point = false)
                    }?.createAnchorOrNull()?.let { anchor ->
                        // Si encontramos suelo, creamos un "ancla" (un punto fijo en el mundo real).
                        val nodeModel = Utils.createAnchorNode(
                            engine = engine,
                            modelLoader = modelLoader,
                            materialLoader = materialLoader,
                            modelInstance = modelInstance,
                            anchor = anchor,
                            // Buscamos el modelo 3D que corresponde al número elegido.
                            model = Utils.getModelForNumber(model)
                        )
                        // Añadimos el objeto a la escena para que el niño lo vea.
                        childNodes += nodeModel
                    }
                }
            }
        )
    )
}