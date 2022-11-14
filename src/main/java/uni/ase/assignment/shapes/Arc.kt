package uni.ase.assignment.shapes

import javafx.scene.canvas.GraphicsContext
import javafx.scene.shape.ArcType
import uni.ase.assignment.controllers.LogController

class Arc(
    log : LogController,
    g: GraphicsContext,
    x : Int = 0,
    y : Int = 0,
    var w : Int = 0,
    var h : Int = 0,
    var startAngle : Int = 0,
    var endAngle : Int = 0,
    var closingMethod : ArcType = ArcType.OPEN
) : Shape(log, g, x, y) {
    override fun draw() {
        TODO("Not yet implemented")
    }
}