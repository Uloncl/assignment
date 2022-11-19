package uni.ase.assignment.shapes

import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import uni.ase.assignment.controllers.LogController

class Rectangle(
    log : LogController,
    g: GraphicsContext,
    x : Double = 0.0,
    y : Double = 0.0,
    var w : Double = 0.0,
    var h : Double = 0.0,
    var rounded : Boolean = false,
    var cornerSize : Double = 10.0,
    var fill: Boolean? = false,
    var strokeCol: Color? = null,
    var fillCol: Color? = null
) : Shape(log, g, x, y) {
    override var out : String = "Rectangle drawn at $x, $y with size: $w x $h"

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
            if (rounded && cornerSize != null) {
                g.fillRoundRect(x, y, w, h, cornerSize!!, cornerSize!!)
            } else {
                g.fillRect(x, y, w, h)
            }
        }
        if (rounded && cornerSize != null) {
            g.strokeRoundRect(x, y, w, h, cornerSize!!, cornerSize!!)
        } else {
            g.strokeRect(x, y, w, h)
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