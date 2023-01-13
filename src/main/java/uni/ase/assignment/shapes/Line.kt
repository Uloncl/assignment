package uni.ase.assignment.shapes

import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import uni.ase.assignment.controllers.LogController

class Line(
    log : LogController,
    g: GraphicsContext,
    var x1 : Double = 0.0,
    var y1 : Double = 0.0,
    var x2 : Double = 0.0,
    var y2 : Double = 0.0,
    var strokeCol: Color? = null,
) : Shape(log, g, x1, y1) {
    override var out : String = "Line drawn from $x1, $y1 to $x2, $y2"
    override fun draw() {
        println("in Line")
        val oldStrokeCol = g.stroke
        if (strokeCol != null) {
            g.stroke = strokeCol
        }
        g.strokeLine(x1, y1, x2, y2)
        if (strokeCol != null) {
            out = "$out stroke colour: $strokeCol"
            g.stroke = oldStrokeCol
        }
    }
}