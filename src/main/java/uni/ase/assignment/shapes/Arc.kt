package uni.ase.assignment.shapes

import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import javafx.scene.shape.ArcType
import uni.ase.assignment.controllers.LogController

class Arc(
    log : LogController,
    g: GraphicsContext,
    x : Double = 0.0,
    y : Double = 0.0,
    var w : Double = 0.0,
    var h : Double = 0.0,
    var startAngle : Double = 0.0,
    var endAngle : Double = 0.0,
    var closingMethod : ArcType = ArcType.OPEN,
    var strokeCol: Color? = null
) : Shape(log, g, x, y) {
    override var out : String = "Arc drawn at $x, $y with size: $w x $h, starting at $startAngle, ending at $endAngle, closed by $closingMethod"

    override fun draw() {
        val oldStrokeCol = g.stroke
        if (strokeCol != null) {
            g.stroke = strokeCol
        }
        g.strokeArc(x, y, w, h, startAngle, endAngle, closingMethod)
        if (strokeCol != null) {
            out = "$out stroke colour: $strokeCol"
            g.stroke = oldStrokeCol
        }
    }
}