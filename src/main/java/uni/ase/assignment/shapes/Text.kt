package uni.ase.assignment.shapes

import javafx.scene.canvas.GraphicsContext
import uni.ase.assignment.controllers.LogController

class Text(
    log : LogController,
    g: GraphicsContext,
    x : Int = 0,
    y : Int = 0,
    var text : String = ""
) : Shape(log, g, x, y) {
    override fun draw() {
        TODO("Not yet implemented")
    }
}