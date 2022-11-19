package uni.ase.assignment.shapes

import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import uni.ase.assignment.controllers.LogController

class Square(
    log: LogController,
    g: GraphicsContext,
    x: Double = 0.0,
    y: Double = 0.0,
    var size: Double = 0.0,
    var fill: Boolean? = false,
    var rounded: Boolean? = false,
    var cornerSize: Double? = 0.0,
    var strokeCol: Color? = null,
    var fillCol: Color? = null
) : Shape(log, g, x, y) {
    override var out : String = "Square drawn at $x, $y with size $size"

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
            if (rounded == true && cornerSize != null) {
                g.fillRoundRect(x, y, size, size, cornerSize!!, cornerSize!!)
            } else {
                g.fillRect(x, y, size, size)
            }
        }
        if (rounded == true && cornerSize != null) {
            g.strokeRoundRect(x, y, size, size, cornerSize!!, cornerSize!!)
        } else {
            g.strokeRect(x, y, size, size)
        }
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