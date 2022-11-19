package uni.ase.assignment.shapes

import javafx.scene.canvas.GraphicsContext
import uni.ase.assignment.controllers.LogController

abstract class Shape (
    val log : LogController,
    val g : GraphicsContext,
    var x : Double = 0.0,
    var y : Double = 0.0
) {
    abstract var out : String
    abstract fun draw()
}