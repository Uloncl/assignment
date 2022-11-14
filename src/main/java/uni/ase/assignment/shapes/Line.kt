package uni.ase.assignment.shapes

import javafx.scene.canvas.GraphicsContext
import uni.ase.assignment.controllers.LogController

class Line(
    log : LogController,
    g: GraphicsContext,
    x1 : Int = 0,
    y1 : Int = 0,
    var x2 : Int = 0,
    var y2 : Int = 0
) : Shape(log, g, x1, y1) {
    override fun draw() {
        TODO("Not yet implemented")
    }
}