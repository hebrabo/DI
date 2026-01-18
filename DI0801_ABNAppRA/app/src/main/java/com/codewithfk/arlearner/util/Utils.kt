package com.codewithfk.arlearner.util

import androidx.compose.ui.graphics.Color
import com.google.android.filament.Engine
import com.google.ar.core.Anchor
import io.github.sceneview.ar.node.AnchorNode
import io.github.sceneview.loaders.MaterialLoader
import io.github.sceneview.loaders.ModelLoader
import io.github.sceneview.model.ModelInstance
import io.github.sceneview.node.CubeNode
import io.github.sceneview.node.ModelNode

/**
 * Utils: Clase de utilidad que contiene las herramientas necesarias para
 * construir el mundo virtual de los (100) juegos ABN.
 */
object Utils {

    /**
     * Mapa de recursos: Asocia cada número con su archivo 3D correspondiente.
     * Para completar los (100) juegos, simplemente deberíamos añadir aquí las entradas
     * del 6 al 100 con sus respectivos archivos .glb.
     */
    val numbers = mapOf(
        "1" to "apple.glb",
        "2" to "ball.glb",
        "3" to "cat.glb",
        "4" to "dog.glb",
        "5" to "elephant.glb"
    )

    /**
     * Obtiene la ruta completa de la carpeta 'assets' para un número dado.
     * Si el número no existe en el mapa, devuelve una manzana por defecto.
     */
    fun getModelForNumber(number: String): String {
        val modelName = numbers[number] ?: "apple.glb"
        return "models/$modelName"
    }

    /**
     * createAnchorNode: Esta función es la "fábrica" de objetos 3D.
     * Convierte un punto en el suelo real (Anchor) en un objeto visible.
     */
    fun createAnchorNode(
        engine: Engine,
        modelLoader: ModelLoader,
        materialLoader: MaterialLoader,
        modelInstance: MutableList<ModelInstance>,
        anchor: Anchor,
        model: String // Ruta del modelo .glb
    ): AnchorNode {

        // 1. El AnchorNode es el "clavo" que sujeta el objeto al suelo real.
        val anchorNode = AnchorNode(engine = engine, anchor = anchor)

        /**
         * 2. Cargamos el modelo usando 'Instancing'.
         * Esto es muy importante para el rendimiento: en lugar de cargar 10 veces
         * la misma manzana, cargamos 1 y creamos 10 copias ligeras.
         * scaleToUnits = 0.2f define un tamaño pequeño y manejable para niños.
         */
        val modelNode = ModelNode(
            modelInstance = modelInstance.apply {
                if (isEmpty()) {
                    // Preparamos hasta 10 copias del objeto para ser colocadas.
                    this += modelLoader.createInstancedModel(model, 10)
                }
            }.removeLast(),
            scaleToUnits = 0.2f
        ).apply {
            // Permite que el usuario pueda mover o rotar el objeto.
            isEditable = true
        }

        /**
         * 3. Bounding Box (Caja de selección):
         * Creamos un cubo invisible alrededor del objeto. Se volverá visible
         * solo cuando alguien esté tocando o moviendo el objeto 3D.
         */
        val boundingBox = CubeNode(
            engine = engine,
            size = modelNode.extents,
            center = modelNode.center,
            materialInstance = materialLoader.createColorInstance(Color.White)
        ).apply {
            isVisible = false // Inicialmente invisible para no distraer.
        }

        // 4. Jerarquía: El ancla sostiene al modelo, y el modelo sostiene a su caja de selección.
        modelNode.addChildNode(boundingBox)
        anchorNode.addChildNode(modelNode)

        // 5. Lógica de selección: Si el usuario edita el objeto, mostramos la caja blanca.
        listOf(modelNode, anchorNode).forEach {
            it.onEditingChanged = { editingTransforms ->
                boundingBox.isVisible = editingTransforms.isNotEmpty()
            }
        }

        return anchorNode
    }

    /**
     * Selecciona un número al azar de la lista disponible.
     * Útil para el modo Quiz, donde el reto debe cambiar constantemente.
     */
    fun randomModel(): Pair<String, String> {
        val randomIndex = (0 until numbers.size).random()
        val number = numbers.keys.elementAt(randomIndex)
        return Pair(number, getModelForNumber(number))
    }
}