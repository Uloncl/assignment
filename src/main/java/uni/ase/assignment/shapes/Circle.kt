package uni.ase.assignment.shapes

import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import uni.ase.assignment.controllers.LogController

class Circle(
    log : LogController,
    g: GraphicsContext,
    x : Double = 0.0,
    y : Double = 0.0,
    var r : Double = 0.0,
    var fill: Boolean? = false,
    var strokeCol: Color? = null,
    var fillCol: Color? = null
) : Shape(log, g, x, y) {
    override var out : String = "circle drawn at $x, $y with radius $r"
    protected var xp = mutableListOf<Double>()
    protected var yp = mutableListOf<Double>()

    protected fun generatePoints() {
        var i = 0
        while (i < 100) {
            xp.add(i, x + (r * Math.cos(2 * Math.PI * i / 100)))
            yp.add(i, y + (r * Math.sin(2 * Math.PI * i / 100)))
            i++
        }
    }

    override fun draw() {
        generatePoints()
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
            g.fillPolygon(xp.toDoubleArray(), yp.toDoubleArray(), 100)
        }
        g.strokePolygon(xp.toDoubleArray(), yp.toDoubleArray(), 100)
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