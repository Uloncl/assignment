package uni.ase.assignment.shapes

import javafx.scene.canvas.GraphicsContext
import uni.ase.assignment.controllers.LogController

class Circle(
    log : LogController,
    g: GraphicsContext,
    x : Int = 0,
    y : Int = 0,
    var radius : Int = 0,
) : Shape(log, g, x, y) {
    override fun draw() {
        TODO("Not yet implemented")
    }
}