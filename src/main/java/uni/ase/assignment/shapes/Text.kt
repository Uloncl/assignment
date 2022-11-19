package uni.ase.assignment.shapes

import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import uni.ase.assignment.controllers.LogController

class Text(
    log : LogController,
    g: GraphicsContext,
    x : Double = 0.0,
    y : Double = 0.0,
    var text : String = "",
    var fill: Boolean? = false,
    var strokeCol: Color? = null,
    var fillCol: Color? = null
) : Shape(log, g, x, y) {
    override var out : String = "Text drawn at $x, $y"

    override fun draw() {
        val oldStrokeCol = g.stroke
        val oldFillCol = g.fill
        if (strokeCol != null) {
            g.stroke = strokeCol
        }
        if (fillCol != null) {
            g.fill = fillCol
        }
        if (fill == true) {
            out = "filled $out"
            g.fillText(text, x, y)
        }
        g.strokeText(text, x, y)
        if (strokeCol != null) {
            out = "$out stroke colour: $strokeCol"
            g.stroke = oldStrokeCol
        }
        if (fillCol != null) {
            out = "$out fill colour: $fillCol"
            g.fill = oldFillCol
        }
        log.out(out)
    }
}