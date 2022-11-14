package uni.ase.assignment.shapes

import javafx.scene.canvas.GraphicsContext
import uni.ase.assignment.controllers.LogController

abstract class Shape (
    val log : LogController,
    val g : GraphicsContext,
    var x : Int = 0,
    var y : Int = 0
) {
    abstract fun draw()
}