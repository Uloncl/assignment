package uni.ase.assignment.shapes

import javafx.scene.canvas.GraphicsContext
import javafx.scene.paint.Color
import uni.ase.assignment.controllers.LogController

class Polygon(
    log : LogController,
    g: GraphicsContext,
    x : Double = 0.0,
    y : Double = 0.0,
    var r : Double = 0.0,
    var n : Int = 3,
    var preset : PolygonPreset = PolygonPreset.NONE,
    var fill: Boolean? = false,
    var strokeCol: Color? = null,
    var fillCol: Color? = null
) : Shape(log, g, x, y) {
    override var out : String = "Polygon drawn at $x, $y"
    protected var xp = mutableListOf<Double>()
    protected var yp = mutableListOf<Double>()

    protected fun generatePoints() {
        var i = 0
        while (i < n) {
            xp.add(i, x + (r * Math.cos(2 * Math.PI * i / n)))
            yp.add(i, y + (r * Math.sin(2 * Math.PI * i / n)))
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

enum class PolygonPreset {
    NONE,
    TRIANGLE,
    SQUARE,
    PENTAGON,
    HEXAGON,
    SEPTAGON,
    OCTAGON,
    NONAGON,
    DECAGON,
    HENDECAGON,
    DODECAGON,
    FIVE_POINTED_STAR,
    SIX_POINTED_STAR
}